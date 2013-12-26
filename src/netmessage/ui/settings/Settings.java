/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage.ui.settings;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author isaac
 */
public class Settings {
    
    private class NetworkSettings {
        private int port;
        private Map<InetAddress, String> allowedList;
        
        NetworkSettings() {
            this.port = 0;
            this.allowedList = new HashMap<>();
        }
        
        public void changePort(int port) {
            this.port = port;
        }
        
        public void addToAllowedList(InetAddress ip, String name) {
            this.allowedList.put(ip, name);
        }
        
        public int getPort() {
            return port;
        }
        
        public List<InetAddress, String> getElementAt(int index) {
            this.allowedList.
        }
    }
    
}
