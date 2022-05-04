package hr.datastock.controllers;

import hr.datastock.controllers.service.StornoStavkaIzdatniceControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.services.StavkaIzdatniceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static hr.datastock.controllers.service.impl.Const.DATE_FORMATTER;
import static hr.datastock.controllers.service.impl.Const.FX_ALIGNMENT_CENTER;

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
