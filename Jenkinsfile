pipeline {
    agent any

    environment {
        SONARQUBE_URL = 'http://192.168.50.4:9000'  // Remplacez par l'URL de votre serveur SonarQube
        SONARQUBE_TOKEN = 'sqp_963e656725369364f1d0f152b908557222e7efbc' // Remplacez par votre jeton d'authentification
        SONAR_PROJECT_KEY = 'Project-deveops' // Remplacez par le projectKey de votre projet
    }

    stages {
        stage('Clone repository') {
            steps {
                git url: 'https://github.com/jmaimohamed/5SLEAM1-G1-TpFoyer.git', branch: 'LyndaGasmiSleam1'
            }
        }

        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('MVN COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('MVN SONARQUBE') {
            steps {
                withSonarQubeEnv('sonarqube') { // Assurez-vous que "sonarqube" est le nom correct de votre instance SonarQube configurée dans Jenkins
                    sh 'mvn sonar:sonar -Dsonar.projectKey=${Project-deveops} -Dsonar.login=${sqp_963e656725369364f1d0f152b908557222e7efbc}'
                }
            }
        }
    }
}
