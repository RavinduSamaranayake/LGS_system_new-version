package lgsapp;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception {




        Parent root = FXMLLoader.load(getClass().getResource("/lgsapp/views/welcome.fxml"));
        Scene scene = new Scene(root);

        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.show();  //show splash screen

        PauseTransition splashScreenDelay = new PauseTransition(Duration.seconds(5));
        splashScreenDelay.setOnFinished(f -> {

            stage.hide();  //hide splash screen

            try {  //open login screen
                Parent rooti = FXMLLoader.load(getClass().getResource("/lgsapp/views/login.fxml"));
                Scene sceene = new Scene(rooti);

                sceene.setFill(Color.TRANSPARENT);

                stage.setScene(sceene);

               // stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }





        });
        splashScreenDelay.playFromStart();
    }



    public static void main(String[] args) {
        launch(args);
    }
}