/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage;

import javax.swing.SwingUtilities;

/**
 *
 * @author isaac
 */
public class Main {
    public static void main(String [] args) {
        //According to the docs, this is the correct way to start a Swing thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Console con = new Console();
                con.setVisible(true);
            }
        });
    }
}
