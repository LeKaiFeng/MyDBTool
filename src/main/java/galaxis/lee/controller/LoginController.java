package galaxis.lee.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.FXMLView;
import galaxis.lee.models.FXMLPage;
import galaxis.lee.utils.ConnectionUtil;
import galaxis.lee.utils.DialogBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author oXCToo
 */

@FXMLView(value = "/fxml/Login.fxml")
@FXMLController
public class LoginController extends BaseFXController {


    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    /// -- 
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    void testConnect() {
        con = ConnectionUtil.conDB();
        if (con != null) {
            DialogBuilder.alert(btnSignin, "数据库连接成功");
        } else {
            DialogBuilder.alert(btnSignin, "数据库连接失败");
        }
        showlable(con);
    }

    Connection conn;

    @FXML
    public void handleButtonAction(MouseEvent event) {
        conn = ConnectionUtil.conDB();
        if (event.getSource() == btnSignin) {
            //login here
            //不用登陆
            // if (logIn().equals("Success")) {
            try {
                if(conn == null){
                    DialogBuilder.alert(btnSignin,"请检查数据库配置");

                    return;
                }
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/OnBoard2.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }finally {
                if(conn != null){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            // }
        }
    }

    @FXML
    private Button btn_Config;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_Config.setOnMouseClicked(event -> {
            ConfigDBController controller = (ConfigDBController) loadFXMLPage("数据配置信息", FXMLPage.DB_CONFIG, false);

        });
        showlable(con);


    }

    public void showlable(Connection con) {
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            // lblErrors.setText("Server Error : Check");
            lblErrors.setText("数据库未连接/连接失败");
            con = ConnectionUtil.conDB();
        } else {
            lblErrors.setTextFill(Color.GREEN);
            // lblErrors.setText("Server is up : Good to go");
            lblErrors.setText("数据库已连接，可以登陆");
        }
    }

    public LoginController() {
        con = ConnectionUtil.conDB();
    }

    //we gonna use string to check for status
    private String logIn() {
        con = ConnectionUtil.conDB();
        String status = "Success";
        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if (email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM admins Where email = ? and password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}
