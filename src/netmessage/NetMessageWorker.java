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
 * <b>Changing the port is done by creating a new NetMessageWorker!</b>
 * 
 * A Message Format should look like this:
 * 
 * <code>[type];[message]</code>
 * 
 * <code>[Type]</code> should always be set to 1 for incoming messages. 0 is for program (local) messages
 * 
 * <code>[Message]</code> is the message. Standard ASCII control characters should be used. 
 * Use carat notation.
 * (E.g Newline should be written as "^J."
 *      CSI codes should be written as "^[CSI"
 *      "^" should be written as "^^")
 * 
 * 
 * Note: Not all escape codes are supported or will be supported (eg. STX, ETX, EOT, never be supported). 
 * @author isaac
 */
public class NetMessageWorker extends SwingWorker<Integer, String>{
    
    WindowMain window;
    NetMessage nm;
    private int maxFailedAttempts = 5;
    //private boolean useRaw = false;
    
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
                publish(this.createMessage("Listing for client connect..."));
                nm.createConnection();
            } catch (SocketTimeoutException e) {
                publish(this.createError("Listen timed out!"));
                failedAttempts++;
            }
        }
        
        //Return an error code if connection failed
        if(failedAttempts < this.maxFailedAttempts) {
            publish(this.createError("Error: Could not create connection with client!"));
            return 2;
        }
        
        //Everything went okay!
        //Start parsing messages and the like
        
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
    
    private String createError(String error) {
        return "0;" + AsciiSGR.BOLD + AsciiSGR.BLUE + ":: " + AsciiSGR.RED + error + AsciiSGR.NORM + "\n";
    }
    
    private String createMessage(String error) {
        return "0;" + AsciiSGR.BOLD + AsciiSGR.BLUE + ":: " + AsciiSGR.F_DEFALT + error + AsciiSGR.NORM + "\n";
    }
    
    public void setMaxFailedAttempts(int i) {
        this.maxFailedAttempts = i;
    }
}
