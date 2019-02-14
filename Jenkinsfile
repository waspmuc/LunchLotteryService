#!groovy
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

node {

    stage 'Checkout'
    checkout scm


    stage('Build') {
        echo 'Building...'
        sh './gradlew clean build'

    }

    stage('Test') {
        echo 'Testing...'
        sh "./gradlew test"
//        step([$class: 'JUnitResultArchiver', testResults: 'build/test-results/**/TEST-*.xml'])
    }

    stage("SonarQube Analysis") {
        withSonarQubeEnv('Gefasoft_Sonar') {
            sh './gradlew sonar'
        }
        retry(5) {
            try {
                timeout(time: 60, unit: 'SECONDS') {
                    echo 'Waiting for Sonarqube to finish analysis.'
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            } catch (err) {
                error "No response from sonarqube in expected time"
            }
        }
    }

    if (isCurrentBranchDevelopBranch()) {
        stage('Upload Artifact') {
            echo 'Creating new pre-release'
            def payload = JsonOutput.toJson(["ref": "${ref}", "description": "${description}", "environment": "${environment}", "required_contexts": []])
            def apiUrl = "https://api.github.com/repos/${getRepoSlug()}/deployments"
            def response = sh(returnStdout: true, script: "curl -X POST ${apiUrl} -H \"Content-Type: application/json\" -d \'{\"tag_name\":\"v3.9.8\",\"target_commitish\":\"master\",\"name\":\"v3.9.8\",\"body\":\"Devil in the detail\",\"draft\":false,\"prerelease\":false}\'").trim()
            def jsonSlurper = new JsonSlurper()
            def data = jsonSlurper.parseText("${response}")
            println(data.message)
        }
    }
}
