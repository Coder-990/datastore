package hr.datastock.controllers.service.impl;

import hr.datastock.DatastoreJavaFXApplication;
import hr.datastock.controllers.LoginController;
import hr.datastock.controllers.service.LoginControllerService;
import hr.datastock.security.PasswordEncryptionService;
import hr.datastock.services.RacunService;
import hr.datastock.services.StageInitializerService;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginControllerServiceImpl implements LoginControllerService {

    private final PasswordEncryptionService passwordEncryptionService;
    private final StageInitializerService stageInitializerService;
    private final RacunService racunService;

    @Override
    public void close(final LoginController loginController) {
        loginController.getButtonClose().getScene().getWindow().hide();
    }

    @Override
    public void register(final LoginController loginController) throws IOException {
        this.stageInitializerService.getRacunScreen();
        this.close(loginController);
    }

    @Override
    public void userLogin(final LoginController loginController) {
        this.racunService.login(loginController.getTextFieldUserId().getText(),
                this.passwordEncryptionService.createMD5(loginController.getTextFieldPassword().getText()));
        this.stageInitializerService.getMainMenuScreen(new DatastoreJavaFXApplication.StageReadyEvent(new Stage()));
        this.close(loginController);
    }

    @Override
    public String getInputDataForDialogCheck(final LoginController loginController) {
        final List<String> checkList = new ArrayList<>();
        if (loginController.getTextFieldUserId().getText().trim().isEmpty()) checkList.add("UserId!");
        if (loginController.getTextFieldPassword().getText().trim().isEmpty()) checkList.add("Password!");
        return String.join("\n", checkList);
    }
}
