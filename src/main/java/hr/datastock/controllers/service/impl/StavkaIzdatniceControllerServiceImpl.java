package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.StavkaIzdatniceController;
import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.controllers.service.StavkaIzdatniceControllerService;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.services.IzdatnicaService;
import hr.datastock.services.RobaService;
import hr.datastock.services.StavkaIzdatniceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static hr.datastock.controllers.service.impl.Const.FX_ALIGNMENT_CENTER;

@RequiredArgsConstructor
@Service
public class StavkaIzdatniceControllerServiceImpl implements StavkaIzdatniceControllerService {

    private final StavkaIzdatniceService stavkaIzdatniceService;

    private final IzdatnicaService izdatnicaService;

    private final RobaService robaService;

    private final UtilService utilService;

    private ObservableList<StavkaIzdatniceEntity> stavkaIzdatniceObservableList;
    private ObservableList<StavkaIzdatniceEntity> filteredStavkaIzdatniceObservableListOfStorno;

    @Override
    public void init(StavkaIzdatniceController sic) {
        this.stavkaIzdatniceObservableList = FXCollections.observableList(this.stavkaIzdatniceService.getAll());
        this.filteredStavkaIzdatniceObservableListOfStorno = FXCollections.observableList(
                this.stavkaIzdatniceObservableList.stream().filter(isStorno -> !isStorno.getStorno()).toList());
        this.setComboBoxIzdatnicaEntity(sic);
        this.setComboBoxRobaEntity(sic);
        this.setValuesToTableColumns(sic);
        this.clearRecords(sic);

        sic.getTableView().setItems(this.filteredStavkaIzdatniceObservableListOfStorno);
    }

    @Override
    public void searchData(StavkaIzdatniceController sic) {
        final FilteredList<StavkaIzdatniceEntity> filteredList = this.filteredStavkaIzdatniceObservableListOfStorno
                .filtered(stavkaIzdatnice -> isTextFieldFirmaContainingSomeData(sic, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isTextFieldArticleContainingSomeData(sic, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isTextFieldAmountContainingSomeData(sic, stavkaIzdatnice));
        sic.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public StavkaIzdatniceEntity saveStavkaIzdatnice(StavkaIzdatniceController sic) {
        if (this.getInputDataForDialogCheck(sic).isEmpty()) {
            final StavkaIzdatniceEntity stavkaIzdatnice = this.save(sic);
            this.init(sic);
            return this.stavkaIzdatniceService.createStavkaIzdatnice(stavkaIzdatnice);
        }
        this.utilService.getWarningAlert(this.getInputDataForDialogCheck(sic));
        return null;
    }

    @Override
    public void stornoStavkaIzdatnice(StavkaIzdatniceController sic) {
        final StavkaIzdatniceEntity stavkaIzdatnice = this.getSelectedDataFromTableViewStavkaIzdatnice(sic);
        if (stavkaIzdatnice != null && this.utilService.isEntityRemoved()) {
            this.stavkaIzdatniceService.createStornoStavkeIzdatnice(stavkaIzdatnice);
            this.init(sic);
        }
    }

    @Override
    public void clearRecords(StavkaIzdatniceController sic) {
        sic.getTextFieldKolicina().clear();
        sic.getTextFieldFirma().clear();
        sic.getTextFieldArticle().clear();
        sic.getComboBoxIzdatnica().getSelectionModel().clearSelection();
        sic.getComboBoxRoba().getSelectionModel().clearSelection();
        sic.getTableView().getSelectionModel().clearSelection();
    }

    private StavkaIzdatniceEntity save(StavkaIzdatniceController sic) {
        return StavkaIzdatniceEntity.builder()
                .idStavkaIzdatnice(this.nextId())
                .stavkaIzdatniceIzdatnica(this.getComboBoxIzdatnicaOnCreate(sic))
                .stavkaIzdatniceRobe(this.getComboBoxRobaOnCreate(sic))
                .kolicina(Integer.valueOf(sic.getTextFieldKolicina().getText()))
                .storno(false)
                .datumStorno(null)
                .build();
    }

    private void setValuesToTableColumns(StavkaIzdatniceController sic) {
        this.setProperty(sic);
        this.setStyle(sic);
        this.setCellValueProperties(sic);
    }

    private void setProperty(StavkaIzdatniceController sic) {
        sic.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idStavkaIzdatnice"));
        sic.getTableColumnIdIzdatnice().setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceIzdatnica"));
        sic.getTableColumnArticle().setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceRobe"));
        sic.getTableColumnKolicina().setCellValueFactory(new PropertyValueFactory<>("kolicina"));
    }

    private void setStyle(StavkaIzdatniceController sic) {
        sic.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        sic.getTableColumnIdIzdatnice().setStyle(FX_ALIGNMENT_CENTER);
        sic.getTableColumnArticle().setStyle(FX_ALIGNMENT_CENTER);
        sic.getTableColumnKolicina().setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties(StavkaIzdatniceController sic) {
        sic.getTableColumnIdIzdatnice().setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(final IzdatnicaEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getIzdatnicaFirme().getOibFirme() + "-(" +
                            item.getIzdatnicaFirme().getNazivFirme() + ")-[created: " +
                            item.getDatum() + "]");
                }
            }
        });

        sic.getTableColumnArticle().setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(final RobaEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getNazivArtikla() + "-(price: " +
                            item.getCijena() + ")-[pieces: " +
                            item.getKolicina() + "]");
                }
            }
        });
    }

    private boolean isTextFieldAmountContainingSomeData(StavkaIzdatniceController sic, StavkaIzdatniceEntity stavkaIzdatnice) {
        String textFieldAmount = sic.getTextFieldKolicina().getText();
        return StringUtils.equals(textFieldAmount, StringUtils.EMPTY) || textFieldAmount == null || stavkaIzdatnice
                .getKolicina().toString().equals(textFieldAmount);
    }

    private boolean isTextFieldArticleContainingSomeData(StavkaIzdatniceController sic, StavkaIzdatniceEntity stavkaIzdatnice) {
        String textFieldArticle = sic.getTextFieldArticle().getText();
        return StringUtils.equals(textFieldArticle, StringUtils.EMPTY) || textFieldArticle == null || stavkaIzdatnice
                .getStavkaIzdatniceRobe().getNazivArtikla().toLowerCase().contains(textFieldArticle);
    }

    private boolean isTextFieldFirmaContainingSomeData(StavkaIzdatniceController sic, StavkaIzdatniceEntity stavkaIzdatnice) {
        String textFieldFirma = sic.getTextFieldFirma().getText();
        return StringUtils.equals(textFieldFirma, StringUtils.EMPTY) || textFieldFirma == null || stavkaIzdatnice
                .getStavkaIzdatniceIzdatnica().getIzdatnicaFirme().getNazivFirme()
                .toLowerCase().trim().contains(textFieldFirma);
    }

    private Long nextId() {
        return !this.stavkaIzdatniceObservableList.isEmpty() ?
                this.stavkaIzdatniceObservableList.stream().mapToLong(StavkaIzdatniceEntity::getIdStavkaIzdatnice).max().getAsLong() + 1 : 1;
    }

    private String getInputDataForDialogCheck(StavkaIzdatniceController sic) {
        final List<String> listaProvjere = new ArrayList<>();
        if (this.getComboBoxIzdatnicaOnCheck(sic) == null) listaProvjere.add("Company!");
        if (this.getComboBoxRobaOnCheck(sic) == null) listaProvjere.add("Article!");
        if (sic.getTextFieldKolicina().getText().trim().isEmpty()) listaProvjere.add("Amount!");
        return String.join("\n", listaProvjere);
    }

    private void setComboBoxIzdatnicaEntity(StavkaIzdatniceController sic) {
        sic.getComboBoxIzdatnica().setItems(FXCollections.observableList(this.izdatnicaService.getAll()));
        sic.getComboBoxIzdatnica().getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity(StavkaIzdatniceController sic) {
        sic.getComboBoxRoba().setItems(FXCollections.observableList(this.robaService.getAll()));
        sic.getComboBoxRoba().getSelectionModel().getSelectedItem();
    }

    private String getComboBoxIzdatnicaOnCheck(StavkaIzdatniceController sic) {
        return sic.getComboBoxIzdatnica().getSelectionModel().getSelectedItem() == null ? null :
                sic.getComboBoxIzdatnica().getSelectionModel().getSelectedItem().getIzdatnicaFirme().getNazivFirme();
    }

    private String getComboBoxRobaOnCheck(StavkaIzdatniceController sic) {
        return sic.getComboBoxRoba().getSelectionModel().getSelectedItem() == null ? null :
                sic.getComboBoxRoba().getSelectionModel().getSelectedItem().getNazivArtikla();
    }

    private IzdatnicaEntity getComboBoxIzdatnicaOnCreate(StavkaIzdatniceController sic) {
        return sic.getComboBoxIzdatnica().getSelectionModel().getSelectedItem() == null ? null :
                sic.getComboBoxIzdatnica().getSelectionModel().getSelectedItem();
    }

    private RobaEntity getComboBoxRobaOnCreate(StavkaIzdatniceController sic) {
        return sic.getComboBoxRoba().getSelectionModel().getSelectedItem() == null ? null :
                sic.getComboBoxRoba().getSelectionModel().getSelectedItem();
    }

    private StavkaIzdatniceEntity getSelectedDataFromTableViewStavkaIzdatnice(StavkaIzdatniceController sic) {
        return sic.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }
}
