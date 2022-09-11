package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.RobaController;
import hr.datastock.dialogutil.DialogService;
import hr.datastock.controllers.service.RobaControllerService;
import hr.datastock.entities.RobaEntity;
import hr.datastock.services.RobaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static hr.datastock.controllers.service.impl.Const.FX_ALIGNMENT_CENTER;

@RequiredArgsConstructor
@Service
public class RobaControllerServiceImpl implements RobaControllerService {

    private final RobaService robaService;
    private final DialogService dialogService;
    private ObservableList<RobaEntity> robaObservableList;

    @Override
    public void init(final RobaController robaController) {
        this.robaObservableList = FXCollections.observableList(this.robaService.getAll());
        this.setValuesToTableColumns(robaController);
        this.clearRecords(robaController);
        robaController.getTextAreaOpis().setWrapText(true);
        robaController.getTableView().setItems(this.robaObservableList);
    }

    @Override
    public void pluckSelectedDataFromTableViewRoba(final RobaController robaController) {
        final RobaEntity selectedData = this.getSelectedDataFromTableViewRobe(robaController);
        if (selectedData != null) {
            robaController.getTextFieldNaziv().setText(selectedData.getNazivArtikla());
            robaController.getTextFieldKolicina().setText(selectedData.getKolicina().toString());
            robaController.getTextFieldCijena().setText(selectedData.getCijena().toEngineeringString());
            robaController.getTextAreaOpis().setText(selectedData.getOpis());
            robaController.getTextFieldJedinicaMjere().setText(selectedData.getJmj());
        } else {
            this.dialogService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void searchData(final RobaController robaController) {
        final FilteredList<RobaEntity> filteredList = this.robaObservableList
                .filtered(roba -> isTextFieldNazivContainingSomeData(robaController, roba))
                .filtered(roba -> isTextFieldCijenaContainingSomeData(robaController, roba))
                .filtered(roba -> isTextFieldKolicinaKontainingSomeData(robaController, roba))
                .filtered(roba -> isTextAreaOpisContainingSomeData(robaController, roba));
        robaController.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public RobaEntity saveArtikl(final RobaController robaController) {
        if (this.getInputDataForDialogCheck(robaController).isEmpty()) {
            final RobaEntity artikl = this.robaService.createArticle(this.save(robaController));
            this.init(robaController);
            return artikl;
        }
        this.dialogService.getWarningAlert(this.getInputDataForDialogCheck(robaController));
        return null;
    }

    @Override
    public RobaEntity updateArtikl(final RobaController robaController) {
        if (this.getInputDataForDialogCheck(robaController).isEmpty()) {
            final RobaEntity updateEntity = this.update(robaController);
            final RobaEntity artikl = this.robaService.updateExistingArticle(updateEntity, updateEntity.getIdRobe());
            this.init(robaController);
            return artikl;
        }
        this.dialogService.getWarningAlert(this.getInputDataForDialogCheck(robaController));
        return null;
    }

    @Override
    public void deleteArtikl(final RobaController robaController) {
        if (this.getSelectedDataFromTableViewRobe(robaController) != null && this.dialogService.isEntityRemoved()) {
            this.robaService.deleteRoba(this.getSelectedDataFromTableViewRobe(robaController).getIdRobe());
            this.init(robaController);
        } else if (this.getSelectedDataFromTableViewRobe(robaController) == null) {
            this.dialogService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void clearRecords(final RobaController robaController) {
        robaController.getTableView().getSelectionModel().clearSelection();
        robaController.getTextFieldNaziv().clear();
        robaController.getTextFieldCijena().clear();
        robaController.getTextFieldKolicina().clear();
        robaController.getTextFieldJedinicaMjere().clear();
        robaController.getTextAreaOpis().clear();
    }

    private RobaEntity save(final RobaController robaController) {
        return RobaEntity.builder()
                .idRobe(this.nextId())
                .nazivArtikla(robaController.getTextFieldNaziv().getText())
                .kolicina(Integer.valueOf(robaController.getTextFieldKolicina().getText()))
                .cijena(new BigDecimal(robaController.getTextFieldCijena().getText()))
                .opis(robaController.getTextAreaOpis().getText())
                .jmj(robaController.getTextFieldJedinicaMjere().getText())
                .build();
    }

    private RobaEntity update(final RobaController robaController) {
        return RobaEntity.builder()
                .idRobe(this.getSelectedDataFromTableViewRobe(robaController).getIdRobe())
                .nazivArtikla(robaController.getTextFieldNaziv().getText())
                .kolicina(Integer.valueOf(robaController.getTextFieldKolicina().getText()))
                .cijena(new BigDecimal(robaController.getTextFieldCijena().getText()))
                .opis(robaController.getTextAreaOpis().getText())
                .jmj(robaController.getTextFieldJedinicaMjere().getText())
                .build();
    }

    private void setValuesToTableColumns(final RobaController robaController) {
        this.setProperties(robaController);
        this.setStyle(robaController);
    }

    private void setProperties(final RobaController robaController) {
        robaController.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idRobe"));
        robaController.getTableColumnNaziv().setCellValueFactory(new PropertyValueFactory<>("nazivArtikla"));
        robaController.getTableColumnCijena().setCellValueFactory(new PropertyValueFactory<>("cijena"));
        robaController.getTableColumnKolicina().setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        robaController.getTableColumnJedinicnaMjera().setCellValueFactory(new PropertyValueFactory<>("jmj"));
    }

    private void setStyle(final RobaController robaController) {
        robaController.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        robaController.getTableColumnNaziv().setStyle(FX_ALIGNMENT_CENTER);
        robaController.getTableColumnCijena().setStyle(FX_ALIGNMENT_CENTER);
        robaController.getTableColumnKolicina().setStyle(FX_ALIGNMENT_CENTER);
        robaController.getTableColumnJedinicnaMjera().setStyle(FX_ALIGNMENT_CENTER);
    }

    private Long nextId() {
        return !this.robaObservableList.isEmpty() ?
                this.robaObservableList.stream().mapToLong(RobaEntity::getIdRobe).max().getAsLong() + 1 : 1;
    }

    private String getInputDataForDialogCheck(final RobaController robaController) {
        final List<String> checkList = new ArrayList<>();
        if (robaController.getTextFieldNaziv().getText().trim().isEmpty()) checkList.add("Article name!");
        if (robaController.getTextFieldKolicina().getText().trim().isEmpty()) checkList.add("Amount");
        if (robaController.getTextFieldCijena().getText().trim().isEmpty()) checkList.add("Price");
        if (robaController.getTextFieldJedinicaMjere().getText().trim().isEmpty()) checkList.add("Unit of measure");
        if (robaController.getTextAreaOpis().getText().trim().isEmpty()) checkList.add("Describe");
        return String.join("\n", checkList);
    }

    private boolean isTextAreaOpisContainingSomeData(final RobaController robaController, final RobaEntity roba) {
        return roba.getOpis().toLowerCase().contains(robaController.getTextAreaOpis().getText());
    }

    private boolean isTextFieldNazivContainingSomeData(final RobaController robaController, final RobaEntity roba) {
        return roba.getNazivArtikla().toLowerCase().contains(robaController.getTextFieldNaziv().getText());
    }

    private boolean isTextFieldKolicinaKontainingSomeData(RobaController robaController, RobaEntity roba) {
        final String valueFromTextField = robaController.getTextFieldKolicina().getText();
        return valueFromTextField == null || valueFromTextField.equals(StringUtils.EMPTY)
                || roba.getKolicina().equals(Integer.parseInt(valueFromTextField));
    }

    private boolean isTextFieldCijenaContainingSomeData(final RobaController robaController, final RobaEntity roba) {
        final String valueFromTextField = robaController.getTextFieldCijena().getText();
        return valueFromTextField == null || valueFromTextField.equals(StringUtils.EMPTY)
                || roba.getCijena().equals(new BigDecimal(valueFromTextField));
    }

    private RobaEntity getSelectedDataFromTableViewRobe(final RobaController robaController) {
        return robaController.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }

}
