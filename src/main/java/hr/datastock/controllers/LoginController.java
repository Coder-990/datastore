package hr.datastock.controllers;

import hr.datastock.entities.RacunEntity;
import hr.datastock.services.RacunService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginController {

    @FXML
    private Button buttonClose;

    @FXML
    private Button buttonLogin;

    @FXML
    private Label labelMessage;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private TextField textFieldUserId;

    private RacunEntity loginUser;

    @Autowired
    RacunService racunService;

    @FXML
     void setButtonClose() {
        buttonLogin.getScene().getWindow().hide();
    }

    @FXML
     void setButtonLogin() {
        try {
            racunService.login(textFieldUserId.getText(), textFieldPassword.getText());
            labelMessage.setText("Login Successful");
            MainController.getMainScreen();
            setButtonClose();
        }catch (RuntimeException ex){
            labelMessage.setText(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
