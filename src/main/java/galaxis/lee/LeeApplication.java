package galaxis.lee;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import galaxis.lee.controller.LoginController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeeApplication extends AbstractJavaFxApplicationSupport {
    //define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;
    public static void main(String[] args) {

        // SpringApplication.run(LeeApplication.class, args);
        launch(LeeApplication.class, LoginController.class,args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        // //we gonna remove the borderless thingie.
        // stage.initStyle(StageStyle.DECORATED);
        // stage.setMaximized(false);
        // stage.setTitle("小工具");
        // //grab your root here
        // root.setOnMousePressed(new EventHandler<MouseEvent>() {
        //     @Override
        //     public void handle(MouseEvent event) {
        //         xOffset = event.getSceneX();
        //         yOffset = event.getSceneY();
        //     }
        // });
        //
        // stage.getIcons().add(new Image(
        //         LeeApplication.class.getResourceAsStream("/image/2.png")));
        // //sorry about that - Windows defender issue.
        // //move around here
        // root.setOnMouseDragged(new EventHandler<MouseEvent>() {
        //     @Override
        //     public void handle(MouseEvent event) {
        //         stage.setX(event.getScreenX() - xOffset);
        //         stage.setY(event.getScreenY() - yOffset);
        //     }
        // });
        //
        // Scene scene = new Scene(root);
        // stage.setScene(scene);
        // stage.show();
    }
}
