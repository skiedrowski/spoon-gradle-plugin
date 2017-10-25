# spoon-gradle-plugin

[![Apache License 2.0](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Maven Central](https://img.shields.io/maven-central/v/com.github.mictaege/spoon-gradle-plugin.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.mictaege%22%20AND%20a%3A%22spoon-gradle-plugin%22)

This repository contains a gradle plugin for the Java analysis and transformation framework [Spoon](http://spoon.gforge.inria.fr/index.html).

> **Note** that there is also an official gradle plugin provided by [SpoonLabs](https://github.com/SpoonLabs/spoon-gradle-plugin).
> The difference between the two plugins is that _SpoonLabs_ plugin focuses on analysis and transformation of Java production files,
> while this plugin focuses on analysis and transformation of Java production _and_ test files. Another difference is that _SpoonLabs_ plugin
> supports Android development, which is not supported by this plugin.

## Prerequisites

The _spoon-gradle-plugin_ requires the Gradle Java Plugin and assumes the default Source Sets structure for Java projects: 

```Groovy
sourceSets {
    main {
        java {
            srcDir 'src/main/java'
        }
    }
    test {
        java {
            srcDir 'src/test/java '
        }        
    }
}
```

## Installation

Add a _buildscript_ section to your _build.gradle_ file and define the required classpath dependencies to the _spoon-gradle-plugin_ and to _org.eclipse.jdt.core_.

**Note** that the _spoon-gradle-plugin_ currently supports [Spoon](http://spoon.gforge.inria.fr/index.html) version _5.6.0_, which requires _org.eclipse.jdt.core_ version _3.12.2_.      

```Groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath group: 'com.github.mictaege', name: 'spoon-gradle-plugin', version:'x.x'
        classpath group: 'org.eclipse.jdt', name: 'org.eclipse.jdt.core', version: '3.12.2'
    }
}
```

Then apply the spoon plugin:

```Groovy
apply plugin: 'spoon'
```

## Adding Spoon Processors

In order to add your Spoon processors you have to add an additional dependency in the _buildscript_ section to the library that contains your processors 

```Groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath group: 'com.github.mictaege', name: 'spoon-gradle-plugin', version:'x.x'
        classpath group: 'org.eclipse.jdt', name: 'org.eclipse.jdt.core', version: '3.12.2'
        classpath group: 'com.github.mictaege', name: 'spoon-processors', version: '1.0'
    }
}
```

Then you have to configure the processors with the fully qualified name

```Groovy
spoon{
    processors = ['com.github.mictaege.spoon_processors.CheckNotNullProcessor']
}
```

Now the configured processors are being executed every time bevore _compileJava_ and _compileTestJava_ are executed.  

The sources generated by _Spoon_ will end up in _./build/generated-sources/spoon_ and the classes compiled from that generated sources
will end up in  _./build/classes/_

```
build
├───classes
│   ├───main
│   │   └───...
│   └───test
│       └───...
├───generated-sources
│   └───spoon
│       ├───main
│       │   └───...
│       └───test
│           └───...

```


## Further settings

Within the _spoon_ section you can also set the following flags 
- _buildOnlyOutdatedFiles_: Set Spoon to build only the source files that have been modified since the latest source code generation, for performance purpose. (default: true) 
- _compliance_: Java source code compliance level (1,2,3,4,5, 6, 7 or 8). (default: 8)

```Groovy
spoon{
    processors = ['com.github.mictaege.spoon_processors.CheckNotNullProcessor']
    buildOnlyOutdatedFiles false
    compliance 7
}
```

## Complete build script template

```Groovy
apply plugin: 'java'
apply plugin: 'spoon'

group 'xxx.xxxx.xxxx'
archivesBaseName = project.name
version '1.0-SNAPSHOT'

sourceCompatibility = 1.8

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath group: 'com.github.mictaege', name: 'spoon-gradle-plugin', version:'x.x'
        classpath group: 'org.eclipse.jdt', name: 'org.eclipse.jdt.core', version: '3.12.2'
        classpath group: 'xxx.xxxx.xxxx', name: 'xxx-processors', version: 'x.x'
    }
}

repositories {
    mavenCentral()
}

spoon{
    processors = ['xxx.xxxx.xxxx.XXXXProcessor']
}
```
