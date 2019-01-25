package lgsapp.controlers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TitleBarController implements Initializable {

    @FXML
    void close(MouseEvent event) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //confirmation alert
        alert.setTitle("Exit");
        String s = "Are You Sure Want to exit..!";
        alert.setContentText(s);
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();


        }




    }

    @FXML
    void max(MouseEvent event) {
        Node node = (Node) event.getSource();


        Stage stage = (Stage) node.getScene().getWindow();

        stage.setFullScreenExitHint(" ");
        stage.setFullScreen(true);

    }

    @FXML
    void min(MouseEvent event) {

        Node node = (Node) event.getSource();


        Stage stage = (Stage) node.getScene().getWindow();

        stage.setIconified(true);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
