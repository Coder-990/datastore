package hr.datastore.controllers.service.impl;

import hr.datastore.controllers.StavkaPrimkeController;
import hr.datastore.dialogutil.DialogService;
import hr.datastore.controllers.service.StavkaPrimkeControllerService;
import hr.datastore.entities.PrimkaEntity;
import hr.datastore.entities.RobaEntity;
import hr.datastore.entities.StavkaPrimkeEntity;
import hr.datastore.services.PrimkaService;
import hr.datastore.services.RobaService;
import hr.datastore.services.StavkaPrimkeService;
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
import java.util.Optional;

import static hr.datastore.controllers.service.impl.Const.FX_ALIGNMENT_CENTER;

@RequiredArgsConstructor
@Service
public class StavkaPrimkeControllerServiceImpl implements StavkaPrimkeControllerService {

    private final StavkaPrimkeService stavkaPrimkeService;

    private final PrimkaService primkaService;

    private final RobaService robaService;

    private final DialogService dialogService;

    private ObservableList<StavkaPrimkeEntity> stavkaPrimkeObservableList;
    private ObservableList<StavkaPrimkeEntity> filteredStavkaPrimkeObservableListOfStorno;

    @Override
    public void init(final StavkaPrimkeController spc) {
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
    public void searchData(final StavkaPrimkeController spc) {
        final FilteredList<StavkaPrimkeEntity> filteredList = this.filteredStavkaPrimkeObservableListOfStorno
                .filtered(stavkaIzdatnice -> isTextFieldFirmaContainingSomeData(spc, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isTextFieldArticleContainingSomeData(spc, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isTextFieldAmountContainingSomeData(spc, stavkaIzdatnice));
        spc.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public StavkaPrimkeEntity saveStavkaPrimke(final StavkaPrimkeController spc) {
        if (this.getInputDataForDialogCheck(spc).isEmpty()) {
            final StavkaPrimkeEntity stavkaPrimke = this.stavkaPrimkeService.createStavkaPrimke(this.save(spc));
            this.init(spc);
            return stavkaPrimke;
        }
        this.dialogService.getWarningAlert(this.getInputDataForDialogCheck(spc));
        return null;
    }

    @Override
    public Optional<StavkaPrimkeEntity> stornoStavkaPrimke(final StavkaPrimkeController spc) {
        final StavkaPrimkeEntity selectedStavka = this.getSelectedDataFromTableViewStavkaIzdatnice(spc);
        if (selectedStavka != null && this.dialogService.isEntityRemoved()) {
            final Optional<StavkaPrimkeEntity> stornoStavkePrimke = this.stavkaPrimkeService.createStornoStavkePrimke(selectedStavka);
            this.init(spc);
            return stornoStavkePrimke;
        } else if (selectedStavka == null) {
            this.dialogService.isDataPickedFromTableViewAlert();
        }
        return Optional.empty();
    }

    @Override
    public void clearRecords(final StavkaPrimkeController spc) {
        spc.getTextFieldKolicina().clear();
        spc.getTextFieldFirma().clear();
        spc.getTextFieldArticle().clear();
        spc.getComboBoxPrimka().getSelectionModel().clearSelection();
        spc.getComboBoxRoba().getSelectionModel().clearSelection();
        spc.getTableView().getSelectionModel().clearSelection();
    }

    private StavkaPrimkeEntity save(final StavkaPrimkeController spc) {
        return StavkaPrimkeEntity.builder()
                .idStavkaPrimke(this.nextId())
                .stavkaPrimkePrimka(this.getComboBoxPrimkaOnCreate(spc))
                .stavkaPrimkeRobe(this.getComboBoxRobaOnCreate(spc))
                .kolicina(Integer.valueOf(spc.getTextFieldKolicina().getText()))
                .storno(false)
                .datumStorno(null)
                .build();
    }

    private void setValuesToTableColumns(final StavkaPrimkeController spc) {
        this.setProperty(spc);
        this.setStyle(spc);
        this.setCellValueProperties(spc);
    }

    private void setProperty(final StavkaPrimkeController spc) {
        spc.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idStavkaPrimke"));
        spc.getTableColumnIdPrimke().setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkePrimka"));
        spc.getTableColumnArticle().setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkeRobe"));
        spc.getTableColumnKolicina().setCellValueFactory(new PropertyValueFactory<>("kolicina"));
    }

    private void setStyle(final StavkaPrimkeController spc) {
        spc.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        spc.getTableColumnIdPrimke().setStyle(FX_ALIGNMENT_CENTER);
        spc.getTableColumnArticle().setStyle(FX_ALIGNMENT_CENTER);
        spc.getTableColumnKolicina().setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties(final StavkaPrimkeController spc) {
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

    private String getInputDataForDialogCheck(final StavkaPrimkeController spc) {
        final List<String> checkList = new ArrayList<>();
        if (this.getComboBoxPrimkaOnCheck(spc) == null) checkList.add("Company!");
        if (this.getComboBoxRobaOnCheck(spc) == null) checkList.add("Article!");
        if (spc.getTextFieldKolicina().getText().trim().isEmpty()) checkList.add("Amount!");
        return String.join("\n", checkList);
    }

    private boolean isTextFieldAmountContainingSomeData(final StavkaPrimkeController spc, final StavkaPrimkeEntity stavkaPrimke) {
        final String textFieldAmount = spc.getTextFieldKolicina().getText();
        return StringUtils.equals(textFieldAmount, StringUtils.EMPTY) || textFieldAmount == null || stavkaPrimke
                .getKolicina().toString().equals(textFieldAmount);
    }

    private boolean isTextFieldArticleContainingSomeData(final StavkaPrimkeController spc, final StavkaPrimkeEntity stavkaPrimke) {
        final String textArticle = spc.getTextFieldArticle().getText();
        return StringUtils.equals(textArticle, StringUtils.EMPTY) || textArticle == null || stavkaPrimke
                .getStavkaPrimkeRobe().toString().toLowerCase().contains(textArticle);
    }

    private boolean isTextFieldFirmaContainingSomeData(final StavkaPrimkeController spc, final StavkaPrimkeEntity stavkaPrimke) {
        final String textFirme = spc.getTextFieldFirma().getText();
        return StringUtils.equals(textFirme, StringUtils.EMPTY) || textFirme == null || stavkaPrimke
                .getStavkaPrimkePrimka().getPrimkaFirme().toString()
                .toLowerCase().trim().contains(textFirme);
    }

    private void setComboBoxPrimkaEntity(final StavkaPrimkeController spc) {
        spc.getComboBoxPrimka().setItems(FXCollections.observableList(this.primkaService.getAll()));
        spc.getComboBoxPrimka().getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity(final StavkaPrimkeController spc) {
        spc.getComboBoxRoba().setItems(FXCollections.observableList(this.robaService.getAll()));
        spc.getComboBoxRoba().getSelectionModel().getSelectedItem();
    }

    private String getComboBoxPrimkaOnCheck(final StavkaPrimkeController spc) {
        return spc.getComboBoxPrimka().getSelectionModel().getSelectedItem() == null ? null :
                spc.getComboBoxPrimka().getSelectionModel().getSelectedItem().getPrimkaFirme().getNazivFirme();
    }

    private String getComboBoxRobaOnCheck(final StavkaPrimkeController spc) {
        return spc.getComboBoxRoba().getSelectionModel().getSelectedItem() == null ? null :
                spc.getComboBoxRoba().getSelectionModel().getSelectedItem().getNazivArtikla();
    }

    private PrimkaEntity getComboBoxPrimkaOnCreate(final StavkaPrimkeController spc) {
        return spc.getComboBoxPrimka().getSelectionModel().getSelectedItem() == null ? null :
                spc.getComboBoxPrimka().getSelectionModel().getSelectedItem();
    }

    private RobaEntity getComboBoxRobaOnCreate(final StavkaPrimkeController spc) {
        return spc.getComboBoxRoba().getSelectionModel().getSelectedItem() == null ? null :
                spc.getComboBoxRoba().getSelectionModel().getSelectedItem();
    }

    private StavkaPrimkeEntity getSelectedDataFromTableViewStavkaIzdatnice(final StavkaPrimkeController spc) {
        return spc.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }

}
