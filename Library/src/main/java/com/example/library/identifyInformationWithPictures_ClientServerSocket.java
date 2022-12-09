package com.example.library;

import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class identifyInformationWithPictures_ClientServerSocket {
    private Socket socket;
    private TextField tF_idSubject;
    private int idSubject;
    private DataOutputStream out;
    private DataInputStream in;

    private ObjectOutputStream out1;
    private ObjectInputStream in1;

    static int counter = 0;

    public identifyInformationWithPictures_ClientServerSocket(Socket socket, TextField tF_idSubject) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.tF_idSubject = tF_idSubject;

        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());

//        out1 = new ObjectOutputStream(socket.getOutputStream());
//        in1 = new ObjectInputStream(socket.getInputStream());

        receive();
    }

    private void receive() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        idSubject = in.readInt();
                        System.out.println(idSubject);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }

    public void sendIdSubject(int idSubject){
        try {
            tF_idSubject.setText(String.valueOf(idSubject));

            out.writeInt(idSubject);
            out.flush();
            System.out.println("Da gui id cua subject");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void receive() {
//        Thread th = new Thread() {
//            public void run() {
//                while (true) {
//                    try {
//                        int fileContentLengh = in.readInt();
//
//                        if (fileContentLengh > 0) {
//                            byte[] fileContentBytes = new byte[fileContentLengh];
//                            in.readFully(fileContentBytes, 0, fileContentBytes.length);
//
//                            // convert array of bytes into file
//                            FileOutputStream fileOutputStream = new FileOutputStream("D:\\Phi Long\\HocTap\\DuAn\\identifyInformationWithPictures_ClientServer\\Server\\photos\\pictureFormClient\\"+ counter +".jpg");
//                            fileOutputStream.write(fileContentBytes);
//                            fileOutputStream.close();
//
//                            //System.out.println(Arrays.toString(fileContentBytes));
//                            System.out.println("Da nhan hinh anh");
//                            counter++;
//                        }
//
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        };
//        th.start();
//    }
//
//    public void sendImage(File fileToSend) throws IOException {
//        if (fileToSend == null) {
//            JOptionPane.showMessageDialog(null, "Chọn file trước khi gửi!");
//        } else {
//            FileInputStream fileInputStream = new FileInputStream(fileToSend.getAbsolutePath());
//
//            byte[] fileContenBytes = new byte[(int) fileToSend.length()];
//            fileInputStream.read(fileContenBytes);
//
//            out.writeInt(fileContenBytes.length);
//            out.write(fileContenBytes);
//            out.flush();
//            System.out.println("Da gui hinh anh");
//        }
//
//    }

    private boolean createFile(){

        return false;
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

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }
}

