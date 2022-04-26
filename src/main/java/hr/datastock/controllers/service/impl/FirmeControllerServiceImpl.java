package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.FirmeController;
import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.controllers.service.FirmeControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static hr.datastock.controllers.service.impl.Const.FX_ALIGNMENT_CENTER;

@RequiredArgsConstructor
@Service
public class FirmeControllerServiceImpl implements FirmeControllerService {

    private final FirmeService firmeService;
    private final UtilService utilService;

    private ObservableList<FirmeEntity> firmeObservableList;

    @Override
    public void init(FirmeController firmeController) {
        this.firmeObservableList = FXCollections.observableList(this.firmeService.getAll());
        this.setValuesToTableColumns(firmeController);
        this.clearRecords(firmeController);
        firmeController.getTableView().setItems(this.firmeObservableList);
    }

    @Override
    public void pluckSelectedDataFromTableViewFirma(FirmeController firmeController) {
        FirmeEntity selectedFirma = this.getSelectedDataFromTableViewFirma(firmeController);
        if (selectedFirma != null) {
            firmeController.getTextFieldOIB().setText(selectedFirma.getOibFirme());
            firmeController.getTextFieldNaziv().setText(selectedFirma.getNazivFirme());
        } else {
            this.utilService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void searchData(FirmeController firmeController) {
        final FilteredList<FirmeEntity> filteredList = this.firmeObservableList
                .filtered(company -> isTextFieldNazivContainingSomeData(firmeController, company))
                .filtered(company -> isTextFieldOIBContainingSomeData(firmeController, company));
        firmeController.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public FirmeEntity saveFirma(FirmeController firmeController) {
        final String dataCheck = this.getInputDataForDialogCheck(firmeController);
        if (StringUtils.isEmpty(dataCheck)) {
            final FirmeEntity firma = this.save(firmeController);
            this.init(firmeController);
            return this.firmeService.createNew(firma);
        }
        this.utilService.getWarningAlert(dataCheck);
        return null;
    }

    @Override
    public FirmeEntity updateFirma(FirmeController firmeController) {
        String checkData = this.getInputDataForDialogCheck(firmeController);
        if (StringUtils.isEmpty(checkData)) {
            final FirmeEntity firma = this.update(firmeController);
            this.init(firmeController);
            return this.firmeService.updateExisting(firma, firma.getIdFirme());
        }
        this.utilService.getWarningAlert(checkData);
        return null;
    }

    @Override
    public void deleteFirma(FirmeController firmeController) {
        FirmeEntity selectedFirma = this.getSelectedDataFromTableViewFirma(firmeController);
        if (selectedFirma != null && this.utilService.isEntityRemoved()) {
            this.firmeService.deleteFirma(selectedFirma.getIdFirme());
            this.init(firmeController);
        } else if (selectedFirma == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void clearRecords(FirmeController firmeController) {
        firmeController.getTextFieldNaziv().clear();
        firmeController.getTextFieldOIB().clear();
        firmeController.getTableView().getSelectionModel().clearSelection();
    }

    private FirmeEntity save(FirmeController firmeController) {
        return FirmeEntity.builder()
                .idFirme(this.nextId())
                .oibFirme(firmeController.getTextFieldOIB().getText())
                .nazivFirme(firmeController.getTextFieldNaziv().getText()).build();
    }

    private FirmeEntity update(FirmeController firmeController) {
        return FirmeEntity.builder()
                .idFirme(this.getSelectedDataFromTableViewFirma(firmeController).getIdFirme())
                .oibFirme(firmeController.getTextFieldOIB().getText())
                .nazivFirme(firmeController.getTextFieldNaziv().getText())
                .build();
    }

    private boolean isTextFieldNazivContainingSomeData(FirmeController firmeController, FirmeEntity company) {
        String textFieldNaziv = firmeController.getTextFieldNaziv().getText();
        return StringUtils.equals(textFieldNaziv, StringUtils.EMPTY) || textFieldNaziv == null ||
                company.getNazivFirme().toLowerCase().contains(textFieldNaziv);
    }

    private boolean isTextFieldOIBContainingSomeData(FirmeController firmeController, FirmeEntity company) {
        String textFieldOIB = firmeController.getTextFieldOIB().getText();
        return StringUtils.equals(textFieldOIB, StringUtils.EMPTY) || textFieldOIB == null ||
                company.getOibFirme().toLowerCase().contains(textFieldOIB);
    }

    private void setValuesToTableColumns(FirmeController firmeController) {
        this.setProperty(firmeController);
        this.setStyle(firmeController);
    }

    private void setProperty(FirmeController firmeController) {
        firmeController.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idFirme"));
        firmeController.getTableColumnOIB().setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
        firmeController.getTableColumnNaziv().setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
    }

    private void setStyle(FirmeController firmeController) {
        firmeController.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        firmeController.getTableColumnOIB().setStyle(FX_ALIGNMENT_CENTER);
        firmeController.getTableColumnNaziv().setStyle(FX_ALIGNMENT_CENTER);
    }

    private Long nextId() {
        return !this.firmeObservableList.isEmpty() ?
                this.firmeObservableList.stream().mapToLong(FirmeEntity::getIdFirme).max().getAsLong() + 1 : 1;
    }

    @Override
    public String getInputDataForDialogCheck(FirmeController firmeController) {
        final List<String> checkList = new ArrayList<>();
        if (firmeController.getTextFieldOIB().getText().trim().isEmpty()) checkList.add("Company name!");
        if (firmeController.getTextFieldNaziv().getText().trim().isEmpty()) checkList.add("Company identity number!");
        return String.join("\n", checkList);
    }

    private FirmeEntity getSelectedDataFromTableViewFirma(FirmeController firmeController) {
        return firmeController.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }
}
