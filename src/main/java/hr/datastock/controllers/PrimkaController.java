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
        this.primkeObservableList = FXCollections.observableList(this.primkaService.getAll());
        this.setComboBoxFirmeEntity();
        this.setComboBoxPrimkaEntity();
        this.setTableColumnProperties();
        this.clearRecords();
        this.tableView.setItems(this.primkeObservableList);
    }

    public void setButtonSearch() {
        final GetDataFromComboAndPicker searchBy = new GetDataFromComboAndPicker();
        this.filteredSearchingOf(searchBy.datePicker, searchBy.oibFirme);
    }

    public PrimkaEntity setButtonSave() {
        final ComboAndPickerSelectedPropertiesData create = new ComboAndPickerSelectedPropertiesData();
        final String alertData = this.setInputCheckingOf(create.selectedDate, create.selectedFirma);
        PrimkaEntity newPrimka = null;
        if (!alertData.isEmpty()) {
            this.utilService.getWarningAlert(alertData);
        } else {
            newPrimka = this.primkaService.createPrimka(new PrimkaEntity(this.nextId(), create.selectedDate, create.selectedFirma));
            this.primkeObservableList.add(newPrimka);
            this.tableView.setItems(this.primkeObservableList);
            this.initialize();
        }
        return newPrimka;
    }

    public void setButtonDelete() {
        final PrimkaEntity primka = this.tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (primka != null && this.utilService.isEntityRemoved()) {
            this.primkaService.deletePrimka(primka.getIdPrimke());
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

    private void setComboBoxPrimkaEntity() {
        final Set<FirmeEntity> oibFirmeFilterList = this.primkeObservableList.stream()
                .map(PrimkaEntity::getPrimkaFirme).collect(Collectors.toSet());
        this.comboBoxCreate.setItems(FXCollections.observableList(new ArrayList<>(oibFirmeFilterList)));
        this.comboBoxCreate.getSelectionModel().selectFirst();
    }

    private void setTableColumnProperties() {
        this.setProperty();
        this.setStyle();
        this.setCellValueProperties();
    }

    private void setProperty() {
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idPrimke"));
        this.tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        this.tableColumnFirmeEntity.setCellValueFactory(new PropertyValueFactory<>("primkaFirme"));
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
        final FilteredList<PrimkaEntity> searchList = this.primkeObservableList
                .filtered(primka -> oibFirme == null || primka.getPrimkaFirme().getOibFirme().equals(oibFirme))
                .filtered(primka -> datePickerFormat == null || primka.getDatum().equals(datePickerFormat));
        this.tableView.setItems(FXCollections.observableList(searchList));
    }

    private Long nextId() {
        return !this.primkeObservableList.isEmpty() ?
                this.primkeObservableList.stream().mapToLong(PrimkaEntity::getIdPrimke).max().getAsLong() + 1 : 1;
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
