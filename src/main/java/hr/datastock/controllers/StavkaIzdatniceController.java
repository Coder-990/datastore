package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StavkaIzdatniceController {

    public static final Logger logger = LoggerFactory.getLogger(StavkaIzdatniceController.class);
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
    private Button buttonClearFields;

    @Autowired
    private StavkaIzdatniceService stavkaIzdatniceService;

    @Autowired
    private UtilService utilService;

    private ObservableList<StavkaIzdatniceEntity> stavkaIzdatniceObservableList;

    @FXML
    public void initialize() {
        stavkaIzdatniceObservableList = FXCollections.observableList(stavkaIzdatniceService.getAll());
        setComboBoxIzdatnicaEntity();
        setComboBoxRobaEntity();
        provideAllProperties();
        clearRecords();
        tableView.setItems(stavkaIzdatniceObservableList);
        logger.info("$%$%$% Izdatnica records initialized successfully!$%$%$%");
    }

    private void setComboBoxIzdatnicaEntity() {
        Set<IzdatnicaEntity> listOfCompanyDate = stavkaIzdatniceObservableList.stream().map(StavkaIzdatniceEntity::getStavkaIzdatniceIzdatnica).collect(Collectors.toSet());
        ObservableList<IzdatnicaEntity> firmeEntityComboSearchObservableList = FXCollections.observableList(new ArrayList<>(listOfCompanyDate));
        comboBoxIzdatnica.setItems(firmeEntityComboSearchObservableList);
        comboBoxIzdatnica.getSelectionModel().selectFirst();
    }

    private void setComboBoxRobaEntity() {
        Set<RobaEntity> listOfArticles = stavkaIzdatniceObservableList.stream().map(StavkaIzdatniceEntity::getStavkaIzdatniceRobe).collect(Collectors.toSet());
        ObservableList<RobaEntity> robaEntityObservableList = FXCollections.observableList(new ArrayList<>(listOfArticles));
        comboBoxRoba.setItems(robaEntityObservableList);
        comboBoxRoba.getSelectionModel().selectFirst();
    }

    @FXML
    private void provideAllProperties() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaIzdatnice"));
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);

        tableColumnIdIzdatnice.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceIzdatnica"));
        tableColumnIdIzdatnice.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnIdIzdatnice.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(IzdatnicaEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getIzdatnicaFirme().getNazivFirme() + "-[" + item.getDatum() + "]");
                }
            }
        });

        tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaIzdatniceRobe"));
        tableColumnArticle.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnArticle.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(RobaEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getNazivArtikla() + "-[" + item.getCijena() + "]-[" + item.getKolicina() + "]");
                }
            }
        });

        tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
    }

    public void setButtonSearch() {
        final String company = textFieldCompany.getText().trim().toLowerCase(Locale.ROOT).equals("") ? null :
                textFieldKolicina.getText().trim().toLowerCase(Locale.ROOT);
        final String articleName = textFieldArticle.getText().trim().toLowerCase(Locale.ROOT).equals("") ? null :
                textFieldKolicina.getText().trim().toLowerCase(Locale.ROOT);
        final Integer amount = textFieldKolicina.getText().trim().toLowerCase(Locale.ROOT).equals("") ? null :
                Integer.valueOf(textFieldKolicina.getText().trim().toLowerCase(Locale.ROOT));
        buttonSearch(company, articleName, amount);
    }

    private void buttonSearch(String company, String articleName, Integer amount) {
        FilteredList<StavkaIzdatniceEntity> searchList = stavkaIzdatniceObservableList
                .filtered(stavkaIzdatnice -> stavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme().getNazivFirme().toLowerCase().contains(company))
                .filtered(stavkaIzdatnice -> stavkaIzdatnice.getStavkaIzdatniceRobe().getNazivArtikla().toLowerCase().contains(articleName))
                .filtered(stavkaIzdatnice -> amount == null || Objects.equals(stavkaIzdatnice.getKolicina(), amount));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    public StavkaIzdatniceEntity setButtonSave() {
        final IzdatnicaEntity companyNameDate = comboBoxIzdatnica.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxIzdatnica.getSelectionModel().getSelectedItem();

        final RobaEntity article = comboBoxRoba.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxRoba.getSelectionModel().getSelectedItem();

        final Integer amount = textFieldKolicina.getText().toLowerCase(Locale.ROOT).trim().equals("") ? null :
                Integer.valueOf(textFieldKolicina.getText().toLowerCase(Locale.ROOT).trim());

        final String alertData = checkInputValues(companyNameDate, article, String.valueOf(amount));
        StavkaIzdatniceEntity newStavkaIzdatnica = null;
        if (Optional.ofNullable(companyNameDate).isPresent() && Optional.ofNullable(article).isPresent() &&
                Optional.ofNullable(amount).isPresent()) {
            if (!alertData.isEmpty()) {
                utilService.getWarningAlert(alertData);
            } else {
                newStavkaIzdatnica = stavkaIzdatniceService.createStavkaIzdatnice(
                        new StavkaIzdatniceEntity(nextId(), companyNameDate, article, amount));
                stavkaIzdatniceObservableList.add(newStavkaIzdatnica);
                tableView.setItems(stavkaIzdatniceObservableList);
                initialize();
            }
        }
        return newStavkaIzdatnica;
    }

    private Long nextId() {
        return stavkaIzdatniceObservableList.size() > 0 ?
                stavkaIzdatniceObservableList.stream().mapToLong(StavkaIzdatniceEntity::getIdStavkaIzdatnice).max().getAsLong() + 1 : 1;
    }

    private String checkInputValues(IzdatnicaEntity company, RobaEntity article, String amount) {
        List<String> listaProvjere = new ArrayList<>();
        if (company == null || company.getIzdatnicaFirme().getNazivFirme().trim().isEmpty())
            listaProvjere.add("Company name!");
        if (article == null || article.toString().trim().isEmpty()) listaProvjere.add("Article name!");
        if (amount == null || amount.trim().isEmpty()) listaProvjere.add("Amount!");
        return String.join("\n", listaProvjere);
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void clearRecords() {
        textFieldKolicina.clear();
        textFieldCompany.clear();
        textFieldArticle.clear();
        comboBoxIzdatnica.getSelectionModel().clearSelection();
        comboBoxRoba.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
    }
}
