package com.example.client;

import com.example.entities.account;
import com.example.entities.error;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginClientController extends Component implements Initializable {
    public LoginClientController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    TextField Tf_ServerHost1;
    @FXML
    TextField Tf_Port1;
    @FXML
    TextField Tf_usename;
    @FXML
    PasswordField Pf_password;
    @FXML
    Button btn_Login;


    @FXML
    Button btn_page_profile;
    @FXML
    Pane pane_profile;
    @FXML
    Button btn_logout;
    /////////
    @FXML
    Button btn_page_connectServer;
    @FXML
    Pane pane_connectServer;
    @FXML
    Button btn_connectServer;
    @FXML
    TextField tF_ServerHost;
    @FXML
    TextField tF_Port;
    @FXML
    TextField tF_UserName;
    @FXML
    PasswordField tF_PassWord;

    /////////
    @FXML
    Button btn_page_search;
    @FXML
    Button btn_page_addError;
    @FXML
    Pane pane_search;
    @FXML
    AnchorPane pane_page_search;
    @FXML
    AnchorPane pane_page_addError;
    @FXML
    Button btn_search;
    @FXML
    ImageView imageView1;
    @FXML
    private TableColumn<error, String> col_TenLoiViPham;
    @FXML
    private TableColumn<error, String> col_MucDoPhat;
    @FXML
    private TableColumn<error, String> col_NgayThangNam;
    @FXML
    private TableColumn<error, String> col_GhiChu;
    @FXML
    private javafx.scene.control.Label lbl_GioiTinh;

    @FXML
    private javafx.scene.control.Label lbl_HovaTen;

    @FXML
    private javafx.scene.control.Label lbl_ID;

    @FXML
    private javafx.scene.control.Label lbl_NgayThangNamSinh;

    @FXML
    private javafx.scene.control.Label lbl_NoiThuongTru;

    @FXML
    private javafx.scene.control.Label lbl_QueQuan;

    @FXML
    private Label lbl_QuocTich;
    @FXML
    private TableView<error> table_error;
    @FXML
    private TextField tF_idSubject;


    ObservableList<error> list_error;

    @FXML
    void setbtn_page_profile(ActionEvent event) {
        if (pane_profile.isVisible()) pane_profile.setVisible(false);
        else pane_profile.setVisible(true);

    }

    @FXML
    void setbtn_page_connectServer(ActionEvent event) {
        pane_profile.setVisible(false);
        pane_connectServer.setVisible(true);
        pane_search.setVisible(false);
    }

    private boolean kiemTraTaiKhoan = false;
    private Socket socket;
    private com.example.entities.subject subject;
    private ObjectOutputStream out;
    private ObjectInputStream in ;

    public void setBtn_Login(ActionEvent event) throws IOException {
        if (Tf_usename.getText().equals("") || Pf_password.getText().equals("") || Tf_ServerHost1.getText().equals("") || Tf_Port1.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("Nhập tên đăng nhập và mật khẩu!");

            alert.showAndWait();
        } else {
            try {
                this.socket = new Socket(Tf_ServerHost1.getText(), Integer.parseInt(Tf_Port1.getText()));

                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                int id = Integer.parseInt(String.valueOf(Tf_usename.getText()));
                String password = String.valueOf(Pf_password.getText());

                account account = new account(id, password);

                out.writeObject(account);
                out.flush();
                System.out.println("Da gui Acount can kiem tra");
                if (kiemTraTaiKhoan == false) {
                    Thread th = new Thread() {
                        public void run() {
                            while (true) {
                                try {
                                    kiemTraTaiKhoan = in.readBoolean();
                                    System.out.println("Da nhan ket qua cua Server: " + kiemTraTaiKhoan);

                                    if (kiemTraTaiKhoan == true) {
                                        System.out.println("Account dung");
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Update UI here.
                                                try {
//                                                    FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("Client-view_test1.fxml"));
//                                                    Parent root1 = (Parent) fxmlLoader1.load();
//                                                    Stage stage = new Stage();
//                                                    stage.initModality(Modality.APPLICATION_MODAL);
//                                                    stage.setTitle("Client");
//                                                    stage.setScene(new Scene(root1));
//                                                    stage.show();
                                                    FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("Client-view_test1.fxml"));
                                                    Scene scene = new Scene(fxmlLoader1.load(), 850, 550);

                                                    Stage stage = (Stage) btn_Login.getScene().getWindow();
                                                    stage.setTitle("Client");
                                                    stage.setScene(scene);
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        });


                                    } else {
                                        System.out.println("Account sai");
                                    out.close();
                                    in.close();
                                    socket.close();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    th.start();
                } else {
                    System.out.println("\nDa ket noi!");

                    Thread th1 = new Thread() {
                        public void run() {
                            while (true) {
                                try {
                                    subject = (com.example.entities.subject) in.readObject();

                                    System.out.println(subject.toString());
                                    System.out.println("Da nhan subject");
                                } catch (IOException | ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    };
                    th1.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void sendAccount() {
        try {
            account account = new account(Integer.parseInt(String.valueOf(Tf_usename)), String.valueOf(Pf_password));
            out.writeObject(account);
            out.flush();
            System.out.println("Da gui Acount can kiem tra");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Kết nối với Server


    @FXML
    void setbtn_connectServer(ActionEvent event) {
        try {
//            Socket socketSendImage = new Socket(tF_ServerHost.getText(), Integer.parseInt(tF_Port.getText()));
//            Socket socketSendSubject = new Socket(tF_ServerHost.getText(), Integer.parseInt(tF_Port.getText()));
//
//            mySocketSendIamge = new identifyInformationWithPictures_ClientServerSocket(socketSendImage, tF_idSubject);
//            mySocketSendSubject = new sendAndReceiveSubject_ClientServerSocket(socketSendSubject);
//            System.out.println("\nDa ket noi!");

            this.socket = new Socket(tF_ServerHost.getText(), Integer.parseInt(tF_Port.getText()));

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            System.out.println("\nDa ket noi!");

            Thread th = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            subject = (com.example.entities.subject) in.readObject();

                            System.out.println(subject.toString());
                            System.out.println("Da nhan subject");
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendIdSubject() {
        //mySocketSendIamge.sendIdSubject(Integer.parseInt(tF_idSubject.getText()));
        try {


            out.writeInt(Integer.parseInt(tF_idSubject.getText()));
            out.flush();
            System.out.println("Da gui id cua subject");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void setbtn_page_search(ActionEvent event) {
        pane_profile.setVisible(false);
        pane_connectServer.setVisible(false);
        pane_search.setVisible(true);
    }

    @FXML
    void setbtn_search(ActionEvent event) throws IOException, InterruptedException {
        //sendImage();
        lbl_ID.setText(String.valueOf(subject.getID()));
        lbl_HovaTen.setText(String.valueOf(subject.getHovaTen()));
        lbl_NgayThangNamSinh.setText(String.valueOf(subject.getNgayThangNamSinh()));
        lbl_GioiTinh.setText(String.valueOf(subject.getGioiTinh()));
        lbl_QueQuan.setText(String.valueOf(subject.getQueQuan()));
        lbl_QuocTich.setText(String.valueOf(subject.getQuocTich()));
        lbl_NoiThuongTru.setText(String.valueOf(subject.getNoiThuongTru()));

        pane_page_search.setVisible(true);
    }

    @FXML
    void btn_sendID() {
        sendIdSubject();
    }

    @FXML
    void setBtn_page_addError() {
        pane_page_addError.setVisible(true);
    }

    @FXML
    void setBtn_addError() {
        pane_page_addError.setVisible(true);
    }


    @FXML
    void setBtn_cancel() { // Trở vê pane_search
        pane_page_search.setVisible(false);
    }

    @FXML
    void setBtn_cancel1() { // Trở vê pane_page_search
        pane_page_addError.setVisible(false);
    }


    public void UpdateTable_error() {
        col_TenLoiViPham.setCellValueFactory(new PropertyValueFactory<error, String>("TenLoiViPham"));
        col_MucDoPhat.setCellValueFactory(new PropertyValueFactory<error, String>("MucDoPhat"));
        col_NgayThangNam.setCellValueFactory(new PropertyValueFactory<error, String>("NgayThangNam"));
        col_GhiChu.setCellValueFactory(new PropertyValueFactory<error, String>("GhiChu"));

        list_error = null;
        table_error.setItems(list_error);
    }


}