package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;

    public static void navigate(Routes routes, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();

        switch (routes) {
            case LOGIN:
                initUI("LoginForm.fxml");
                break;

            case SERVER:
                initUI("ServerForm.fxml");
                break;

            case CLIENT:
                initUI("ClientForm.fxml");
                break;
        }
    }

    public static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader
                .load(Navigation.class.getResource("../src/view/"+location))
        );
    }
}
