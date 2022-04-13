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

    @FXML
    public void initialize() {
        izdatnicaControllerService.init(this);
    }

    public void setButtonSearch() {
        izdatnicaControllerService.searchData(this);
    }

    public IzdatnicaEntity setButtonSave() {
        return izdatnicaControllerService.saveIzdatnica(this);
    }

    public void setButtonDelete() {
        izdatnicaControllerService.deleteIzdatnica(this);
    }

    public void setButtonClear() {
        izdatnicaControllerService.clearRecords(this);
    }

}
