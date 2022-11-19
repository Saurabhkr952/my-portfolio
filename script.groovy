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
    git credentialsId: 'f87a34a8-0e09-45e7-b9cf-6dc68feac670', 
    url: 'https://github.com/Saurabhkr952/k8s_manifest.git',
    branch: 'main'
}

def update_k8s_manifest() {
    withCredentials([usernamePassword(credentialsId: 'github-credentials', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
    sh '''
    cat my-portfolio.yaml
    sed -i 's+saurabhkr952/my-portfolio:.*+saurabhkr952/my-portfolio:35+g' my-portfolio.yaml
    cat my-portfolio.yaml
    git add my-portfolio.yaml
    git commit -m 'Updated the deploy yaml | Jenkins Pipeline'
    git remote -v
    git push https://github.com/Saurabhkr952/k8s_manifest.git HEAD:main
    ''' 
    }      
}

return this
