import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as iam from 'aws-cdk-lib/aws-iam';
import * as ec2 from 'aws-cdk-lib/aws-ec2';
import * as ecr from 'aws-cdk-lib/aws-ecr';
import * as ecs from 'aws-cdk-lib/aws-ecs';
import * as ecsp from 'aws-cdk-lib/aws-ecs-patterns';
import * as codepipeline from 'aws-cdk-lib/aws-codepipeline';
import * as codepipeline_actions from 'aws-cdk-lib/aws-codepipeline-actions';
import { DockerImageAsset } from "aws-cdk-lib/aws-ecr-assets";


export class CdkStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // *** Create a new VPC with single NAT Gateway ***
    const vpc = new ec2.Vpc(this, 'ecs-cdk-vpc', {
      cidr: '10.0.0.0/16',
      natGateways: 1,
      maxAzs: 3
    });

    // *** Create a new Cluster ***
    const clusterAdmin = new iam.Role(this, 'AdminRole', {
      assumedBy: new iam.AccountRootPrincipal()
    });

    const cluster = new ecs.Cluster(this, "ecs-cluster", {
      vpc: vpc
    });

    const logging = new ecs.AwsLogDriver({
      streamPrefix: "ecs-logs"
    });

    const taskRole = new iam.Role(this, `ecs-taskRole-${this.stackName}`, {
      roleName: `ecs-taskRole-${this.stackName}`,
      assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com')
    });

    // *** ECS Constructs ***
    const executionRolePolicy =  new iam.PolicyStatement({
      effect: iam.Effect.ALLOW,
      resources: ['*'],
      actions: [
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ]
    });

    const taskDef = new ecs.FargateTaskDefinition(this, "ecs-taskdef", {
      taskRole: taskRole
    });

    taskDef.addToExecutionRolePolicy(executionRolePolicy);

    const container = taskDef.addContainer('payslip-app', {
      image: ecs.ContainerImage.fromEcrRepository(ecr.Repository.fromRepositoryName(this, 'payslip-demo-microservice', 'payslip-demo-microservice')),
      memoryLimitMiB: 256,
      cpu: 256,
      logging
    });

    container.addPortMappings({
      containerPort: 8080,
      protocol: ecs.Protocol.TCP
    });

    const fargateService = new ecsp.ApplicationLoadBalancedFargateService(this, "ecs-service", {
      cluster: cluster,
      taskDefinition: taskDef,
      publicLoadBalancer: true,
      desiredCount: 1,
      listenerPort: 80,
      assignPublicIp: true,
      // taskImageOptions: {
      //   image: ecs.ContainerImage.fromEcrRepository(ecrRepo, "latest"),
      //   containerName: 'payslip-app',
      //   containerPort: 8080
      // }
    });

    const scaling = fargateService.service.autoScaleTaskCount({ maxCapacity: 6 });
    scaling.scaleOnCpuUtilization('CpuScaling', {
      targetUtilizationPercent: 10,
      scaleInCooldown: cdk.Duration.seconds(60),
      scaleOutCooldown: cdk.Duration.seconds(60)
    });

    // *** PIPELINE CONSTRUCTS ***
    // const deployAction = new codepipeline_actions.EcsDeployAction({
    //   actionName: 'DeployAction',
    //   service: fargateService.service,
    //   // imageFile: new codepipeline.ArtifactPath(buildOutput, `imagedefinitions.json`)
    // });

    // *** PIPELINE STAGES ***
    // new codepipeline.Pipeline(this, 'MyECSPipeline', {
    //   stages: [
    //     // {
    //     //   stageName: 'Source',
    //     //   actions: [sourceAction],
    //     // },
    //     // {
    //     //   stageName: 'Build',
    //     //   actions: [buildAction],
    //     // },
    //     // {
    //     //   stageName: 'Approve',
    //     //   actions: [manualApprovalAction],
    //     // },
    //     {
    //       stageName: 'Deploy-to-ECS',
    //       actions: [deployAction],
    //     }
    //   ]
    // });

    //OUTPUT
    new cdk.CfnOutput(this, 'LoadBalancerDNS', { value: fargateService.loadBalancer.loadBalancerDnsName });

    // @ts-ignore
    // return fargateService.service;
  }
}
