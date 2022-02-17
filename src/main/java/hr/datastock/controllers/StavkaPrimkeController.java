package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.*;
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
import java.util.stream.Collectors;

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
    private Button buttonClearFields;

    @Autowired
    private StavkaPrimkeService stavkaPrimkeService;

    @Autowired
    private UtilService utilService;

    private ObservableList<StavkaPrimkeEntity> stavkaPrimkeObservableList;

    @FXML
    public void initialize() {
        stavkaPrimkeObservableList = FXCollections.observableList(stavkaPrimkeService.getAll());
        setComboBoxPrimkaEntity();
        setComboBoxRobaEntity();
        provideAllProperties();
        clearRecords();
        tableView.setItems(stavkaPrimkeObservableList);
        logger.info("$%$%$% Izdatnica records initialized successfully!$%$%$%");
    }

    private void setComboBoxPrimkaEntity() {
        Set<PrimkaEntity> listOfCompanyAndDate = stavkaPrimkeObservableList.stream().map(StavkaPrimkeEntity::getStavkaPrimkePrimka).collect(Collectors.toSet());
        ObservableList<PrimkaEntity> firmeEntityComboSearchObservableList = FXCollections.observableList(new ArrayList<>(listOfCompanyAndDate));
        comboBoxPrimka.setItems(firmeEntityComboSearchObservableList);
        comboBoxPrimka.getSelectionModel().selectFirst();
    }

    private void setComboBoxRobaEntity() {
        Set<RobaEntity> listOfArticles = stavkaPrimkeObservableList.stream().map(StavkaPrimkeEntity::getStavkaPrimkeRobe).collect(Collectors.toSet());
        ObservableList<RobaEntity> robaEntityObservableList = FXCollections.observableList(new ArrayList<>(listOfArticles));
        comboBoxRoba.setItems(robaEntityObservableList);
        comboBoxRoba.getSelectionModel().selectFirst();
    }

    @FXML
    private void provideAllProperties() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idStavkaPrimke"));
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);

        tableColumnPrimka.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkePrimka"));
        tableColumnPrimka.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnPrimka.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(PrimkaEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getPrimkaFirme().getNazivFirme() + "-[" + item.getDatum() + "]");
                }
            }
        });

        tableColumnArticle.setCellValueFactory(new PropertyValueFactory<>("stavkaPrimkeRobe"));
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
        FilteredList<StavkaPrimkeEntity> searchList = stavkaPrimkeObservableList
                .filtered(stavkaIzdatnice -> stavkaIzdatnice.getStavkaPrimkePrimka().getPrimkaFirme().getNazivFirme().toLowerCase().contains(company))
                .filtered(stavkaIzdatnice -> stavkaIzdatnice.getStavkaPrimkeRobe().getNazivArtikla().toLowerCase().contains(articleName))
                .filtered(stavkaIzdatnice -> amount == null || Objects.equals(stavkaIzdatnice.getKolicina(), amount));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    public StavkaPrimkeEntity setButtonSave() {
        final PrimkaEntity companyNameAndDate = comboBoxPrimka.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxPrimka.getSelectionModel().getSelectedItem();

        final RobaEntity article = comboBoxRoba.getSelectionModel().getSelectedItem() == null ? null :
                comboBoxRoba.getSelectionModel().getSelectedItem();

        final Integer amount = textFieldKolicina.getText().toLowerCase(Locale.ROOT).trim().equals("") ? null :
                Integer.valueOf(textFieldKolicina.getText().toLowerCase(Locale.ROOT).trim());

        final String alertData = checkInputValues(companyNameAndDate, article, String.valueOf(amount));
        StavkaPrimkeEntity newStavkaIzdatnica = null;
        if (Optional.ofNullable(companyNameAndDate).isPresent() && Optional.ofNullable(article).isPresent() &&
                Optional.ofNullable(amount).isPresent()) {
            if (!alertData.isEmpty()) {
                utilService.getWarningAlert(alertData);
            } else {
                newStavkaIzdatnica = stavkaPrimkeService.createStavkaIzdatnice(
                        new StavkaPrimkeEntity(nextId(), companyNameAndDate, article, amount, false, null));
                stavkaPrimkeObservableList.add(newStavkaIzdatnica);
                tableView.setItems(stavkaPrimkeObservableList);
                initialize();
            }
        }
        return newStavkaIzdatnica;
    }

    private Long nextId() {
        return stavkaPrimkeObservableList.size() > 0 ?
                stavkaPrimkeObservableList.stream().mapToLong(StavkaPrimkeEntity::getIdStavkaPrimke).max().getAsLong() + 1 : 1;
    }

    private String checkInputValues(PrimkaEntity company, RobaEntity article, String amount) {
        List<String> listaProvjere = new ArrayList<>();
        if (company == null || company.getPrimkaFirme().getNazivFirme().trim().isEmpty())
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
        comboBoxPrimka.getSelectionModel().clearSelection();
        comboBoxRoba.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
    }
}
