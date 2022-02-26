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
    private TableViewSelectedData tvSelectedData;
    private TextFieldsData textFieldsData;
    private DataFromPropertiesForFirmeEntity dataPoperty;

    @FXML
    public void initialize() {
        this.firmeObservableList = FXCollections.observableList(this.firmeService.getAll());
        this.setValuesToTableColumns();
        this.clearRecords();
        this.tableView.setItems(this.firmeObservableList);
    }

    public void getAllDataFromTableViewButton() {
        this.tvSelectedData = new TableViewSelectedData();
        if (this.tvSelectedData.firma != null) {
            this.setDataTotextFields(this.tvSelectedData);
        }
    }

    public void setButtonSearch() {
        this.textFieldsData = new TextFieldsData();
        this.filteredSearchingOf(textFieldsData);
    }

    public FirmeEntity setButtonSave() {
        this.dataPoperty = new DataFromPropertiesForFirmeEntity();
        this.textFieldsData = new TextFieldsData();
        FirmeEntity newFirma = null;
        if (!this.dataPoperty.alertData.isEmpty()) {
            this.utilService.getWarningAlert(this.dataPoperty.alertData);
        } else {
            newFirma = this.firmeService.createFirma(new FirmeEntity(this.nextId(),
                    this.textFieldsData.oib, this.textFieldsData.naziv));
            setNewData(newFirma);
            this.initialize();
        }
        return newFirma;
    }

    public FirmeEntity setButtonUpdate() {
        this.tvSelectedData = new TableViewSelectedData();
        this.textFieldsData = new TextFieldsData();
        this.dataPoperty = new DataFromPropertiesForFirmeEntity();
        FirmeEntity updatedFirma = null;
        if (!this.dataPoperty.alertData.isEmpty()) {
            this.utilService.getWarningAlert(this.dataPoperty.alertData);
        } else {
            updatedFirma = this.firmeService.updateFirma(new FirmeEntity(this.tvSelectedData.firma.getIdFirme(),
                    this.textFieldsData.oib, this.textFieldsData.naziv), this.tvSelectedData.firma.getIdFirme());
            setNewData(updatedFirma);
        }
        return updatedFirma;
    }

    public void setButtonDelete() {
        if (this.tvSelectedData.firma != null && this.utilService.getConfirmForRemoveAlert()) {
            this.firmeService.deleteFirma(this.tvSelectedData.firma.getIdFirme());
            this.initialize();
        }
    }

    public void setButtonClearFields() {
        this.clearRecords();
    }

    private void filteredSearchingOf(final TextFieldsData getData) {
        final List<FirmeEntity> filteredListOfCompanies = new ArrayList<>(this.firmeObservableList
                .filtered(company -> company.getNazivFirme().toLowerCase().contains(getData.naziv))
                .filtered(company -> company.getOibFirme().toLowerCase().contains(getData.oib)));
        this.tableView.setItems(FXCollections.observableList(filteredListOfCompanies));
    }

    private void setNewData(FirmeEntity firmeEntity) {
        this.firmeObservableList.add(firmeEntity);
        this.tableView.setItems(this.firmeObservableList);
        this.initialize();
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

    private Long nextId() {
        return !this.firmeObservableList.isEmpty() ?
                this.firmeObservableList.stream().mapToLong(FirmeEntity::getIdFirme).max().getAsLong() + 1 : 1;
    }

    protected String setInputCheckingOf(final String naziv, final String oib) {
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

    private void setDataTotextFields(TableViewSelectedData selectedData) {
        this.textFieldOIB.setText(selectedData.oib);
        this.textFieldNaziv.setText(selectedData.naziv);
    }

    private class TableViewSelectedData {
        final FirmeEntity firma = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        final String oib = firma.getOibFirme();
        final String naziv = firma.getNazivFirme();
    }

    private class TextFieldsData {
        final String oib = textFieldOIB.getText();
        final String naziv = textFieldNaziv.getText();
    }

    private class DataFromPropertiesForFirmeEntity {
        final TextFieldsData getData = new TextFieldsData();
        final String alertData = setInputCheckingOf(getData.naziv, getData.oib);
    }


}
