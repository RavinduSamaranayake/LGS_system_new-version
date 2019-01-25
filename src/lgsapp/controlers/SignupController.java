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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    Connection con = DbConnect.getConnection();

    @FXML
    private TextField txt_uname;

    @FXML
    private TextField txt_email;

    @FXML
    private PasswordField txt_password;

    double x = 0, y = 0;

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
    void btnlogin(MouseEvent event) throws IOException {

        //load dashboard
        Parent root = FXMLLoader.load(getClass().getResource("/lgsapp/views/dashbord.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setFullScreen (true);




    }

    @FXML
    void signup(MouseEvent event) {

        String username = txt_uname.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();

        boolean emailvalid = isValidEmailAddress(email); //for valid email address
        boolean valid = true;

        if(username.isEmpty()){
            valid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't change your account details");
            String s = "The Username is empty";
            alert.setContentText(s);
            alert.showAndWait();


        }
        else if(email.isEmpty()){
            valid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't change your account details");
            String s = "The Email is empty";
            alert.setContentText(s);
            alert.showAndWait();

        }

        else if(!emailvalid){
            valid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't change your account details");
            String s = "The Email is not valid";
            alert.setContentText(s);
            alert.showAndWait();

        }
        else if(password.isEmpty()){
            valid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't change your account details");
            String s = "The Password is empty";
            alert.setContentText(s);
            alert.showAndWait();

        }
        else if(password.length()<8){
            valid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't change your account details");
            String s = "The Password must be at least 8 characters ";
            alert.setContentText(s);
            alert.showAndWait();

        }

        else if(password.length()>20){
            valid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't change your account details");
            String s = "The Password is too long ";
            alert.setContentText(s);
            alert.showAndWait();

        }

        else {



            try {


                PreparedStatement ps = null;

                String query = "UPDATE `users` SET `username`=?,`email`=?,`password`=?  WHERE `username`=? ";



                ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, password);

                String myuname = LoginController.getMyusername();
                ps.setString(4, myuname);



                if (ps.executeUpdate() > 0) {
                    System.out.println("user registered");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Change Your Account details");
                    String s = "Successfully Change Your Account details";
                    alert.setContentText(s);
                    alert.showAndWait();

                    txt_uname.setText("");
                    txt_email.setText("");
                    txt_password.setText("");


                   //load dashboard
                    Parent root = FXMLLoader.load(getClass().getResource("/lgsapp/views/dashbord.fxml"));
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setFullScreen (true);


                }

            } catch (SQLException e) {
                e.printStackTrace();
                valid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Can't change your account details");
                String s = "Please re Login and try..";
                alert.setContentText(s);
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public boolean isValidEmailAddress(String email) {  //for email validation
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
             String myuname = LoginController.getMyusername();
             PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM users WHERE username=? ");
            ps.setString(1,myuname);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                txt_uname.setText(myuname);
                txt_email.setText(rs.getString(2));
                txt_password.setText(rs.getString(3));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }









    }
}
