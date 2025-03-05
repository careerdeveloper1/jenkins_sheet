**1. Generate SSH Key on Windows Machine:**
1. Ppen Command Prompt (CMD) on your Windows machine. Run the command: ssh-keygen 
2. Follow the prompts. By default, the keys will be saved in the following location: C:\Users\<YourUsername>\.ssh\

**The SSH key pair generated will include:**

Private Key:  > id_ed.* (for use on Jenkins) Public Key: > id_ed.*.pub (for use on the Linux VM)

**2. Add a New Node in Jenkins (Windows to Linux VM):**

1. Go to Jenkins Dashboard Open your Jenkins web interface: http://localhost:8080/computer/

2. Click on "New Node"

3. Node Name:  **agent-vishal** (this is the name you'll reference in your Jenkins pipeline).

4. Type: Permanent Agent.

5. Description: (Optional, add anything you like here).

6. Number of Executors: 1 or 2, depending on your requirements.

7. Remote Root Directory: /home/welcomeuser (this should match the directory on your Linux VM where the agent will run).

8. Labels: vishal (this label will be used in your Jenkins pipeline).

9. Usage: As much as possible (to allow Jenkins to schedule builds on this node).

10. Launch Method: SSH

11. Host: Enter the IP address of the Linux VM.

12. Credentials:

13. Click on "Add - Jenkins".

14. Domain: global restricted (default).

15. Kind: SSH Username with Private Key.

16. ID: welcomeuser-key (or any identifier you choose).

17. Description: (Optional).

18. Username: welcomeuser (this should match the username on your Linux VM).

19. Private Key: Choose Enter directly, and paste the private key from your id_ed.* file here.

20. Host Key Verification Strategy: Non-verifying verification strategy (to avoid SSH host key verification issues).

21. Availability: As much as possible.

22. Click Save to complete the Jenkins node configuration.

**3. Configure the Linux VM (Agent):**

1. SSH into the Linux VM:

2. Use your terminal to SSH into the Linux VM:

3. ssh welcomeuser@<IP>

4. Navigate to the .ssh Directory:
``` cd ~/.ssh/ ```

5. Edit the authorized_keys File:

6. Open the file with a text editor like nano:

7. nano authorized_keys

8. Copy the Public Key:

9. On your Windows machine, locate the public key (id_ed.*.pub) generated earlier.

10. Copy the entire contents of id_ed.*.pub and paste it into the authorized_keys file on the Linux VM.

11. Save and Exit:

After pasting the key, save and exit:

Press CTRL + s, then x, then Enter to confirm.

**4. Final Steps: Ensure SSH Access:**

1. Make sure the Linux VM is configured to allow SSH connections and that the firewall is not blocking the connection.

Restart Jenkins (if needed):

If you face any issues, restart Jenkins to ensure the agent is properly connected and recognized.

Check Node Connectivity:

Go back to the Jenkins dashboard, check if the node (agent-vishal) is online and connected successfully.


**5. Extra Troubleshoot in SSH and firewall**

sudo systemctl status ssh

sudo apt-get update
sudo apt-get install openssh-server
sudo systemctl start ssh
sudo systemctl enable ssh

sudo ufw allow ssh
sudo ufw enable

sudo ufw status
sudo ufw status verbose


Allow any port to get into your machine:
sudo ufw allow from any to any

ip addr show

sudo systemctl restart ssh
