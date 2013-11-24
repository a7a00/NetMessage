/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import netmessage.ui.WindowMain;

/**
 * Worker thread class for connecting and communicating with NetMessage
 * 
 * @author isaac
 */
public class NetMessageWorker extends SwingWorker<Integer, String>{

    WindowMain window;
    NetMessage nm;
    
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //This function runs when update() is called on the worker thread,
    //and runs on the EDT, allowing you to update the GUI.
    @Override
    protected void process(String chunks) {
        
    }
    
}
