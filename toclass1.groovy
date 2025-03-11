pipeline {
    agent any
    
    stages {
        stage('clearn work space') {
            steps {
                cleanWs()
            }
        }

        stage('git-clone') {
            steps {
                withCredentials([gitUsernamePassword(credentialsId: 'github_user', gitToolName: 'Default')]) {
                    bat 'git clone https://github.com/careerdeveloper1/030325.git'
                }
            }
        }


        stage('az-login') {
            steps {
                withCredentials([azureServicePrincipal('azure_sp')]) {
                    /* groovylint-disable-next-line LineLength */
                    bat 'az login --service-principal -u %AZURE_CLIENT_ID% -p %AZURE_CLIENT_SECRET% -t %AZURE_TENANT_ID%'
                    echo 'Az login successfully'
                }
            }
        }
        stage('terraform-init') {
            steps {
                dir('\\030325\\ENV') {
                    bat 'terraform init'
                    echo 'terraoform intialized done'
                }
            }
        }
        stage('terraform-plan') {
            steps {
                dir('\\030325\\ENV') {
                    bat 'terraform plan'
                    echo 'terraoform plan done'
                }
            }
        }

        stage('terraform-apply') {
            steps {
                dir('\\030325\\ENV') {
                    bat 'terraform apply -auto-approve'
                    echo 'terraoform apply done'
                }
            }
        }
    }
}