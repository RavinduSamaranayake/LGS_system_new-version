package lgsapp.controlers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lgsapp.helpers.DbConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    static String myusername = "";

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField pf_password;

   @FXML
    void btnlogin(MouseEvent event) throws SQLException, IOException {


        String username, password;
        PreparedStatement ps;

        username = tf_username.getText();
        password = pf_password.getText();

        myusername = username;

        Connection con = DbConnect.getConnection();

       try {
           ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password =?");
           ps.setString(1,username);
           ps.setString(2, password);
           ResultSet rs = ps.executeQuery();

           String msg = "Text saved: ";
           
           if (rs.next()) {
               System.out.print("Login Sucess");
               Parent root = FXMLLoader.load(getClass().getResource("/lgsapp/views/dashbord.fxml"));

               Node node = (Node) event.getSource();

               Stage stage = (Stage) node.getScene().getWindow();

               stage.setScene(new Scene(root));

               stage.setFullScreen (true);

           } else {


               System.out.print("Login fail");

               //Add dialog box

               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Login Error");
               String s = "Enter The valid user name or password ";
               alert.setContentText(s);
               alert.showAndWait();







       }


       } catch (Exception e) {
           System.out.println("Login fail with sqlite error");
           System.out.println(e);
       }
   }


    double x = 0, y =0;
    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void dragged(MouseEvent event) {

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }


    @FXML
    void linksignup(MouseEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/lgsapp/views/signup.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }

    public static String getMyusername(){
        return myusername;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
