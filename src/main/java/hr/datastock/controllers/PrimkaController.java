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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PrimkaController {

    public static final Logger logger = LoggerFactory.getLogger(PrimkaController.class);
    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @FXML
    private ComboBox<FirmeEntity> comboBoxPrimka;
    @FXML
    private ComboBox<FirmeEntity> comboBoxFirmaEntity;
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

    private ObservableList<PrimkaEntity> primkaObservableList;
    private ObservableList<FirmeEntity> firmeEntityObservableList;

    @FXML
    public void initialize() {
        primkaObservableList = FXCollections.observableList(primkaService.getAll());
        firmeEntityObservableList = FXCollections.observableList(firmeService.getAll());
        setComboBoyFirmeEntity();
        setComboBoxPrimkaEntity();
        provideAllProperties();
        clearRecords();
        tableView.setItems(primkaObservableList);
        logger.info("$%$%$% Primka records initialized successfully!$%$%$%");
    }

    private void setComboBoyFirmeEntity() {
        comboBoxFirmaEntity.setItems(firmeEntityObservableList);
        comboBoxFirmaEntity.getSelectionModel().selectFirst();
    }

    private void setComboBoxPrimkaEntity() {
        Set<FirmeEntity> listaOIBaFirme = primkaObservableList.stream().map(PrimkaEntity::getPrimkaFirme).collect(Collectors.toSet());
        ObservableList<FirmeEntity> firmeEntityComboSearchObservableList = FXCollections.observableList(new ArrayList<>(listaOIBaFirme));
        comboBoxPrimka.setItems(firmeEntityComboSearchObservableList);
        comboBoxPrimka.getSelectionModel().selectFirst();
    }

    private void provideAllProperties() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idPrimke"));
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);

        tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        tableColumnDatum.setStyle(FX_ALIGNMENT_CENTER);
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

        tableColumnFirmeEntity.setCellValueFactory(new PropertyValueFactory<>("primkaFirme"));
        tableColumnFirmeEntity.setStyle(FX_ALIGNMENT_CENTER);
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

    public void setButtonSearch() {
        final LocalDate datePickerFormat = datePickerDatum.getValue() == null ? null :
                datePickerDatum.getValue();
        final String oibFirme = comboBoxPrimka.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxPrimka.getSelectionModel().getSelectedItem().getOibFirme();
        buttonSearch(datePickerFormat, oibFirme);
    }

    private void buttonSearch(LocalDate datePickerFormat, String oibFirme) {
        FilteredList<PrimkaEntity> searchList = primkaObservableList
                .filtered(primka -> oibFirme == null || primka.getPrimkaFirme().getOibFirme().equals(oibFirme))
                .filtered(primka -> datePickerFormat == null || primka.getDatum().equals(datePickerFormat));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    public PrimkaEntity setButtonSave() {
        final FirmeEntity firma = comboBoxFirmaEntity.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxFirmaEntity.getSelectionModel().getSelectedItem();
        final LocalDate datum = datePickerDatum.getValue();

        final String alertData = setInputCheck(datum, firma);
        PrimkaEntity newPrimka = null;
        if (Optional.ofNullable(firma).isPresent()) {
            if (!alertData.isEmpty()) {
                utilService.getWarningAlert(alertData);
            } else {
                try {
                    newPrimka = primkaService.createPrimka(new PrimkaEntity(nextId(), datum, firma));
                } catch (Exception ex) {
                    logger.error("Error in method 'createPrimka'", ex);
                    ex.printStackTrace();
                }
                primkaObservableList.add(newPrimka);
                tableView.setItems(primkaObservableList);
                initialize();
            }
        }
        return newPrimka;
    }

    private Long nextId() {
        return primkaObservableList.size() > 0 ?
                primkaObservableList.stream().mapToLong(PrimkaEntity::getIdPrimke).max().getAsLong() + 1 : 1;
    }

    private String setInputCheck(LocalDate datum, FirmeEntity firme) {
        List<String> listaProvjere = new ArrayList<>();
        if (firme == null || firme.getOibFirme().trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (datum == null || datum.toString().trim().isEmpty()) listaProvjere.add("Date!");
        return String.join("\n", listaProvjere);
    }

    public void setButtonDelete() {
        PrimkaEntity primka = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (primka != null) {
            primkaService.deletePrimka(primka.getIdPrimke());
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void clearRecords() {
        datePickerDatum.setValue(null);
        datePickerDatum.getEditor().clear();
        comboBoxPrimka.getSelectionModel().clearSelection();
        comboBoxFirmaEntity.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
    }
}
