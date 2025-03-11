// step-1 isntall dcoker on your master machine
// step-2 install plungin docker pipeline


pipeline {
  agent {
    docker { 
        image 'hashicorp/terraform'
        args '--entrypoint=""'
        
    }
  }
  stages {
    stage('Test') {
      steps {
        sh 'terraform --version'
      }
    }
  }
}