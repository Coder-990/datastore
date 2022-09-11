package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.StornoStavkaPrimkeController;
import hr.datastock.controllers.service.StornoStavkaPrimkeControllerService;
import hr.datastock.entities.*;
import hr.datastock.services.StavkaPrimkeService;
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
public class StornoStavkaPrimkeControllerServiceImpl implements StornoStavkaPrimkeControllerService {

    private final StavkaPrimkeService stavkaPrimkeService;

    private ObservableList<StavkaPrimkeEntity> stavkaPrimkeObservableList;

    @Override
    public void init(final StornoStavkaPrimkeController sspc) {
        this.stavkaPrimkeObservableList = FXCollections.observableList(this.stavkaPrimkeService.getAll()
                .stream().filter(StavkaPrimkeEntity::getStorno).toList());
        this.setComboBoxIzdatnicaEntity(sspc);
        this.setComboBoxRobaEntity(sspc);
        this.setTableColumnProperties(sspc);
        this.clearRecords(sspc);
        sspc.getTableView().setItems(this.stavkaPrimkeObservableList);
    }

    @Override
    public void searchData(final StornoStavkaPrimkeController sspc) {
        final FilteredList<StavkaPrimkeEntity> filteredList = this.stavkaPrimkeObservableList
                .filtered(stavkaPrimke -> isTextFieldFirmaContainingSomeData(sspc, stavkaPrimke))
                .filtered(stavkaPrimke -> isTextFieldArticleContainingSomeData(sspc, stavkaPrimke))
                .filtered(stavkaPrimke -> isIzdatnicaOfFirmaEntityContainingSelectedValue(sspc, stavkaPrimke))
                .filtered(stavkaPrimke -> isRobaContainingSelectedValue(sspc, stavkaPrimke))
                .filtered(stavkaPrimke -> isDatumContainingSelectedValue(sspc, stavkaPrimke));
        sspc.getTableView().setItems(FXCollections.observableList(filteredList));
    }

    @Override
    public void clearRecords(final StornoStavkaPrimkeController sspc) {
        sspc.getDatePickerDatumStorno().setValue(null);
        sspc.getDatePickerDatumStorno().getEditor().clear();
        sspc.getTextFieldFirma().clear();
        sspc.getTextFieldArticle().clear();
        sspc.getComboBoxPrimka().getSelectionModel().clearSelection();
        sspc.getComboBoxRoba().getSelectionModel().clearSelection();
        sspc.getTableView().getSelectionModel().clearSelection();
    }

    private void setComboBoxIzdatnicaEntity(final StornoStavkaPrimkeController sspc) {
        final Set<FirmeEntity> listOfFirme = this.stavkaPrimkeObservableList.stream()
                .map(stornoStavkaIzdatnice -> stornoStavkaIzdatnice.getStavkaPrimkePrimka().getPrimkaFirme())
                .collect(Collectors.toSet());
        sspc.getComboBoxPrimka().setItems(FXCollections.observableList(new ArrayList<>(listOfFirme)));
        sspc.getComboBoxPrimka().getSelectionModel().getSelectedItem();
    }

    private boolean isDatumContainingSelectedValue(final StornoStavkaPrimkeController sspc, final StavkaPrimkeEntity stavkaPrimke) {
        final LocalDate selectedDate = sspc.getDatePickerDatumStorno().getValue();
        return selectedDate == null || stavkaPrimke.getDatumStorno().equals(selectedDate);
    }

    private boolean isRobaContainingSelectedValue(final StornoStavkaPrimkeController sspc, final StavkaPrimkeEntity stavkaPrimke) {
        final RobaEntity selectedRoba = sspc.getComboBoxRoba().getSelectionModel().getSelectedItem();
        return selectedRoba == null ||
                stavkaPrimke.getStavkaPrimkeRobe().equals(selectedRoba);
    }

    private boolean isIzdatnicaOfFirmaEntityContainingSelectedValue(final StornoStavkaPrimkeController sspc, final StavkaPrimkeEntity stavkaPrimke) {
        final FirmeEntity selectedFirma = sspc.getComboBoxPrimka().getSelectionModel().getSelectedItem();
        return selectedFirma == null ||
                stavkaPrimke.getStavkaPrimkePrimka().getPrimkaFirme().equals(
                        selectedFirma);
    }

    private boolean isTextFieldArticleContainingSomeData(final StornoStavkaPrimkeController sspc, final StavkaPrimkeEntity stavkaPrimke) {
        final String articleText = sspc.getTextFieldArticle().getText();
        return StringUtils.equals(articleText, StringUtils.EMPTY) || articleText == null || stavkaPrimke
                .getStavkaPrimkeRobe().toString().toLowerCase().contains(articleText);
    }

    private boolean isTextFieldFirmaContainingSomeData(final StornoStavkaPrimkeController sspc, final StavkaPrimkeEntity stavkaPrimke) {
        final String firmaText = sspc.getTextFieldFirma().getText();
        return StringUtils.equals(firmaText, StringUtils.EMPTY) || firmaText == null || stavkaPrimke
                .getStavkaPrimkePrimka().getPrimkaFirme().toString()
                .toLowerCase().trim().contains(firmaText);
    }

    private void setComboBoxRobaEntity(final StornoStavkaPrimkeController sspc) {
        final Set<RobaEntity> listOfArticles = this.stavkaPrimkeObservableList.stream()
                .map(StavkaPrimkeEntity::getStavkaPrimkeRobe)
                .collect(Collectors.toSet());
        sspc.getComboBoxRoba().setItems(FXCollections.observableList(new ArrayList<>(listOfArticles)));
        sspc.getComboBoxRoba().getSelectionModel().getSelectedItem();
    }

    private void setTableColumnProperties(final StornoStavkaPrimkeController sspc) {
        this.setProperty(sspc);
        this.setStyle(sspc);
        this.setCellValueFactory(sspc);
    }

    private void setProperty(final StornoStavkaPrimkeController sspc) {
        sspc.getTableColumnId().setCellValueFactory(new PropertyValueFactory<>("idStavkaPrimke"));
        sspc.getTableColumnIdPrimke().setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkePrimka"));
        sspc.getTableColumnArticle().setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkeRobe"));
        sspc.getTableColumnKolicina().setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        sspc.getTableColumnDatum().setCellValueFactory(new PropertyValueFactory<>("datumStorno"));
    }

    private void setStyle(final StornoStavkaPrimkeController sspc) {
        sspc.getTableColumnId().setStyle(FX_ALIGNMENT_CENTER);
        sspc.getTableColumnIdPrimke().setStyle(FX_ALIGNMENT_CENTER);
        sspc.getTableColumnArticle().setStyle(FX_ALIGNMENT_CENTER);
        sspc.getTableColumnKolicina().setStyle(FX_ALIGNMENT_CENTER);
        sspc.getTableColumnDatum().setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueFactory(final StornoStavkaPrimkeController sspc) {
        sspc.getTableColumnIdPrimke().setCellFactory(column -> new TableCell<>() {
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

        sspc.getTableColumnArticle().setCellFactory(column -> new TableCell<>() {
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

        sspc.getTableColumnDatum().setCellFactory(column -> new TableCell<>() {
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
