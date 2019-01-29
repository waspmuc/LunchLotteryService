#!groovy

node {

    stage 'Checkout'
    checkout scm


    stage('Build') {
        echo 'Building....'
        sh './gradlew clean build'

    }

    stage("SonarQube Analysis") {
        withSonarQubeEnv('Gefasoft_Jenkins') {
            sh './gradlew clean sonar'
        }
        timeout(time: 1, unit: 'MINUTES') {
            sh 'sleep 10s'

            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
                error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
        }
    }

    stage("Quality Gate") {

    }


    stage('Test') {
        echo 'Testing....'
        sh "./gradlew clean test"
        step([$class: 'JUnitResultArchiver', testResults: 'build/test-results/**/TEST-*.xml'])
    }
    stage('Deploy') {
        echo 'Deploying....'
    }
}
