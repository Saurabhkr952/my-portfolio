def incrementVersion(){
    echo 'Increment version'
    def matcher = readFile('package.json') =~ '"version": (".+"),'
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

def k8sManifest() {
    git credentialsId: 'github-credentials', 
    url: 'https://github.com/Saurabhkr952/k8s_manifest.git',
    branch: 'main'
}

def update_k8s_manifest() {
    echo "pushing updated manifest to repository"
    withCredentials([usernamePassword(credentialsId: 'git-token', passwordVariable: 'GIT_USERNAME', usernameVariable: 'GIT_PASSWORD')]) {
    sh "git config --global user.email 'saurabhkr952@gmail.com'"
    sh "git config --global user.name 'Saurabhkr952'"
    sh "cat my-portfolio.yaml"
    sh "sed -i 's+saurabhkr952/my-portfolio:.*+saurabhkr952/my-portfolio:$IMAGE_NAME+g' my-portfolio.yaml"
    sh "cat my-portfolio.yaml"
    sh "git add my-portfolio.yaml"
    sh "git commit -m 'Updated the my-portfolio yaml | Jenkins Pipeline'"
    sh "git remote -v"
    sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/Saurabhkr952/k8s_manifest.git HEAD:main" 
    }      
}

return this
