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
        stage("Pushing Artifact to Dockerhub") {
            steps {
                script {
                    gv.deployApp()
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
    }
}
