package hr.datastock.controllers;

import hr.datastock.entities.FirmeEntity;
import hr.datastock.entities.RobaEntity;
import hr.datastock.services.RobaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class RobaController {

    private static final Logger logger = LoggerFactory.getLogger(RobaController.class);
    private static final String ARTIKL_EXCEPTION_MESSAGE = "Ups, something went wrong with: ";
    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";

    @FXML
    private TextField textFieldNaziv;
    @FXML
    private TextField textFieldCijena;
    @FXML
    private TextField textFieldKolicina;
    @FXML
    private TextField textFieldJedinicaMjere;
    @FXML
    private TextArea textAreaOpis;
    @FXML
    private TableView<RobaEntity> tableView;
    @FXML
    private TableColumn<RobaEntity, Long> tableColumnId;
    @FXML
    private TableColumn<RobaEntity, String> tableColumnNaziv;
    @FXML
    private TableColumn<RobaEntity, BigDecimal> tableColumnCijena;
    @FXML
    private TableColumn<RobaEntity, Integer> tableColumnKolicina;
    @FXML
    private TableColumn<RobaEntity, String> tableColumnJedinicnaMjera;
    @FXML
    private ListView<String> listViewOpis;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonClearFields;
    @FXML
    private Button buttonDelete;

    @Autowired
    private RobaService robaService;

    private ObservableList<RobaEntity> robaObservableList;

    @FXML
    public void initialize() {
        logger.info("$%$%$% Article records initializing! $%$%$%");
        robaObservableList = FXCollections.observableList(robaService.getAll());
        provideAllProperties();


        tableView.setItems(robaObservableList);

        logger.info("$%$%$% Article records initialized successfully! $%$%$%");
    }

    private void provideAllProperties() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idRobe"));
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);

        tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivArtikla"));
        tableColumnNaziv.setStyle(FX_ALIGNMENT_CENTER);

        tableColumnCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        tableColumnCijena.setStyle(FX_ALIGNMENT_CENTER);

        tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);

        tableColumnJedinicnaMjera.setCellValueFactory(new PropertyValueFactory<>("jmj"));
        tableColumnJedinicnaMjera.setStyle(FX_ALIGNMENT_CENTER);
    }

    public void getAllDataFromTableView() {
        RobaEntity roba = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (roba != null) {
            String nazivArtikla = roba.getNazivArtikla();
            textFieldNaziv.setText(nazivArtikla);
            String cijena = String.valueOf(roba.getCijena());
            textFieldCijena.setText(cijena);
            String kolicina = String.valueOf(roba.getKolicina());
            textFieldKolicina.setText(kolicina);
            String jedinicnaMjera = roba.getJmj();
            textFieldJedinicaMjere.setText(jedinicnaMjera);
            String opis = roba.getOpis();
            textAreaOpis.setText(opis);
        }
    }

    public void setButtonSearch() {
        String nazivArtikla = textFieldNaziv.getText();
        String cijena = textFieldCijena.getText();
        String kolicina = textFieldKolicina.getText();
        String opis = textAreaOpis.getText();
        List<RobaEntity> filtriranaListaArtikla = new ArrayList<>(robaObservableList
                .filtered(roba -> roba.getNazivArtikla().toLowerCase().contains(nazivArtikla))
                .filtered(roba -> cijena == null || cijena.equals("") ||
                        roba.getCijena().equals(new BigDecimal(cijena)))
                .filtered(artikl -> kolicina == null || kolicina.equals("")
                        || artikl.getKolicina() == Integer.parseInt(kolicina))
                .filtered(roba -> roba.getOpis().toLowerCase().contains(opis)));
        tableView.setItems(FXCollections.observableList(filtriranaListaArtikla));
    }

    public RobaEntity setButtonSave() {
        String nazivArtikla = textFieldNaziv.getText();
        String cijena = textFieldCijena.getText();
        BigDecimal cijenaAsString = new BigDecimal(cijena);
        String kolicina = textFieldKolicina.getText();
        Integer kolicinaAsString = Integer.parseInt(kolicina);
        String jedinicnaMjera = textFieldJedinicaMjere.getText();
        String opis = textAreaOpis.getText();

        String alert = unosProvjera(nazivArtikla, kolicina, cijena, jedinicnaMjera);
        RobaEntity newRoba = null;
        if (!alert.isEmpty()) {
            Alert alertWindow = new Alert(Alert.AlertType.WARNING);
            alertWindow.setTitle("Error");
            alertWindow.setHeaderText("Please input missing records: ");
            alertWindow.setContentText(alert);
            alertWindow.showAndWait();
        } else {
            newRoba = new RobaEntity(nextId(),nazivArtikla,kolicinaAsString,cijenaAsString,opis,jedinicnaMjera);
            try {
                robaService.createRoba(newRoba);
            } catch (Exception ex) {
                logger.error("Error in method 'create article'", ex);
                ex.printStackTrace();
            }
            robaObservableList.add(newRoba);

            tableView.setItems(robaObservableList);
            initialize();
            logger.info("Article records saved successfully!");
        }
        return newRoba;
    }

    public FirmeEntity setButtonUpdate() {
//        String newOib = textFieldOIB.getText();
//        String newNaziv = textFieldNaziv.getText();
//        FirmeEntity firma = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
//        FirmeEntity updatedFirma = null;
//        if (firma != null && !newOib.equals("") && !newNaziv.equals("")) {
//            updatedFirma = new FirmeEntity(firma.getIdFirme(), newOib, newNaziv);
//            try {
//                updatedFirma = firmeService.updateFirma(updatedFirma, firma.getIdFirme());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            initialize();
//        }
//        return updatedFirma;
        return null;
    }

    private Long nextId() {
        return robaObservableList.size() > 0 ?
                robaObservableList.stream().mapToLong(RobaEntity::getIdRobe).max().getAsLong() + 1 : 1;
    }

    private String unosProvjera(String naziv, String kolicina, String cijena, String jmj) {
        List<String> listaProvjere = new ArrayList<>();
        if (naziv.trim().isEmpty()) listaProvjere.add("naziv");
        if (kolicina.trim().isEmpty()) listaProvjere.add("kolicina");
        if (cijena.trim().isEmpty()) listaProvjere.add("cijena");
        if (jmj.trim().isEmpty()) listaProvjere.add("jedinicnaMjera");
        return String.join("\n", listaProvjere);
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    public void clearRecords() {
        textFieldNaziv.clear();
        textFieldCijena.clear();
        textFieldKolicina.clear();
        textFieldJedinicaMjere.clear();
        textAreaOpis.clear();
        listViewOpis.getSelectionModel().clearSelection();

    }
}
