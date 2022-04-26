package hr.datastock.controllers;

import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.controllers.service.RobaControllerService;
import hr.datastock.entities.RobaEntity;
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
    private final UtilService utilService;
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
    public RobaEntity setButtonSave() {
        RobaEntity artikl = null;
        try {
            artikl = this.robaControllerService.saveArtikl(this);
            if (artikl != null) log.info("Roba record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        return artikl;
    }
    public RobaEntity setButtonUpdate() {
        RobaEntity artikl = null;
        try {
            artikl = this.robaControllerService.updateArtikl(this);
            if (artikl != null) log.info("Roba record update successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        return artikl;
    }
    public void setButtonDelete() {
        try {
            this.robaControllerService.deleteArtikl(this);
            log.info("Roba record deleted successful");
        } catch (RuntimeException ex) {
            this.utilService.isEntityUnableToRemove();
            log.error(ex.getMessage(), ex.fillInStackTrace());
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
