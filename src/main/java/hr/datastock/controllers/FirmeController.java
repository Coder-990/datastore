package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FirmeController {

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
        this.firmeObservableList = FXCollections.observableList(this.firmeService.getAll());
        this.setValuesToTableColumns();
        this.clearRecords();
        this.tableView.setItems(this.firmeObservableList);
    }

    public void getAllDataFromTableViewButton() {
        final FirmeEntity firma = this.tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (firma != null) {
            final String oib = firma.getOibFirme();
            this.textFieldOIB.setText(oib);
            final String naziv = firma.getNazivFirme();
            this.textFieldNaziv.setText(naziv);
        }
    }

    public void setButtonSearch() {
        final GetDataFromTextField getData = new GetDataFromTextField();
        this.filteredSearchingOf(getData);
    }

    public FirmeEntity setButtonSave() {
        final GetDataFromTextField getData = new GetDataFromTextField();
        final String alertData = this.setInputCheckingOf(getData.naziv, getData.oib);
        FirmeEntity newFirma = null;
        if (!alertData.isEmpty()) {
            this.utilService.getWarningAlert(alertData);
        } else {
            newFirma = new FirmeEntity(this.nextId(), getData.oib, getData.naziv);
            this.firmeService.createFirma(newFirma);
            this.firmeObservableList.add(newFirma);
            this.tableView.setItems(this.firmeObservableList);
            this.initialize();
        }
        return newFirma;
    }

    public FirmeEntity setButtonUpdate() {
        final String newOib = textFieldOIB.getText();
        final String newNaziv = textFieldNaziv.getText();
        final FirmeEntity firma = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        FirmeEntity updatedFirma = null;
        if (firma != null && !newOib.equals("") && !newNaziv.equals("")) {
            updatedFirma = new FirmeEntity(firma.getIdFirme(), newOib, newNaziv);
            updatedFirma = this.firmeService.updateFirma(updatedFirma, firma.getIdFirme());
            this.initialize();
        }
        return updatedFirma;
    }

    public void setButtonDelete() {
        final FirmeEntity firma = this.tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (firma != null && this.utilService.getConfirmForRemoveAlert()) {
            this.firmeService.deleteFirma(firma.getIdFirme());
            this.initialize();
        }
    }

    public void setButtonClearFields() {
        this.clearRecords();
    }

    private void setValuesToTableColumns() {
        this.setProperty();
        this.setStyle();
    }

    private void setProperty() {
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idFirme"));
        this.tableColumnOIB.setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
        this.tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
    }

    private void setStyle() {
        this.tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnOIB.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnNaziv.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void filteredSearchingOf(final GetDataFromTextField getData) {
        final List<FirmeEntity> filteredListOfCompanies = new ArrayList<>(this.firmeObservableList
                .filtered(company -> company.getNazivFirme().toLowerCase().contains(getData.naziv))
                .filtered(company -> company.getOibFirme().toLowerCase().contains(getData.oib)));
        this.tableView.setItems(FXCollections.observableList(filteredListOfCompanies));
    }
    private Long nextId() {
        return !this.firmeObservableList.isEmpty() ?
                this.firmeObservableList.stream().mapToLong(FirmeEntity::getIdFirme).max().getAsLong() + 1 : 1;
    }

    private String setInputCheckingOf(final String naziv, final String oib) {
        return this.getDialogData(naziv, oib);
    }

    private String getDialogData(final String naziv, final String oib) {
        final List<String> listaProvjere = new ArrayList<>();
        if (oib.trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (naziv.trim().isEmpty()) listaProvjere.add("Company name!");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        this.textFieldNaziv.clear();
        this.textFieldOIB.clear();
        this.tableView.getSelectionModel().clearSelection();
    }
    private class GetDataFromTextField {
        final String oib = textFieldOIB.getText();
        final String naziv = textFieldNaziv.getText();
    }
}
