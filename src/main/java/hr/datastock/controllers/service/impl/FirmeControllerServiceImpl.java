package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.FirmeController;
import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.controllers.service.FirmeControllerService;
import hr.datastock.controllers.service.ITableViewSelectedData;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public abstract class FirmeControllerServiceImpl extends FirmeController implements FirmeControllerService {

    private  FirmeControllerService firmeControllerService;
    protected FirmeService firmeService;
    protected UtilService utilService;
//    private final ITableViewSelectedData iTableViewSelectedData;
    protected ObservableList<FirmeEntity> firmeObservableList;
    //    protected TableViewSelectedData tvSelectedData;
    protected TextFieldsData textFieldsData;
    protected DataFromPropertiesForFirmeEntity dataPoperty;

    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";

    public FirmeControllerServiceImpl(@Qualifier("firmeControllerServiceImpl") FirmeControllerService firmeControllerService) {
        super(firmeControllerService);
    }

    @Override
    public void initialize() {
        this.firmeObservableList = FXCollections.observableList(this.firmeService.getAll());
        this.setValuesToTableColumns();
        this.clearRecords();
        this.tableView.setItems(this.firmeObservableList);
    }

    @Override
    public void getAllDataFromTableViewButton() {
        if (this.firmeControllerService.getFirmaFromTableView() != null) {
            this.setDataTotextFields(this.firmeControllerService);
        }
    }

    @Override
    public void setButtonSearch() {
        this.textFieldsData = new TextFieldsData();
        this.filteredSearchingOf(textFieldsData);
    }

    @Override
    public FirmeEntity setButtonSave() {
        this.dataPoperty = new DataFromPropertiesForFirmeEntity();
        this.textFieldsData = new TextFieldsData();
        FirmeEntity newFirma = null;
        if (!this.dataPoperty.alertData.isEmpty()) {
            this.utilService.getWarningAlert(this.dataPoperty.alertData);
        } else {
            newFirma = this.firmeService.createFirma(this.saveFirma());
            setNewData(newFirma);
            this.initialize();
        }
        return newFirma;
    }

    @Override
    public FirmeEntity setButtonUpdate() {
//        this.tvSelectedData = new TableViewSelectedData();
        this.textFieldsData = new TextFieldsData();
        this.dataPoperty = new DataFromPropertiesForFirmeEntity();
        FirmeEntity updatedFirma = null;
        if (!this.dataPoperty.alertData.isEmpty()) {
            this.utilService.getWarningAlert(this.dataPoperty.alertData);
        } else {
            updatedFirma = this.firmeService.updateFirma(new FirmeEntity(this.firmeControllerService.getFirmaFromTableView().getIdFirme(),
                    this.textFieldsData.oib, this.textFieldsData.naziv), this.firmeControllerService.getFirmaFromTableView().getIdFirme());
            setNewData(updatedFirma);
        }
        return updatedFirma;
    }

    @Override
    public void setButtonDelete() {
        if (this.firmeControllerService.getFirmaFromTableView() != null && this.utilService.getConfirmForRemoveAlert()) {
            this.firmeService.deleteFirma(this.firmeControllerService.getFirmaFromTableView().getIdFirme());
            this.initialize();
        }
    }

    @Override
    public void setButtonClearFields() {
        this.clearRecords();
    }

    private FirmeEntity saveFirma() {
        return FirmeEntity.builder()
                .idFirme(this.nextId()).oibFirme(this.textFieldsData.oib).nazivFirme(this.textFieldsData.naziv).build();
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

    public void setProperty() {
        firmeControllerService.setProperty();
//        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idFirme"));
//        this.tableColumnOIB.setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
//        this.tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
    }

    public void setStyle() {
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

    private void setDataTotextFields(FirmeControllerService selectedData) {
        this.textFieldOIB.setText(selectedData.getFirmaFromTableView().getOibFirme());
        this.textFieldNaziv.setText(selectedData.getFirmaFromTableView().getNazivFirme());
    }

//    private class TableViewSelectedData {
//        final FirmeEntity firma = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
//        final String oib = firma.getOibFirme();
//        final String naziv = firma.getNazivFirme();
//    }

    private class TextFieldsData {
        final String oib = textFieldOIB.getText();
        final String naziv = textFieldNaziv.getText();
    }

    private class DataFromPropertiesForFirmeEntity {
        final TextFieldsData getData = new TextFieldsData();
        final String alertData = setInputCheckingOf(getData.naziv, getData.oib);
    }
}
