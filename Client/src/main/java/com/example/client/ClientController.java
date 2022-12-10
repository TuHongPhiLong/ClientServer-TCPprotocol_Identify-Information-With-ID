package com.example.client;

import com.example.entities.account;
import com.example.entities.error;
import com.example.entities.subject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController extends Component implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

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
    private Label lbl_GioiTinh;

    @FXML
    private Label lbl_HovaTen;

    @FXML
    private Label lbl_ID;

    @FXML
    private Label lbl_NgayThangNamSinh;

    @FXML
    private Label lbl_NoiThuongTru;

    @FXML
    private Label lbl_QueQuan;

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

    //Kết nối với Server
    private Socket socket;
    private com.example.entities.subject subject;
    private com.example.entities.account account;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean kiemTraTaiKhoan;


    @FXML
    void setbtn_connectServer(ActionEvent event) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            if (tF_UserName.getText().equals("") || tF_PassWord.getText().equals("") || tF_ServerHost.getText().equals("") || tF_Port.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Warning");
                alert.setContentText("Nhập tên đăng nhập và mật khẩu!");

                alert.showAndWait();
            } else {
                this.socket = new Socket(tF_ServerHost.getText(), Integer.parseInt(tF_Port.getText()));

                int id = Integer.parseInt(String.valueOf(tF_UserName.getText()));
                String password = String.valueOf(tF_PassWord.getText());
                account = new account(id, password);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            out = new ObjectOutputStream(socket.getOutputStream());
                            in = new ObjectInputStream(socket.getInputStream());

                            sendAcccount(account);

                            while (true) {
                                kiemTraTaiKhoan = in.readBoolean();
                                System.out.println("Da nhan ket qua cua Server: " + kiemTraTaiKhoan);
                                if (kiemTraTaiKhoan) {
                                    System.out.println("Account dung");
                                    System.out.println("\nDa ket noi!");
                                    break;
                                } else {
                                    System.out.println("Account sai, nhap lai!!!");
                                    socket.close();
                                }

                            }
                            while (true) {
                                subject = (subject) in.readObject();

                                System.out.println(subject.toString());
                                System.out.println("Da nhan subject");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                executorService.execute(thread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendIdSubject() {
        try {
            out.writeInt(Integer.parseInt(tF_idSubject.getText()));
            out.flush();
            System.out.println("Da gui id cua subject");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAcccount(account account) {
        try {
            out.writeObject(account);
            out.flush();
            System.out.println("Da gui Acount can kiem tra");
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
