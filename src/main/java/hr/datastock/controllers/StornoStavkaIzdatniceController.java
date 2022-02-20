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
        stavkaIzdatniceObservableList = FXCollections.observableList(stavkaIzdatniceService.getAll()
                .stream().filter(StavkaIzdatniceEntity::getStorno).toList());
        setComboBoxIzdatnicaEntity();
        setComboBoxRobaEntity();
        setTableColumnProperties();
        clearRecords();
        tableView.setItems(stavkaIzdatniceObservableList);
    }

    public void setButtonSearch() {
        TextFieldDatePickerInsertedPropertyData searchBy = new TextFieldDatePickerInsertedPropertyData();
        filteredSerachOf(searchBy.firma, searchBy.artikl, searchBy.datumStorno);
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void setComboBoxIzdatnicaEntity() {
        Set<FirmeEntity> listOfFirme = stavkaIzdatniceObservableList.stream()
                .map(stornoStavkaIzdatnice -> stornoStavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme())
                .collect(Collectors.toSet());
        comboBoxIzdatnica.setItems(FXCollections.observableList(new ArrayList<>(listOfFirme)));
        comboBoxIzdatnica.getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity() {
        Set<RobaEntity> listOfArticles = stavkaIzdatniceObservableList.stream()
                .map(StavkaIzdatniceEntity::getStavkaIzdatniceRobe)
                .collect(Collectors.toSet());
        comboBoxRoba.setItems(FXCollections.observableList(new ArrayList<>(listOfArticles)));
        comboBoxRoba.getSelectionModel().getSelectedItem();
    }

    private void setTableColumnProperties() {
        setProperty();
        setStyle();
        setCellValueFactory();
    }

    private void setProperty() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaIzdatnice"));
        tableColumnIdIzdatnice.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceIzdatnica"));
        tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceRobe"));
        tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datumStorno"));
    }

    private void setStyle() {
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnIdIzdatnice.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnDatum.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueFactory() {
        tableColumnIdIzdatnice.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(IzdatnicaEntity item, boolean empty) {
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

        tableColumnArticle.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(RobaEntity item, boolean empty) {
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

        tableColumnDatum.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(DATE_FORMATTER.format(item));
                }
            }
        });
    }

    private void filteredSerachOf(String firma, String artikl, LocalDate datePickerStorno) {
        FilteredList<StavkaIzdatniceEntity> searchList = stavkaIzdatniceObservableList
                .filtered(stavkaIzdatnice -> firma.equals("") || stavkaIzdatnice.getStavkaIzdatniceIzdatnica()
                        .getIzdatnicaFirme().toString().toLowerCase().trim().contains(firma.toLowerCase()))
                .filtered(stavkaIzdatnice -> artikl.equals("") || stavkaIzdatnice.getStavkaIzdatniceRobe()
                        .toString().toLowerCase().trim().contains(artikl.toLowerCase()))
                .filtered(stavkaIzdatnice -> comboBoxIzdatnica.getSelectionModel().getSelectedItem() == null ||
                        stavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme().equals(
                                comboBoxIzdatnica.getSelectionModel().getSelectedItem()))
                .filtered(stavkaIzdatnice -> comboBoxRoba.getSelectionModel().getSelectedItem() == null ||
                        stavkaIzdatnice.getStavkaIzdatniceRobe().equals(comboBoxRoba.getSelectionModel().getSelectedItem()))
                .filtered(stavkaIzdatnice -> datePickerStorno == null ||
                        stavkaIzdatnice.getDatumStorno().equals(datePickerStorno));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    private void clearRecords() {
        datePickerDatumStorno.setValue(null);
        datePickerDatumStorno.getEditor().clear();
        textFieldCompany.clear();
        textFieldArticle.clear();
        comboBoxIzdatnica.getSelectionModel().clearSelection();
        comboBoxRoba.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
    }

    private class TextFieldDatePickerInsertedPropertyData {
        final String artikl = textFieldArticle.getText().trim().toLowerCase(Locale.ROOT);
        final String firma = textFieldCompany.getText().trim().toLowerCase(Locale.ROOT);
        final LocalDate datumStorno = datePickerDatumStorno.getValue() == null ? null :
                datePickerDatumStorno.getValue();
    }
}
