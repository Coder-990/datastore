package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.RobaController;
import hr.datastock.controllers.dialogutil.UtilService;
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
    private final UtilService utilService;
    private ObservableList<RobaEntity> robaObservableList;

    @Override
    public void init(RobaController robaController) {
        this.robaObservableList = FXCollections.observableList(this.robaService.getAll());
        this.setValuesToTableColumns(robaController);
        this.clearRecords(robaController);
        robaController.getTextAreaOpis().setWrapText(true);
        robaController.getTableView().setItems(this.robaObservableList);
    }

    @Override
    public void pluckSelectedDataFromTableViewRoba(RobaController robaController) {
        if (this.getSelectedDataFromTableViewRobe(robaController) != null) {
            robaController.getTextFieldNaziv().setText(this.getSelectedDataFromTableViewRobe(robaController).getNazivArtikla());
            robaController.getTextFieldKolicina().setText(this.getSelectedDataFromTableViewRobe(robaController).getKolicina().toString());
            robaController.getTextFieldCijena().setText(this.getSelectedDataFromTableViewRobe(robaController).getCijena().toEngineeringString());
            robaController.getTextAreaOpis().setText(this.getSelectedDataFromTableViewRobe(robaController).getOpis());
            robaController.getTextFieldJedinicaMjere().setText(this.getSelectedDataFromTableViewRobe(robaController).getJmj());
        } else if (this.getSelectedDataFromTableViewRobe(robaController) == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void searchData(RobaController robaController) {
        final FilteredList<RobaEntity> filteredList = this.robaObservableList
                .filtered(roba -> roba.getNazivArtikla().toLowerCase().contains(robaController.getTextFieldNaziv().getText()))
                .filtered(roba -> robaController.getTextFieldCijena().getText() == null || robaController.getTextFieldCijena().getText().equals(StringUtils.EMPTY)
                        || roba.getCijena().equals(new BigDecimal(robaController.getTextFieldCijena().getText())))
                .filtered(roba -> robaController.getTextFieldKolicina().getText() == null || robaController.getTextFieldKolicina().getText().equals(StringUtils.EMPTY)
                        || roba.getKolicina().equals(Integer.parseInt(robaController.getTextFieldKolicina().getText())))
                .filtered(roba -> roba.getOpis().toLowerCase().contains(robaController.getTextAreaOpis().getText()));
        robaController.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public RobaEntity saveArtikl(RobaController robaController) {
        if (this.getInputDataForDialogCheck(robaController).isEmpty()) {
            final RobaEntity artikl = this.robaService.createNew(this.save(robaController));
            this.init(robaController);
            return artikl;
        }
        this.utilService.getWarningAlert(this.getInputDataForDialogCheck(robaController));
        return null;
    }

    @Override
    public RobaEntity updateArtikl(RobaController robaController) {
        if (this.getInputDataForDialogCheck(robaController).isEmpty()) {
            RobaEntity updateEntity = this.update(robaController);
            final RobaEntity artikl = this.robaService.updateExisting(updateEntity, updateEntity.getIdRobe());
            this.init(robaController);
            return artikl;
        }
        this.utilService.getWarningAlert(this.getInputDataForDialogCheck(robaController));
        return null;
    }

    @Override
    public void deleteArtikl(RobaController robaController) {
        if (this.getSelectedDataFromTableViewRobe(robaController) != null && this.utilService.isEntityRemoved()) {
            this.robaService.deleteRoba(this.getSelectedDataFromTableViewRobe(robaController).getIdRobe());
            this.init(robaController);
        } else if (this.getSelectedDataFromTableViewRobe(robaController) == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void clearRecords(RobaController robaController) {
        robaController.getTableView().getSelectionModel().clearSelection();
        robaController.getTextFieldNaziv().clear();
        robaController.getTextFieldCijena().clear();
        robaController.getTextFieldKolicina().clear();
        robaController.getTextFieldJedinicaMjere().clear();
        robaController.getTextAreaOpis().clear();
    }

    private RobaEntity save(RobaController robaController) {
        return RobaEntity.builder()
                .idRobe(this.nextId())
                .nazivArtikla(robaController.getTextFieldNaziv().getText())
                .kolicina(Integer.valueOf(robaController.getTextFieldKolicina().getText()))
                .cijena(new BigDecimal(robaController.getTextFieldCijena().getText()))
                .opis(robaController.getTextAreaOpis().getText())
                .jmj(robaController.getTextFieldJedinicaMjere().getText())
                .build();
    }

    private RobaEntity update(RobaController robaController) {
        return RobaEntity.builder()
                .idRobe(this.getSelectedDataFromTableViewRobe(robaController).getIdRobe())
                .nazivArtikla(robaController.getTextFieldNaziv().getText())
                .kolicina(Integer.valueOf(robaController.getTextFieldKolicina().getText()))
                .cijena(new BigDecimal(robaController.getTextFieldCijena().getText()))
                .opis(robaController.getTextAreaOpis().getText())
                .jmj(robaController.getTextFieldJedinicaMjere().getText())
                .build();
    }

    private void setValuesToTableColumns(RobaController robaController) {
        this.setProperties(robaController);
        this.setStyle(robaController);
    }

    private void setProperties(RobaController robaController) {
        robaController.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idRobe"));
        robaController.getTableColumnNaziv().setCellValueFactory(new PropertyValueFactory<>("nazivArtikla"));
        robaController.getTableColumnCijena().setCellValueFactory(new PropertyValueFactory<>("cijena"));
        robaController.getTableColumnKolicina().setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        robaController.getTableColumnJedinicnaMjera().setCellValueFactory(new PropertyValueFactory<>("jmj"));
    }

    private void setStyle(RobaController robaController) {
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

    private String getInputDataForDialogCheck(RobaController robaController) {
        final List<String> checkList = new ArrayList<>();
        if (robaController.getTextFieldNaziv().getText().trim().isEmpty()) checkList.add("Article name!");
        if (robaController.getTextFieldKolicina().getText().trim().isEmpty()) checkList.add("Amount");
        if (robaController.getTextFieldCijena().getText().trim().isEmpty()) checkList.add("Price");
        if (robaController.getTextFieldJedinicaMjere().getText().trim().isEmpty()) checkList.add("Unit of measure");
        if (robaController.getTextAreaOpis().getText().trim().isEmpty()) checkList.add("Describe");
        return String.join("\n", checkList);
    }

    private RobaEntity getSelectedDataFromTableViewRobe(RobaController robaController) {
        return robaController.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }

}
