package com.example.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FindSubject {


    private Socket socket;
    private com.example.entities.subject subject;
    private Integer idSubject;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public FindSubject(Socket socket) throws IOException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        receiveSubject();
    }

    public void sendIdSubject(Integer idSubject) throws IOException {
//        out = new ObjectOutputStream(socket.getOutputStream());

        out.writeInt(idSubject);
        out.flush();
        System.out.println("Da gui id cua subject");
    }

    public void receiveSubject() {
        Thread th = new Thread() {
            public void run() {
                try {
//                    out = new ObjectOutputStream(socket.getOutputStream());
//                    in = new ObjectInputStream(socket.getInputStream());
                    while (true) {
                        subject = (com.example.entities.subject) in.readObject();

                        System.out.println(subject.toString());
                        System.out.println("Da nhan subject");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        th.start();
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

}
