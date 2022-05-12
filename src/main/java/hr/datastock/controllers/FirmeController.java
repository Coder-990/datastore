package hr.datastock.controllers;

import hr.datastock.dialogutil.DialogService;
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
    private final DialogService dialogService;

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

    public void setButtonSave() {
        try {
            if (this.firmeControllerService.saveFirma(this) != null) log.info("Record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonUpdate() {
        try {
            if (this.firmeControllerService.updateFirma(this) != null) log.info("Record update successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonDelete() {
        try {
            this.firmeControllerService.deleteFirma(this);
            log.info("Record deleted successful");
        } catch (RuntimeException ex) {
            this.dialogService.isEntityUnableToRemove();
            log.error(ex.getMessage());
        }
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
