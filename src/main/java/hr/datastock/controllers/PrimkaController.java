package hr.datastock.controllers;

import hr.datastock.dialogutil.UtilService;
import hr.datastock.controllers.service.PrimkaControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.PrimkaEntity;
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
public class PrimkaController {
    private final PrimkaControllerService primkaControllerService;
    private final UtilService utilService;
    @Getter
    @FXML
    private ComboBox<FirmeEntity> comboBoxCreate;
    @Getter
    @FXML
    private ComboBox<FirmeEntity> comboBoxSearch;
    @Getter
    @FXML
    private TableView<PrimkaEntity> tableView;
    @Getter
    @FXML
    private TableColumn<PrimkaEntity, Long> tableColumnId;
    @Getter
    @FXML
    private TableColumn<PrimkaEntity, LocalDate> tableColumnDatum;
    @Getter
    @FXML
    private TableColumn<PrimkaEntity, FirmeEntity> tableColumnFirmeEntity;
    @Getter
    @FXML
    private DatePicker datePickerDatum;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonClearFields;
    @FXML
    private Button buttonDelete;

    public void initialize() {
        try {
            this.primkaControllerService.init(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Primka controller initializing successful");
    }

    public void setButtonSearch() {
        try {
            this.primkaControllerService.searchData(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Data records searched successful");
    }

    public void setButtonSave() {
        try {
            if (this.primkaControllerService.savePrimka(this) != null)
                log.info("Primka record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonDelete() {
        try {
            this.primkaControllerService.deletePrimka(this);
            log.info("Primka record deleted successful");
        } catch (RuntimeException ex) {
            this.utilService.isEntityUnableToRemove();
        }
    }

    public void setButtonClear() {
        try {
            this.primkaControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Field records cleared successful");
    }
}
