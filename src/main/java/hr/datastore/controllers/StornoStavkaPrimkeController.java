package hr.datastore.controllers;

import hr.datastore.controllers.service.StornoStavkaPrimkeControllerService;
import hr.datastore.entities.FirmeEntity;
import hr.datastore.entities.PrimkaEntity;
import hr.datastore.entities.RobaEntity;
import hr.datastore.entities.StavkaPrimkeEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
@Slf4j
@RequiredArgsConstructor
@Controller
public class StornoStavkaPrimkeController {

    private final StornoStavkaPrimkeControllerService stavkaPrimkeControllerService;
    @FXML
    @Getter
    private ComboBox<FirmeEntity> comboBoxPrimka;
    @FXML
    @Getter
    private ComboBox<RobaEntity> comboBoxRoba;
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
    @Getter
    private TableColumn<StavkaPrimkeEntity, LocalDate> tableColumnDatum;
    @FXML
    @Getter
    private DatePicker datePickerDatumStorno;
    @FXML
    private Button buttonSearch;
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

    public void setButtonClear() {
        try {
            this.stavkaPrimkeControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Field records cleared successful");
    }

}
