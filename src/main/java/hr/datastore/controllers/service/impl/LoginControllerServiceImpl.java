package hr.datastore.controllers.service.impl;

import hr.datastore.DatastoreJavaFXApplication;
import hr.datastore.controllers.LoginController;
import hr.datastore.controllers.service.LoginControllerService;
import hr.datastore.security.PasswordEncryptionService;
import hr.datastore.services.RacunService;
import hr.datastore.services.StageInitializerService;
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
        this.stageInitializerService.getRacunScreen(new DatastoreJavaFXApplication.StageReadyEvent(new Stage()));
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
