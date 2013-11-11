/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netmessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author cnrobotics
 */
public class NetMessage {

    //This is the socket we open so the Robot can communitcate with us
    private ServerSocket server;
    //This is the connection generated once the client connects with us
    private Socket client;
    //Anything we get from the Robot will be read by this
    private BufferedReader clientInput;
    
    /**
     * Creates as new NetMessage object
     * @throws IOException if there is an error creating the socket. Usually it because the port is already bound.
     */
    public NetMessage() throws IOException {
        server = new ServerSocket(123);
        server.setSoTimeout(10);
    }
    
    /**
     * Tries to create the connection between the Robot.
     * @throws IOException If a connection could not be established after 10 seconds
     */
    public void createConnection() throws IOException {
        client = server.accept();
        clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }
    
    /**
     * Reads the latest message from the Robot
     * @return Latest message
     * @throws IOException A line could not be read.
     */
    private String getLine() throws IOException {
        return clientInput.readLine();
    }
}
