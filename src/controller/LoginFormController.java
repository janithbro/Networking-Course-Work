package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;

    public AnchorPane pane;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        ClientFormController.userName = txtUserName.getText();

        txtUserName.clear();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"))));
        stage.show();
    }

//    public void btnSignInOnAction(ActionEvent actionEvent) {
//    }
}
