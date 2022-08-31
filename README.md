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
* Metrics reporting
* Argo-CD (Continuous Deployment)

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


## Deploy application on local dev Cluster
### Prerequisites:
* Install [kind](https://kind.sigs.k8s.io/), instead of kind you can also use any other similar utility to spin up local dev k8s clusters like minikube or docker-desktop.

### Deploy and run
* Start the dev cluster (a config is provided for specific k8s version)
```shell
cd deployment/kubernetes
kind create cluster --name dev-startmeup --config kind/config.yaml

# check status of nodes
k get nodes
```
* Once cluster is up and running, deploy the app
```shell
k create namespace startmeup
k apply -f startmeup/ --recursive 
```
* Monitor the pods to come-up in ready state
```shell
k get pods -n startmeup
```

* Once all pods are ready, port forward to the api
```shell
k port-forward service/startmeup-api -n startmeup 8080:80
```

* Access Swagger docs at `localhost:8080/q/swager-ui`

* Once done, clean up by deleting the cluster
```shell
kind delete cluster --name dev-startmeup
```

## Deploy application on remote Kubernetes Cluster

### Prerequisites:
* A fully configured kubernetes cluster with following features:
 * Nginx Ingress Controller installed and configured
 * cert-manager installed and configured
 * A `default` StorageClass installed and configured for PersistentVolumeClaims

This repo: [openstack-kubernetes](https://github.com/mumeraltaf/openstack-kubernetes) can help set up all of the above on OpenStack using Terraform.
If using other Cloud provider some changes to configs may be needed, mainly to the Terraform providers, K8s StorageClass and Ingresses.

### Deploy by registering with Argo-CD:

Once you have a functional K8s cluster, configure your deployment as follows:
* Set your own secret values in `deployment/kubernetes/startmeup/secret/startmeup.yaml`, example values are provided
* Update `deployment/kubernetes/startmeup/ingress/ingress.yaml` with your own host name

Then simply register the app with pre-configured Argo-CD:
```shell
cd /deployment/kubernetes/argo-cd
kubectl apply -f startmeup.yaml
```

If you have kubectl access to the cluster, you can check on the progress of Argo CD piepline using the web-dashboard:
```shell
# get password for the UI interface
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo

# port-forward to the ArgoCD UI Dashboard, i.e. localhost/8080
kubectl port-forward svc/argocd-server -n argocd 8080:443
```

Wait a couple of minutes for all services to come up and TLS configured, then get the ingress host using:

```shell
kubectl get ingress -n startmeup   
```

The app will be available on the `startmeup` host, and SwagerUI will be available on `https://<hostname>/q/swagger-ui`


## Related Guides

- [Official Quarkus Guides](https://quarkus.io/guides/)