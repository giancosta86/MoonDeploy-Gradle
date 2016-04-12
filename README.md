# MoonDeploy - Gradle

*MoonDeploy for Gradle*


## Introduction

*MoonDeploy - Gradle* is a plugin for Gradle that transparently integrates [MoonDeploy's Ant tasks](https://github.com/giancosta86/MoonDeploy-Ant) into the Gradle build process, to automate the creation of [application descriptors](https://github.com/giancosta86/moondeploy/wiki/App-descriptor:-Version-3).

[MoonDeploy](https://github.com/giancosta86/moondeploy) is designed to make it easy to write app descriptors - as they are in plain JSON format - but you might want to inject a few variables (app name, version, description, ...) from your Gradle script - and the plugin simplifies such activity.

More precisely, the plugin provides an **ant.moonDeploy** Ant task definition that can be called from within any Gradle task - as in the example below.


## Installation

To add *MoonDeploy-Gradle* to your project, just include the following lines at the beginning of your build script:

```
buildscript {
    repositories {
        maven {
            url "https://dl.bintray.com/giancosta86/Hephaestus"
        }
    }

    dependencies {
        classpath "info.gianlucacosta.moondeploy:moondeploy-gradle:3.0"
    }
}

apply plugin: "info.gianlucacosta.moondeploy"
```


## Usage

The simplest way to employ the plugin is to:

0. Create a dedicated task in your project
0. Make the **assemble** task (or another predefined task) depend on it
0. In the task body, use **ant.moonDeploy**

What follows is a possible and quite common solution pattern that can be added to the build script of an application (in the example, named *MyApp*) based on the [Gradle application plugin](https://docs.gradle.org/current/userguide/application_plugin.html):

```groovy
task createAppDescriptor {
    dependsOn(distZip)
} << {
  ant.moonDeploy(
    //If this URL is on GitHub and ends with /releases/latest, MoonDeploy will
    //automatically retrieve the latest version when the descriptor is run
    baseURL: "THE_BASE_URL_OF_YOUR_APPLICATION",

    //If missing, "App.moondeploy" is employed
    descriptorFileName: "MyApp.moondeploy"

    name: "MyApp",
    version: project.version,

    description: project.description,
    publisher: "The application publisher",

    //This is frequently needed when using Gradle's application plugin
    skipPackageLevels: 1,

    iconPath: "mainIcon.png"
    ) {
      //When the supported OS are the 3 OS on which MoonDeploy runs, we can just
      //omit these 3 lines and have the same actual effect
      supportedOS(name: "windows")
      supportedOS(name: "linux")
      supportedOS(name: "darwin")

      commandLine {
        param("bash")
        param("bin/MyApp")
      }

      pkg(name: "MyApp-${project.version}.zip")

      os(
        name: "windows",
        iconPath: "mainIcon.ico"
      ) {
        commandLine {
          param("bin\\MyApp.bat")
        }
      }
    }
}

tasks["assemble"].dependsOn("createAppDescriptor")
```


## Further references

* [MoonDeploy](https://github.com/giancosta86/moondeploy)

* [MoonDeploy - Application descriptor format](https://github.com/giancosta86/moondeploy/wiki/App-descriptor:-Version-3)
