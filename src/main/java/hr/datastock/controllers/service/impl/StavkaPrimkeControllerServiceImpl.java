package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.StavkaPrimkeController;
import hr.datastock.dialogutil.UtilService;
import hr.datastock.controllers.service.StavkaPrimkeControllerService;
import hr.datastock.entities.PrimkaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaPrimkeEntity;
import hr.datastock.services.PrimkaService;
import hr.datastock.services.RobaService;
import hr.datastock.services.StavkaPrimkeService;
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
public class StavkaPrimkeControllerServiceImpl implements StavkaPrimkeControllerService {

    private final StavkaPrimkeService stavkaPrimkeService;

    private final PrimkaService primkaService;

    private final RobaService robaService;

    private final UtilService utilService;

    private ObservableList<StavkaPrimkeEntity> stavkaPrimkeObservableList;
    private ObservableList<StavkaPrimkeEntity> filteredStavkaPrimkeObservableListOfStorno;

    @Override
    public void init(StavkaPrimkeController spc) {
        this.stavkaPrimkeObservableList = FXCollections.observableList(this.stavkaPrimkeService.getAll());
        this.filteredStavkaPrimkeObservableListOfStorno = FXCollections.observableList(
                this.stavkaPrimkeObservableList.stream().filter(isStorno -> !isStorno.getStorno()).toList());
        this.setComboBoxPrimkaEntity(spc);
        this.setComboBoxRobaEntity(spc);
        this.setValuesToTableColumns(spc);
        this.clearRecords(spc);
        spc.getTableView().setItems(this.filteredStavkaPrimkeObservableListOfStorno);
    }

    @Override
    public void searchData(StavkaPrimkeController spc) {
        final FilteredList<StavkaPrimkeEntity> filteredList = this.filteredStavkaPrimkeObservableListOfStorno
                .filtered(stavkaIzdatnice -> isTextFieldFirmaContainingSomeData(spc, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isTextFieldArticleContainingSomeData(spc, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isTextFieldAmountContainingSomeData(spc, stavkaIzdatnice));
        spc.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public StavkaPrimkeEntity saveStavkaPrimke(StavkaPrimkeController spc) {
        if (this.getInputDataForDialogCheck(spc).isEmpty()) {
            final StavkaPrimkeEntity stavkaPrimke = this.stavkaPrimkeService.createStavkaPrimke(this.save(spc));
            this.init(spc);
            return stavkaPrimke;
        }
        this.utilService.getWarningAlert(this.getInputDataForDialogCheck(spc));
        return null;
    }

    @Override
    public void stornoStavkaPrimke(StavkaPrimkeController spc) {
        final StavkaPrimkeEntity selectedStavka = this.getSelectedDataFromTableViewStavkaIzdatnice(spc);
        if (selectedStavka != null && this.utilService.isEntityRemoved()) {
            this.stavkaPrimkeService.createStornoStavkePrimke(selectedStavka);
            this.init(spc);
        } else if (selectedStavka == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        }
    }

    @Override
    public void clearRecords(StavkaPrimkeController spc) {
        spc.getTextFieldKolicina().clear();
        spc.getTextFieldFirma().clear();
        spc.getTextFieldArticle().clear();
        spc.getComboBoxPrimka().getSelectionModel().clearSelection();
        spc.getComboBoxRoba().getSelectionModel().clearSelection();
        spc.getTableView().getSelectionModel().clearSelection();
    }

    private StavkaPrimkeEntity save(StavkaPrimkeController spc) {
        return StavkaPrimkeEntity.builder()
                .idStavkaPrimke(this.nextId())
                .stavkaPrimkePrimka(this.getComboBoxPrimkaOnCreate(spc))
                .stavkaPrimkeRobe(this.getComboBoxRobaOnCreate(spc))
                .kolicina(Integer.valueOf(spc.getTextFieldKolicina().getText()))
                .storno(false)
                .datumStorno(null)
                .build();
    }

    private void setValuesToTableColumns(StavkaPrimkeController spc) {
        this.setProperty(spc);
        this.setStyle(spc);
        this.setCellValueProperties(spc);
    }

    private void setProperty(StavkaPrimkeController spc) {
        spc.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idStavkaPrimke"));
        spc.getTableColumnIdPrimke().setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkePrimka"));
        spc.getTableColumnArticle().setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkeRobe"));
        spc.getTableColumnKolicina().setCellValueFactory(new PropertyValueFactory<>("kolicina"));
    }

    private void setStyle(StavkaPrimkeController spc) {
        spc.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        spc.getTableColumnIdPrimke().setStyle(FX_ALIGNMENT_CENTER);
        spc.getTableColumnArticle().setStyle(FX_ALIGNMENT_CENTER);
        spc.getTableColumnKolicina().setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties(StavkaPrimkeController spc) {
        spc.getTableColumnIdPrimke().setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(final PrimkaEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getPrimkaFirme().getOibFirme() + "-(" +
                            item.getPrimkaFirme().getNazivFirme() + ")-[created: " +
                            item.getDatum() + "]");
                }
            }
        });

        spc.getTableColumnArticle().setCellFactory(column -> new TableCell<>() {
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

    private Long nextId() {
        return !this.stavkaPrimkeObservableList.isEmpty() ?
                this.stavkaPrimkeObservableList.stream().mapToLong(StavkaPrimkeEntity::getIdStavkaPrimke).max().getAsLong() + 1 : 1;
    }

    private String getInputDataForDialogCheck(StavkaPrimkeController spc) {
        final List<String> checkList = new ArrayList<>();
        if (this.getComboBoxPrimkaOnCheck(spc) == null) checkList.add("Company!");
        if (this.getComboBoxRobaOnCheck(spc) == null) checkList.add("Article!");
        if (spc.getTextFieldKolicina().getText().trim().isEmpty()) checkList.add("Amount!");
        return String.join("\n", checkList);
    }

    private boolean isTextFieldAmountContainingSomeData(StavkaPrimkeController spc, StavkaPrimkeEntity stavkaPrimke) {
        String textFieldAmount = spc.getTextFieldKolicina().getText();
        return StringUtils.equals(textFieldAmount, StringUtils.EMPTY) || textFieldAmount == null || stavkaPrimke
                .getKolicina().toString().equals(textFieldAmount);
    }

    private boolean isTextFieldArticleContainingSomeData(StavkaPrimkeController spc, StavkaPrimkeEntity stavkaPrimke) {
        String textArticle = spc.getTextFieldArticle().getText();
        return StringUtils.equals(textArticle, StringUtils.EMPTY) || textArticle == null || stavkaPrimke
                .getStavkaPrimkeRobe().toString().toLowerCase().contains(textArticle);
    }

    private boolean isTextFieldFirmaContainingSomeData(StavkaPrimkeController spc, StavkaPrimkeEntity stavkaPrimke) {
        String textFirme = spc.getTextFieldFirma().getText();
        return StringUtils.equals(textFirme, StringUtils.EMPTY) || textFirme == null || stavkaPrimke
                .getStavkaPrimkePrimka().getPrimkaFirme().toString()
                .toLowerCase().trim().contains(textFirme);
    }

    private void setComboBoxPrimkaEntity(StavkaPrimkeController spc) {
        spc.getComboBoxPrimka().setItems(FXCollections.observableList(this.primkaService.getAll()));
        spc.getComboBoxPrimka().getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity(StavkaPrimkeController spc) {
        spc.getComboBoxRoba().setItems(FXCollections.observableList(this.robaService.getAll()));
        spc.getComboBoxRoba().getSelectionModel().getSelectedItem();
    }

    private String getComboBoxPrimkaOnCheck(StavkaPrimkeController spc) {
        return spc.getComboBoxPrimka().getSelectionModel().getSelectedItem() == null ? null :
                spc.getComboBoxPrimka().getSelectionModel().getSelectedItem().getPrimkaFirme().getNazivFirme();
    }

    private String getComboBoxRobaOnCheck(StavkaPrimkeController spc) {
        return spc.getComboBoxRoba().getSelectionModel().getSelectedItem() == null ? null :
                spc.getComboBoxRoba().getSelectionModel().getSelectedItem().getNazivArtikla();
    }

    private PrimkaEntity getComboBoxPrimkaOnCreate(StavkaPrimkeController spc) {
        return spc.getComboBoxPrimka().getSelectionModel().getSelectedItem() == null ? null :
                spc.getComboBoxPrimka().getSelectionModel().getSelectedItem();
    }

    private RobaEntity getComboBoxRobaOnCreate(StavkaPrimkeController spc) {
        return spc.getComboBoxRoba().getSelectionModel().getSelectedItem() == null ? null :
                spc.getComboBoxRoba().getSelectionModel().getSelectedItem();
    }

    private StavkaPrimkeEntity getSelectedDataFromTableViewStavkaIzdatnice(StavkaPrimkeController spc) {
        return spc.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }

}
