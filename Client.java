package com.app.client;

import com.app.frame.LoginFrame;
import com.app.server.Server;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {

    protected static Socket socket;
    protected static ObjectOutputStream output;
    protected static ObjectInputStream input;

    public static void main(String[] args) {
        new Thread(new Server()).start();
        new Thread(new LoginFrame()).start();
    }

    @Override
    public void run(){
        try {
            while (true){
                socket = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                JOptionPane.showMessageDialog(null, (String)input.readObject());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
