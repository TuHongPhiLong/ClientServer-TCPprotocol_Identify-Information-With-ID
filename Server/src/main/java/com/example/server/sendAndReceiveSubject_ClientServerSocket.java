package com.example.server;


import com.example.server.model.subject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class sendAndReceiveSubject_ClientServerSocket {


    private Socket socket;
    private subject subject1;
    private ObjectOutputStream out;
    private ObjectInputStream in;



    public sendAndReceiveSubject_ClientServerSocket(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        receiveSubject();
    }

    public void receiveSubject() throws IOException, ClassNotFoundException {
        Thread th = new Thread() {
            public void run() {
                while (true) {
                    try {
                        subject1 = (subject) in.readObject();

                        System.out.println(subject1.toString());
                        System.out.println("Received subject");
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        th.start();
    }

    public void sendSubject(subject subject) throws IOException {

        out.writeObject(subject);
        out.flush();
        System.out.println("Sent subject");
    }


    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public subject getSubject1() {
        return subject1;
    }

    public void setSubject1(subject subject1) {
        this.subject1 = subject1;
    }
}
