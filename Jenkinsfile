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
        stage("pushing it to dockerhub") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
        stage("checkout k8s manifest SCM") {
            steps {
                script {
                    gv.k8s_manifest()
                }
            }
        }
        stage("update k8s manifest repo") {
            steps {
                script {
                    gv.update_k8s_manifest()
                }
            }
        }
    }
}
