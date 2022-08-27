# Start-Me-Up

This is a sample template project that uses [Quarkus](https://quarkus.io/) Java Framework.

Following features have been implemented:
* OpenAPI documentation and SwaggerUI
* PostgreSQL DB
* JDBI for database interaction (without any ORM like Hibernate)
* Integration Tests
* Liquibase DB migrations
* Multi arch docker image builds using buildx


## Running the application in dev mode

* I use IntelliJ IDEA for development, follow these guides to set up your IDE: [https://quarkus.io/guides/tooling](https://quarkus.io/guides/tooling)
* Once set up a run configuration will automatically be created OR create once yourself and just hit run or debug on IDE


## Build and push the Container image

I have configured Quarkus to build Docker images for following platforms
* linux/amd64
* linux/arm64

### Setup Container Repository 
To use a remote docker repository set following ENV variables:

```shell
export QUARKUS_CONTAINER_IMAGE_REGISTRY=<REPO_URL>  # e.g. docker.io
export QUARKUS_CONTAINER_IMAGE_USERNAME=<REPO_USERNAME>
export QUARKUS_CONTAINER_IMAGE_USERNAME=<REPO_PASSWORD>
```

* If these variables are not provided the following build command will give error while uploading the image, but image will be available in local docker desktop repo.

### Build and push Image

```shell
./mvnw clean package -Dquarkus.container-image.push=true
```


## Run application using docker-compose

An example docker compose is given in `/docker-compose` directory.
```shell
cd docker-compose
```
* Check/Update the provided `.env` file to change any parameters if needed like DB username/password etc.
* Source the `.env` file
* Make sure the image repo and name for the `fruits` container matches to the image that built in the previous step.
* Start the compose stack
```shell
docker-compose up -d
```

* API application will be running at `http://localhost:8080`.
* Check the Swagger UI documentation at [http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)

## Related Guides

- [Official Quarkus Guides](https://quarkus.io/guides/)