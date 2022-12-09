package com.example.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CheckAccount {


    private Socket socket;
    private com.example.entities.account account;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public CheckAccount(Socket socket) throws IOException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        receiveBoolean();
    }

    public void sendAccount(com.example.entities.account account) throws IOException {
        out.writeObject(account);
        out.flush();
        System.out.println("Da gui account cho Server kiem tra");
    }

    public void receiveBoolean() {
        Thread th = new Thread() {
            public void run() {
                try {
                    while (true) {
                        boolean ketQuaKiemTra = in.readBoolean();

                        System.out.println("Da nhan ket qua tu Server" + ketQuaKiemTra);
                        if (ketQuaKiemTra == true) {
                            System.out.println("Account dung");
                            System.out.println("\nDa ket noi!");

                            break;
                        } else {
                            System.out.println("Account sai, nhap lai!!!");
                        }
                    }
                } catch (IOException e) {
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
