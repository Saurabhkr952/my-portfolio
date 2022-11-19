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
        }
        post {
        always {
            emailext body: 'From Jenkins', subject: 'Build Successful', to 'saurabhkr952@gmail.com'
        }
      }        
    }
}
