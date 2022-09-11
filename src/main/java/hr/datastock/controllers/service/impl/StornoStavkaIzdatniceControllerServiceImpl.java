package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.StornoStavkaIzdatniceController;
import hr.datastock.controllers.service.StornoStavkaIzdatniceControllerService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.services.StavkaIzdatniceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static hr.datastock.controllers.service.impl.Const.DATE_FORMATTER;
import static hr.datastock.controllers.service.impl.Const.FX_ALIGNMENT_CENTER;

@RequiredArgsConstructor
@Service
public class StornoStavkaIzdatniceControllerServiceImpl implements StornoStavkaIzdatniceControllerService {
    private final StavkaIzdatniceService stavkaIzdatniceService;
    private ObservableList<StavkaIzdatniceEntity> stavkaIzdatniceObservableList;

    @Override
    public void init(final StornoStavkaIzdatniceController ssic) {
        this.stavkaIzdatniceObservableList = FXCollections.observableList(this.stavkaIzdatniceService.getAll()
                .stream().filter(StavkaIzdatniceEntity::getStorno).toList());
        this.setComboBoxIzdatnicaEntity(ssic);
        this.setComboBoxRobaEntity(ssic);
        this.setTableColumnProperties(ssic);
        this.clearRecords(ssic);
        ssic.getTableView().setItems(this.stavkaIzdatniceObservableList);
    }

    @Override
    public void searchData(final StornoStavkaIzdatniceController ssic) {
        final FilteredList<StavkaIzdatniceEntity> filteredList = this.stavkaIzdatniceObservableList
                .filtered(stavkaIzdatnice -> isTextFieldFirmaContainingSomeData(ssic, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isTextFieldArticleContainingSomeData(ssic, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isIzdatnicaFirmaEntityContainingSelectedValue(ssic, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isRobaContainingSelectedValue(ssic, stavkaIzdatnice))
                .filtered(stavkaIzdatnice -> isDatumContainingSelectedValue(ssic, stavkaIzdatnice));
        ssic.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public void clearRecords(final StornoStavkaIzdatniceController ssic) {
        ssic.getDatePickerDatumStorno().setValue(null);
        ssic.getDatePickerDatumStorno().getEditor().clear();
        ssic.getTextFieldFirma().clear();
        ssic.getTextFieldArticle().clear();
        ssic.getComboBoxIzdatnica().getSelectionModel().clearSelection();
        ssic.getComboBoxRoba().getSelectionModel().clearSelection();
        ssic.getTableView().getSelectionModel().clearSelection();
    }

    private void setComboBoxIzdatnicaEntity(final StornoStavkaIzdatniceController ssic) {
        final Set<FirmeEntity> listOfFirme = this.stavkaIzdatniceObservableList.stream()
                .map(stornoStavkaIzdatnice -> stornoStavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme())
                .collect(Collectors.toSet());
        ssic.getComboBoxIzdatnica().setItems(FXCollections.observableList(new ArrayList<>(listOfFirme)));
        ssic.getComboBoxIzdatnica().getSelectionModel().getSelectedItem();
    }

    private boolean isDatumContainingSelectedValue(final StornoStavkaIzdatniceController ssic, final StavkaIzdatniceEntity stavkaIzdatnice) {
        final LocalDate selectedDate = ssic.getDatePickerDatumStorno().getValue();
        return selectedDate == null || stavkaIzdatnice.getDatumStorno().equals(selectedDate);
    }

    private boolean isRobaContainingSelectedValue(final StornoStavkaIzdatniceController ssic, final StavkaIzdatniceEntity stavkaIzdatnice) {
        final RobaEntity selectedRoba = ssic.getComboBoxRoba().getSelectionModel().getSelectedItem();
        return selectedRoba == null ||
                stavkaIzdatnice.getStavkaIzdatniceRobe().equals(selectedRoba);
    }

    private boolean isIzdatnicaFirmaEntityContainingSelectedValue(final StornoStavkaIzdatniceController ssic, final StavkaIzdatniceEntity stavkaIzdatnice) {
        final FirmeEntity selectedFirma = ssic.getComboBoxIzdatnica().getSelectionModel().getSelectedItem();
        return selectedFirma == null ||
                stavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme().equals(
                        selectedFirma);
    }

    private boolean isTextFieldArticleContainingSomeData(final StornoStavkaIzdatniceController ssic, final StavkaIzdatniceEntity stavkaIzdatnice) {
        final String articleText = ssic.getTextFieldArticle().getText();
        return StringUtils.equals(articleText, StringUtils.EMPTY) || articleText == null || stavkaIzdatnice
                .getStavkaIzdatniceRobe().toString().toLowerCase().contains(articleText);
    }

    private boolean isTextFieldFirmaContainingSomeData(final StornoStavkaIzdatniceController ssic, final StavkaIzdatniceEntity stavkaIzdatnice) {
        final String firmaText = ssic.getTextFieldFirma().getText();
        return StringUtils.equals(firmaText, StringUtils.EMPTY) || firmaText == null || stavkaIzdatnice
                .getStavkaIzdatniceIzdatnica().getIzdatnicaFirme().toString()
                .toLowerCase().trim().contains(firmaText);
    }

    private void setComboBoxRobaEntity(final StornoStavkaIzdatniceController ssic) {
        final Set<RobaEntity> listOfArticles = this.stavkaIzdatniceObservableList.stream()
                .map(StavkaIzdatniceEntity::getStavkaIzdatniceRobe)
                .collect(Collectors.toSet());
        ssic.getComboBoxRoba().setItems(FXCollections.observableList(new ArrayList<>(listOfArticles)));
        ssic.getComboBoxRoba().getSelectionModel().getSelectedItem();
    }

    private void setTableColumnProperties(final StornoStavkaIzdatniceController ssic) {
        this.setProperty(ssic);
        this.setStyle(ssic);
        this.setCellValueFactory(ssic);
    }

    private void setProperty(final StornoStavkaIzdatniceController ssic) {
        ssic.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idStavkaIzdatnice"));
        ssic.getTableColumnIdIzdatnice().setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceIzdatnica"));
        ssic.getTableColumnArticle().setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceRobe"));
        ssic.getTableColumnKolicina().setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        ssic.getTableColumnDatum().setCellValueFactory(new PropertyValueFactory<>("datumStorno"));
    }

    private void setStyle(final StornoStavkaIzdatniceController ssic) {
        ssic.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        ssic.getTableColumnIdIzdatnice().setStyle(FX_ALIGNMENT_CENTER);
        ssic.getTableColumnArticle().setStyle(FX_ALIGNMENT_CENTER);
        ssic.getTableColumnKolicina().setStyle(FX_ALIGNMENT_CENTER);
        ssic.getTableColumnDatum().setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueFactory(final StornoStavkaIzdatniceController ssic) {
        ssic.getTableColumnIdIzdatnice().setCellFactory(column -> new TableCell<>() {
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

        ssic.getTableColumnArticle().setCellFactory(column -> new TableCell<>() {
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

        ssic.getTableColumnDatum().setCellFactory(column -> new TableCell<>() {
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
}
