package hr.datastore.controllers.service.impl;

import hr.datastore.controllers.FirmeController;
import hr.datastore.controllers.service.FirmeControllerService;
import hr.datastore.dialogutil.DialogService;
import hr.datastore.entities.FirmeEntity;
import hr.datastore.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static hr.datastore.controllers.service.impl.Const.FX_ALIGNMENT_CENTER;

@RequiredArgsConstructor
@Service
public class FirmeControllerServiceImpl implements FirmeControllerService {

    private final FirmeService firmeService;
    private final DialogService dialogService;

    private ObservableList<FirmeEntity> firmeObservableList;

    @Override
    public void init(final FirmeController firmeController) {
        this.firmeObservableList = FXCollections.observableList(this.firmeService.getAll());
        this.setValuesToTableColumns(firmeController);
        this.clearRecords(firmeController);
        firmeController.getTableView().setItems(this.firmeObservableList);
    }

    @Override
    public void pluckSelectedDataFromTableViewFirma(final FirmeController firmeController) {
        final FirmeEntity selectedFirma = this.getSelectedDataFromTableViewFirma(firmeController);
        if (selectedFirma != null) {
            firmeController.getTextFieldOIB().setText(selectedFirma.getOibFirme());
            firmeController.getTextFieldNaziv().setText(selectedFirma.getNazivFirme());
        } else {
            this.dialogService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void searchData(final FirmeController firmeController) {
        final FilteredList<FirmeEntity> filteredList = this.firmeObservableList
                .filtered(company -> isTextFieldNazivContainingSomeData(firmeController, company))
                .filtered(company -> isTextFieldOIBContainingSomeData(firmeController, company));
        firmeController.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public FirmeEntity saveFirma(final FirmeController firmeController) {
        final String dataCheck = this.getInputDataForDialogCheck(firmeController);
        if (StringUtils.isEmpty(dataCheck)) {
            final FirmeEntity firma = firmeService.createFirma(this.save(firmeController));
            this.init(firmeController);
            return firma;
        }
        this.dialogService.getWarningAlert(dataCheck);
        return null;
    }

    @Override
    public FirmeEntity updateFirma(final FirmeController firmeController) {
        final String checkData = this.getInputDataForDialogCheck(firmeController);
        if (StringUtils.isEmpty(checkData)) {
            final FirmeEntity entity = this.update(firmeController);
            final FirmeEntity firma = this.firmeService.updateExistingFirma(entity, entity.getIdFirme());
            this.init(firmeController);
            return firma;
        }
        this.dialogService.getWarningAlert(checkData);
        return null;
    }

    @Override
    public void deleteFirma(final FirmeController firmeController) {
        final FirmeEntity selectedFirma = this.getSelectedDataFromTableViewFirma(firmeController);
        if (selectedFirma != null && this.dialogService.isEntityRemoved()) {
            this.firmeService.deleteFirma(selectedFirma.getIdFirme());
            this.init(firmeController);
        } else if (selectedFirma == null) {
            this.dialogService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void clearRecords(final FirmeController firmeController) {
        firmeController.getTextFieldNaziv().clear();
        firmeController.getTextFieldOIB().clear();
        firmeController.getTableView().getSelectionModel().clearSelection();
    }

    @Override
    public String getInputDataForDialogCheck(final FirmeController firmeController) {
        final List<String> checkList = new ArrayList<>();
        if (firmeController.getTextFieldOIB().getText().trim().isEmpty()) checkList.add("Company name!");
        if (firmeController.getTextFieldNaziv().getText().trim().isEmpty()) checkList.add("Company identity number!");
        return String.join("\n", checkList);
    }

    private FirmeEntity save(final FirmeController firmeController) {
        return FirmeEntity.builder()
                .idFirme(this.nextId())
                .oibFirme(firmeController.getTextFieldOIB().getText())
                .nazivFirme(firmeController.getTextFieldNaziv().getText()).build();
    }

    private FirmeEntity update(final FirmeController firmeController) {
        return FirmeEntity.builder()
                .idFirme(this.getSelectedDataFromTableViewFirma(firmeController).getIdFirme())
                .oibFirme(firmeController.getTextFieldOIB().getText())
                .nazivFirme(firmeController.getTextFieldNaziv().getText())
                .build();
    }

    private boolean isTextFieldNazivContainingSomeData(final FirmeController firmeController, final FirmeEntity company) {
        final String textFieldNaziv = firmeController.getTextFieldNaziv().getText();
        return StringUtils.equals(textFieldNaziv, StringUtils.EMPTY) || textFieldNaziv == null ||
                company.getNazivFirme().toLowerCase().contains(textFieldNaziv);
    }

    private boolean isTextFieldOIBContainingSomeData(final FirmeController firmeController, final FirmeEntity company) {
        final String textFieldOIB = firmeController.getTextFieldOIB().getText();
        return StringUtils.equals(textFieldOIB, StringUtils.EMPTY) || textFieldOIB == null ||
                company.getOibFirme().toLowerCase().contains(textFieldOIB);
    }

    private void setValuesToTableColumns(final FirmeController firmeController) {
        this.setProperty(firmeController);
        this.setStyle(firmeController);
    }

    private void setProperty(final FirmeController firmeController) {
        firmeController.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idFirme"));
        firmeController.getTableColumnOIB().setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
        firmeController.getTableColumnNaziv().setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
    }

    private void setStyle(final FirmeController firmeController) {
        firmeController.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        firmeController.getTableColumnOIB().setStyle(FX_ALIGNMENT_CENTER);
        firmeController.getTableColumnNaziv().setStyle(FX_ALIGNMENT_CENTER);
    }

    private Long nextId() {
        return !this.firmeObservableList.isEmpty() ?
                this.firmeObservableList.stream()
                        .mapToLong(FirmeEntity::getIdFirme)
                        .max()
                        .getAsLong() + 1 : 1;
    }

    private FirmeEntity getSelectedDataFromTableViewFirma(final FirmeController firmeController) {
        return firmeController.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }
}
