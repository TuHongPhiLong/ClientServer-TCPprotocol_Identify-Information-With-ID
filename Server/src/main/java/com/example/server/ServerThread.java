package com.example.server;

import com.example.server.model.account;
import com.example.server.model.error;
import com.example.server.model.subject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ServerThread implements Runnable{
    private final Socket socket;
    private int clientNumber;
    private account account;
    private subject subject;
    private error error;
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
                    socket.close();
                }
            }
            while (true) {
                idSubject = in.readInt();

                System.out.println("idSubject Client gui: " + idSubject);
                sendResultCheckIdSubject();
                break;
            }
            while (true){
                error = (error) in.readObject();
                AddError();
                //gửi danh sách lỗi của subject
                out.writeObject(resultErrorCompareIdSubject());

                System.out.println("Them loi vi pham thanh cong");
                System.out.println(error);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean resultCompareAccount() throws SQLException {
        conn = ConnectDB.ConnectDb();
        account account1 = new account();

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
            out.writeObject(resultSubjectCompareIdSubject());
            out.writeObject(resultErrorCompareIdSubject()) ;
            out.flush();

            System.out.println(resultSubjectCompareIdSubject());
            System.out.println(resultErrorCompareIdSubject());
            System.out.println("Da gui subject va error cua subject");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private subject resultSubjectCompareIdSubject() {
        conn = ConnectDB.ConnectDb();
        subject subject = new subject();
        try {
            String value1 = String.valueOf(idSubject);
            String sql = "select * from subject where ID_subject= '" + value1 + "' ";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                subject = new subject(parseInt(rs.getString("ID_subject")), rs.getString("HovaTen"), rs.getDate("NgayThangNamSinh"), rs.getString("GioiTinh"), rs.getString("QuocTich"), rs.getString("QueQuan"), rs.getString("NoiThuongTru"), rs.getBytes("URL") );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subject;
    }
    private List<error> resultErrorCompareIdSubject() {
        conn = ConnectDB.ConnectDb();
        List<error> list = new ArrayList<>();
        try {
            String value1 = String.valueOf(idSubject);
            String sql = "select * from error where ID_subject= '" + value1 + "' ";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new error(rs.getString("TenLoiViPham"), rs.getString("MucDoPhat"), rs.getDate("NgayThangNam"), rs.getString("GhiChu")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    private void AddError(){
        conn = ConnectDB.ConnectDb();
        try {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            String value3 = error.getTenLoiViPham();
            String value4 = error.getMucDoPhat();
            String value5 = dateformat.format(error.getNgayThangNam());
            String value6 = error.getGhiChu();
            String value7 = idSubject.toString();
            String sql = "insert into error(TenLoiViPham, MucDoPhat, NgayThangNam, GhiChu, ID_subject) values( '" + value3 + "', '" + value4 + "', '" + value5 + "', '" + value6 + "', '" + value7 + "')";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
