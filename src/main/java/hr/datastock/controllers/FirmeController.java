package hr.datastock.controllers;

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

    @FXML
    public void initialize() {
        try {
            this.firmeControllerService.init(this);
            log.info("Firme controller initializing successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void tableViewData() {
        try {
            this.firmeControllerService.pluckingAllDataFromTableView(this);
            log.info("Data fetched successful from table view");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonSearch() {
        try {
            this.firmeControllerService.searchData(this);
            log.info("Data records searched successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public FirmeEntity setButtonSave() {
        FirmeEntity firmeEntity = null;
        try {
            firmeEntity = this.firmeControllerService.saveFirma(this);
            log.info("Firma record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        return firmeEntity;
    }

    public FirmeEntity setButtonUpdate() {
        FirmeEntity firmeEntity = null;
        try {
            firmeEntity = this.firmeControllerService.updateFirma(this);
            log.info("Firma record update successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        return firmeEntity;

    }

    public void setButtonDelete() {
        try {
            this.firmeControllerService.deleteFirma(this);
            log.info("Firma record deleted successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonClear() {
        try {
            this.firmeControllerService.clearRecords(this);
            log.info("Field records cleared successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

}
