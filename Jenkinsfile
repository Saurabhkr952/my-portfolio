def gv

pipeline {
    agent {
        docker { image 'scratch' }
    }

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
        stage("Pushing Artifact to Dockerhub") {
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
        stage("Update k8s manifest Repo") {
            steps {
                script {
                    gv.update_k8s_manifest()
                }
            }
        }
    }
    post {                      // manually need to configure smtp gmail using ssl
         always {  
             echo 'This will always run'  
         }  
         success {  
             echo 'Sending email about Build Success '  
             mail bcc: '', body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> Build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Build Successful: Project name -> ${env.JOB_NAME}", to: "saurabhkr952@gmail.com";  
         }  
         failure { 
             echo 'Sending email about Build Unsuccessful ' 
             mail bcc: '', body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> Build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Build Unsuccessful: Project name -> ${env.JOB_NAME}", to: "saurabhkr952@gmail.com";  
         } 
    }
}
