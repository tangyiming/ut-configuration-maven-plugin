# Unit Test Coverage Report Configuration Helper Maven Plugin

## Description

### Github

https://github.com/tangyiming/ut-configuration-maven-plugin

### Main Feature
- Help developers to add and config the necessary plugins to the pom files for unit test coverage reports

### Support JDK Version of Project
- Java 8 and above

### support maven structure:
```
    - single module

    - multiple module (parent module and child module)

    - multiple module (parent module and child module and grandchild module and so on)
```

Diff coverage report will only generated in the aggregate report module when your project contains multiple modules.

## Goals Description

### add-coverage-config
Check if the necessary plugins are configured in the pom.xml, and add the necessary plugins if not configured **incrementally**. 

Incrementally means that the plugin will not add the plugin if it is already configured in the pom.xml no matter the version or other configurations.

Plugin will also add a report module called "aggregate-report" if your project contains multiple modules. 

- If your project already contains a module called "aggregate-report", the plugin will not add the module again.

- If your project already contains a report module but not called "aggregate-report", the plugin will add a new report module called "aggregate-report", you should take care of this situation.

The aggregate-report will add all the jar type sub modules as dependencies to the pom.xml, and add an empty diff.txt file inside the module for diff coverage plugin. Without the file, the diff coverage plugin will not work.

The target plugins are:
- maven-surefire-plugin 
- maven-surfire-report-plugin
- [jacoco-maven-plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
- [diff-coverage-maven-plugin](https://github.com/SurpSG/diff-coverage-maven-plugin)

We will add the basic configuration for these plugins, and you can add more configuration if you need.

### check-config
Check if the expected reports are generated.

### other goals you can add...
You can add other goals, for example, you can add the goal to report the coverage data to your server. 
And the server can decide whether to fail the build or not.
I have provided an api and diff coverage data parser util for you to use.

## Phase of Goal
### add-coverage-config
> LifecyclePhase.COMPILE
### check-config
> LifecyclePhase.PREPARE_PACKAGE

after test phase, before package phase

## Installation
Add the plugin dependency into the build section of your project root pom.xml

```xml
<plugin>
    <groupId>com.tangyiming</groupId>
    <artifactId>ut-configuration-maven-plugin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <executions>
        <execution>
            <id>add-coverage-config</id>
            <goals>
                <goal>add-coverage-config</goal>
            </goals>
        </execution>
        <execution>
            <id>check-config</id>
            <goals>
                <goal>check-config</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Development and Debug
### Development
1. Clone the project to your local
2. Add or modify the goals
3. Run `mvn clean install` to install the plugin to your local maven repository
### Local Debug
1. Add the plugin dependency into the build section of your testing project's root pom.xml
2. Install maven by `brew install maven`
3. Run `mvnDebug xxx any goals...` in your test project to start the debug mode, the maven will wait for the remote debug connection
4. Add Remote JVM Debug Configuration in your IDE, and set the port to 8000 (maven default port fro remote debug)
5. Add the breakpoints in your plugin code, then click Debug
6. The debug mode will start when the maven compile phase is triggered
7. You also need start the server in your local, and you should not use port 8000, you also need change the server address and port in the plugin configs

That's all, enjoy your debug.