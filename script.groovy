def incrementVersion(){
    echo 'Increment version'
    def matcher = readFile('package.json') =~ '"version": (.+),'
    def version = matcher[0][1]
    env.IMAGE_NAME = "${version}"
    
}
def textManipulate() {
    sh "sed -i 's+saurabhkr952/my-portfolio:.*+saurabhkr952/my-portfolio:$IMAGE_NAME-$BUILD_NUMBER+g' my-portfolio.yaml."
    env.TEXT_MANIPULATE = "${textManipulate}"
} 

def buildImage() {
    echo 'Building Docker Image'
    sh "docker build -t saurabhkr952/my-portfolio:$IMAGE_NAME-$BUILD_NUMBER ."
} 

def deployApp() {
    echo 'deploying the application...'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push saurabhkr952/my-portfolio:$IMAGE_NAME-$BUILD_NUMBER"
    }
}


def k8sManifest() {
    git credentialsId: 'github-credentials', 
    url: 'https://github.com/Saurabhkr952/k8s_manifest.git',
    branch: 'main'
   }
    
def update_k8s_manifest() {
    echo "pushing updated manifest to repository"
    withCredentials([usernamePassword(credentialsId: 'github-credentials', passwordVariable: 'password', usernameVariable: 'username')]) {
    $TEXT_MANIPULATE
    sh "git add my-portfolio.yaml"
    sh "git commit -m 'Updated the my-portfolio yaml | Image Version=$IMAGE_NAME'"
    sh "git remote -v"
    sh "git push https://$password@github.com/Saurabhkr952/k8s_manifest.git HEAD:main"
    }      
}

return this
