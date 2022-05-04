package hr.datastock.controllers;

import hr.datastock.controllers.service.StavkaPrimkeControllerService;
import hr.datastock.entities.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StavkaPrimkeController {

    private final StavkaPrimkeControllerService stavkaPrimkeControllerService;
    @FXML
    @Getter
    private ComboBox<PrimkaEntity> comboBoxPrimka;
    @FXML
    @Getter
    private ComboBox<RobaEntity> comboBoxRoba;
    @FXML
    @Getter
    private TextField textFieldKolicina;
    @FXML
    @Getter
    private TextField textFieldFirma;
    @FXML
    @Getter
    private TextField textFieldArticle;
    @FXML
    @Getter
    private TableView<StavkaPrimkeEntity> tableView;
    @FXML
    @Getter
    private TableColumn<StavkaPrimkeEntity, Long> tableColumnId;
    @FXML
    @Getter
    private TableColumn<StavkaPrimkeEntity, PrimkaEntity> tableColumnIdPrimke;
    @FXML
    @Getter
    private TableColumn<StavkaPrimkeEntity, RobaEntity> tableColumnArticle;
    @FXML
    @Getter
    private TableColumn<StavkaPrimkeEntity, Integer> tableColumnKolicina;
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
            this.stavkaPrimkeControllerService.init(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Controller initialized successful");
    }

    public void setButtonSearch() {
        try {
            this.stavkaPrimkeControllerService.searchData(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Record searched successful");
    }

    public void setButtonSave() {
        try {
            if (this.stavkaPrimkeControllerService.saveStavkaPrimke(this) != null)
                log.info("Record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonStorno() {
        try {
            this.stavkaPrimkeControllerService.stornoStavkaPrimke(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Record cancelled successful");
    }

    public void setButtonClear() {
        try {
            this.stavkaPrimkeControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Field records cleared successful");
    }

}
