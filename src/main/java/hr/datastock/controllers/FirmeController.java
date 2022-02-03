package hr.datastock.controllers;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.mapping.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FirmeController {

    public static final Logger logger = LoggerFactory.getLogger(FirmeController.class);
    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";
    @FXML
    private ComboBox<Long> comboBoxID;
    @FXML
    private TextField textFieldNaziv;
    @FXML
    private TextField textFieldOIB;
    @FXML
    private TableView<FirmeEntity> tableView;
    @FXML
    private ListView<Any> listView;
    @FXML
    private TableColumn<FirmeEntity, Long> tableColumnId;
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnNaziv;
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnOIB;
    @FXML
    private Button buttonGetOneById;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonSelectProperty;
    @FXML
    private Button buttonInsert;
    @FXML
    private Button buttonClearFields;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;

    @Autowired
    private FirmeService firmeService;

    private ObservableList<FirmeEntity> firmeObservableList;

    @FXML
    public void initialize() {
        firmeObservableList = FXCollections.observableList(firmeService.getAll());
        refreshComboBox();
        logger.info("$%$%$% Company records initializing! $%$%$%");
        provideAllProperties();
        tableView.setItems(firmeObservableList);
        logger.info("$%$%$% Poduzece records initialized successfully!$%$%$%");
    }

    private void refreshComboBox() {
        List<Long> listaPoduzecaIda = firmeObservableList.stream().map(FirmeEntity::getIdFirme).toList();
        ObservableList<Long> firmeIdsObservableList = FXCollections.observableList(listaPoduzecaIda);
        comboBoxID.getSelectionModel().selectFirst();
        comboBoxID.setItems(firmeIdsObservableList);
    }

    private void provideAllProperties() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idFirme"));
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnOIB.setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
        tableColumnOIB.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
        tableColumnNaziv.setStyle(FX_ALIGNMENT_CENTER);
    }

    public void selectProperty() {
        Long selectedId = getOneById();
        if (tableColumnId.getTableView().getSelectionModel().getSelectedItem().getIdFirme().equals(selectedId)) {
            String oib = tableView.getSelectionModel().getSelectedItem().getOibFirme();
            textFieldOIB.setText(oib);
            String naziv = tableView.getSelectionModel().getSelectedItem().getNazivFirme();
            textFieldNaziv.setText(naziv);
        }
    }

    public Long getOneById() {
        Long selectedId = (long) getComboBoxSelectedId();
        if (tableColumnId.getTableView().getSelectionModel().getSelectedItem().getIdFirme().equals(selectedId)){
            return firmeService.getOneByID(selectedId);
        }
        return null;
    }

    private int getComboBoxSelectedId() {
        return Math.toIntExact(comboBoxID.getSelectionModel().getSelectedItem());
    }

    public void setButtonSearch() {
        String naziv = textFieldNaziv.getText();
        String oib = textFieldOIB.getText();
        List<FirmeEntity> filteredListOfCompanies = new ArrayList<>(firmeObservableList
                .filtered(company -> company.getNazivFirme().toLowerCase().contains(naziv))
                .filtered(company -> company.getOibFirme().toLowerCase().contains(oib)));
        tableView.setItems(FXCollections.observableList(filteredListOfCompanies));
        refreshComboBox();
        logger.info("Article record searched successfully!");
    }

    public FirmeEntity setButtonSave() {
        String oib = textFieldOIB.getText();
        String naziv = textFieldNaziv.getText();

        String alert = unosProvjera(naziv, oib);
        if (!alert.isEmpty()) {
            Alert alertWindow = new Alert(Alert.AlertType.WARNING);
            alertWindow.setTitle("Error");
            alertWindow.setHeaderText("Please input missing records: ");
            alertWindow.setContentText(alert);
            alertWindow.showAndWait();
        } else {
            FirmeEntity newCompany = new FirmeEntity(nextId(), oib, naziv);
            try {
                firmeService.createCompany(newCompany);
            } catch (Exception ex) {
                logger.error("Error in method 'unesi poduzece'", ex);
                ex.printStackTrace();
            }
            firmeObservableList.add(newCompany);
            tableView.setItems(firmeObservableList);
            refreshComboBox();
            clearRecords();
            logger.info("Poduzece records saved successfully!");
        }
        return firmeService.createCompany(new FirmeEntity(nextId(), oib, naziv));
    }

    public FirmeEntity setButtonUpdate() {
        Long selectedId = getOneById();
        String oib = tableView.getSelectionModel().getSelectedItem().getOibFirme();
        textFieldOIB.setText(oib);
        String naziv = tableView.getSelectionModel().getSelectedItem().getNazivFirme();
        textFieldNaziv.setText(naziv);
        FirmeEntity firmeEntity = new FirmeEntity(selectedId, oib, naziv);
        FirmeEntity updateCompany = (FirmeEntity) firmeObservableList.stream().map(existingCompany -> {
            existingCompany.setOibFirme(firmeEntity.getOibFirme());
            existingCompany.setNazivFirme(firmeEntity.getNazivFirme());
            return firmeService.updateCompany(existingCompany, selectedId);
        });
        return firmeService.updateCompany(updateCompany, selectedId);
    }

    private Long nextId() {
        return firmeObservableList.stream().mapToLong(FirmeEntity::getIdFirme).max().getAsLong() + 1;
    }

    private String unosProvjera(String naziv, String oib) {

        logger.info("Checking data...");
        List<String> listaProvjere = new ArrayList<>();
        if (oib.trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (naziv.trim().isEmpty()) listaProvjere.add("Company name!");
        return String.join("\n", listaProvjere);
    }

    public void setButtonDelete() {
        Long selectedId = getOneById();
        firmeService.deleteCompany(selectedId);
        refreshComboBox();
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void clearRecords() {
        comboBoxID.getSelectionModel().clearSelection();
        textFieldNaziv.clear();
        textFieldOIB.clear();
    }
}
