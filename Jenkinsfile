node {

    stage 'Checkout'
    checkout scm

    stage 'Gradle Static Analysis'

    //withSonarQubeEnv {
        //sh "./gradlew clean sonarqube"
    //}


    stage('Build') {
        echo 'Building....'
        sh "./gradlew clean build"

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
