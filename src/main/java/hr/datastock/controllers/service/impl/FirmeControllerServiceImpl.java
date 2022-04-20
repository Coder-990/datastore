package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.FirmeController;
import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.controllers.service.FirmeControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FirmeControllerServiceImpl implements FirmeControllerService {

    private static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";

    private final FirmeService firmeService;
    private final UtilService utilService;

    private ObservableList<FirmeEntity> firmeObservableList;

    @Override
    @FXML
    public void init(FirmeController firmeController) {
        this.firmeObservableList = FXCollections.observableList(this.firmeService.getAll());
        this.setValuesToTableColumns(firmeController);
        this.clearRecords(firmeController);
        firmeController.getTableView().setItems(this.firmeObservableList);
    }

    @Override
    public void pluckingAllDataFromTableView(FirmeController firmeController) {
        if (this.getTableViewFirma(firmeController) != null) {
            firmeController.getTextFieldOIB().setText(this.getTableViewFirma(firmeController).getOibFirme());
            firmeController.getTextFieldNaziv().setText(this.getTableViewFirma(firmeController).getNazivFirme());
        } else if (this.getTableViewFirma(firmeController) == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void searchData(FirmeController firmeController) {
        if (firmeController.getTextFieldNaziv() != null || firmeController.getTextFieldOIB() != null) {
            final FilteredList<FirmeEntity> filteredList = this.firmeObservableList
                    .filtered(company -> company.getNazivFirme().toLowerCase().contains(firmeController.getTextFieldNaziv().getText()))
                    .filtered(company -> company.getOibFirme().toLowerCase().contains(firmeController.getTextFieldOIB().getText()));
            firmeController.getTableView().setItems(FXCollections.observableList(filteredList));
        }
    }

    @Override
    public FirmeEntity saveFirma(FirmeController firmeController) {
        if (this.getTextFieldDataForDialogCheck(firmeController).isEmpty()) {
            final FirmeEntity firma = this.save(firmeController);
            this.init(firmeController);
            return this.firmeService.createNew(firma);
        }
        this.utilService.getWarningAlert(this.getTextFieldDataForDialogCheck(firmeController));
        return null;
    }

    @Override
    public FirmeEntity updateFirma(FirmeController firmeController) {
        if (this.getTextFieldDataForDialogCheck(firmeController).isEmpty()) {
            final FirmeEntity firma = this.update(firmeController);
            this.init(firmeController);
            return this.firmeService.updateExisting(firma, firma.getIdFirme());
        }
        this.utilService.getWarningAlert(this.getTextFieldDataForDialogCheck(firmeController));
        return null;
    }

    @Override
    public void deleteFirma(FirmeController firmeController) {
        if (this.getTableViewFirma(firmeController) != null && this.utilService.isEntityRemoved()) {
            this.firmeService.deleteFirma(this.getTableViewFirma(firmeController).getIdFirme());
            this.init(firmeController);
        } else if (this.getTableViewFirma(firmeController) == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        } else {
            this.utilService.isEntityUnableToRemove();
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
                .idFirme(this.getTableViewFirma(firmeController).getIdFirme())
                .oibFirme(firmeController.getTextFieldOIB().getText())
                .nazivFirme(firmeController.getTextFieldNaziv().getText()).build();
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

    private String getTextFieldDataForDialogCheck(FirmeController firmeController) {
        final List<String> checkList = new ArrayList<>();
        if (firmeController.getTextFieldOIB().getText().trim().isEmpty()) checkList.add("Company name!");
        if (firmeController.getTextFieldNaziv().getText().trim().isEmpty()) checkList.add("Company identity number!");
        return String.join("\n", checkList);
    }

    private FirmeEntity getTableViewFirma(FirmeController firmeController) {
        return firmeController.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }
}
