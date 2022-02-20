package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.services.FirmeService;
import hr.datastock.services.IzdatnicaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class IzdatnicaController {

    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @FXML
    private ComboBox<FirmeEntity> comboBoxCreate;
    @FXML
    private ComboBox<FirmeEntity> comboBoxSearch;
    @FXML
    private TableView<IzdatnicaEntity> tableView;
    @FXML
    private TableColumn<IzdatnicaEntity, Long> tableColumnId;
    @FXML
    private TableColumn<IzdatnicaEntity, LocalDate> tableColumnDatum;
    @FXML
    private TableColumn<IzdatnicaEntity, FirmeEntity> tableColumnFirmeEntity;
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
    private IzdatnicaService izdatnicaService;

    @Autowired
    private FirmeService firmeService;

    @Autowired
    private UtilService utilService;

    private ObservableList<IzdatnicaEntity> izdatnicaObservableList;

    @FXML
    public void initialize() {
        izdatnicaObservableList = FXCollections.observableList(izdatnicaService.getAll());
        setComboBoxFirmeEntity();
        setComboBoxIzdatnicaEntity();
        setTableColumnProperties();
        clearRecords();
        tableView.setItems(izdatnicaObservableList);
    }

    public void setButtonSearch() {
        GetDataFromComboAndPicker searchBy = new GetDataFromComboAndPicker();
        filteredSearchingOf(searchBy.datePicker, searchBy.oibFirme);
    }

    public IzdatnicaEntity setButtonSave() {
        ComboAndPickerSelectedPropertiesData create = new ComboAndPickerSelectedPropertiesData();
        final String alertData = setInputCheckingOf(create.selectedDate, create.selectedFirma);
        IzdatnicaEntity newIzdatnica = null;
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        } else {
            newIzdatnica = izdatnicaService.createIzdatnica(new IzdatnicaEntity(nextId(), create.selectedDate, create.selectedFirma));
            izdatnicaObservableList.add(newIzdatnica);
            tableView.setItems(izdatnicaObservableList);
            initialize();
        }
        return newIzdatnica;
    }

    public void setButtonDelete() {
        IzdatnicaEntity izdatnica = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (izdatnica != null && utilService.getConfirmForRemoveAlert()) {
            izdatnicaService.deleteIzdatnica(izdatnica.getIdIzdatnice());
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

    private void setComboBoxIzdatnicaEntity() {
        final Set<FirmeEntity> oibFirmeFilterList = izdatnicaObservableList.stream()
                .map(IzdatnicaEntity::getIzdatnicaFirme).collect(Collectors.toSet());
        comboBoxCreate.setItems(FXCollections.observableList(new ArrayList<>(oibFirmeFilterList)));
        comboBoxCreate.getSelectionModel().selectFirst();
    }

    private void setTableColumnProperties() {
        setProperty();
        setStyle();
        setCellValueProperties();
    }

    private void setProperty() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idIzdatnice"));
        tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        tableColumnFirmeEntity.setCellValueFactory(new PropertyValueFactory<>("izdatnicaFirme"));
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
        FilteredList<IzdatnicaEntity> searchList = izdatnicaObservableList
                .filtered(izdatnica -> oibFirme == null || izdatnica.getIzdatnicaFirme().getOibFirme().equals(oibFirme))
                .filtered(izdatnica -> datePickerFormat == null || izdatnica.getDatum().equals(datePickerFormat));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    private Long nextId() {
        return izdatnicaObservableList.size() > 0 ?
                izdatnicaObservableList.stream().mapToLong(IzdatnicaEntity::getIdIzdatnice).max().getAsLong() + 1001 : 1001;
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
