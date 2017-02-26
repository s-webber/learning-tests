I created this project to allow me to investigate using [Geb](http://www.gebish.org/).

It is a work-in-progress. For a better example of using Geb please see the [geb-example-gradle](https://github.com/geb/geb-example-gradle) project.

Below are the steps I took to use it from the [Spring Tool Suite](https://spring.io/tools) development environment:

1. Download, install and launch "Spring Tool Suite".

Note: If using Windows then you may get a "Path too long" error when attempting to unzip `spring-tool-suite-3.8.3.RELEASE-e4.6.2-win32-x86_64.zip` using the "Extract All..." option.
A workaround for this is to unzip the file using `jar`. e.g.: `C:\>jar xf spring-tool-suite-3.8.3.RELEASE-e4.6.2-win32-x86_64.zip`

2. Enable Groovy extensions:
* From the "Help" section of the top menu bar select "Dashboard".
* Select the "IDE Extensions" option under the "Manage" section.
* Install both "Groovy 2.4 Compiler for Groovy-Eclipse" and "Groovy-Eclipse".
* From the "Help" section of the top menu bar select "Eclipse Marketplace...".
* Install "Buildship Gradle Integration 2.0".

3. Open project:
* From the "File" section of the top menu bar select "New" and "Create a Java Project".
* Enter the directory of the project to be opened into the "Location:" text input (you may first need to uncheck "Use default location").

4. Configure project:
* From the "Package Explorer" view, right-click on the project's directory and select "Configure" and then "Convert to Groovy Project".
* From the "Package Explorer" view, right-click on the project's directory and select "Configure" and then "Add Gradle Nature".
---

Links:

* [geb-example-gradle](https://github.com/geb/geb-example-gradle)
* [Spring Tool Suite Downloads](https://spring.io/tools/sts/all)
* [The Book Of Geb](http://www.gebish.org/manual/current/)
* [Spock Framework Reference Documentation](http://spockframework.org/spock/docs/1.1-rc-3/index.html)
* [Adding the Wrapper to a project](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:wrapper_generation)
