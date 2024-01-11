# Getting Started

## Preconditions
1) Java 17
2) Maven 3.9.2
3) Docker with Docker Compose

## Installation
To install project you need to follow below steps:
1) `mvn package`
to generate .jar file inside target folder
2) `docker build -t com.marcinski/task_postgres Dockerfile_db .`
to generate a new image of a postgres container where the taskdb Database will be created automatically  
3) `docker build --build-arg JAR_FILE=target/\*.jar -t com.marcinski/taskprocessor -f Dockerfile_app .`
to generate a new image of a task-processor app
4) `docker compose up -d`
to run whole stack
### Inspection
-`docker logs --follow taskprocessor`
to check the application logs

-`http://localhost:5050`
to login into pgadmin where you can check how db looks like

## Development
`java -jar ./target/task-processor-{version}.jar --spring.profiles.active=dev`
To run project locally for development (without docker and postgres - embedded h2 db will be used)

# API Usage Instructions
## Task Creation

To create a new task, make a POST request to the endpoint /tasks with a JSON payload in the request body. The payload should conform to the CreateTaskRequest structure:

POST /tasks
Content-Type: application/json

`
{
"input": "ABCD",
"pattern": "ADC"
}
`

Example using cURL:

`
curl -X POST -H "Content-Type: application/json" -d '{"input": "ABCD", "pattern": "ABC"}' http://localhost:8080/tasks
`

If the request is successful, the API will return a UUID representing the newly created task.

## Task Retrieval

To retrieve information about a specific task, make a GET request to the endpoint /tasks/{taskUuid} by replacing {taskUuid} with the UUID of the task you want to retrieve:


GET /tasks/{taskUuid}

Example using cURL:

`
curl http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440000
`

If the task exists, the API will respond with details about the task in the response body.

## List Tasks

To retrieve a paginated list of tasks, make a GET request to the endpoint /tasks. You can provide optional query parameters page and size to control the pagination:


GET /tasks?page=1&size=10

    page (optional): The page number (default is 0).
    size (optional): The number of tasks per page (default is 30).

Example using cURL:

`
curl http://localhost:8080/tasks?page=1&size=10
`

The API will respond with a paginated list of tasks based on the provided parameters.