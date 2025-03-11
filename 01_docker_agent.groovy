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