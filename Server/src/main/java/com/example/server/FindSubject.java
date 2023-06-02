package com.example.server;


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

public class FindSubject {

    private Socket socket;
    private subject subject;
    private Integer idSubject;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    Connection conn = null;
    public FindSubject(Socket socket) throws IOException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        receiveIdSubject();
    }

    public void receiveIdSubject() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        idSubject = in.readInt();

                        System.out.println("idSubject Client gui: " + idSubject);
                        sendResultCheckIdSubject();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
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
                subject = new subject(parseInt(rs.getString("ID_subject")), rs.getString("HovaTen"), rs.getDate("NgayThangNamSinh"), rs.getString("GioiTinh"), rs.getString("QuocTich"), rs.getString("QueQuan"), rs.getString("NoiThuongTru"), rs.getBytes("URL"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subject;
    }

    public void sendIdSubject(Integer idSubject) throws IOException {

        out.writeInt(idSubject);
        out.flush();
        System.out.println("Da gui id cua subject");
    }

    public void receiveSubject() {
        Thread th = new Thread() {
            public void run() {
                try {
                    while (true) {
                        subject = (subject) in.readObject();

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
