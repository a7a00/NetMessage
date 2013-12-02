/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import netmessage.ui.WindowMain;

/**
 * SwingWorker class for connecting and communicating with NetMessage.
 * 
 * A Message Format should look like this:
 * 
 * <code>[type];[message];[newline]</code>
 * 
 * <code>[Type]</code> should always be set to 1 for incoming messages. 0 is for program (local) messages
 * 
 * <code>[Message]</code> is the message. Standard ANSII escape codes should be used:
 * 
 * Escape Character is ^[ (\033, \x1B)
 * 
 * <code>[Newline]</code> is a boolean value of 0(False) or 1(True), depending on if there should be a newline or
 * not. Note that any value higher than 1 will be registered as true.
 * 
 * @author isaac
 */
public class NetMessageWorker extends SwingWorker<Integer, String>{
    
    WindowMain window;
    NetMessage nm;
    private int maxFailedAttempts = 5;
    
    public NetMessageWorker(WindowMain window) {
        this.window = window;
        try {
            nm = new NetMessage();
        } catch (IOException ex) {
            try {
                window.AppendStyledText("::", WindowMain.NM_PRE);
                window.AppendStyledLine("Error: Could not estabish a server on port " + nm.getPort(), WindowMain.NM_ERROR);
            } catch (BadLocationException ex1) {
                Logger.getLogger(NetMessageWorker.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }  
    }
    
    public NetMessageWorker(WindowMain window, int port) {
        this.window = window;
        try {
            nm = new NetMessage(port);
        } catch (IOException ex) {
            try {
                window.AppendStyledText("::", WindowMain.NM_PRE);
                window.AppendStyledLine("Error: Could not estabish a server on port " + nm.getPort(), WindowMain.NM_ERROR);
            } catch (BadLocationException ex1) {
                Logger.getLogger(NetMessageWorker.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @Override
    protected Integer doInBackground() throws Exception {
        int failedAttempts = 0;
        
        while(failedAttempts < this.maxFailedAttempts) {
            try {
                publish("0;6;Listening for client connect...;1");
                nm.createConnection();
            } catch (SocketTimeoutException e) {
                publish("0;5;Listen timed out!;1");
                failedAttempts++;
            }
        }
        
        //Return an error code if connection failed
        if(failedAttempts < this.maxFailedAttempts) {
            publish("0;5;Error: Could not establish a connection with client!;1");
            return 2;
        }
        
        return 1;
    }
    
    //This function runs when publish() is called on the worker thread,
    //and runs on the EDT, allowing you to update the GUI.
    @Override
    protected void process(List<String> list) {
        //The list is a sort of buffer
        for (String s : list) {
            
        }
    }
    
    public void setMaxFailedAttempts(int i) {
        this.maxFailedAttempts = i;
    }
}
