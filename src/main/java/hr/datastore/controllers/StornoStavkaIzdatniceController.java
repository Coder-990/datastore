package hr.datastore.controllers;

import hr.datastore.controllers.service.StornoStavkaIzdatniceControllerService;
import hr.datastore.entities.FirmeEntity;
import hr.datastore.entities.IzdatnicaEntity;
import hr.datastore.entities.RobaEntity;
import hr.datastore.entities.StavkaIzdatniceEntity;
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
public class StornoStavkaIzdatniceController {

    private final StornoStavkaIzdatniceControllerService stornoStavkaIzdatniceControllerService;
    @FXML
    @Getter
    private ComboBox<FirmeEntity> comboBoxIzdatnica;
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
    private TableView<StavkaIzdatniceEntity> tableView;
    @FXML
    @Getter
    private TableColumn<StavkaIzdatniceEntity, Long> tableColumnId;
    @FXML
    @Getter
    private TableColumn<StavkaIzdatniceEntity, IzdatnicaEntity> tableColumnIdIzdatnice;
    @FXML
    @Getter
    private TableColumn<StavkaIzdatniceEntity, RobaEntity> tableColumnArticle;
    @FXML
    @Getter
    private TableColumn<StavkaIzdatniceEntity, Integer> tableColumnKolicina;
    @FXML
    @Getter
    private TableColumn<StavkaIzdatniceEntity, LocalDate> tableColumnDatum;
    @FXML
    @Getter
    private DatePicker datePickerDatumStorno;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonClearFields;

    public void initialize() {
        try {
            this.stornoStavkaIzdatniceControllerService.init(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Controller initialized successful");

    }

    public void setButtonSearch() {
        try {
            this.stornoStavkaIzdatniceControllerService.searchData(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Record searched successful");

    }

    public void setButtonClear() {
        try {
            this.stornoStavkaIzdatniceControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Field records cleared successful");
    }
}
