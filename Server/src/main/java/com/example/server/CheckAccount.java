package com.example.server;

import com.example.entities.account;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class CheckAccount {


    private Socket socket;
    private com.example.entities.account account;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    Connection conn = null;


    public CheckAccount(Socket socket) throws IOException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        receiveAccount();
    }

    public void receiveAccount() {
        Thread th = new Thread() {
            public void run() {
                try {
                    while (true) {
                        com.example.entities.account account1 = (com.example.entities.account) in.readObject();
                        if (account1 != null) {
                            account = account1;
                        }
                        System.out.println("Da nhan account" + account);

                        if (resultCompareAccount()) {
                            out.writeBoolean(resultCompareAccount());
                            out.flush();
                            System.out.println("\n Client da ket noi");
                        } else {
                            out.writeBoolean(resultCompareAccount());
                            out.flush();
                            System.out.println("Account cua client sai!!!");
                        }
                    }
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        th.start();
    }

    private Boolean resultCompareAccount() throws SQLException {
        conn = ConnectDB.ConnectDb();
        com.example.entities.account account1 = new account();

        String value1 = String.valueOf(account.getId());//trong data là id

        PreparedStatement pst = conn.prepareStatement("select * from account where ID= '" + value1 + "'");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            account1 = new account(parseInt(rs.getString("ID")), rs.getString("Password"));
        }
        // kiem tra id và password Client gửi
        if (account.getId() == account1.getId() && account.getPassword().equals(account1.getPassword())) {
            return true;
        } else {
            return false;
        }
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
                            out.close();
                            in.close();
                            socket.close();
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
