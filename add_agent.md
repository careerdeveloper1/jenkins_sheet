Steps to Add a New Node as an Agent in Jenkins
1. Generate SSH Key on Windows Machine
Open Command Prompt (CMD) on your Windows machine.
Run the following command to generate the SSH key pair:
bash
Copy
ssh-keygen
The keys will be saved in your local drive at:
bash
Copy
C:\Users\<YourUsername>\.ssh\
The generated key files will be:
Private Key: id_ed.* (used on Jenkins)
Public Key: id_ed.*.pub (used on the Linux VM)
2. Add a New Node in Jenkins (Windows to Linux VM)
Open your Jenkins dashboard: http://localhost:8080/computer/

Click on "New Node".

Enter the following details:

Node Name: agent-vishal (use this name in your Jenkins pipeline).
Type: Permanent Agent.
Description: (Optional, add anything you'd like).
Number of Executors: 1 or 2 (based on your needs).
Remote Root Directory: /home/welcomeuser (this should match the directory on the Linux VM).
Labels: vishal (this label will be used in the Jenkins pipeline).
Usage: As much as possible (allows Jenkins to schedule builds on this node).
Set the Launch Method to Via SSH.

Host: Enter the IP address of the Linux VM.
Under Credentials:

Click on "Add - Jenkins".
Domain: global restricted (default).
Kind: SSH Username with Private Key.
ID: welcomeuser-key (or any identifier).
Description: (Optional).
Username: welcomeuser (should match the username on the Linux VM).
Private Key: Choose Enter directly, and paste the private key from id_ed.* here.
Host Key Verification Strategy: Non-verifying verification strategy (avoids SSH host key verification issues).
Availability: As much as possible.
Click Save to save the Jenkins node configuration.

3. Configure the Linux VM (Agent)
SSH into the Linux VM:
bash
Copy
ssh welcomeuser@<VM-IP>
Navigate to the .ssh directory:
bash
Copy
cd ~/.ssh/
Edit the authorized_keys file:
bash
Copy
nano authorized_keys
Copy the contents of the public key (id_ed.*.pub) from the Windows machine and paste it into the authorized_keys file on the Linux VM.
Save and exit by pressing:
CTRL + X, then Y, then Enter.
4. Final Steps
Ensure the Linux VM is configured to allow SSH connections and that no firewall is blocking the connection.
If needed, restart Jenkins to make sure the agent is connected properly.
Check the Node Connectivity on Jenkins:
Visit the Jenkins dashboard to see if the node (agent-vishal) is online and connected.
