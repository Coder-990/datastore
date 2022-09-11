package hr.datastock.controllers.service.impl;


import hr.datastock.DatastoreJavaFXApplication;
import hr.datastock.controllers.RacunController;
import hr.datastock.controllers.service.RacunControllerService;
import hr.datastock.dialogutil.DialogService;
import hr.datastock.entities.RacunEntity;
import hr.datastock.security.PasswordEncryptionService;
import hr.datastock.services.RacunService;
import hr.datastock.services.StageInitializerService;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RacunControllerServiceImpl implements RacunControllerService {

    private final DialogService dialogService;
    private final RacunService racunService;
    private final StageInitializerService stageInitializerService;
    private final PasswordEncryptionService passwordEncryptionService;

    @Override
    public RacunEntity saveUser(final RacunController racunController) {
        if (this.getInputDataForDialogCheck(racunController).isEmpty()) {
            final RacunEntity racun = this.registerNewUser(racunController);
            if (racun != null) this.dialogService.isUserRegistered(racun);
            this.clearRecords(racunController);
            return this.racunService.createAccount(racun);
        }
        this.dialogService.getWarningAlert(this.getInputDataForDialogCheck(racunController));
        return null;
    }

    @Override
    public void clearRecords(final RacunController racunController) {
        racunController.getTextFieldUserID().clear();
        racunController.getTextFieldPassword().clear();
    }

    @Override
    public void backToLogin(final RacunController racunController) {
        this.stageInitializerService.getLoginScreen(new DatastoreJavaFXApplication.StageReadyEvent(new Stage()));
        racunController.getButtonLogin().getScene().getWindow().hide();
    }

    private RacunEntity registerNewUser(final RacunController racunController) {
        return RacunEntity.builder()
                .userId(racunController.getTextFieldUserID().getText())
                .password(passwordEncryptionService.createMD5(racunController.getTextFieldPassword().getText()))
                .build();
    }

    @Override
    public String getInputDataForDialogCheck(final RacunController racunController) {
        final List<String> checkList = new ArrayList<>();
        if (racunController.getTextFieldUserID().getText().trim().isEmpty()) checkList.add("UserId!");
        if (racunController.getTextFieldPassword().getText().trim().isEmpty()) checkList.add("Password!");
        return String.join("\n", checkList);
    }
}
