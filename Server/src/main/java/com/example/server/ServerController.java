package com.example.server;


import com.example.library.identifyInformationWithPictures_ClientServerSocket;
import com.example.library.sendAndReceiveSubject_ClientServerSocket;
import com.example.server.model.error;
import com.example.server.model.subject;
import com.example.server.model.account;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

//https://devindeep.com/face-recognition-with-java/
public class ServerController extends Component implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateTable_subject();
        combobox.setItems(list_GioiTinh);
        combobox1.setItems(list_GioiTinh);
    }

    @FXML
    Button btn_page_profile;
    @FXML
    Pane pane_profile;
    @FXML
    Button btn_logout;
    /////////
    @FXML
    Button btn_page_initServer;
    @FXML
    Pane pane_initServer;
    @FXML
    Button btn_initServer;
    @FXML
    TextField tF_Port;
    /////////
    @FXML
    Button btn_page_edit;
    @FXML
    Pane pane_edit;
    @FXML
    AnchorPane pane_page_add;
    @FXML
    AnchorPane pane_page_modify;
    @FXML
    AnchorPane pane_page_review;
    @FXML
    AnchorPane pane_page_addError;
    @FXML
    Button btn_add;
    @FXML
    Button btn_delete;
    @FXML
    Button btn_modify;
    @FXML
    Button btn_addError;
    @FXML
    Button btn_page_review;
    @FXML
    TextField tF_search;
    @FXML
    DatePicker datepicker;
    @FXML
    ComboBox<String> combobox;
    @FXML
    TextField tF_HovaTen;
    @FXML
    TextField tF_ID;
    @FXML
    TextField tF_NoiThuongTru;
    @FXML
    TextField tF_QueQuan;
    @FXML
    TextField tF_QuocTich;
    @FXML
    DatePicker datepicker1;
    @FXML
    ComboBox<String> combobox1;
    @FXML
    TextField tF_HovaTen1;
    @FXML
    TextField tF_ID1;
    @FXML
    TextField tF_NoiThuongTru1;
    @FXML
    TextField tF_QueQuan1;
    @FXML
    TextField tF_QuocTich1;
    @FXML
    Label lbl_ID_subject;
    @FXML
    private DatePicker datepicker2;
    @FXML
    private TextField tF_ID2;
    @FXML
    private TextField tF_GhiChu2;
    @FXML
    private TextField tF_MucDoPhat2;
    @FXML
    private TextField tF_TenLoiViPham2;
    @FXML
    private TextField tF_HovaTen2;
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
    public TableColumn<subject, Integer> col_ID;
    @FXML
    public TableColumn<subject, String> col_HovaTen;
    @FXML
    public TableColumn<subject, Date> col_NgayThangNamSinh;
    @FXML
    public TableColumn<subject, String> col_GioiTinh;
    @FXML
    public TableColumn<subject, String> col_QuocTich;
    @FXML
    public TableColumn<subject, String> col_QueQuan;
    @FXML
    public TableColumn<subject, String> col_NoiThuongTru;
    @FXML
    private TableColumn<error, String> col_TenLoiViPham;
    @FXML
    private TableColumn<error, String> col_MucDoPhat;
    @FXML
    private TableColumn<error, String> col_NgayThangNam;
    @FXML
    private TableColumn<error, String> col_GhiChu;
    @FXML
    public TableView<subject> table_subject;
    @FXML
    public TableView<error> table_error;
    ObservableList<subject> list_subject;
    ObservableList<error> list_error;
    ObservableList<subject> list_search;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private Button btn_uploadFromFile;
    ObservableList<String> list_GioiTinh = FXCollections.observableArrayList("Nam", "Nữ");
    Connection conn = null;
    private identifyInformationWithPictures_ClientServerSocket mySocketReceiveImage;
    private sendAndReceiveSubject_ClientServerSocket mySocketSendSubject;


    @FXML
    void setBtn_page_profile(ActionEvent event) {
        pane_profile.setVisible(!pane_profile.isVisible());
    }

    @FXML
    void setBtn_logout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) btn_logout.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
    }

    @FXML
    void setbtn_page_initServer(ActionEvent event) {
        pane_profile.setVisible(false);
        pane_initServer.setVisible(true);
        pane_edit.setVisible(false);
    }

    //Khởi tạo server và cụm chức năng gửi và nhận
    private TextField tF_idSubject;
    private static Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ObjectOutputStream out1;
    private ObjectInputStream in1;
    private int idSubject;
    private account account;
    private int clientNumber = 0;

    private static final ArrayList<Socket> listSocket = new ArrayList<>();

    //https://topdev.vn/blog/xay-dung-ung-dung-client-server-voi-socket-trong-java/
    @FXML
    private void setBtn_initServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(parseInt(tF_Port.getText()));
        System.out.println("Da khoi tao Server");
        System.out.println("Dang cho Client");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,//corePoolSize
                100,//maximumPoolSize
                10,//thread timeout
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8) // queueCapacity
        );
        //cach 2:
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        socket = serverSocket.accept();
                        ServerThread serverThread = new ServerThread(socket, clientNumber++);
                        executor.execute(serverThread);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

    }

    private com.example.server.model.subject resultCompareIdSubject() {
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

    @FXML
    void setbtn_page_edit(ActionEvent event) {
        pane_profile.setVisible(false);
        pane_initServer.setVisible(false);
        pane_edit.setVisible(true);
    }

    //
    //Cụm chức năng Thêm
    @FXML
    void setbtn_page_add() {
        pane_page_add.setVisible(true);
    }
    String filename;
    String filename1;
    @FXML
    void setBtn_add() {
        conn = ConnectDB.ConnectDb();
        String sql = "insert into subject (ID_subject, HovaTen, NgayThangNamSinh, GioiTinh, QuocTich, QueQuan, NoiThuongTru, URL) values(?,?,?,?,?,?,?,?)";
        try {
            //filename là String lấy từ hàm setbtn_uploadFromFile() nếu upload từ máy tính còn từ camera thì từ openCamera()
            File imageFile = new File(filename);
            FileInputStream fileInput = new FileInputStream(imageFile);

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, tF_ID.getText());
            pst.setString(2, tF_HovaTen.getText());
            pst.setDate(3, java.sql.Date.valueOf(datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            pst.setString(4, combobox.getValue());
            pst.setString(5, tF_QuocTich.getText());
            pst.setString(6, tF_QueQuan.getText());
            pst.setString(7, tF_NoiThuongTru.getText());
            pst.setBinaryStream(8, fileInput, imageFile.length());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Thêm thành công!");
            setBtn_cancel2();
            UpdateTable_subject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void setbtn_uploadFromFile() {
        Stage stage = (Stage) pane_page_add.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn hình từ file trên máy tính");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image FIles", "*.jpg", "*png");
        fc.getExtensionFilters().add(imageFilter);
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            filename = file.getAbsolutePath();
            System.out.println(filename);
            Image image = new Image(file.toURI().toString());
            imageView1.setImage(image);
        }
        System.out.println(file);
    }

    //
    //Nút Xoá
    @FXML
    void setBtn_delete() {
        conn = ConnectDB.ConnectDb();
        try {
            String value1 = tF_ID1.getText();
            String sql = "delete from subject where ID_subject='" + value1 + "' ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Xoá thành công!");
            setBtn_cancel();
            UpdateTable_subject();
            cleanTextfield();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    //Cụm chức năng Sửa
    @FXML
    void setBtn_page_modify(ActionEvent event) {
        pane_page_modify.setVisible(true);
        setimageView2();
    }

    @FXML
    void setBtn_modify(ActionEvent event) {
        conn = ConnectDB.ConnectDb();
        String value1 = tF_ID1.getText();
        String sql = "update subject set HovaTen = ?, NgayThangNamSinh = ?, GioiTinh = ?, QuocTich = ?, QueQuan = ?, NoiThuongTru = ?, URL = ? where ID_subject = '" + value1 + "' ";
        try {
            //filename là String lấy từ hàm setbtn_uploadFromFile
            File imageFile = new File(filename1);
            FileInputStream fileInput = new FileInputStream(imageFile);

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, tF_HovaTen1.getText());
            pst.setDate(2, java.sql.Date.valueOf(datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            pst.setString(3, combobox1.getValue());
            pst.setString(4, tF_QuocTich1.getText());
            pst.setString(5, tF_QueQuan1.getText());
            pst.setString(6, tF_NoiThuongTru1.getText());
            pst.setBinaryStream(7, fileInput, imageFile.length());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Sửa thành công!");
            setBtn_cancel();
            UpdateTable_subject();
            cleanTextfield();
            filename1 = null;
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void setimageView2() {
        conn = ConnectDB.ConnectDb();
        try {
            String value1 = tF_ID1.getText();
            String sql = "select URL from subject where ID_subject= '" + value1 + "' ";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            InputStream input = null;
            while (rs.next()) {
                input = rs.getBinaryStream("URL");
            }
            if (input != null) {
                Image image = new Image(input, 151.181, 226.771, true, true);
                imageView2.setImage(image);
            } else {
                JOptionPane.showMessageDialog(null, "Hãy cập nhập hình ảnh");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void setbtn_uploadFromFile1() {
        Stage stage = (Stage) pane_page_modify.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn hình từ file trên máy tính");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image FIles", "*.jpg", "*png");
        fc.getExtensionFilters().add(imageFilter);
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            filename1 = file.getAbsolutePath();
            System.out.println(filename1);
            Image image = new Image(file.toURI().toString());
            imageView2.setImage(image);
        }
        System.out.println(file);
    }
    //

    //Tìm kiếm đang lỗi
    //Cách 1:
    @FXML
    void search_subject1() {
        col_ID.setCellValueFactory(new PropertyValueFactory<subject, Integer>("ID"));
        col_HovaTen.setCellValueFactory(new PropertyValueFactory<subject, String>("HovaTen"));
        col_NgayThangNamSinh.setCellValueFactory(new PropertyValueFactory<subject, Date>("NgayThangNamSinh"));
        col_GioiTinh.setCellValueFactory(new PropertyValueFactory<subject, String>("GioiTinh"));
        col_QuocTich.setCellValueFactory(new PropertyValueFactory<subject, String>("QuocTich"));
        col_QueQuan.setCellValueFactory(new PropertyValueFactory<subject, String>("QueQuan"));
        col_NoiThuongTru.setCellValueFactory(new PropertyValueFactory<subject, String>("NoiThuongTru"));

        //đưa dữ liệu vào chuổi list_search.
        list_search = ConnectDB.getDatasubject();
        //hiện dữ liệu từ chuổi list_search lên bản.
        table_subject.setItems(list_search);
        //tạo chuổi filteredList bao gồm chuổi list_search và  predicate.
        // Cú pháp biểu thức lambda (argument-list) -> {body}.
        FilteredList<subject> filteredList = new FilteredList<>(list_search, (subject b) -> true);

        // biểu thức lambda.
        tF_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredList.setPredicate(subject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String integer = newValue.toLowerCase();

                if (String.valueOf(subject.getID()).toLowerCase().contains(integer)) {
                    return true; // Filter matches HovaTen
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return subject.getHovaTen().toLowerCase().contains(lowerCaseFilter); // Filter matches HovaTen
            });
        });
        SortedList<subject> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table_subject.comparatorProperty());
        table_subject.setItems(sortedList);
    }

    //Cách 2:
//    @FXML
//    void search_subject1() {
//        col_ID.setCellValueFactory(new PropertyValueFactory<subject, Integer>("ID"));
//        col_HovaTen.setCellValueFactory(new PropertyValueFactory<subject, String>("HovaTen"));
//        col_NgayThangNamSinh.setCellValueFactory(new PropertyValueFactory<subject, Date>("NgayThangNamSinh"));
//        col_GioiTinh.setCellValueFactory(new PropertyValueFactory<subject, String>("GioiTinh"));
//        col_QuocTich.setCellValueFactory(new PropertyValueFactory<subject, String>("QuocTich"));
//        col_QueQuan.setCellValueFactory(new PropertyValueFactory<subject, String>("QueQuan"));
//        col_NoiThuongTru.setCellValueFactory(new PropertyValueFactory<subject, String>("NoiThuongTru"));
//        list_search = ConnectDB.getDatasubject();
//
//        conn = ConnectDB.ConnectDb();
//        try {
//            if (tF_search.getText() != null) {
//                String value1 = tF_search.getText();
//                String sql = "select * from subject where ID_subject = '" + value1 + "' ";
//                pst = conn.prepareStatement(sql);
//                ResultSet rs = pst.executeQuery();
//                ObservableList<subject> list_After_search = FXCollections.observableArrayList();
//                while (rs.next()) {
//                    list_After_search.add(new subject(Integer.parseInt(rs.getString("ID_subject")), rs.getString("HovaTen"), rs.getDate("NgayThangNamSinh"), rs.getString("GioiTinh"), rs.getString("QuocTich"), rs.getString("QueQuan"), rs.getString("NoiThuongTru")));
//                }
//                table_subject.setItems(list_After_search);
//                conn.close();
//            } else table_subject.setItems(list_search);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    //

    //Cụm chức năng Thêm lỗi
    @FXML
    void setBtn_page_addError(ActionEvent event) throws IOException {
        pane_page_addError.setVisible(true);
    }

    @FXML
    void setBtn_addError(ActionEvent event) {
        conn = ConnectDB.ConnectDb();
        try {
            String value1 = tF_ID2.getText();
            String value3 = tF_TenLoiViPham2.getText();
            String value4 = tF_MucDoPhat2.getText();
            String value5 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String value6 = tF_GhiChu2.getText();
            String value7 = lbl_ID_subject.getText();
            String sql = "insert into error(ID_error, TenLoiViPham, MucDoPhat, NgayThangNam, GhiChu, ID_subject) values('" + value1 + "', '" + value3 + "', '" + value4 + "', '" + value5 + "', '" + value6 + "', '" + value7 + "')";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Thêm lỗi vi phạm thành công");
            setBtn_cancel();
            cleanTextfield();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    //Cụm chức năng xem thông tin
    @FXML
    void setBtn_page_review(ActionEvent event) {
        pane_page_review.setVisible(true);
        UpdateTable_error();
        setimageView();
    }

    @FXML
    void setimageView() {
        conn = ConnectDB.ConnectDb();
        try {
            String value1 = lbl_ID.getText();
            String sql = "select URL from subject where ID_subject= '" + value1 + "' ";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            InputStream input = null;
            while (rs.next()) {
                input = rs.getBinaryStream("URL");
            }
            if (input != null) {
                Image image = new Image(input, 151.181, 226.771, true, true);
                imageView.setImage(image);
            } else {
                JOptionPane.showMessageDialog(null, "Hãy cập nhập hình ảnh");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //

    public void UpdateTable_subject() {
        col_ID.setCellValueFactory(new PropertyValueFactory<subject, Integer>("ID"));
        col_HovaTen.setCellValueFactory(new PropertyValueFactory<subject, String>("HovaTen"));
        col_NgayThangNamSinh.setCellValueFactory(new PropertyValueFactory<subject, Date>("NgayThangNamSinh"));
        col_GioiTinh.setCellValueFactory(new PropertyValueFactory<subject, String>("GioiTinh"));
        col_QuocTich.setCellValueFactory(new PropertyValueFactory<subject, String>("QuocTich"));
        col_QueQuan.setCellValueFactory(new PropertyValueFactory<subject, String>("QueQuan"));
        col_NoiThuongTru.setCellValueFactory(new PropertyValueFactory<subject, String>("NoiThuongTru"));

        list_subject = ConnectDB.getDatasubject();
        table_subject.setItems(list_subject);
    }

    public void UpdateTable_error() {
        col_TenLoiViPham.setCellValueFactory(new PropertyValueFactory<error, String>("TenLoiViPham"));
        col_MucDoPhat.setCellValueFactory(new PropertyValueFactory<error, String>("MucDoPhat"));
        col_NgayThangNam.setCellValueFactory(new PropertyValueFactory<error, String>("NgayThangNam"));
        col_GhiChu.setCellValueFactory(new PropertyValueFactory<error, String>("GhiChu"));

        list_error = ConnectDB.getDataerror(lbl_ID);
        table_error.setItems(list_error);
    }

    //Khi chọn đối trượng trong tableView
    @FXML
    void getSelected(MouseEvent event) throws ParseException {
        int index = -1;
        index = table_subject.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        //pst.setDate(3, java.sql.Date.valueOf(datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = col_NgayThangNamSinh.getCellData(index).toString();
        // datapicker yêu cầu input localdate nên phải tạo localdate rồi cho chuổi date lấy từ cột của tableview và một datetimeformatter.
        LocalDate localDate = LocalDate.parse(date, formatter);

        tF_ID1.setText(col_ID.getCellData(index).toString());
        tF_HovaTen1.setText(col_HovaTen.getCellData(index));
        datepicker1.setValue(localDate);
        combobox1.setValue(col_GioiTinh.getCellData(index));
        tF_QuocTich1.setText(col_QuocTich.getCellData(index));
        tF_QueQuan1.setText(col_QueQuan.getCellData(index));
        tF_NoiThuongTru1.setText(col_NoiThuongTru.getCellData(index));

        lbl_ID.setText(col_ID.getCellData(index).toString());
        lbl_HovaTen.setText(col_HovaTen.getCellData(index));
        lbl_NgayThangNamSinh.setText(String.valueOf(col_NgayThangNamSinh.getCellData(index)));
        lbl_GioiTinh.setText(col_GioiTinh.getCellData(index));
        lbl_QuocTich.setText(col_QuocTich.getCellData(index));
        lbl_QueQuan.setText(col_QueQuan.getCellData(index));
        lbl_NoiThuongTru.setText(col_NoiThuongTru.getCellData(index));

        lbl_ID_subject.setText(col_ID.getCellData(index).toString());
    }

    //
    public void cleanTextfield() {
        tF_ID.clear();
        tF_HovaTen.clear();
        datepicker.setValue(null);
        combobox.setValue("");
        tF_QuocTich.clear();
        tF_QueQuan.clear();
        tF_NoiThuongTru.clear();
        tF_ID1.clear();
        tF_HovaTen1.clear();
        datepicker1.setValue(null);
        combobox1.setValue("");
        tF_QuocTich1.clear();
        tF_QueQuan1.clear();
        tF_NoiThuongTru1.clear();
        lbl_ID.setText("");
        lbl_HovaTen.setText("");
        lbl_NgayThangNamSinh.setText("");
        lbl_GioiTinh.setText("");
        lbl_QuocTich.setText("");
        lbl_QueQuan.setText("");
        lbl_NoiThuongTru.setText("");
        tF_ID2.clear();
        tF_HovaTen2.clear();
        tF_TenLoiViPham2.clear();
        tF_MucDoPhat2.clear();
        datepicker.setValue(null);
        tF_GhiChu2.clear();
        lbl_ID_subject.setText("");
        tF_search.clear();
    }

    @FXML
    void setBtn_cancel() {
        pane_page_add.setVisible(false);
        pane_page_modify.setVisible(false);
        pane_page_review.setVisible(false);
        pane_page_addError.setVisible(false);
        pane_edit.setVisible(true);
    }

    @FXML
    void setBtn_cancel2() {
        pane_page_add.setVisible(false);
        pane_edit.setVisible(true);
    }
}
