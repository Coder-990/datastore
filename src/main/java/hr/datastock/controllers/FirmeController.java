package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TextField textFieldNaziv;
    @FXML
    private TextField textFieldOIB;
    @FXML
    private TableView<FirmeEntity> tableView;
    @FXML
    private TableColumn<FirmeEntity, Long> tableColumnId;
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnNaziv;
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnOIB;
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
    private FirmeService firmeService;

    @Autowired
    private UtilService utilService;

    private ObservableList<FirmeEntity> firmeObservableList;

    @FXML
    public void initialize() {
        firmeObservableList = FXCollections.observableList(firmeService.getAll());
        setValuesToTableColumns();
        clearRecords();
        tableView.setItems(firmeObservableList);
    }

    public void getAllDataFromTableViewButton() {
        FirmeEntity firma = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (firma != null) {
            final String oib = firma.getOibFirme();
            textFieldOIB.setText(oib);
            final String naziv = firma.getNazivFirme();
            textFieldNaziv.setText(naziv);
        }
    }

    public void setButtonSearch() {
        GetDataFromTextField getData = new GetDataFromTextField();
        filteredSearchingOf(getData);
    }

    public FirmeEntity setButtonSave() {
        GetDataFromTextField getData = new GetDataFromTextField();
        final String alertData = setInputCheckingOf(getData.naziv, getData.oib);
        FirmeEntity newFirma = null;
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        } else {
            newFirma = new FirmeEntity(nextId(), getData.oib, getData.naziv);
            firmeService.createFirma(newFirma);
            firmeObservableList.add(newFirma);
            tableView.setItems(firmeObservableList);
            initialize();
        }
        return newFirma;
    }

    public FirmeEntity setButtonUpdate() {
        String newOib = textFieldOIB.getText();
        String newNaziv = textFieldNaziv.getText();
        FirmeEntity firma = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        FirmeEntity updatedFirma = null;
        if (firma != null && !newOib.equals("") && !newNaziv.equals("")) {
            updatedFirma = new FirmeEntity(firma.getIdFirme(), newOib, newNaziv);
            updatedFirma = firmeService.updateFirma(updatedFirma, firma.getIdFirme());
            initialize();
        }
        return updatedFirma;
    }

    public void setButtonDelete() {
        FirmeEntity firma = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (firma != null && utilService.getConfirmForRemoveAlert()) {
            firmeService.deleteFirma(firma.getIdFirme());
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void setValuesToTableColumns() {
        setProperty();
        setStyle();
    }

    private void setProperty() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idFirme"));
        tableColumnOIB.setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
        tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
    }

    private void setStyle() {
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnOIB.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnNaziv.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void filteredSearchingOf(GetDataFromTextField getData) {
        List<FirmeEntity> filteredListOfCompanies = new ArrayList<>(firmeObservableList
                .filtered(company -> company.getNazivFirme().toLowerCase().contains(getData.naziv))
                .filtered(company -> company.getOibFirme().toLowerCase().contains(getData.oib)));
        tableView.setItems(FXCollections.observableList(filteredListOfCompanies));
    }
    private Long nextId() {
        return firmeObservableList.size() > 0 ?
                firmeObservableList.stream().mapToLong(FirmeEntity::getIdFirme).max().getAsLong() + 1001 : 1001;
    }

    private String setInputCheckingOf(String naziv, String oib) {
        return getDialogData(naziv, oib);
    }

    private String getDialogData(String naziv, String oib) {
        List<String> listaProvjere = new ArrayList<>();
        if (oib.trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (naziv.trim().isEmpty()) listaProvjere.add("Company name!");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        textFieldNaziv.clear();
        textFieldOIB.clear();
        tableView.getSelectionModel().clearSelection();
    }
    private class GetDataFromTextField {
        final String oib = textFieldOIB.getText();
        final String naziv = textFieldNaziv.getText();
    }
}
