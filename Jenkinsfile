#!groovy

node {

    stage 'Checkout'
    checkout scm


    stage('Build') {
        echo 'Building....'
        sh "./gradlew clean build"

    }

    stage("SonarQube Analysis") {
        node {
            withSonarQubeEnv('Gefasoft_Jenkins') {
                sh 'gradle clean sonar'
            }
        }
    }

    stage("Quality Gate") {
        timeout(time: 1, unit: 'HOURS') {
            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
                error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
        }
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
