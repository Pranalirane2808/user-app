pipeline {
    agent any

    environment {
        IMAGE_NAME = "user-app"
        REGISTRY = "your-dockerhub-username"
    }

    stages {
        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }
        stage('Docker Build & Push') {
            steps {
                script {
                    def imageTag = "v${env.BUILD_NUMBER}"
                    sh """
                        docker build -t ${REGISTRY}/${IMAGE_NAME}:${imageTag} .
                        docker push ${REGISTRY}/${IMAGE_NAME}:${imageTag}
                    """
                    writeFile file: 'deployment/image_tag.txt', text: imageTag
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    def imageTag = readFile('deployment/image_tag.txt').trim()
                    sh """
                        sed 's|IMAGE_TAG|${imageTag}|' deployment/deployment.yml > deployment/temp_deployment.yml
                        kubectl apply -f deployment/temp_deployment.yml
                    """
                }
            }
        }
    }

    triggers {
        pollSCM('* * * * *')
    }
}
