/*§
  ===========================================================================
  MoonDeploy - Gradle
  ===========================================================================
  Copyright (C) 2016 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.moondeploy.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class MoonDeployPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.repositories {
            maven {
                url "https://dl.bintray.com/giancosta86/Hephaestus"
            }

            mavenCentral()
        }

        project.configurations {
            moonDeployAnt
        }

        project.dependencies {
            moonDeployAnt 'info.gianlucacosta.moondeploy:moondeploy-ant:3.2'
            moonDeployAnt 'javax.json:javax.json-api:1.0'
            moonDeployAnt 'org.glassfish:javax.json:1.0.4'
        }

        String moonDeployAntPath = project.configurations.moonDeployAnt.asPath

        project.ant.taskdef(name: 'moonDeploy',
                classname: 'info.gianlucacosta.moondeploy.ant.AppDescriptor',
                classpath: moonDeployAntPath)
    }
}
