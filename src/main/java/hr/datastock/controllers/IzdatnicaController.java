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
        this.izdatnicaObservableList = FXCollections.observableList(this.izdatnicaService.getAll());
        this.setComboBoxFirmeEntity();
        this.setComboBoxIzdatnicaEntity();
        this.setTableColumnProperties();
        this.clearRecords();
        this.tableView.setItems(this.izdatnicaObservableList);
    }

    public IzdatnicaEntity setButtonSave() {
        final ComboAndPickerSelectedPropertiesData create = new ComboAndPickerSelectedPropertiesData();
        final String alertData = this.setInputCheckingOf(create.selectedDate, create.selectedFirma);
        IzdatnicaEntity newIzdatnica = null;
        if (!alertData.isEmpty()) {
            this.utilService.getWarningAlert(alertData);
        } else {
            newIzdatnica = this.izdatnicaService.createIzdatnica(new IzdatnicaEntity(this.nextId(),
                    create.selectedDate, create.selectedFirma));
            this.izdatnicaObservableList.add(newIzdatnica);
            this.tableView.setItems(this.izdatnicaObservableList);
            this.initialize();
        }
        return newIzdatnica;
    }

    public void setButtonSearch() {
        final GetDataFromComboAndPicker searchBy = new GetDataFromComboAndPicker();
        this.filteredSearchingOf(searchBy.datePicker, searchBy.oibFirme);
    }

    public void setButtonDelete() {
        final IzdatnicaEntity izdatnica = this.tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (izdatnica != null && this.utilService.getConfirmForRemoveAlert()) {
            this.izdatnicaService.deleteIzdatnica(izdatnica.getIdIzdatnice());
            this.initialize();
        }
    }

    public void setButtonClearFields() {
        this.clearRecords();
    }

    private void setComboBoxFirmeEntity() {
        this.comboBoxSearch.setItems(FXCollections.observableList(this.firmeService.getAll()));
        this.comboBoxSearch.getSelectionModel().selectFirst();
    }

    private void setComboBoxIzdatnicaEntity() {
        final Set<FirmeEntity> oibFirmeFilterList = this.izdatnicaObservableList.stream()
                .map(IzdatnicaEntity::getIzdatnicaFirme).collect(Collectors.toSet());
        this.comboBoxCreate.setItems(FXCollections.observableList(new ArrayList<>(oibFirmeFilterList)));
        this.comboBoxCreate.getSelectionModel().selectFirst();
    }

    private void setTableColumnProperties() {
        this.setProperty();
        this.setStyle();
        this.setCellValueProperties();
    }

    private void setProperty() {
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idIzdatnice"));
        this.tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        this.tableColumnFirmeEntity.setCellValueFactory(new PropertyValueFactory<>("izdatnicaFirme"));
    }

    private void setStyle() {
        this.tableColumnFirmeEntity.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnDatum.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties() {
        this.tableColumnDatum.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(final LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(DATE_FORMATTER.format(item));
                }
            }
        });

        this.tableColumnFirmeEntity.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(final FirmeEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    }

    private void filteredSearchingOf(final LocalDate datePickerFormat, final String oibFirme) {
        final FilteredList<IzdatnicaEntity> searchList = this.izdatnicaObservableList
                .filtered(izdatnica -> oibFirme == null || izdatnica.getIzdatnicaFirme().getOibFirme().equals(oibFirme))
                .filtered(izdatnica -> datePickerFormat == null || izdatnica.getDatum().equals(datePickerFormat));
        this.tableView.setItems(FXCollections.observableList(searchList));
    }

    private Long nextId() {
        return !this.izdatnicaObservableList.isEmpty() ?
                this.izdatnicaObservableList.stream().mapToLong(IzdatnicaEntity::getIdIzdatnice).max().getAsLong() + 1 : 1;
    }

    private String setInputCheckingOf(final LocalDate datum, final FirmeEntity firme) {
        return this.getDialogData(datum, firme);
    }

    private String getDialogData(final LocalDate datum, final FirmeEntity firme) {
        final List<String> listaProvjere = new ArrayList<>();
        if (firme == null || firme.getOibFirme().trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (datum == null || datum.toString().trim().isEmpty()) listaProvjere.add("Date!");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        this.datePickerDatum.setValue(null);
        this.datePickerDatum.getEditor().clear();
        this.comboBoxCreate.getSelectionModel().clearSelection();
        this.comboBoxSearch.getSelectionModel().clearSelection();
        this.tableView.getSelectionModel().clearSelection();
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
