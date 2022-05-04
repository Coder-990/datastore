package hr.datastock.controllers;

import hr.datastock.controllers.service.StavkaIzdatniceControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaIzdatniceEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StavkaIzdatniceController {

    private final StavkaIzdatniceControllerService stavkaIzdatniceControllerService;
    @FXML @Getter
    private ComboBox<IzdatnicaEntity> comboBoxIzdatnica;
    @FXML @Getter
    private ComboBox<RobaEntity> comboBoxRoba;
    @FXML @Getter
    private TextField textFieldKolicina;
    @FXML @Getter
    private TextField textFieldFirma;
    @FXML @Getter
    private TextField textFieldArticle;
    @FXML @Getter
    private TableView<StavkaIzdatniceEntity> tableView;
    @FXML @Getter
    private TableColumn<StavkaIzdatniceEntity, Long> tableColumnId;
    @FXML @Getter
    private TableColumn<StavkaIzdatniceEntity, IzdatnicaEntity> tableColumnIdIzdatnice;
    @FXML @Getter
    private TableColumn<StavkaIzdatniceEntity, RobaEntity> tableColumnArticle;
    @FXML @Getter
    private TableColumn<StavkaIzdatniceEntity, Integer> tableColumnKolicina;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonStorno;
    @FXML
    private Button buttonClearFields;

    public void initialize() {
        try {
            this.stavkaIzdatniceControllerService.init(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Controller initialized successful");
    }

    public void setButtonSearch() {
        try {
            this.stavkaIzdatniceControllerService.searchData(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Record searched successful");
    }

    public void setButtonSave() {
        try {
            if (this.stavkaIzdatniceControllerService.saveStavkaIzdatnice(this) != null)
                log.info("Record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonStorno() {
        try {
            this.stavkaIzdatniceControllerService.stornoStavkaIzdatnice(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Record cancelled successful");
    }

    public void setButtonClear() {
        try {
            this.stavkaIzdatniceControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Field records cleared successful");
    }

}
