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
        this.stavkaIzdatniceObservableList = FXCollections.observableList(this.stavkaIzdatniceService.getAll());
        this.filteredStavkaIzdatniceObservableListOfStorno = FXCollections.observableList(
                this.stavkaIzdatniceObservableList.stream().filter(isStorno -> !isStorno.getStorno()).toList());
        this.setComboBoxIzdatnicaEntity();
        this.setComboBoxRobaEntity();
        this.setTableColumnProperties();
        this.clearRecords();
        this.tableView.setItems(this.filteredStavkaIzdatniceObservableListOfStorno);
    }

    public void setButtonSearch() {
        final TextFieldInsertedPropertiesData searchBy = new TextFieldInsertedPropertiesData();
        this.filteredSearchingOf(searchBy.company, searchBy.articleName, searchBy.amount);
    }

    public StavkaIzdatniceEntity setButtonSave() {
        final ComboBoxSelectedPropertiesData create = new ComboBoxSelectedPropertiesData();
        final String alertData = this.setInputCheckingOf(
                create.selectedIzdatnica, create.selectedArticle, String.valueOf(create.amount));
        StavkaIzdatniceEntity newStavkaIzdatnica = null;
        if (!alertData.isEmpty()) {
            this.utilService.getWarningAlert(alertData);
        } else {
            newStavkaIzdatnica = stavkaIzdatniceService.createStavkaIzdatnice(new StavkaIzdatniceEntity(this.nextId(),
                    create.selectedIzdatnica, create.selectedArticle, create.amount, false, null));
            this.stavkaIzdatniceObservableList.add(newStavkaIzdatnica);
            this.tableView.setItems(this.stavkaIzdatniceObservableList);
            this.initialize();
        }
        return newStavkaIzdatnica;
    }

    public void setButtonStorno() {
        final StavkaIzdatniceEntity stavkaIzdatnice = this.tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (stavkaIzdatnice != null && this.utilService.getConfirmForRemoveAlert()) {
            this.stavkaIzdatniceService.createStornoStavkeIzdatnice(stavkaIzdatnice);
            this.initialize();
        }
    }

    public void setButtonClearFields() {
        this.clearRecords();
    }

    private void setComboBoxIzdatnicaEntity() {
        this.comboBoxIzdatnica.setItems(FXCollections.observableList(this.izdatnicaService.getAll()));
        this.comboBoxIzdatnica.getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity() {
        this.comboBoxRoba.setItems(FXCollections.observableList(this.robaService.getAll()));
        this.comboBoxRoba.getSelectionModel().getSelectedItem();
    }

    private void setTableColumnProperties() {
        this.setProperty();
        this.setStyle();
        this.setCellValueProperties();
    }

    private void setProperty() {
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaIzdatnice"));
        this.tableColumnIdIzdatnice.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceIzdatnica"));
        this.tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceRobe"));
        this.tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
    }

    private void setStyle() {
        this.tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnIdIzdatnice.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties() {
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
    }

    private void filteredSearchingOf(final String firma, final String artikl, final String kolicina) {
        final FilteredList<StavkaIzdatniceEntity> searchList = this.filteredStavkaIzdatniceObservableListOfStorno
                .filtered(stavkaIzdatnice -> firma.equals("") || stavkaIzdatnice.getStavkaIzdatniceIzdatnica()
                        .getIzdatnicaFirme().getNazivFirme().toLowerCase().trim().contains(firma))
                .filtered(stavkaIzdatnice -> artikl.equals("") || stavkaIzdatnice.getStavkaIzdatniceRobe()
                        .getNazivArtikla().toLowerCase().contains(artikl))
                .filtered(stavkaIzdatnice -> kolicina.equals("") || stavkaIzdatnice.getKolicina().toString().equals(kolicina));
        this.tableView.setItems(FXCollections.observableList(searchList));
    }

    private Long nextId() {
        return !this.stavkaIzdatniceObservableList.isEmpty() ?
                this.stavkaIzdatniceObservableList.stream().mapToLong(StavkaIzdatniceEntity::getIdStavkaIzdatnice).max().getAsLong() + 1 : 1;
    }

    private String setInputCheckingOf(final IzdatnicaEntity company, final RobaEntity article, final String amount) {
        return this.getDialogData(company, article, amount);
    }

    private String getDialogData(final IzdatnicaEntity company, final RobaEntity article, final String amount) {
        final List<String> listaProvjere = new ArrayList<>();
        if (company == null || company.getIzdatnicaFirme().getNazivFirme().isEmpty()) listaProvjere.add("Company!");
        if (article == null || article.toString().isEmpty()) listaProvjere.add("Article!");
        if (amount == null || amount.isEmpty()) listaProvjere.add("Amount!");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        this.textFieldKolicina.clear();
        this.textFieldCompany.clear();
        this.textFieldArticle.clear();
        this.comboBoxIzdatnica.getSelectionModel().clearSelection();
        this.comboBoxRoba.getSelectionModel().clearSelection();
        this.tableView.getSelectionModel().clearSelection();
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
