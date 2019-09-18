/**
 * Copyright (C),2019-08-22,Galaxis
 * Date:2019/8/22 0022 15:33
 * Author:LELE
 * Description:
 */

package galaxis.lee.controller;

import de.felixroske.jfxsupport.AbstractFxmlView;
import galaxis.lee.models.FXMLPage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseFXController extends AbstractFxmlView implements Initializable {

    private static final Logger _LOG = LoggerFactory.getLogger(BaseFXController.class);

    private Stage primaryStage;
    private Stage dialogStage;



    private static Map<FXMLPage, SoftReference<? extends BaseFXController>> cacheNodeMap = new HashMap<>();

    public BaseFXController loadFXMLPage(String title, FXMLPage fxmlPage, boolean cache) {
        SoftReference<? extends BaseFXController> parentNodeRef = cacheNodeMap.get(fxmlPage);
        //闪烁效果，无用
        // if(!primaryStage.isFocused()){
        //     primaryStage.requestFocus();
        // }
        if (cache && parentNodeRef != null) {
            return parentNodeRef.get();
        }

        URL skeletonResource = Thread.currentThread().getContextClassLoader().getResource(fxmlPage.getFxml());
        FXMLLoader loader = new FXMLLoader(skeletonResource);
        Parent loginNode;
        try {
            loginNode = loader.load();
            BaseFXController controller = loader.getController();
            dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.getIcons().add(new Image(
                    BaseFXController.class.getResourceAsStream("/image/database.png")));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            dialogStage.setScene(new Scene(loginNode));
            dialogStage.setMaximized(false);
            dialogStage.setResizable(false);
            dialogStage.show();
            controller.setDialogStage(dialogStage);
            // put into cache map
            SoftReference<BaseFXController> softReference = new SoftReference<>(controller);
            cacheNodeMap.put(fxmlPage, softReference);

            return controller;
        } catch (IOException e) {
            _LOG.error(e.getMessage(), e);
            //
            // AlertUtil.showErrorAlert(e.getMessage());
        }
        return null;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void showDialogStage() {
        if (dialogStage != null) {
            dialogStage.show();
        }
    }

    public void closeDialogStage() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }



}
