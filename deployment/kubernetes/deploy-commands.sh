#First Deploy
k create namespace startmeup

k apply -f ./persistent --recursive

k apply -f ./startmeup --recursive


# Create Argo-CD Continuous Delivery definition
cd /argo-cd
k apply -f startmeup.yaml

