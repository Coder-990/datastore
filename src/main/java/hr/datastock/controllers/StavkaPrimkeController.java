package hr.datastock.controllers;

import hr.datastock.controllers.dialogutil.UtilService;
import hr.datastock.entities.PrimkaEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.entities.StavkaPrimkeEntity;
import hr.datastock.services.PrimkaService;
import hr.datastock.services.RobaService;
import hr.datastock.services.StavkaPrimkeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Controller
public class StavkaPrimkeController {

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

    private final StavkaPrimkeService stavkaPrimkeService;

    private final PrimkaService primkaService;

    private final RobaService robaService;

    private final UtilService utilService;

    private ObservableList<StavkaPrimkeEntity> stavkaPrimkeObservableList;
    private ObservableList<StavkaPrimkeEntity> filteredStavkaPrimkeObservableListOfStorno;

    @FXML
    public void initialize() {
        this.stavkaPrimkeObservableList = FXCollections.observableList(this.stavkaPrimkeService.getAll());
        this.filteredStavkaPrimkeObservableListOfStorno = FXCollections.observableList(
                this.stavkaPrimkeObservableList.stream().filter(isStorno -> !isStorno.getStorno()).toList());
        this.setComboBoxPrimkaEntity();
        this.setComboBoxRobaEntity();
        this.setTableColumnProperties();
        this.clearRecords();
        this.tableView.setItems(this.filteredStavkaPrimkeObservableListOfStorno);
    }

    public void setButtonSearch() {
        final TextFieldInsertedPropertiesData searchBy = new TextFieldInsertedPropertiesData();
        this.filteredSearchingOf(searchBy.company, searchBy.articleName, searchBy.amount);
    }

    public StavkaPrimkeEntity setButtonSave() {
        final ComboBoxSelectedPropertiesData create = new ComboBoxSelectedPropertiesData();
        final String alertData = this.setInputCheckingOf(
                create.selectedPrimka, create.selectedArticle, String.valueOf(create.amount));
        StavkaPrimkeEntity newStavkaPrimke = null;
        if (!alertData.isEmpty()) {
            this.utilService.getWarningAlert(alertData);
        } else {
            newStavkaPrimke = stavkaPrimkeService.createStavkaPrimke(new StavkaPrimkeEntity(this.nextId(),
                    create.selectedPrimka, create.selectedArticle, create.amount, false, null));
            this.stavkaPrimkeObservableList.add(newStavkaPrimke);
            this.tableView.setItems(this.stavkaPrimkeObservableList);
            this.initialize();
        }
        return newStavkaPrimke;
    }

    public void setButtonStorno() {
        final StavkaPrimkeEntity stavkaPrimke = this.tableColumnId.getTableView()
                .getSelectionModel().getSelectedItem();
        if (stavkaPrimke != null && this.utilService.isEntityRemoved()) {
            this.stavkaPrimkeService.createStornoStavkePrimke(stavkaPrimke);
            this.initialize();
        }
    }

    public void setButtonClearFields() {
        this.clearRecords();
    }

    private void setComboBoxPrimkaEntity() {
        this.comboBoxPrimka.setItems(FXCollections.observableList(this.primkaService.getAll()));
        this.comboBoxPrimka.getSelectionModel().selectFirst();
    }

    private void setComboBoxRobaEntity() {
        this.comboBoxRoba.setItems(FXCollections.observableList(this.robaService.getAll()));
        this.comboBoxRoba.getSelectionModel().selectFirst();
    }

    private void setTableColumnProperties() {
        this.setProperty();
        this.setStyle();
        this.setCellValueProperties();
    }

    private void setProperty() {
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaPrimke"));
        this.tableColumnPrimka.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkePrimka"));
        this.tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkeRobe"));
        this.tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
    }

    private void setStyle() {
        this.tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnPrimka.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
    }

    private void setCellValueProperties() {
        this.tableColumnPrimka.setCellFactory(column -> new TableCell<>() {
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
    }

    private void filteredSearchingOf(final String firma, final String artikl, final String kolicina) {
        final FilteredList<StavkaPrimkeEntity> searchList = this.filteredStavkaPrimkeObservableListOfStorno
                .filtered(stavkaPrimke -> firma.equals("") || stavkaPrimke.getStavkaPrimkePrimka()
                        .getPrimkaFirme().getNazivFirme().toLowerCase().trim().contains(firma))
                .filtered(stavkaPrimke -> artikl.equals("") || stavkaPrimke.getStavkaPrimkeRobe()
                        .getNazivArtikla().toLowerCase().contains(artikl))
                .filtered(stavkaIzdatnice -> kolicina.equals("") || stavkaIzdatnice.getKolicina().toString().equals(kolicina));
        this.tableView.setItems(FXCollections.observableList(searchList));
    }

    private Long nextId() {
        return !this.stavkaPrimkeObservableList.isEmpty() ?
                this.stavkaPrimkeObservableList.stream().mapToLong(StavkaPrimkeEntity::getIdStavkaPrimke).max().getAsLong() + 1 : 1;
    }

    private String setInputCheckingOf(final PrimkaEntity company, final RobaEntity article, final String amount) {
        return this.getDialogData(company, article, amount);
    }

    private String getDialogData(final PrimkaEntity company, final RobaEntity article, final String amount) {
        List<String> listaProvjere = new ArrayList<>();
        if (company == null || company.getPrimkaFirme().getNazivFirme().isEmpty()) listaProvjere.add("Company name!");
        if (article == null || article.toString().isEmpty()) listaProvjere.add("Article name!");
        if (amount == null || amount.isEmpty()) listaProvjere.add("Amount!");
        return String.join("\n", listaProvjere);
    }

    private void clearRecords() {
        this.textFieldKolicina.clear();
        this.textFieldCompany.clear();
        this.textFieldArticle.clear();
        this.comboBoxPrimka.getSelectionModel().clearSelection();
        this.comboBoxRoba.getSelectionModel().clearSelection();
        this.tableView.getSelectionModel().clearSelection();
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
