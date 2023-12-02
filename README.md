# connect
a backend service developed by Spring Boot

## run the application

### step 1
have your docker, docker-compose ready

```
docker: https://docs.docker.com/desktop/install/mac-install/
docker-compose: brew install docker-compose (https://formulae.brew.sh/formula/docker-compose)
```

### step 2
run docker-compose to set up containers of mysql datavase and the backend service (indicated in Dockerfile)

```cd docker-compose && docker-compose up --build```

### step 3
interact with the backend-service where API spec documentations can be found at http://localhost:8080/swagger-ui.html

```e.g. http://localhost:8080/api/connect/v1/comment/1```
