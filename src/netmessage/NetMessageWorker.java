package netmessage;

import java.awt.Color;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
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
    private boolean stopRecieving = false;
    //private boolean useRaw = false;
    
    /**
     * Creates a new NetMessageWorker by loading the configuration or using defaults if it doesn't work.
     * @param window A window to send messages through.
     */
    public NetMessageWorker(WindowMain window){
        this.window = window;
        try {
            nm = new NetMessage(NetMessage.networkSettings.getInt("port", 5555), NetMessage.networkSettings.getInt("timeout", 5000) * 1000);
            window.AppendStyledText("::", WindowMain.NM_PRE);
            window.AppendStyledLine(" Hosting on  " + nm.getPort(), WindowMain.NM_MSG);

        } catch (IOException ex) {
            try {
                window.AppendStyledText("::", WindowMain.NM_PRE);
                window.AppendStyledLine(" Error: Could not estabish a server on port " + NetMessage.networkSettings.getInt("port", 5555), WindowMain.NM_ERROR);
            } catch (BadLocationException ex1) {
                Logger.getLogger(NetMessageWorker.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(NetMessageWorker.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public NetMessageWorker(WindowMain window, int port, int timeout) {
        this.window = window;
        try {
            nm = new NetMessage(port, timeout);
        } catch (IOException ex) {
            try {
                window.AppendStyledText("::", WindowMain.NM_PRE);
                window.AppendStyledLine(" Error: Could not estabish a server on port " + port, WindowMain.NM_ERROR);
            } catch (BadLocationException ex1) {
                Logger.getLogger(NetMessageWorker.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @Override
    protected Integer doInBackground() throws Exception {
        int failedAttempts = 1;
        
        while(failedAttempts <= this.maxFailedAttempts) {
            try {
                publish(this.createLMessage("Listening for client connect... (" + failedAttempts + "/" + this.maxFailedAttempts + ")"));
                nm.createConnection();
            } catch (SocketTimeoutException e) {
                publish(this.createLError("Listen timed out!"));
                failedAttempts++;
            }
        }
        
        //Return an error code if connection failed
        if(failedAttempts >= this.maxFailedAttempts) {
            publish(this.createLError("Error: Could not create connection with client!"));
            //Unbind the port
            nm.close();
            return 2;
        }
        
        //Everything went okay!
        //Start parsing messages and the like
        while(stopRecieving != true) {
            publish(nm.getLine());
        }
        
        return 1;
    }
    
    //This function runs when publish() is called on the worker thread,
    //and runs on the EDT, allowing you to update the GUI.
    @Override
    protected void process(List<String> list) {
        //The list is a sort of buffer
        for (String s : list) {
            parseMessage(s);
        }
    }
    
    private void parseMessage(String message) {
        String[] split = message.split(";", 2);
        //Need to make sure that the first part of a message is a message
        try {
            if(Integer.parseInt(split[0]) == 0) {
                //It's a special message
                split[1] = AsciiSGR.BLUE + " " + AsciiSGR.BOLD + " :: " + AsciiSGR.NORM + " " + split[1];
            }
        } catch (NumberFormatException e) {
            //There was an error, so do this clever hack.
            split[1] = AsciiSGR.BOLD + " " + AsciiSGR.BLUE + " :: " + AsciiSGR.RED + " Error parsing message! (int type is not a number)" + AsciiSGR.NORM + "\n";
        }
        
        /*Parse the message Ascii codes */
        
        //Create the Attribute set. Clear it when NORM is used
        SimpleAttributeSet as = new SimpleAttributeSet();
        
        //Split the message by space
        String[] msplit = split[1].split(" ");
        for(String s : msplit) {
            System.out.println(s);
            //Check that the first character is escape
            if(s.charAt(0) == '^') {
                //Now we need to check what it's escaping
                switch(s.charAt(1)) {
                    case '[':
                        //It's an SGR!
                        switch(AsciiSGR.lookupValue(s.substring(0, s.indexOf("m") + 1))) {
                            case NORM:
                                as.removeAttributes(as);
                                break;
                            case BOLD:
                                as.addAttribute(StyleConstants.Bold, true);
                                break;
                            case FAINT:
                                break;
                            case ITALIC:
                                break;
                            case UNDERLINE:
                                break;
                            case BLACK:
                                break;
                            case RED:
                                as.removeAttribute(StyleConstants.Foreground);
                                as.addAttribute(StyleConstants.Foreground, Color.RED);
                                break;
                            case GREEN:
                                break;
                            case YELLOW:
                                break;
                            case BLUE:
                                as.removeAttribute(StyleConstants.Foreground);
                                as.addAttribute(StyleConstants.Foreground, Color.BLUE);
                                break;
                            case MAGENTA:
                                break;
                            case CYAN:
                                break;
                            case GREY:
                                break;
                            case FCOLOR:
                                break;
                            case F_DEFALT:
                                as.removeAttribute(StyleConstants.Foreground);
                                as.addAttribute(StyleConstants.Foreground, Color.LIGHT_GRAY);
                                break;
                            case B_BLACK:
                                break;
                            case B_RED:
                                break;
                            case B_GREEN:
                                break;
                            case B_YELLOW:
                                break;
                            case B_BLUE:
                                break;
                            case B_MAGENTA:
                                break;
                            case B_CYAN:
                                break;
                            case B_GREY:
                                break;
                            case B_FCOLOR:
                                break;
                            case B_DEFALT:
                                break;
                            case I_BLACK:
                                break;
                            case I_RED:
                                break;
                            case I_GREEN:
                                break;
                            case I_YELLOW:
                                break;
                            case I_BLUE:
                                break;
                            case I_MAGENTA:
                                break;
                            case I_CYAN:
                                break;
                            case I_GREY:
                                break;
                            case IB_BLACK:
                                break;
                            case IB_RED:
                                break;
                            case IB_GREEN:
                                break;
                            case IB_YELLOW:
                                break;
                            case IB_BLUE:
                                break;
                            case IB_MAGENTA:
                                break;
                            case IB_CYAN:
                                break;
                            case IB_GREY:
                                break;
                            default:
                                throw new AssertionError(AsciiSGR.values()[Integer.decode(s.substring(2, s.indexOf("m")))].name());
                        }
                        break;
                    default:
                        //TODO: Figure out and add a switch statement
                        break;    
                }
            } else {
                try {
                    //It's not an escape sequence, leave it alone
                    this.window.AppendStyledText(" " + s, as);
                } catch (BadLocationException ex) {
                    System.err.println(ex);
                }
            }
        }
    }
    
    private String createLError(String error) {
        return "0;" + AsciiSGR.RED + " " + error + " " + AsciiSGR.NORM + " \n";
    }
    
    private String createLMessage(String error) {
        return "0;" + AsciiSGR.F_DEFALT + " " + error + " " + AsciiSGR.NORM + " \n";
    }
    
    public void setMaxFailedAttempts(int i) {
        this.maxFailedAttempts = i;
    }
}
