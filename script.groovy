def incrementVersion(){
    echo 'Increment version'
    def matcher = readFile('package.json') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
}
def buildImage() {
    echo "building the docker image..."
    sh "docker build -t saurabhkr952/my-portfolio:$IMAGE_NAME ."
} 

def deployApp() {
    echo 'deploying the application...'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push saurabhkr952/my-portfolio:$IMAGE_NAME"
    }
} 

return this
