package lgsapp.controlers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

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
    ImageView img_frme;

    @FXML
    void btntoback(MouseEvent event) throws IOException {

        //load dashboard
        Parent root = FXMLLoader.load(getClass().getResource("/lgsapp/views/dashbord.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setFullScreen (true);




    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {



      //  Image image = new Image("lgsapp/myicons/look.png");  //refresh image view
       // img_frme.setImage(image);

    }
}
