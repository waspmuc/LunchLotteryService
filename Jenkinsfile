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
    }
    stage('Deploy') {
        echo 'Deploying....'
    }
}
