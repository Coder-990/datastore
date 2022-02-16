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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class IzdatnicaController {

    public static final Logger logger = LoggerFactory.getLogger(IzdatnicaController.class);
    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @FXML
    private ComboBox<FirmeEntity> comboBoxIzdatnica;
    @FXML
    private ComboBox<FirmeEntity> comboBoxFirmaEntity;
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
    private ObservableList<FirmeEntity> firmeEntityObservableList;

    @FXML
    public void initialize() {
        logger.info("$%$%$% StavkaIzdatnice records initializing!$%$%$%");
        izdatnicaObservableList = FXCollections.observableList(izdatnicaService.getAll());
        firmeEntityObservableList = FXCollections.observableList(firmeService.getAll());
        setComboBoxFirmeEntity();
        setComboBoxIzdatnicaEntity();
        provideAllProperties();
        clearRecords();
        tableView.setItems(izdatnicaObservableList);
        logger.info("$%$%$% StavkaIzdatnice records initialized successfully!$%$%$%");
    }

    private void setComboBoxFirmeEntity() {
        comboBoxFirmaEntity.setItems(firmeEntityObservableList);
        comboBoxFirmaEntity.getSelectionModel().selectFirst();
    }

    private void setComboBoxIzdatnicaEntity() {
        Set<FirmeEntity> listaOIBaFirme = izdatnicaObservableList.stream().map(IzdatnicaEntity::getIzdatnicaFirme).collect(Collectors.toSet());
        ObservableList<FirmeEntity> firmeEntityComboSearchObservableList = FXCollections.observableList(new ArrayList<>(listaOIBaFirme));
        comboBoxIzdatnica.setItems(firmeEntityComboSearchObservableList);
        comboBoxIzdatnica.getSelectionModel().selectFirst();
    }

    private void provideAllProperties() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idIzdatnice"));
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

        tableColumnFirmeEntity.setCellValueFactory(new PropertyValueFactory<>("izdatnicaFirme"));
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
        final String oibFirme = comboBoxIzdatnica.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxIzdatnica.getSelectionModel().getSelectedItem().getOibFirme();
        buttonSearch(datePickerFormat, oibFirme);
    }

    private void buttonSearch(LocalDate datePickerFormat, String oibFirme) {
        FilteredList<IzdatnicaEntity> searchList = izdatnicaObservableList
                .filtered(izdatnica -> oibFirme == null || izdatnica.getIzdatnicaFirme().getOibFirme().equals(oibFirme))
                .filtered(izdatnica -> datePickerFormat == null || izdatnica.getDatum().equals(datePickerFormat));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    public IzdatnicaEntity setButtonSave() {
        final FirmeEntity firma = comboBoxFirmaEntity.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxFirmaEntity.getSelectionModel().getSelectedItem();

        final LocalDate datum = datePickerDatum.getValue();

        final String alertData = setInputCheck(datum, firma);
        IzdatnicaEntity newIzdatnica = null;
        if (Optional.ofNullable(firma).isPresent() && Optional.ofNullable(datum).isPresent()) {
            if (!alertData.isEmpty()) {
                utilService.getWarningAlert(alertData);
            } else {
                LocalDate dateTime = datePickerDatum.getValue();
                try {
                    newIzdatnica = izdatnicaService.createIzdatnica(new IzdatnicaEntity(nextId(), dateTime, firma));
                } catch (Exception ex) {
                    logger.error("Error in method 'unesi poduzece'", ex);
                    ex.printStackTrace();
                }
                izdatnicaObservableList.add(newIzdatnica);
                tableView.setItems(izdatnicaObservableList);
                initialize();
            }
        }
        return newIzdatnica;
    }

    private Long nextId() {
        return izdatnicaObservableList.size() > 0 ?
                izdatnicaObservableList.stream().mapToLong(IzdatnicaEntity::getIdIzdatnice).max().getAsLong() + 1 : 1;
    }

    private String setInputCheck(LocalDate datum, FirmeEntity firme) {
        List<String> listaProvjere = new ArrayList<>();
        if (firme == null || firme.getOibFirme().trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (datum == null || datum.toString().trim().isEmpty()) listaProvjere.add("Date!");
        return String.join("\n", listaProvjere);
    }

    public void setButtonDelete() {
        IzdatnicaEntity izdatnica = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (izdatnica != null) {
            izdatnicaService.deleteIzdatnica(izdatnica.getIdIzdatnice());
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void clearRecords() {
        datePickerDatum.setValue(null);
        datePickerDatum.getEditor().clear();
        comboBoxIzdatnica.getSelectionModel().clearSelection();
        comboBoxFirmaEntity.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
    }
}
