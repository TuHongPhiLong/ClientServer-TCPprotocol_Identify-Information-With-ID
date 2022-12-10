package com.example.server;

import com.example.entities.account;
import com.example.entities.subject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class ServerThread implements Runnable{
    private final Socket socket;
    private final int clientNumber;
    private account account;
    private subject subject;
    private Integer idSubject;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    Connection conn = null;

    public ServerThread(Socket socket, int clientNumber) throws IOException {
        this.socket = socket;
        this.clientNumber = clientNumber;

        System.out.println("Server thread number " + clientNumber + " Started");

    }
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Khoi dong luong moi cho client " + clientNumber);

            while (true) {
                account account1 = (account) in.readObject();
                if (account1 != null) {
                    account = account1;
                }
                System.out.println("Da nhan account" + account);

                if (resultCompareAccount()) {
                    out.writeBoolean(resultCompareAccount());
                    out.flush();
                    System.out.println(resultCompareAccount());
                    System.out.println("\n Client da ket noi");
                    break;
                } else {
                    out.writeBoolean(resultCompareAccount());
                    out.flush();
                    System.out.println("Account cua client sai!!!");
                }
            }
            while (true) {
                idSubject = in.readInt();

                System.out.println("idSubject Client gui: " + idSubject);
                sendResultCheckIdSubject();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        return account.getId() == account1.getId() && account.getPassword().equals(account1.getPassword());
    }
    private void sendResultCheckIdSubject() {
        try {
            out.writeObject(resultCompareIdSubject());
            out.flush();
            System.out.println(resultCompareIdSubject());
            System.out.println("Da gui subject");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private subject resultCompareIdSubject() {
        conn = ConnectDB.ConnectDb();
        subject subject = new subject();
        try {
            String value1 = String.valueOf(idSubject);
            String sql = "select * from subject where ID_subject= '" + value1 + "' ";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                subject = new subject(parseInt(rs.getString("ID_subject")), rs.getString("HovaTen"), rs.getDate("NgayThangNamSinh"), rs.getString("GioiTinh"), rs.getString("QuocTich"), rs.getString("QueQuan"), rs.getString("NoiThuongTru"), rs.getString("CacLoiViPham"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subject;
    }

    public void sendAccount(account account) throws IOException {
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
