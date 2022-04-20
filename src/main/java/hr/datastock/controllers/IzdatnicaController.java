package hr.datastock.controllers;

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

    public IzdatnicaEntity setButtonSave() {
        IzdatnicaEntity izdatnica = null;
        try {
            izdatnica = this.izdatnicaControllerService.saveIzdatnica(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Izdatnica record saved successful");
        return izdatnica;
    }

    public void setButtonDelete() {
        try {
            this.izdatnicaControllerService.deleteIzdatnica(this);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
        log.info("Izdatnica record deleted successful");
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
