package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.*;
import hr.datastock.services.PrimkaService;
import hr.datastock.services.RobaService;
import hr.datastock.services.StavkaPrimkeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StavkaPrimkeController {

    public static final Logger logger = LoggerFactory.getLogger(StavkaPrimkeController.class);
    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";

    @FXML
    private ComboBox<PrimkaEntity> comboBoxPrimka;
    @FXML
    private ComboBox<RobaEntity> comboBoxRoba;
    @FXML
    private TextField textFieldKolicina;
    @FXML
    private TextField textFieldCompany;
    @FXML
    private TextField textFieldArticle;
    @FXML
    private TableView<StavkaPrimkeEntity> tableView;
    @FXML
    private TableColumn<StavkaPrimkeEntity, Long> tableColumnId;
    @FXML
    private TableColumn<StavkaPrimkeEntity, PrimkaEntity> tableColumnPrimka;
    @FXML
    private TableColumn<StavkaPrimkeEntity, RobaEntity> tableColumnArticle;
    @FXML
    private TableColumn<StavkaPrimkeEntity, Integer> tableColumnKolicina;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonStorno;
    @FXML
    private Button buttonClearFields;

    @Autowired
    private StavkaPrimkeService stavkaPrimkeService;

    @Autowired
    private PrimkaService primkaService;

    @Autowired
    private RobaService robaService;

    @Autowired
    private UtilService utilService;

    private ObservableList<StavkaPrimkeEntity> stavkaPrimkeObservableList;
    private ObservableList<StavkaPrimkeEntity> filteredStavkaPrimkeObservableListOfStorno;

    @FXML
    public void initialize() {
        stavkaPrimkeObservableList = FXCollections.observableList(stavkaPrimkeService.getAll());
        filteredStavkaPrimkeObservableListOfStorno = FXCollections.observableList(
                stavkaPrimkeObservableList.stream().filter(isStorno -> !isStorno.getStorno()).toList());
        setComboBoxPrimkaEntity();
        setComboBoxRobaEntity();
        setTableColumnProperties();
        clearRecords();
        tableView.setItems(filteredStavkaPrimkeObservableListOfStorno);
    }

    public void setButtonSearch() {
        TextFieldInsertedPropertiesData searchBy = new TextFieldInsertedPropertiesData();
        filteredSearchingOf(searchBy.company, searchBy.articleName, searchBy.amount);
    }

    public StavkaPrimkeEntity setButtonSave() {
        ComboBoxSelectedPropertiesData create = new ComboBoxSelectedPropertiesData();
        final String alertData = setInputCheckingOf(
                create.selectedPrimka, create.selectedArticle, String.valueOf(create.amount));
        StavkaPrimkeEntity newStavkaPrimke = null;
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        } else {
            newStavkaPrimke = stavkaPrimkeService.createStavkaPrimke(new StavkaPrimkeEntity(nextId(),
                    create.selectedPrimka, create.selectedArticle, create.amount, false, null));
            stavkaPrimkeObservableList.add(newStavkaPrimke);
            tableView.setItems(stavkaPrimkeObservableList);
            initialize();
        }
        return newStavkaPrimke;
    }

    public void setButtonStorno() {
        StavkaPrimkeEntity stavkaPrimke = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (stavkaPrimke != null && utilService.getConfirmForRemoveAlert()) {
            stavkaPrimkeService.createStornoStavkePrimke(stavkaPrimke);
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void setComboBoxPrimkaEntity() {
        comboBoxPrimka.setItems(FXCollections.observableList(primkaService.getAll()));
        comboBoxPrimka.getSelectionModel().selectFirst();
    }

    private void setComboBoxRobaEntity() {
        comboBoxRoba.setItems(FXCollections.observableList(robaService.getAll()));
        comboBoxRoba.getSelectionModel().selectFirst();
    }

    private void setTableColumnProperties() {
        setProperty();
        setStyle();
        setCellValueProperties();
    }

    private void setProperty() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaPrimke"));
        tableColumnPrimka.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkePrimka"));
        tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkeRobe"));
        tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
    }

    private void setStyle() {
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnPrimka.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties() {
        tableColumnPrimka.setCellFactory(column -> new TableCell<>() {
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
    }

    private void filteredSearchingOf(String firma, String artikl, String kolicina) {
        FilteredList<StavkaPrimkeEntity> searchList = filteredStavkaPrimkeObservableListOfStorno
                .filtered(stavkaPrimke -> firma.equals("") || stavkaPrimke.getStavkaPrimkePrimka()
                        .getPrimkaFirme().getNazivFirme().toLowerCase().trim().contains(firma))
                .filtered(stavkaPrimke -> artikl.equals("") || stavkaPrimke.getStavkaPrimkeRobe()
                        .getNazivArtikla().toLowerCase().contains(artikl))
                .filtered(stavkaIzdatnice -> kolicina.equals("") || stavkaIzdatnice.getKolicina().toString().equals(kolicina));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    private Long nextId() {
        return stavkaPrimkeObservableList.size() > 0 ?
                stavkaPrimkeObservableList.stream().mapToLong(StavkaPrimkeEntity::getIdStavkaPrimke).max().getAsLong() + 1001 : 1001;
    }

    private String setInputCheckingOf(PrimkaEntity company, RobaEntity article, String amount) {
        return getDialogData(company, article, amount);
    }

    private String getDialogData(PrimkaEntity company, RobaEntity article, String amount) {
        List<String> listaProvjere = new ArrayList<>();
        if (company == null || company.getPrimkaFirme().getNazivFirme().isEmpty()) listaProvjere.add("Company name!");
        if (article == null || article.toString().isEmpty()) listaProvjere.add("Article name!");
        if (amount == null || amount.isEmpty()) listaProvjere.add("Amount!");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        textFieldKolicina.clear();
        textFieldCompany.clear();
        textFieldArticle.clear();
        comboBoxPrimka.getSelectionModel().clearSelection();
        comboBoxRoba.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
    }

    private class TextFieldInsertedPropertiesData {
        final String company = textFieldCompany.getText().trim().toLowerCase(Locale.ROOT);
        final String articleName = textFieldArticle.getText().trim().toLowerCase(Locale.ROOT);
        final String amount = textFieldKolicina.getText().trim().toLowerCase(Locale.ROOT);
    }

    private class ComboBoxSelectedPropertiesData {
        final PrimkaEntity selectedPrimka = comboBoxPrimka.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxPrimka.getSelectionModel().getSelectedItem();
        final RobaEntity selectedArticle = comboBoxRoba.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxRoba.getSelectionModel().getSelectedItem();
        final Integer amount = textFieldKolicina.getText().toLowerCase(Locale.ROOT).trim().equals("") ? null :
                Integer.valueOf(textFieldKolicina.getText().toLowerCase(Locale.ROOT).trim());
    }
}
