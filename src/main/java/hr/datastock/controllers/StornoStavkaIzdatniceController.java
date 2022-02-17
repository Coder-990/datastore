package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.FirmeEntity;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StornoStavkaIzdatniceController {

    public static final Logger logger = LoggerFactory.getLogger(StornoStavkaIzdatniceController.class);
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
    private TableColumn<StavkaIzdatniceEntity, LocalDateTime> tableColumnDatum;
    @FXML
    private DatePicker datePickerDatumStorno;
    @FXML
    private Button buttonSearch;
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


    @FXML
    public void initialize() {
        stavkaIzdatniceObservableList = FXCollections.observableList(stavkaIzdatniceService.getAll()
                .stream().filter(StavkaIzdatniceEntity::getStorno).toList());
        setComboBoxIzdatnicaEntity();
        setComboBoxRobaEntity();
        provideAllProperties();
        clearRecords();
        tableView.setItems(stavkaIzdatniceObservableList);
        logger.info("$%$%$% Izdatnica records initialized successfully!$%$%$%");
    }

    private void setComboBoxIzdatnicaEntity() {
        Set<FirmeEntity> listOfCompanyAndDate = stavkaIzdatniceObservableList.stream().map(stornoStavkaIzdatnice ->
                stornoStavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme()).collect(Collectors.toSet());
        ObservableList<FirmeEntity> firmeEntityComboSearchObservableList = FXCollections.observableList(new ArrayList<>(listOfCompanyAndDate));
        comboBoxIzdatnica.setItems(firmeEntityComboSearchObservableList);
        comboBoxIzdatnica.getSelectionModel().getSelectedItem();
    }

    private void setComboBoxRobaEntity() {
        Set<RobaEntity> listOfArticles = stavkaIzdatniceObservableList.stream().map(StavkaIzdatniceEntity::getStavkaIzdatniceRobe).collect(Collectors.toSet());
        ObservableList<RobaEntity> firmeEntityComboSearchObservableList = FXCollections.observableList(new ArrayList<>(listOfArticles));
        comboBoxRoba.setItems(firmeEntityComboSearchObservableList);
        comboBoxRoba.getSelectionModel().getSelectedItem();
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

        tableColumnDatum.setCellValueFactory(new PropertyValueFactory<>("datumStorno"));
        tableColumnDatum.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnDatum.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(DATE_FORMATTER.format(item));
                }
            }
        });
    }

    public void setButtonSearch() {
        final String article = textFieldArticle.getText().trim().toLowerCase(Locale.ROOT);
        final String company = textFieldCompany.getText().trim().toLowerCase(Locale.ROOT);
        final LocalDateTime datePickerDatumStornoValue = datePickerDatumStorno.getValue() == null ? null :
                datePickerDatumStorno.getValue().atStartOfDay();
        buttonSearch(company, article, datePickerDatumStornoValue);
    }

    private void buttonSearch(String company, String articleName, LocalDateTime datePickerStorno) {
        FilteredList<StavkaIzdatniceEntity> searchList = stavkaIzdatniceObservableList
                .filtered(stavkaIzdatnice -> company.equals("") || stavkaIzdatnice.getStavkaIzdatniceIzdatnica()
                        .getIzdatnicaFirme().getNazivFirme().toLowerCase().trim().contains(company.toLowerCase()))
                .filtered(stavkaIzdatnice -> articleName.equals("") || stavkaIzdatnice.getStavkaIzdatniceRobe()
                        .getNazivArtikla().toLowerCase().trim().contains(articleName.toLowerCase()))
                .filtered(stavkaIzdatnice -> comboBoxIzdatnica.getSelectionModel().getSelectedItem() == null ||
                        stavkaIzdatnice.getStavkaIzdatniceIzdatnica().getIzdatnicaFirme().equals(
                                comboBoxIzdatnica.getSelectionModel().getSelectedItem()))
                .filtered(stavkaIzdatnice -> comboBoxRoba.getSelectionModel().getSelectedItem() == null ||
                        stavkaIzdatnice.getStavkaIzdatniceRobe().equals(comboBoxRoba.getSelectionModel().getSelectedItem()))
                .filtered(stavkaIzdatnice -> datePickerStorno == null ||
                        stavkaIzdatnice.getDatumStorno().equals(datePickerStorno));
        tableView.setItems(FXCollections.observableList(searchList));
    }

    public void setButtonClearFields() {
        clearRecords();
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
}
