package galaxis.lee.models;

/**
 * FXML User Interface enum
 * <p>
 * Created by Owen on 6/20/16.
 */
public enum FXMLPage {

    DB_CONFIG("fxml/ConfigDB.fxml"),
    ;

    private String fxml;

    FXMLPage(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return this.fxml;
    }


}
