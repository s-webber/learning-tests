# Maven Code Coverage, Javadoc and Static Analysis Example

This project provides an example of integrating [Maven](https://maven.apache.org/) with [Checkstyle](http://checkstyle.sourceforge.net/), [FindBugs](http://findbugs.sourceforge.net/), [Jacoco](http://eclemma.org/jacoco/), [Javadoc](https://en.wikipedia.org/wiki/Javadoc) and [PMD](https://pmd.github.io/).

To run the Jacoco tool:

```
mvn clean package
```

The code coverage report will then be located at:

```
target/site/jacoco/index.html
```

To run the Checkstyle, FindBugs, Javadoc and PMD tools:

```
mvn site
```

The Javadoc and static analysis reports will then be accessible from:

```
target/site/project-reports.html
```

The configuration for Checkstyle and PMD is located in:

```
src/test/resources
```
