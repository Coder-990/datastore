package hr.datastock.controllers;

import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.controllers.service.IzdatnicaControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;
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
public class IzdatnicaController {
    private final IzdatnicaControllerService izdatnicaControllerService;
    private final UtilService utilService;
    @Getter
    @FXML
    private ComboBox<FirmeEntity> comboBoxCreate;
    @Getter
    @FXML
    private ComboBox<FirmeEntity> comboBoxSearch;
    @Getter
    @FXML
    private TableView<IzdatnicaEntity> tableView;
    @Getter
    @FXML
    private TableColumn<IzdatnicaEntity, Long> tableColumnId;
    @Getter
    @FXML
    private TableColumn<IzdatnicaEntity, LocalDate> tableColumnDatum;
    @Getter
    @FXML
    private TableColumn<IzdatnicaEntity, FirmeEntity> tableColumnFirmeEntity;
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
            izdatnicaControllerService.init(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Izdatnica controller initializing successful");
    }

    public void setButtonSearch() {
        try {
            izdatnicaControllerService.searchData(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Data records searched successful");
    }

    public void setButtonSave() {
        try {
           if (this.izdatnicaControllerService.saveIzdatnica(this) != null)
               log.info("Izdatnica record saved successful");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonDelete() {
        try {
            this.izdatnicaControllerService.deleteIzdatnica(this);
            log.info("Izdatnica record deleted successful");
        } catch (RuntimeException ex) {
            this.utilService.isEntityUnableToRemove();
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }

    public void setButtonClear() {
        try {
            this.izdatnicaControllerService.clearRecords(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Field records cleared successful");
    }

}
