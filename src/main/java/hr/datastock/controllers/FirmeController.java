package hr.datastock.controllers;

import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.controllers.service.FirmeControllerService;
import hr.datastock.entities.FirmeEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FirmeController {
    private final FirmeControllerService firmeControllerService;
    private final UtilService utilService;

    @Getter
    @FXML
    private TextField textFieldNaziv;
    @Getter
    @FXML
    private TextField textFieldOIB;
    @Getter
    @FXML
    private TableView<FirmeEntity> tableView;
    @Getter
    @FXML
    private TableColumn<FirmeEntity, Long> tableColumnId;
    @Getter
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnNaziv;
    @Getter
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnOIB;

    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonGetDataFromTable;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonClearFields;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;

    public void initialize() {
        try {
            this.firmeControllerService.init(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Firme controller initializing successful");
    }

    public void getValuesFromTables() {
        try {
            this.firmeControllerService.pluckSelectedDataFromTableViewFirma(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Data fetched successful");
    }

    public void setButtonSearch() {
        try {
            this.firmeControllerService.searchData(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Record searched successful");
    }

    public FirmeEntity setButtonSave() {
        FirmeEntity firma = null;
        try {
            firma = this.firmeControllerService.saveFirma(this);
            if (firma != null) log.info("Record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        return firma;
    }

    public FirmeEntity setButtonUpdate() {
        FirmeEntity firma = null;
        try {
            firma = this.firmeControllerService.updateFirma(this);
            if (firma != null) log.info("Record update successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        return firma;
    }

    public void setButtonDelete() {
        try {
            this.firmeControllerService.deleteFirma(this);
        } catch (RuntimeException ex) {
            this.utilService.isEntityUnableToRemove();
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Record deleted successful");
    }

    public void setButtonClear() {
        try {
            this.firmeControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Field records cleared successful");
    }

}
