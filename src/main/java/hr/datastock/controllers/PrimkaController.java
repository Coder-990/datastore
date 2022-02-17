package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.PrimkaEntity;
import hr.datastock.services.FirmeService;
import hr.datastock.services.PrimkaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PrimkaController {

    public static final Logger logger = LoggerFactory.getLogger(PrimkaController.class);
    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @FXML
    private ComboBox<FirmeEntity> comboBoxCreate;
    @FXML
    private ComboBox<FirmeEntity> comboBoxSearch;
    @FXML
    private TableView<PrimkaEntity> tableView;
    @FXML
    private TableColumn<PrimkaEntity, Long> tableColumnId;
    @FXML
    private TableColumn<PrimkaEntity, LocalDate> tableColumnDatum;
    @FXML
    private TableColumn<PrimkaEntity, FirmeEntity> tableColumnFirmeEntity;
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

    @Autowired
    private PrimkaService primkaService;

    @Autowired
    private FirmeService firmeService;

    @Autowired
    private UtilService utilService;

    private ObservableList<PrimkaEntity> primkeObservableList;

    @FXML
    public void initialize() {
        primkeObservableList = FXCollections.observableList(primkaService.getAll());
        setComboBoxFirmeEntity();
        setComboBoxPrimkaEntity();
        setTableColumnProperties();
        clearRecords();
        tableView.setItems(primkeObservableList);
    }

    public void setButtonSearch() {
        GetDataFromComboAndPicker searchBy = new GetDataFromComboAndPicker();
        filteredSearchingOf(searchBy.datePicker, searchBy.oibFirme);
    }

    public PrimkaEntity setButtonSave() {
        ComboAndPickerSelectedPropertiesData create = new ComboAndPickerSelectedPropertiesData();
        final String alertData = setInputCheckingOf(create.selectedDate, create.selectedFirma);
        PrimkaEntity newPrimka = null;
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        } else {
            newPrimka = primkaService.createPrimka(new PrimkaEntity(nextId(), create.selectedDate, create.selectedFirma));
            primkeObservableList.add(newPrimka);
            tableView.setItems(primkeObservableList);
            initialize();
        }
        return newPrimka;
    }

    public void setButtonDelete() {
        PrimkaEntity primka = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (primka != null && utilService.getConfirmForRemoveAlert()) {
            primkaService.deletePrimka(primka.getIdPrimke());
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void setComboBoxFirmeEntity() {
        comboBoxSearch.setItems(FXCollections.observableList(firmeService.getAll()));
        comboBoxSearch.getSelectionModel().selectFirst();
    }

    private void setComboBoxPrimkaEntity() {
        final Set<FirmeEntity> oibFirmeFilterList = primkeObservableList.stream()
                .map(PrimkaEntity::getPrimkaFirme).collect(Collectors.toSet());
        comboBoxCreate.setItems(FXCollections.observableList(new ArrayList<>(oibFirmeFilterList)));
        comboBoxCreate.getSelectionModel().selectFirst();
    }

    private void setTableColumnProperties() {
        setProperty();
        setStyle();
        setCellValueProperties();
    }

    private void setProperty() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idPrimke"));
        tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        tableColumnFirmeEntity.setCellValueFactory(new PropertyValueFactory<>("primkaFirme"));
    }

    private void setStyle() {
        tableColumnFirmeEntity.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnDatum.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties() {
        tableColumnDatum.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(DATE_FORMATTER.format(item));
                }
            }
        });

        tableColumnFirmeEntity.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(FirmeEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    }

    private void filteredSearchingOf(LocalDate datePickerFormat, String oibFirme) {
        FilteredList<PrimkaEntity> searchList = primkeObservableList
                .filtered(primka -> oibFirme == null || primka.getPrimkaFirme().getOibFirme().equals(oibFirme))
                .filtered(primka -> datePickerFormat == null || primka.getDatum().equals(datePickerFormat));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    private Long nextId() {
        return primkeObservableList.size() > 0 ?
                primkeObservableList.stream().mapToLong(PrimkaEntity::getIdPrimke).max().getAsLong() + 1001 : 1001;
    }

    private String setInputCheckingOf(LocalDate datum, FirmeEntity firme) {
        return getDialogData(datum, firme);
    }

    private String getDialogData(LocalDate datum, FirmeEntity firme) {
        List<String> listaProvjere = new ArrayList<>();
        if (firme == null || firme.getOibFirme().trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (datum == null || datum.toString().trim().isEmpty()) listaProvjere.add("Date!");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        datePickerDatum.setValue(null);
        datePickerDatum.getEditor().clear();
        comboBoxCreate.getSelectionModel().clearSelection();
        comboBoxSearch.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
    }

    private class GetDataFromComboAndPicker {
        final LocalDate datePicker = datePickerDatum.getValue() == null ? null : datePickerDatum.getValue();
        final String oibFirme = comboBoxCreate.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxCreate.getSelectionModel().getSelectedItem().getOibFirme();
    }

    private class ComboAndPickerSelectedPropertiesData {
        final FirmeEntity selectedFirma = comboBoxSearch.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxSearch.getSelectionModel().getSelectedItem();
        final LocalDate selectedDate = datePickerDatum.getValue();
    }
}
