/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage.ui;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import netmessage.NetMessageWorker;

/**
 *
 * @author isaac
 */
public class Main {
    public static void main(String[] args) {
        WindowMain wm = new WindowMain();
        
        wm.setVisible(true);
        new NetMessageWorker(wm, 5555).execute();
    }
}
