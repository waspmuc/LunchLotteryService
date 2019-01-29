#!groovy

node {

    stage 'Checkout'
    checkout scm


    stage('Build') {
        echo 'Building....'
        sh './gradlew clean build'

    }

    stage('Test') {
        echo 'Testing....'
        sh "./gradlew clean test"
        step([$class: 'JUnitResultArchiver', testResults: 'build/test-results/**/TEST-*.xml'])
    }

    stage("SonarQube Analysis") {
        withSonarQubeEnv('Gefasoft_Jenkins') {
            sh './gradlew clean sonar'
        }
        retry(3) {
            timeout(time: 5, unit: 'SECONDS') {

                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
            }

        }
    }

    stage('Deploy') {
        echo 'Deploying....'
    }
}
