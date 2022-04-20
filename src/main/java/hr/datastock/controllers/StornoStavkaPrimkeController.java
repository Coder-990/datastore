package hr.datastock.controllers;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.PrimkaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaPrimkeEntity;
import hr.datastock.services.StavkaPrimkeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Controller
public class StornoStavkaPrimkeController {

    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @FXML
    private ComboBox<FirmeEntity> comboBoxPrimka;
    @FXML
    private ComboBox<RobaEntity> comboBoxRoba;
    @FXML
    private TextField textFieldCompany;
    @FXML
    private TextField textFieldArticle;
    @FXML
    private TableView<StavkaPrimkeEntity> tableView;
    @FXML
    private TableColumn<StavkaPrimkeEntity, Long> tableColumnId;
    @FXML
    private TableColumn<StavkaPrimkeEntity, PrimkaEntity> tableColumnIdPrimke;
    @FXML
    private TableColumn<StavkaPrimkeEntity, RobaEntity> tableColumnArticle;
    @FXML
    private TableColumn<StavkaPrimkeEntity, Integer> tableColumnKolicina;
    @FXML
    private TableColumn<StavkaPrimkeEntity, LocalDate> tableColumnDatum;
    @FXML
    private DatePicker datePickerDatumStorno;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonClearFields;

    private final StavkaPrimkeService stavkaPrimkeService;

    private ObservableList<StavkaPrimkeEntity> stavkaPrimkeObservableList;

    @FXML
    public void initialize() {
        this.stavkaPrimkeObservableList = FXCollections.observableList(this.stavkaPrimkeService.getAll()
                .stream().filter(StavkaPrimkeEntity::getStorno).toList());
        this.setComboBoxPrimkeEntity();
        this.setComboBoxRobaEntity();
        this.setTableColumnProperties();
        this.clearRecords();
        this.tableView.setItems(this.stavkaPrimkeObservableList);
    }

    public void setButtonSearch() {
        final TextFieldDatePickerInsertedPropertyData searchBy = new TextFieldDatePickerInsertedPropertyData();
        this.felteredSearchingOf(searchBy.firma, searchBy.artikl, searchBy.datumStorno);
    }

    public void setButtonClearFields() {
        this.clearRecords();
    }

    private void setComboBoxPrimkeEntity() {
        final Set<FirmeEntity> listOfFirme = this.stavkaPrimkeObservableList.stream()
                .map(stornoStavkaPrimke -> stornoStavkaPrimke.getStavkaPrimkePrimka().getPrimkaFirme())
                .collect(Collectors.toSet());
        this.comboBoxPrimka.setItems(FXCollections.observableList(new ArrayList<>(listOfFirme)));
        this.comboBoxPrimka.getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity() {
        final Set<RobaEntity> listOfArticles = this.stavkaPrimkeObservableList.stream()
                .map(StavkaPrimkeEntity::getStavkaPrimkeRobe)
                .collect(Collectors.toSet());
        this.comboBoxRoba.setItems(FXCollections.observableList(new ArrayList<>(listOfArticles)));
        this.comboBoxRoba.getSelectionModel().getSelectedItem();
    }

    private void setTableColumnProperties() {
        this.setProperty();
        this.setStyle();
        this.setCellValueFactory();
    }

    private void setProperty() {
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaPrimke"));
        this.tableColumnIdPrimke.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkePrimka"));
        this.tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkeRobe"));
        this.tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        this.tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datumStorno"));
    }

    private void setStyle() {
        this.tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnIdPrimke.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnDatum.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueFactory() {
        this.tableColumnIdPrimke.setCellFactory(column -> new TableCell<>() {
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

        this.tableColumnArticle.setCellFactory(column -> new TableCell<>() {
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

        this.tableColumnDatum.setCellFactory(column -> new TableCell<>() {
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

    private void felteredSearchingOf(final String firma, final String artikl, final LocalDate datePickerStorno) {
        final FilteredList<StavkaPrimkeEntity> searchList = this.stavkaPrimkeObservableList
                .filtered(stavkaPrimke -> firma.equals("") || stavkaPrimke.getStavkaPrimkePrimka()
                        .getPrimkaFirme().getNazivFirme().toLowerCase().trim().contains(firma.toLowerCase()))
                .filtered(stavkaPrimke -> artikl.equals("") || stavkaPrimke.getStavkaPrimkeRobe().toString()
                        .toLowerCase().trim().contains(artikl.toLowerCase()))
                .filtered(stavkaPrimke -> this.comboBoxPrimka.getSelectionModel().getSelectedItem() == null ||
                        stavkaPrimke.getStavkaPrimkePrimka().getPrimkaFirme().equals(
                                this.comboBoxPrimka.getSelectionModel().getSelectedItem()))
                .filtered(stavkaPrimke -> this.comboBoxRoba.getSelectionModel().getSelectedItem() == null ||
                        stavkaPrimke.getStavkaPrimkeRobe().equals(this.comboBoxRoba.getSelectionModel().getSelectedItem()))
                .filtered(stavkaPrimke -> datePickerStorno == null ||
                        stavkaPrimke.getDatumStorno().equals(datePickerStorno));
        this.tableView.setItems(FXCollections.observableList(searchList));
    }

    private void clearRecords() {
        this.datePickerDatumStorno.setValue(null);
        this.datePickerDatumStorno.getEditor().clear();
        this.textFieldCompany.clear();
        this.textFieldArticle.clear();
        this.comboBoxPrimka.getSelectionModel().clearSelection();
        this.comboBoxRoba.getSelectionModel().clearSelection();
        this.tableView.getSelectionModel().clearSelection();
    }

    private class TextFieldDatePickerInsertedPropertyData {
        final String artikl = textFieldArticle.getText().trim().toLowerCase(Locale.ROOT);
        final String firma = textFieldCompany.getText().trim().toLowerCase(Locale.ROOT);
        final LocalDate datumStorno = datePickerDatumStorno.getValue() == null ? null :
                datePickerDatumStorno.getValue();
    }
}
