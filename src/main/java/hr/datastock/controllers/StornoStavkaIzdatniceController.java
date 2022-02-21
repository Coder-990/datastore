package hr.datastock.controllers;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.services.StavkaIzdatniceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StornoStavkaIzdatniceController {

    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @FXML
    private ComboBox<FirmeEntity> comboBoxIzdatnica;
    @FXML
    private ComboBox<RobaEntity> comboBoxRoba;
    @FXML
    private TextField textFieldCompany;
    @FXML
    private TextField textFieldArticle;
    @FXML
    private TableView<StavkaIzdatniceEntity> tableView;
    @FXML
    private TableColumn<StavkaIzdatniceEntity, Long> tableColumnId;
    @FXML
    private TableColumn<StavkaIzdatniceEntity, IzdatnicaEntity> tableColumnIdIzdatnice;
    @FXML
    private TableColumn<StavkaIzdatniceEntity, RobaEntity> tableColumnArticle;
    @FXML
    private TableColumn<StavkaIzdatniceEntity, Integer> tableColumnKolicina;
    @FXML
    private TableColumn<StavkaIzdatniceEntity, LocalDate> tableColumnDatum;
    @FXML
    private DatePicker datePickerDatumStorno;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonClearFields;

    @Autowired
    private StavkaIzdatniceService stavkaIzdatniceService;

    private ObservableList<StavkaIzdatniceEntity> stavkaIzdatniceObservableList;

    @FXML
    public void initialize() {
        this.stavkaIzdatniceObservableList = FXCollections.observableList(this.stavkaIzdatniceService.getAll()
                .stream().filter(StavkaIzdatniceEntity::getStorno).toList());
        this.setComboBoxIzdatnicaEntity();
        this.setComboBoxRobaEntity();
        this.setTableColumnProperties();
        this.clearRecords();
        this.tableView.setItems(this.stavkaIzdatniceObservableList);
    }

    public void setButtonSearch() {
        final TextFieldDatePickerInsertedPropertyData searchBy = new TextFieldDatePickerInsertedPropertyData();
        this.filteredSerachOf(searchBy.firma, searchBy.artikl, searchBy.datumStorno);
    }

    public void setButtonClearFields() {
        this.clearRecords();
    }

    private void setComboBoxIzdatnicaEntity() {
        final Set<FirmeEntity> listOfFirme = this.stavkaIzdatniceObservableList.stream()
                .map(stornoStavkaIzdatnice -> stornoStavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme())
                .collect(Collectors.toSet());
        this.comboBoxIzdatnica.setItems(FXCollections.observableList(new ArrayList<>(listOfFirme)));
        this.comboBoxIzdatnica.getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity() {
        final Set<RobaEntity> listOfArticles = this.stavkaIzdatniceObservableList.stream()
                .map(StavkaIzdatniceEntity::getStavkaIzdatniceRobe)
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
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaIzdatnice"));
        this.tableColumnIdIzdatnice.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceIzdatnica"));
        this.tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceRobe"));
        this.tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        this.tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datumStorno"));
    }

    private void setStyle() {
        this.tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnIdIzdatnice.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnDatum.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueFactory() {
        this.tableColumnIdIzdatnice.setCellFactory(column -> new TableCell<>() {
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

    private void filteredSerachOf(final String firma, final String artikl, final LocalDate datePickerStorno) {
        final FilteredList<StavkaIzdatniceEntity> searchList = this.stavkaIzdatniceObservableList
                .filtered(stavkaIzdatnice -> firma.equals("") || stavkaIzdatnice.getStavkaIzdatniceIzdatnica()
                        .getIzdatnicaFirme().toString().toLowerCase().trim().contains(firma.toLowerCase()))
                .filtered(stavkaIzdatnice -> artikl.equals("") || stavkaIzdatnice.getStavkaIzdatniceRobe()
                        .toString().toLowerCase().trim().contains(artikl.toLowerCase()))
                .filtered(stavkaIzdatnice -> this.comboBoxIzdatnica.getSelectionModel().getSelectedItem() == null ||
                        stavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme().equals(
                                this.comboBoxIzdatnica.getSelectionModel().getSelectedItem()))
                .filtered(stavkaIzdatnice -> this.comboBoxRoba.getSelectionModel().getSelectedItem() == null ||
                        stavkaIzdatnice.getStavkaIzdatniceRobe().equals(this.comboBoxRoba.getSelectionModel().getSelectedItem()))
                .filtered(stavkaIzdatnice -> datePickerStorno == null ||
                        stavkaIzdatnice.getDatumStorno().equals(datePickerStorno));
        this.tableView.setItems(FXCollections.observableList(searchList));
    }

    private void clearRecords() {
        this.datePickerDatumStorno.setValue(null);
        this.datePickerDatumStorno.getEditor().clear();
        this.textFieldCompany.clear();
        this.textFieldArticle.clear();
        this.comboBoxIzdatnica.getSelectionModel().clearSelection();
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
