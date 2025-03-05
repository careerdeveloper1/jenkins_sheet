**1. Generate SSH Key on Windows Machine:**

**Open Command Prompt (CMD) on your Windows machine.**
Run the command:
```ssh-keygen```
**Follow the prompts. By default, the keys will be saved in the following location:**
```C:\Users\<YourUsername>\.ssh\```
**The SSH key pair generated will include:**

Private Key: id_ed.* (for use on Jenkins)
Public Key: id_ed.*.pub (for use on the Linux VM)

**2. Add a New Node in Jenkins (Windows to Linux VM):**

Go to Jenkins Dashboard
Open your Jenkins web interface: http://localhost:8080/computer/


Click on "New Node"

Node Name: agent-vishal (this is the name you'll reference in your Jenkins pipeline).

Type: Permanent Agent.

Description: (Optional, add anything you like here).

Number of Executors: 1 or 2, depending on your requirements.

Remote Root Directory: /home/welcomeuser (this should match the directory on your Linux VM where the agent will run).

Labels: vishal (this label will be used in your Jenkins pipeline).

Usage: As much as possible (to allow Jenkins to schedule builds on this node).

Launch Method: SSH

Host: Enter the IP address of the Linux VM.

Credentials:

Click on "Add - Jenkins".

Domain: global restricted (default).

Kind: SSH Username with Private Key.

ID: welcomeuser-key (or any identifier you choose).

Description: (Optional).

Username: welcomeuser (this should match the username on your Linux VM).

Private Key: Choose Enter directly, and paste the private key from your id_ed.* file here.

Host Key Verification Strategy: Non-verifying verification strategy (to avoid SSH host key verification issues).

Availability: As much as possible.

Click Save to complete the Jenkins node configuration.

**3. Configure the Linux VM (Agent):**

SSH into the Linux VM:

Use your terminal to SSH into the Linux VM:

ssh welcomeuser@<VM-IP>

Navigate to the .ssh Directory:


```cd ~/.ssh/```

Edit the authorized_keys File:

Open the file with a text editor like nano:

```nano authorized_keys```

Copy the Public Key:

On your Windows machine, locate the public key (id_ed.*.pub) generated earlier.

Copy the entire contents of id_ed.*.pub and paste it into the authorized_keys file on the Linux VM.

Save and Exit:

After pasting the key, save and exit:

Press CTRL + X, then Y, then Enter to confirm.


**4. Final Steps:**
Ensure SSH Access:

Make sure the Linux VM is configured to allow SSH connections and that the firewall is not blocking the connection.

Restart Jenkins (if needed):

If you face any issues, restart Jenkins to ensure the agent is properly connected and recognized.

Check Node Connectivity:

Go back to the Jenkins dashboard, check if the node (agent-vishal) is online and connected successfully.
