# Start-Me-Up

This is a sample template project that uses [Quarkus](https://quarkus.io/) Java Framework.

Following features have been implemented:
* OpenAPI documentation and SwaggerUI
* PostgreSQL DB
* JDBI for database interaction (without any ORM like Hibernate)
* Integration Tests
* Liquibase DB migrations
* Multi arch docker image builds using buildx
* Integrated with GitHub CI to run tests and build/push container images
* Deploy on a Kubernetes Cluster


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
export QUARKUS_CONTAINER_IMAGE_PASSWORD=<REPO_PASSWORD>
```

* If these variables are not provided the following build command will give error while uploading the image, but image will be available in local docker desktop repo.

### Build and push Image

```shell
./mvnw clean package -Dquarkus.container-image.push=true
```


## Run application using docker-compose

An example docker compose is given in `/deployment/docker-compose` directory.
```shell
cd deployment/docker-compose
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



## Deploy Application on Kubernetes

### Prerequisites:
* A fully configured kubernetes cluster with following features:
 * Nginx Ingress Controller installed and configured
 * cert-manager installed and configured
 * A `default` StorageClass installed and configured for PersistentVolumeClaims

This repo: [openstack-kubernetes](https://github.com/mumeraltaf/openstack-kubernetes) can help set up all of the above on OpenStack using Terraform.
If using other Cloud provider some changes to configs may be needed, mainly to the Terraform providers, K8s StorageClass and Ingresses.

### Deploy:

Once you have a functional K8s cluster, configure your deployment as follows:
* Set your own secret values in `deployment/kubernetes/startmeup/secret/startmeup.yaml`, example values are provided
* Update `deployment/kubernetes/startmeup/ingress/ingress.yaml` with your own host name

Then deploy:
```shell
cd /deployment/kubernetes/
kubectl apply startmeup/ns
kubectl apply -f startmeup/ --recursive
```

Wait a couple of minutes for all services to come up and TLS configured, then get the ingress host using:

```shell
kubectl get ingress -n startmeup   
```

The app will be available on the `startmeup` host, and SwagerUI will be available on `https://<hostname>/q/swagger-ui`


## Related Guides

- [Official Quarkus Guides](https://quarkus.io/guides/)