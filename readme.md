# Guide to Micronaut Kubernetes Demo Project [![Twitter](https://img.shields.io/twitter/follow/piotr_minkowski.svg?style=social&logo=twitter&label=Follow%20Me)](https://twitter.com/piotr_minkowski)

In this project I'm demonstrating you the most interesting features of [Micronaut Kubernetes Project](https://micronaut-projects.github.io/micronaut-kubernetes/snapshot/guide/) for integration between Micronaut and Kubernetes API.

## Getting Started 
Currently you may find here some examples of microservices implementation using different projects from Micronaut. All the examples are divided into the branches and described in a
 separated articles on my blog. Here's a full list of available examples:
1. Using **Micronaut Kubernetes** for integration with Kubernetes discovery, `ConfigMaps` and `Secrets`. A source code is available in
 the branch [master](https://github.com/piomin/sample-micronaut-kubernetes/tree/master). A detailed guide may be find in the following article: [Guide to Micronaut Kubernetes
 ](https://piotrminkowski.com/2020/01/07/guide-to-micronaut-kubernetes/)
 
### Usage
To successfully run example applications you need to have:
1. JDK11+ as a default Java on your machine
2. Maven 3.5+ available under PATH
3. Minikube (I tested on version `1.6.1`)
4. Skaffold available under PATH

## Architecture

Our sample microservices-based system consists of the following modules:
- **employee-service** - a module containing the first of our sample microservices that allows to perform CRUD operation on Mongo repository of employees
- **department-service** - a module containing the second of our sample microservices that allows to perform CRUD operation on Mongo repository of departments. It communicates with employee-service. 
- **organization-service** - a module containing the third of our sample microservices that allows to perform CRUD operation on Mongo repository of organizations. It communicates with both employee-service and organization-service.

The following picture illustrates the architecture described above including Kubernetes objects.

<img src="https://piotrminkowski.files.wordpress.com/2020/01/guide-to-micronaut-kubernetes-architecture.png" title="Architecture1">
