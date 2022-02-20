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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
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

    @Autowired
    private StavkaPrimkeService stavkaPrimkeService;

    private ObservableList<StavkaPrimkeEntity> stavkaPrimkeObservableList;

    @FXML
    public void initialize() {
        stavkaPrimkeObservableList = FXCollections.observableList(stavkaPrimkeService.getAll()
                .stream().filter(StavkaPrimkeEntity::getStorno).toList());
        setComboBoxrimkeEntity();
        setComboBoxRobaEntity();
        setTableColumnProperties();
        clearRecords();
        tableView.setItems(stavkaPrimkeObservableList);
    }

    public void setButtonSearch() {
        TextFieldDatePickerInsertedPropertyData searchBy = new TextFieldDatePickerInsertedPropertyData();
        felteredSearchingOf(searchBy.firma, searchBy.artikl, searchBy.datumStorno);
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void setComboBoxrimkeEntity() {
        Set<FirmeEntity> listOfFirme = stavkaPrimkeObservableList.stream()
                .map(stornoStavkaPrimke -> stornoStavkaPrimke.getStavkaPrimkePrimka().getPrimkaFirme())
                .collect(Collectors.toSet());
        comboBoxPrimka.setItems(FXCollections.observableList(new ArrayList<>(listOfFirme)));
        comboBoxPrimka.getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity() {
        Set<RobaEntity> listOfArticles = stavkaPrimkeObservableList.stream()
                .map(StavkaPrimkeEntity::getStavkaPrimkeRobe)
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
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaPrimke"));
        tableColumnIdPrimke.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkePrimka"));
        tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkeRobe"));
        tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datumStorno"));
    }

    private void setStyle() {
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnIdPrimke.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnDatum.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueFactory() {
        tableColumnIdPrimke.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(PrimkaEntity item, boolean empty) {
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

    private void felteredSearchingOf(String firma, String artikl, LocalDate datePickerStorno) {
        FilteredList<StavkaPrimkeEntity> searchList = stavkaPrimkeObservableList
                .filtered(stavkaPrimke -> firma.equals("") || stavkaPrimke.getStavkaPrimkePrimka()
                        .getPrimkaFirme().getNazivFirme().toLowerCase().trim().contains(firma.toLowerCase()))
                .filtered(stavkaPrimke -> artikl.equals("") || stavkaPrimke.getStavkaPrimkeRobe().toString()
                        .toLowerCase().trim().contains(artikl.toLowerCase()))
                .filtered(stavkaPrimke -> comboBoxPrimka.getSelectionModel().getSelectedItem() == null ||
                        stavkaPrimke.getStavkaPrimkePrimka().getPrimkaFirme().equals(
                                comboBoxPrimka.getSelectionModel().getSelectedItem()))
                .filtered(stavkaPrimke -> comboBoxRoba.getSelectionModel().getSelectedItem() == null ||
                        stavkaPrimke.getStavkaPrimkeRobe().equals(comboBoxRoba.getSelectionModel().getSelectedItem()))
                .filtered(stavkaPrimke -> datePickerStorno == null ||
                        stavkaPrimke.getDatumStorno().equals(datePickerStorno));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    private void clearRecords() {
        datePickerDatumStorno.setValue(null);
        datePickerDatumStorno.getEditor().clear();
        textFieldCompany.clear();
        textFieldArticle.clear();
        comboBoxPrimka.getSelectionModel().clearSelection();
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
