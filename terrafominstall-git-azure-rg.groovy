pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/devops-2611/OnCloud.git']])
            }
        }

        stage('Debug Git Checkout') {
            steps {
                script {
                    // Debugging step to confirm files are present
                    sh 'ls -al'
                }
            }
        }

        stage('Install Terraform') {
            steps {
                script {
                    sh 'sudo apt-get update -y'
                    sh 'sudo apt-get install -y gnupg software-properties-common curl wget'
                    sh 'wget -q https://apt.releases.hashicorp.com/gpg -O /tmp/hashicorp-archive-keyring.gpg'
                    sh 'sudo rm -f /usr/share/keyrings/hashicorp-archive-keyring.gpg'
                    sh 'sudo gpg --dearmor --batch --no-tty -o /usr/share/keyrings/hashicorp-archive-keyring.gpg /tmp/hashicorp-archive-keyring.gpg'
                    sh 'echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/hashicorp.list'
                    sh 'sudo apt-get update -y'
                    sh 'sudo apt-get install terraform -y'
                    sh 'terraform --version'
                }
            }
        }

        stage('Install Azure CLI') {
            steps {
                script {
                    sh 'curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash'
                    sh 'az --version'
                }
            }
        }

        stage('Login to Azure') {
            steps {
                script {
                    withCredentials([azureServicePrincipal('jenkins')]) {
                        sh 'az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID'
                        echo "Azure login successful"
                    }
                }
            }
        }

        stage('Terraform Init, Plan, and Apply') {
            steps {
                script {
                    dir('.') { // Point to the root directory
                        sh 'terraform init'
                        sh 'terraform plan'
                        sh 'terraform apply -auto-approve'
                    }
                }
            }
        }
    }
}
