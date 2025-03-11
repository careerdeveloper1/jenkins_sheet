##################### Terraform 

pipeline {
    agent any

    stages {
        stage('Install Terraform') {
            steps {
                // Add HashiCorp GPG key
                sh 'wget -O - https://apt.releases.hashicorp.com/gpg | sudo gpg --dearmor -o /usr/share/keyrings/hashicorp-archive-keyring.gpg'
                
                // Add HashiCorp APT repository
                sh 'echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/hashicorp.list'
                
                // Update apt and install Terraform
                sh 'sudo apt update && sudo apt install -y terraform'
            }
        }
        
        stage('Check Terraform Version') {
            steps {
                // Verify Terraform installation
                sh 'terraform --version'
            }
        }
    }
}





