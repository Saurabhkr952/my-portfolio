def gv

pipeline {
    agent any

    stages {
        stage("Init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("Increment Version") {
            steps {
                script {
                    gv.incrementVersion()
                }
            }
        }
        stage("Build docker image") {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage("Pushing it to Dockerhub") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
        stage("Checkout k8s manifest SCM") {
            steps {
                script {
                    gv.k8sManifest()
                }
            }
        }
        stage("Update k8s manifest repo") {
            steps {
                script {
                    gv.update_k8s_manifest()
                }
            }
            post {
        failure {
            emailext attachmentsPattern: 'test.zip', body: '''${SCRIPT, template="groovy-html.template"}''', 
                    subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - Failed", 
                    mimeType: 'text/html',to: "saurabhkr952@gmail.com"
            }
         success {
               emailext body: 'A Test EMail', subject: 'Test', to: 'saurabhkr952@gmail.com'
          }      
    }
        }
    }
}
