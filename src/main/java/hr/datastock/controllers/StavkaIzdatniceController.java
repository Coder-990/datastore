package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.services.IzdatnicaService;
import hr.datastock.services.RobaService;
import hr.datastock.services.StavkaIzdatniceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class StavkaIzdatniceController {

    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";

    @FXML
    private ComboBox<IzdatnicaEntity> comboBoxIzdatnica;
    @FXML
    private ComboBox<RobaEntity> comboBoxRoba;
    @FXML
    private TextField textFieldKolicina;
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
    private Button buttonSearch;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonStorno;
    @FXML
    private Button buttonClearFields;

    @Autowired
    private StavkaIzdatniceService stavkaIzdatniceService;

    @Autowired
    private IzdatnicaService izdatnicaService;

    @Autowired
    private RobaService robaService;

    @Autowired
    private UtilService utilService;

    private ObservableList<StavkaIzdatniceEntity> stavkaIzdatniceObservableList;
    private ObservableList<StavkaIzdatniceEntity> filteredStavkaIzdatniceObservableListOfStorno;

    @FXML
    public void initialize() {
        stavkaIzdatniceObservableList = FXCollections.observableList(stavkaIzdatniceService.getAll());
        filteredStavkaIzdatniceObservableListOfStorno = FXCollections.observableList(
                stavkaIzdatniceObservableList.stream().filter(isStorno -> !isStorno.getStorno()).toList());
        setComboBoxIzdatnicaEntity();
        setComboBoxRobaEntity();
        setTableColumnProperties();
        clearRecords();
        tableView.setItems(filteredStavkaIzdatniceObservableListOfStorno);
    }

    public void setButtonSearch() {
        TextFieldInsertedPropertiesData searchBy = new TextFieldInsertedPropertiesData();
        filteredSearchingOf(searchBy.company, searchBy.articleName, searchBy.amount);
    }

    public StavkaIzdatniceEntity setButtonSave() {
        ComboBoxSelectedPropertiesData create = new ComboBoxSelectedPropertiesData();
        final String alertData = setInputCheckingOf(
                create.selectedIzdatnica, create.selectedArticle, String.valueOf(create.amount));
        StavkaIzdatniceEntity newStavkaIzdatnica = null;
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        } else {
            newStavkaIzdatnica = stavkaIzdatniceService.createStavkaIzdatnice(new StavkaIzdatniceEntity(nextId(),
                    create.selectedIzdatnica, create.selectedArticle, create.amount, false, null));
            stavkaIzdatniceObservableList.add(newStavkaIzdatnica);
            tableView.setItems(stavkaIzdatniceObservableList);
            initialize();
        }
        return newStavkaIzdatnica;
    }

    public void setButtonStorno() {
        StavkaIzdatniceEntity stavkaIzdatnice = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (stavkaIzdatnice != null && utilService.getConfirmForRemoveAlert()) {
            stavkaIzdatniceService.createStornoStavkeIzdatnice(stavkaIzdatnice);
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void setComboBoxIzdatnicaEntity() {
        comboBoxIzdatnica.setItems(FXCollections.observableList(izdatnicaService.getAll()));
        comboBoxIzdatnica.getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity() {
        comboBoxRoba.setItems(FXCollections.observableList(robaService.getAll()));
        comboBoxRoba.getSelectionModel().getSelectedItem();
    }

    private void setTableColumnProperties() {
        setProperty();
        setStyle();
        setCellValueProperties();
    }

    private void setProperty() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaIzdatnice"));
        tableColumnIdIzdatnice.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceIzdatnica"));
        tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceRobe"));
        tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
    }

    private void setStyle() {
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnIdIzdatnice.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties() {
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
    }

    private void filteredSearchingOf(String firma, String artikl, String kolicina) {
        FilteredList<StavkaIzdatniceEntity> searchList = filteredStavkaIzdatniceObservableListOfStorno
                .filtered(stavkaIzdatnice -> firma.equals("") || stavkaIzdatnice.getStavkaIzdatniceIzdatnica()
                        .getIzdatnicaFirme().getNazivFirme().toLowerCase().trim().contains(firma))
                .filtered(stavkaIzdatnice -> artikl.equals("") || stavkaIzdatnice.getStavkaIzdatniceRobe()
                        .getNazivArtikla().toLowerCase().contains(artikl))
                .filtered(stavkaIzdatnice -> kolicina.equals("") || stavkaIzdatnice.getKolicina().toString().equals(kolicina));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    private Long nextId() {
        return stavkaIzdatniceObservableList.size() > 0 ?
                stavkaIzdatniceObservableList.stream().mapToLong(StavkaIzdatniceEntity::getIdStavkaIzdatnice).max().getAsLong() + 1001 : 1001;
    }

    private String setInputCheckingOf(IzdatnicaEntity company, RobaEntity article, String amount) {
        return getDialogData(company, article, amount);
    }

    private String getDialogData(IzdatnicaEntity company, RobaEntity article, String amount) {
        List<String> listaProvjere = new ArrayList<>();
        if (company == null || company.getIzdatnicaFirme().getNazivFirme().isEmpty()) listaProvjere.add("Company!");
        if (article == null || article.toString().isEmpty()) listaProvjere.add("Article!");
        if (amount == null || amount.isEmpty()) listaProvjere.add("Amount!");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        textFieldKolicina.clear();
        textFieldCompany.clear();
        textFieldArticle.clear();
        comboBoxIzdatnica.getSelectionModel().clearSelection();
        comboBoxRoba.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
    }

    private class TextFieldInsertedPropertiesData {
        final String company = textFieldCompany.getText().trim().toLowerCase(Locale.ROOT);
        final String articleName = textFieldArticle.getText().trim().toLowerCase(Locale.ROOT);
        final String amount = textFieldKolicina.getText().trim().toLowerCase(Locale.ROOT);
    }

    private class ComboBoxSelectedPropertiesData {
        final IzdatnicaEntity selectedIzdatnica = comboBoxIzdatnica.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxIzdatnica.getSelectionModel().getSelectedItem();
        final RobaEntity selectedArticle = comboBoxRoba.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxRoba.getSelectionModel().getSelectedItem();
        final Integer amount = textFieldKolicina.getText().toLowerCase(Locale.ROOT).trim().equals("") ? null :
                Integer.valueOf(textFieldKolicina.getText().toLowerCase(Locale.ROOT).trim());
    }
}
