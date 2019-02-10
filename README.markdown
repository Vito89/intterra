JAX-RS/Jersey application for Revolut
========================================

A project of a Java web application is based on:

* [JAX-RS API](https://java.net/projects/jax-rs-spec) — Java API for RESTful services
* [Jersey](https://jersey.java.net/) — JAX-RS implementation
* [Gradle](http://www.gradle.org/) — Build Automation tool

The application is written in style TDD, using Jersey framework.
The data storage layer was left to the further discretion of the developers
 and uses the ordinary type of implementing Map.

This approach eliminated the writing of redundant API methods.


## Building and running

### Building WAR file

```
./gradlew --daemon war
```

### Running on Jetty for development

```
./gradlew --daemon jettyRun
```

### Running tests

```
./gradlew --daemon test
```
