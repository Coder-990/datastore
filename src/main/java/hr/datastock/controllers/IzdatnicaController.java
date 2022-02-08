package hr.datastock.controllers;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.services.IzdatnicaServis;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.stream.Collectors;

@Component
public class IzdatnicaController {

    public static final Logger logger = LoggerFactory.getLogger(IzdatnicaController.class);
    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy. HH:mm";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    @FXML
    private TextField textFieldOIB;
    @FXML
    private ComboBox<String> comboBoxFirmaEntityOib;
    @FXML
    private TableView<IzdatnicaEntity> tableView;
    @FXML
    private TableColumn<IzdatnicaEntity, Long> tableColumnId;
    @FXML
    private TableColumn<IzdatnicaEntity, LocalDateTime> tableColumnDatumIzdatnice;
    @FXML
    private TableColumn<IzdatnicaEntity, String> tableColumnOIB;
    @FXML
    private DatePicker datePickerDatumIzdatnice;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonGetDataFromTable;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonClearFields;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;

    @Autowired
    private IzdatnicaServis izdatnicaServis;

    private ObservableList<IzdatnicaEntity> izdatnicaObservableList;

    @FXML
    public void initialize() {
        izdatnicaObservableList = FXCollections.observableList(izdatnicaServis.getAll());
        setComboBoxOib();
        provideAllProperties();
        clearRecords();
        tableView.setItems(izdatnicaObservableList);
        logger.info("$%$%$% Poduzece records initialized successfully!$%$%$%");
    }

    private void setComboBoxOib() {
        List<String> listaOIBaFirme = izdatnicaObservableList.stream().map(izdatnica -> izdatnica.getIzdatnicaFirme().getOibFirme()).toList();
        ObservableList<String> firmeOIBObservableList = FXCollections.observableList(listaOIBaFirme);
        comboBoxFirmaEntityOib.getSelectionModel().selectFirst();
        comboBoxFirmaEntityOib.setItems(firmeOIBObservableList);
    }

    private void provideAllProperties() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idIzdatnice"));
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnDatumIzdatnice.setCellValueFactory(new PropertyValueFactory<>("datum"));
        tableColumnDatumIzdatnice.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnDatumIzdatnice.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
        tableColumnOIB.setCellValueFactory(new PropertyValueFactory<>("izdatnicaFirme"));
        tableColumnOIB.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnOIB.setCellValueFactory(firmeEntity -> {
            SimpleStringProperty property = new SimpleStringProperty();
            String firmaOib = firmeEntity.getValue().getIzdatnicaFirme().getOibFirme();
            property.setValue(firmaOib);
            return property;
        });
    }

    public void getAllDataFromTableView() {
        IzdatnicaEntity izdatnica = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (izdatnica != null) {
            String oib = izdatnica.getIzdatnicaFirme().getOibFirme();
            comboBoxFirmaEntityOib.valueProperty().setValue(oib);
            textFieldOIB.setText(oib);
            LocalDateTime datum = izdatnica.getDatum();
            datePickerDatumIzdatnice.setValue(LocalDate.from(datum));
        }
    }

    public void setButtonSearch() {
       final String oib = textFieldOIB.getText();
        List<IzdatnicaEntity> listaIzdatnica = new ArrayList<>(izdatnicaObservableList
                .filtered(izdatnica -> izdatnica.getIzdatnicaFirme().getOibFirme().toLowerCase().contains(oib)));
        if (datePickerDatumIzdatnice.getValue() != null) {
            listaIzdatnica.stream().filter(izdatnica -> izdatnica.getDatum()
                    .format(DateTimeFormatter.ofPattern(formatter.toString()))
                    .contains(datePickerDatumIzdatnice
                            .getValue().format(DateTimeFormatter.ofPattern(formatter.toString())))).toList();
            datePickerDatumIzdatnice.setValue(null);
        }
        tableView.setItems(FXCollections.observableList(listaIzdatnica));
    }

    public IzdatnicaEntity setButtonSave() {
        String oib = comboBoxFirmaEntityOib.getSelectionModel().getSelectedItem();
        String datum = (datePickerDatumIzdatnice.getValue() != null) ?
                datePickerDatumIzdatnice.getValue().format(formatter) : "";

        String alert = unosProvjera(datum, oib);
        IzdatnicaEntity newIzdatnica;
        FirmeEntity firmeEntity = new FirmeEntity(null, null, oib);
        if (!alert.isEmpty()) {
            Alert alertWindow = new Alert(Alert.AlertType.WARNING);
            alertWindow.setTitle("Error");
            alertWindow.setHeaderText("Please input missing records: ");
            alertWindow.setContentText(alert);
            alertWindow.showAndWait();
        } else {

            if (newIzdatnica != null){
                LocalDateTime dateTime = LocalDateTime.parse(datum, formatter);
                newIzdatnica = new IzdatnicaEntity(nextId(), dateTime, firmeEntity);
                try {
                    izdatnicaServis.createIzdatnica(newIzdatnica);
                } catch (Exception ex) {
                    logger.error("Error in method 'unesi poduzece'", ex);
                    ex.printStackTrace();
                }
                izdatnicaObservableList.add(newIzdatnica);
                tableView.setItems(izdatnicaObservableList);
                initialize();
            }

        }
        return izdatnicaServis.createIzdatnica(newIzdatnica);
    }

    public IzdatnicaEntity setButtonUpdate() {
        String newOib = textFieldOIB.getText();
        String newNaziv = comboBoxFirmaEntityOib.getSelectionModel().getSelectedItem();
        IzdatnicaEntity izdatnica = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        IzdatnicaEntity updatedIzdatnica = null;
        if (izdatnica != null && !newOib.equals("") && !newNaziv.equals("")) {
            updatedIzdatnica = new IzdatnicaEntity(izdatnica.getIdIzdatnice(), newOib, newNaziv);
            try {
                updatedIzdatnica = izdatnicaServis.updateIzdatnica(updatedIzdatnica, izdatnica.getIdIzdatnice());
            } catch (Exception e) {
                e.printStackTrace();
            }
            initialize();
        }
        return updatedIzdatnica;
        return null;
    }

    private Long nextId() {
        return izdatnicaObservableList.stream().mapToLong(IzdatnicaEntity::getIdIzdatnice).max().getAsLong() + 1;
    }

    private String unosProvjera(String naziv, String oib) {
        List<String> listaProvjere = new ArrayList<>();
        if (oib.trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (naziv.trim().isEmpty()) listaProvjere.add("Company name!");
        return String.join("\n", listaProvjere);
    }

    public void setButtonDelete() {
        IzdatnicaEntity izdatnica = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (izdatnica != null) {
            izdatnicaServis.deleteIzdatnica(izdatnica.getIdIzdatnice());
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void clearRecords() {
        datePickerDatumIzdatnice.getEditor().clear();
        textFieldOIB.clear();
        tableView.getSelectionModel().clearSelection();
    }
}
