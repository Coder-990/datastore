package hr.datastock.controllers;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FirmeController {

    @FXML
    private ComboBox<Long> comboBoxID;
    @FXML
    private TextField textFieldNaziv;
    @FXML
    private TextField textFieldOIB;
    @FXML
    private TableView<FirmeEntity> tableView;
    @FXML
    private ListView<FirmeEntity> listView;
    @FXML
    private TableColumn<FirmeEntity, Long> tableColumnId;
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnNaziv;
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnOIB;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonInsert;
    @FXML
    private Button buttonClearFields;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;

    @Autowired
    private FirmeService firmeService;

    private ObservableList<FirmeEntity> firmeObservableList;

    @FXML
    public void initialize() {

        System.out.println("firstTest");
        firmeObservableList = FXCollections.observableList(firmeService.getAll());

        System.out.println("$%$%$% Company records initializing! $%$%$%");

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idFirme"));
        tableColumnId.setStyle("-fx-alignment: CENTER");

        tableColumnOIB.setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
        tableColumnOIB.setStyle("-fx-alignment: CENTER");

        tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
        tableColumnNaziv.setStyle("-fx-alignment: CENTER");

        List<Long> listaPoduzecaID = firmeObservableList.stream().map(FirmeEntity::getIdFirme).collect(Collectors.toList());
        ObservableList<Long> obListPoduzeceID = FXCollections.observableList(listaPoduzecaID);
        comboBoxID.setItems(obListPoduzeceID);
        comboBoxID.getSelectionModel().selectFirst();

        tableView.setItems(firmeObservableList);
        System.out.println("$%$%$% Company records initialized successfully with size of: + " + firmeObservableList.size() + "!$%$%$%");
//        logger.info("$%$%$% Poduzece records initialized successfully with size of: " + poduzeceObservableList.size() + "!$%$%$%");
    }

    @FXML
    public void setButtonSearch() {

        String naziv = textFieldNaziv.getText();
        String oib = textFieldOIB.getText();
        List<FirmeEntity> filteredListOfCompanies = new ArrayList<>(firmeObservableList
                .filtered(company -> company.getNazivFirme().toLowerCase().contains(naziv))
                .filtered(company -> company.getOibFirme().toLowerCase().contains(oib)));
        tableView.setItems(FXCollections.observableList(filteredListOfCompanies));
        System.out.println("Search result of: " + filteredListOfCompanies.size() + " records.");
//        logger.info("Article record searched successfully!");
    }

    @FXML
    public void setButtonSave() {

        String oib = textFieldOIB.getText();
        String naziv = textFieldNaziv.getText();

        String alert = unosProvjera(naziv, oib);
        if (!alert.isEmpty()) {
            Alert alertWindow = new Alert(Alert.AlertType.WARNING);
            alertWindow.setTitle("Error");
            alertWindow.setHeaderText("Molim popunite sljedeca polja: ");
            alertWindow.setContentText(alert);
            alertWindow.showAndWait();
        } else {
            FirmeEntity newCompany = new FirmeEntity(nextId(), oib, naziv);
            try {
                firmeService.createCompany(newCompany);
            } catch (Exception ex) {
                System.err.println("Error in method 'unesi poduzece'" + ex);
                ex.printStackTrace();
            }
            firmeObservableList.add(newCompany);
            tableView.setItems(firmeObservableList);
            clearRecords();
            System.out.println("Poduzece records saved successfully!");
        }

    }

    private Long nextId() {
        return firmeObservableList.stream().mapToLong(FirmeEntity::getIdFirme).max().getAsLong() + 1;
    }

    private String unosProvjera(String naziv, String oib) {

        System.out.println("Checking data...");
        List<String> listaProvjere = new ArrayList<>();
        if (oib.trim().isEmpty()) listaProvjere.add("Company identity number!");
        if (naziv.trim().isEmpty()) listaProvjere.add("Company name!");
        return String.join("\n", listaProvjere);
    }

    @FXML
    public void setButtonUpdate(FirmeEntity updateCompay) {
        tableView.getSelectionModel().getSelectedCells();
        Long selectedId = comboBoxID.getSelectionModel().getSelectedItem();
        firmeService.updateCompany(updateCompay, selectedId);
    }

    @FXML
    public void setButtonDelete() {
        Long selectedId = comboBoxID.getSelectionModel().getSelectedItem();
        firmeService.deleteCompany(selectedId);

    }

    @FXML
    public void setButtonClearFields() {
        clearRecords();
    }

    private void clearRecords() {
        comboBoxID.getSelectionModel().clearSelection();
        textFieldNaziv.clear();
        textFieldOIB.clear();
    }


}
