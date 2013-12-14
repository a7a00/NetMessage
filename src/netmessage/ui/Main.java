/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage.ui;

import netmessage.NetMessageWorker;

/**
 *
 * @author isaac
 */
public class Main {
    public static void main() {
        WindowMain wm = new WindowMain();
        
        wm.setVisible(true);
        new NetMessageWorker(wm, 5555).execute();
    }
}
