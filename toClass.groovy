pipeline {
    agent any

    stages {
        stage('vishal') {
            steps {
                sh '''sudo chmod 755 /home/welcomeuser
                cd /home/welcomeuser
                sudo mkdir vishal
                sleep 30
                sudo rm -rf vishal'''
            }
        }
    }
}
#########################################################

pipeline {
    agent any
    stages {
        stage('isntall nginx') {
            steps {
                sh '''sudo apt-get update
                sudo apt-get install -y nginx
                sudo nginx -v'''
            }
        }
    }
}


#########################################################

pipeline {
    agent any

    stages {
        stage('vishal') {
            steps {
                sh '''
                sudo apt-get install -y sshpass
                sshpass -p "welcome@12345" ssh welcomeuser@4.188.224.92 \
                "cd /home/welcomeuser && sudo mkdir -p vishal && exit"
                '''
            }
        }
    }
}



#########################################################

pipeline {
    agent any

    stages {
        stage('vishal') {
            steps {
                sh '''
                sudo apt-get install -y sshpass
                sshpass -p "welcome@12345" ssh welcomeuser@4.188.224.92 \
                "cd /home/welcomeuser && sudo mkdir -p vishal &&\
                sleep 30 && sudo rm -rf vishal && exit"
                '''
            }
        }
    }
}


#########################################################

pipeline {
    agent any
triggers {
    cron '''TZ=Europe/London
* * * * *'''
}    

    stages {
        stage('vishal') {
            steps {
                sh '''
                sudo apt-get install -y sshpass
                sshpass -p "welcome@12345" ssh welcomeuser@4.188.224.92 \
                "cd /home/welcomeuser && sudo mkdir -p vishal &&\
                sleep 30 && sudo rm -rf vishal && exit"
                '''
            }
        }
    }
}


pipeline {
    agent any

    stages {
        stage('Hello') {
            environment {
                SERVICE_CREDS = credentials('user_pass_vm')
            }
            steps {
                echo 'Hello World'
                sh 'echo "Service user is $SERVICE_CREDS_USR"'
                sh 'echo "Service password is $SERVICE_CREDS_PSW"'
            }
        }
    }
}



#######################################################################################################3

pipeline {
    agent any
    environment {
        MY_CRED = credentials('welcomeuser') // Using Jenkins stored credentials
    }

    stages {
        stage('SSH Login') {
            steps {
                    sh '''
                    sudo apt-get install -y sshpass
                    sshpass -p "$MY_CRED_PSW" ssh "$MY_CRED_USR"@4.188.224.92 \
                    "echo "Successfully logged into the target VM" && \
                    cd /home/welcomeuser &&\
                    sudo mkdir vishal && \
                    hostname && \
                    exit"
                    '''
            }
        }
    }
}

// OR


pipeline {
    agent any
    environment {
        MY_CRED = credentials('welcomeuser') // Using Jenkins stored credentials
    }

    stages {
        stage('SSH Login') {
            steps {
                    sh '''
                    sudo apt-get install -y sshpass
                    sshpass -p "$MY_CRED_PSW" ssh "$MY_CRED_USR"@4.188.224.92 << EOF 
                    echo "Successfully logged into the target VM"
                    cd /home/welcomeuser
                    sudo mkdir vishal
                    hostname
                    exit
                    EOF
                    '''
            }
        }
        stage('check terraform version') {
            steps {
                    sh '''
                    sudo apt-get install -y sshpass
                    sshpass -p "$MY_CRED_PSW" ssh "$MY_CRED_USR"@4.188.224.92 << EOF 
                    echo "Successfully logged into the target VM"
                    cd /home/welcomeuser
                    terraform --version
                    EOF
                    '''
            }
        }

    }
}


##############################################################

pipeline {
    agent any
    environment {
                MY_CRED = credentials('user_pass_vm')
                VM_IP = "4.188.224.92"
            }

    stages {
        stage('vishal') {
            steps {
                sh '''
                sudo apt-get install -y sshpass
                sshpass -p "$MY_CRED_PSW" ssh "$MY_CRED_USR"@$VM_IP \
                "cd /home/welcomeuser &&\
                sudo mkdir -p vishal"
                '''
            }
        }
    }
}


##############################################################

// //etc/nginx/sites-available 
// /var/www/html;

pipeline {
    agent any
    environment {
                MY_CRED = credentials('user_pass_vm')
                VM_IP = "4.188.224.92"
            }

    stages {
        stage('vishal') {
            steps {
                sh '''
                sudo apt-get install -y sshpass
                sshpass -p "$MY_CRED_PSW" ssh "$MY_CRED_USR"@$VM_IP \
                "cd /home/welcomeuser &&\
                sudo mkdir -p vishal"
                '''
            }
        }
        stage('nginx') {
            steps {
                sh '''
                sudo apt-get install -y sshpass
                sshpass -p "$MY_CRED_PSW" ssh "$MY_CRED_USR"@$VM_IP \
                "sudo apt-get update && sudo apt-get install -y nginx &&\
                sudo nginx -v"

                '''
            }
        }
        stage('web page') {
            steps {
                sh '''
                sudo apt-get install -y sshpass
                sshpass -p "$MY_CRED_PSW" ssh "$MY_CRED_USR"@$VM_IP \
                "cd /var/www/html &&  "

                '''
            }
        }


    }
}


##############################################################

Azure service pricipal
#########################################################

// sudo chown -R jenkins:jenkins /home/welcomeuser/practice/RG
// sudo chmod -R 775 /home/welcomeuser/practice/RG


pipeline {
    agent any

    stages {
        stage('terraform init') {
            steps {
                withCredentials([azureServicePrincipal('welcome')]) {
                    sh '''
                    cd /home/welcomeuser/practice/RG
                    az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID
                    terraform init
                    terraform plan
                    terraform apply -auto-approve
                    '''
                }
            }
        }
    }
}



#########################################################


pipeline {
    agent any

    stages {
        stage('clean WS') {
            steps {
                cleanWs()
            }
        }



        stage('git checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github', url: 'https://github.com/Developer-Personal/invoice-crm.git']])
            }
        }
    stage('Node Installation') {
        steps {
            sh '''
            if command -v node >/dev/null 2>&1; then
                echo "Node.js installed successfully: $(node -v)"
            else
                echo "Node.js is not installed. Installing now..."
                sudo apt-get update
                sudo apt-get install -y curl
                curl -fsSL https://deb.nodesource.com/setup_23.x | sudo -E bash -
                sudo apt-get install -y nodejs
                echo "Node.js is already installed: $(node -v)"
            fi
            '''
        }
    }

        stage('copy env') {
            steps {
                sh 'cp -f .env client/.env'


                
            }
        }

        stage('build content') {
            steps {
                dir('client') {
                sh '''
                npm install
                npm run build
                '''
            }

            }
        }
        stage('Clone Additional Repository') {
            steps {
                dir('server1') {
                    sh '''
                    git clone https://github.com/devops-2611/crm.git
                    cd crm
                    cp -r ../../client/dist ./
                    cp -r ../../server ./
                    git push https://github.com/devops-2611/crm.git

                    '''
                }
            }
        }

    }
}



#########################################################
