# payslip-demo-microservice

## App Instruction
### Pre-requisite
Make sure development environment is set-up with following tools: jdk17, docker, aws cli.
### For Test Locally

- Docker Build Image
`docker build -t payslip-demo-microservice .`
- Check Docker Image
`docker images`
- Run Docker Image Locally
`docker run -p 8080:8080 -t payslip-demo-microservice`
- Note: Generate JAR executable is already included in Dockerfile. For manual generation just use `./mvnw clean package`, then execute the file by `java -jar target/payslipDemo-0.0.1.jar`
- Use any tools (For example, Postman) to access the api by creating a POST request to [localhost:8080/demo/payslips]()
- Payload is like:
`[
  {
  "firstName":"David",
  "lastName":"Rudd",
  "annualSalary":60050,
  "paymentMonth":2,
  "superRate":0.09
  },
  {
  "firstName":"Ryan",
  "lastName":"Chen",
  "annualSalary":120000,
  "paymentMonth":4,
  "superRate":0.1
  }
  ]
`
- If success, the api should return 200 with following response:
`[
  {
  "employee": {
  "firstName": "David",
  "lastName": "Rudd",
  "annualSalary": 60050,
  "paymentMonth": 2,
  "superRate": 0.09
  },
  "fromDate": "01 February",
  "toDate": "28 February",
  "grossIncome": 5004,
  "incomeTax": 922,
  "superannuation": 450,
  "netIncome": 4082
  },
  {
  "employee": {
  "firstName": "Ryan",
  "lastName": "Chen",
  "annualSalary": 120000,
  "paymentMonth": 4,
  "superRate": 0.1
  },
  "fromDate": "01 April",
  "toDate": "30 April",
  "grossIncome": 10000,
  "incomeTax": 2669,
  "superannuation": 1000,
  "netIncome": 7331
  }
  ]
`

### For Test with AWS 

- This project is also deployed on AWS ECS, current public IP address is [13.210.38.27:8080](http://13.210.38.27:8080)
- Like testing locally, creat any POST request to [13.210.38.27:8080/demo/payslips]() with same payload as above


### CI/CD Workflow
This project supports automatic deployment to AWS ECS, any push to the main branch will trigger Github workflow.
(For more details please look at below section and the code in /.github/workflows/aws.yml & /cdk)

## Design & Thoughts

### Assumptions

- All inputs are validated.
- By passing **paymentMonth** in the request payload, the api assumes it refers to the month of current year, for example, "1" should refer to January 2022.
- All calculation results are rounded to the whole dollar. If >= 50 cents round up to the next dollar increment, otherwise round down.
- Database is not required in this project.

### On App Development

- The app uses Java & Spring Framework to develop the RestAPI.
- The app also uses Docker to contain the microservice.
- Since DB is not required in this project, only 3 layers are needed in the app: **model, service, controller**.
- **Model layer** is for defining the object class, in this case, abstract request payload as **Employee** and response as **Payslip**.
- **Service Layer** is for handling the task logic, including **calculation of income tax, netincome and superannuation** by providing an employee object.
- **Controller Layer** is for handling the HTTP Request, which obtains the request payload and returns the calculated result provided by service layer as JSON.
- Unit Tests for both service layer and controller layer are defined in /test folder.

### On AWS Deployment

- To deploy the service to AWS, IAM, ECR, ECS are used.
- IAM is required to create the deploy user and add all sorts of permissions related to ECR & ECS to this user.
- ECR is required to store the docker image and provide reference for ECS to access.
- ECS is used to serve the microservice by running task definition in the container instance.

### on CI/CD

- To implement CI/CD on this project, Github workflow & AWS CDK are used.
- Github workflow is used to deploy automatically by pushing new changes to main branch of the repo.
- The workflow involves building docker image for the app, deploying image to AWS ECR, and deploying ECS with the latest image using CDK.
- AWS CDK is used to deploy the service to ECS by setting up vpc, security group, cluster, and attaching the uploaded image in ECR.


## Reference Documentation
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#web)
* [Docker Web](https://docs.docker.com/ci-cd/github-actions/)
* [AWS ECS Deployment Steps](https://docs.aws.amazon.com/codedeploy/latest/userguide/deployment-steps-ecs.html)
* [AWS CDK](https://docs.aws.amazon.com/cdk/api/v1/)
