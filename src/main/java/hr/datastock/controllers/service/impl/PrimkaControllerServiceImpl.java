package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.PrimkaController;
import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.controllers.service.PrimkaControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.PrimkaEntity;
import hr.datastock.services.FirmeService;
import hr.datastock.services.PrimkaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hr.datastock.controllers.service.impl.Const.DATE_FORMATTER;
import static hr.datastock.controllers.service.impl.Const.FX_ALIGNMENT_CENTER;

@Service
@RequiredArgsConstructor
public class PrimkaControllerServiceImpl implements PrimkaControllerService {

    private final PrimkaService primkaService;
    private final FirmeService firmeService;
    private final UtilService utilService;
    private ObservableList<PrimkaEntity> primkeObservableList;

    @Override
    public void init(final PrimkaController primkaController) {
        this.primkeObservableList = FXCollections.observableList(this.primkaService.getAll());
        this.setComboBoxFirmeOnCreateIzdatnicaEntity(primkaController);
        this.setComboBoxFirmaOibOnSearchFirmaEntity(primkaController);
        this.setTableColumnProperties(primkaController);
        this.clearRecords(primkaController);
        primkaController.getTableView().setItems(this.primkeObservableList);
    }

    @Override
    public void searchData(final PrimkaController primkaController) {
        final FilteredList<PrimkaEntity> filteredList = this.primkeObservableList
                .filtered(primka -> this.getComboBoxFirmaOibOnSearch(primkaController) == null || primka.getPrimkaFirme()
                        .getOibFirme().equals(this.getComboBoxFirmaOibOnSearch(primkaController)))
                .filtered(primka -> this.getDateFromDatePicker(primkaController) == null ||
                        primka.getDatum().equals(this.getDateFromDatePicker(primkaController)));
        primkaController.getTableView().setItems(FXCollections.observableList(filteredList));

    }

    @Override
    public PrimkaEntity savePrimka(final PrimkaController primkaController) {
        if (this.getTextFieldAndDateDataForDialogCheck(primkaController).isEmpty()) {
            final PrimkaEntity primka = this.primkaService.createPrimka(this.save(primkaController));
            this.init(primkaController);
            return primka;
        }
        this.utilService.getWarningAlert(this.getTextFieldAndDateDataForDialogCheck(primkaController));
        return null;
    }

    @Override
    public void deletePrimka(final PrimkaController primkaController) {
        if (this.getTableViewPrimka(primkaController) != null && this.utilService.isEntityRemoved()) {
            this.primkaService.deletePrimka(this.getTableViewPrimka(primkaController).getIdPrimke());
            this.init(primkaController);
        } else if (this.getTableViewPrimka(primkaController) == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        } else {
            this.utilService.isEntityUnableToRemove();
        }
    }

    @Override
    public void clearRecords(final PrimkaController primkaController) {
        primkaController.getDatePickerDatum().setValue(null);
        primkaController.getDatePickerDatum().getEditor().clear();
        primkaController.getComboBoxCreate().getSelectionModel().clearSelection();
        primkaController.getComboBoxSearch().getSelectionModel().clearSelection();
        primkaController.getTableView().getSelectionModel().clearSelection();
    }

    private PrimkaEntity save(final PrimkaController primkaController) {
        return PrimkaEntity.builder()
                .idPrimke(this.nextId())
                .datum(primkaController.getDatePickerDatum().getValue())
                .primkaFirme(this.getComboBoxFirmeOnCreate(primkaController))
                .build();
    }

    private void setTableColumnProperties(final PrimkaController primkaController) {
        this.setProperty(primkaController);
        this.setStyle(primkaController);
        this.setCellValueProperties(primkaController);
    }

    private void setProperty(final PrimkaController primkaController) {
        primkaController.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idPrimke"));
        primkaController.getTableColumnDatum().setCellValueFactory(new PropertyValueFactory<>("datum"));
        primkaController.getTableColumnFirmeEntity().setCellValueFactory(new PropertyValueFactory<>("primkaFirme"));
    }

    private void setStyle(final PrimkaController primkaController) {
        primkaController.getTableColumnFirmeEntity().setStyle(FX_ALIGNMENT_CENTER);
        primkaController.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        primkaController.getTableColumnDatum().setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties(final PrimkaController primkaController) {
        primkaController.getTableColumnFirmeEntity().setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(final FirmeEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        primkaController.getTableColumnDatum().setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(final LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(DATE_FORMATTER.format(item));
                }
            }
        });
    }

    private Long nextId() {
        return !this.primkeObservableList.isEmpty() ?
                this.primkeObservableList.stream().mapToLong(PrimkaEntity::getIdPrimke).max().getAsLong() + 1 : 1;
    }

    private String getTextFieldAndDateDataForDialogCheck(final PrimkaController primkaController) {
        final List<String> checkList = new ArrayList<>();
        if (getComboBoxFirmeOnCreate(primkaController) == null) checkList.add("Company identity number!");
        if (getDateFromDatePicker(primkaController) == null) checkList.add("Date!");
        return String.join("\n", checkList);
    }

    private void setComboBoxFirmeOnCreateIzdatnicaEntity(final PrimkaController primkaController) {
        final Set<FirmeEntity> oibFirmeFilterList = this.primkeObservableList.stream()
                .map(PrimkaEntity::getPrimkaFirme).collect(Collectors.toSet());
        primkaController.getComboBoxSearch().setItems(FXCollections.observableList(new ArrayList<>(oibFirmeFilterList)));
        primkaController.getComboBoxSearch().getSelectionModel().selectFirst();

    }

    private void setComboBoxFirmaOibOnSearchFirmaEntity(final PrimkaController primkaController) {
        primkaController.getComboBoxCreate().setItems(FXCollections.observableList(this.firmeService.getAll()));
        primkaController.getComboBoxCreate().getSelectionModel().selectFirst();
    }

    private LocalDate getDateFromDatePicker(final PrimkaController primkaController) {
        return primkaController.getDatePickerDatum().getValue() == null ? null :
                primkaController.getDatePickerDatum().getValue();
    }

    private FirmeEntity getComboBoxFirmeOnCreate(final PrimkaController primkaController) {
        return primkaController.getComboBoxCreate().getSelectionModel().getSelectedItem() == null ? null :
                primkaController.getComboBoxCreate().getSelectionModel().getSelectedItem();

    }

    private String getComboBoxFirmaOibOnSearch(final PrimkaController primkaController) {
        return primkaController.getComboBoxSearch().getSelectionModel().getSelectedItem() == null ? null :
                primkaController.getComboBoxSearch().getSelectionModel().getSelectedItem().getOibFirme();
    }

    private PrimkaEntity getTableViewPrimka(final PrimkaController primkaController) {
        return primkaController.getTableColumnId().getTableView().getSelectionModel().getSelectedItem();
    }
}
