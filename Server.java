package com.app.server;

import com.app.client.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    static protected ServerSocket server;
    static protected Socket socket;
    static protected ObjectOutputStream output;
    static protected ObjectInputStream input;

    @Override
    public void run() {
        try {
            server = new ServerSocket(5678, 10);
            while (true){
                socket = server.accept();
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                output.writeObject(input.readObject());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
