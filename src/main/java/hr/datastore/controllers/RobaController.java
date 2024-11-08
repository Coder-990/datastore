package hr.datastore.controllers;

import hr.datastore.dialogutil.DialogService;
import hr.datastore.controllers.service.RobaControllerService;
import hr.datastore.entities.RobaEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RobaController {

    private final RobaControllerService robaControllerService;
    private final DialogService dialogService;
    @Getter
    @FXML
    private TextField textFieldNaziv;
    @Getter
    @FXML
    private TextField textFieldCijena;
    @Getter
    @FXML
    private TextField textFieldKolicina;
    @Getter
    @FXML
    private TextField textFieldJedinicaMjere;
    @Getter
    @FXML
    private TableView<RobaEntity> tableView;
    @Getter
    @FXML
    private TableColumn<RobaEntity, Long> tableColumnId;
    @Getter
    @FXML
    private TableColumn<RobaEntity, String> tableColumnNaziv;
    @Getter
    @FXML
    private TableColumn<RobaEntity, BigDecimal> tableColumnCijena;
    @Getter
    @FXML
    private TableColumn<RobaEntity, Integer> tableColumnKolicina;
    @Getter
    @FXML
    private TableColumn<RobaEntity, String> tableColumnJedinicnaMjera;
    @Getter
    @FXML
    private TextArea textAreaOpis;
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
            this.robaControllerService.init(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Roba controller initializing successful");
    }

    public void getValuesFromTables() {
        try {
            this.robaControllerService.pluckSelectedDataFromTableViewRoba(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Data fetched successful from table view");
    }

    public void setButtonSearch() {
        try {
            this.robaControllerService.searchData(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Roba records searched successful");
    }

    public void setButtonSave() {
        try {
            if (this.robaControllerService.saveArtikl(this) != null) log.info("Roba record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonUpdate() {
        try {
            if (this.robaControllerService.updateArtikl(this) != null)
                log.info("Roba record update successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonDelete() {
        try {
            this.robaControllerService.deleteArtikl(this);
            log.info("Roba record deleted successful");
        } catch (RuntimeException ex) {
            this.dialogService.isEntityUnableToRemove();
            log.error(ex.getMessage());
        }
    }

    public void setButtonClear() {
        try {
            this.robaControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Field records cleared successful");
    }
}
