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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class RobaController {

    private static final Logger logger = LoggerFactory.getLogger(RobaController.class);
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
    private TextArea textAreaTableColumnOpis;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonGetDataFromTable;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonClearFields;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;

    @Autowired
    private RobaService robaService;

    @Autowired
    private UtilService utilService;

    private ObservableList<RobaEntity> robaObservableList;

    @FXML
    public void initialize() {
        logger.info("$%$%$% Article records initializing! $%$%$%");
        robaObservableList = FXCollections.observableList(robaService.getAll());
        setValuesToTableColumns();
        setButtonClearFields();
        textAreaTableColumnOpis.setWrapText(true);
        tableView.setItems(robaObservableList);
        logger.info("$%$%$% Article records initialized successfully! $%$%$%");
    }

    private void setValuesToTableColumns() {
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
            textAreaTableColumnOpis.setText(opis) ;
        }
    }

    public void setButtonSearch() {
        GetDataFromTextFields getDataFromTextFields = new GetDataFromTextFields();

        List<RobaEntity> filtriranaListaArtikla = new ArrayList<>(robaObservableList
                .filtered(roba -> roba.getNazivArtikla().toLowerCase().contains(getDataFromTextFields.nazivArtikla))
                .filtered(roba -> getDataFromTextFields.cijena == null || getDataFromTextFields.cijena.equals("") ||
                        roba.getCijena().equals(new BigDecimal(getDataFromTextFields.cijena)))
                .filtered(artikl -> getDataFromTextFields.kolicina == null || getDataFromTextFields.kolicina.equals("")
                        || artikl.getKolicina() == Integer.parseInt(getDataFromTextFields.kolicina))
                .filtered(roba -> roba.getOpis().toLowerCase().contains(getDataFromTextFields.opis)));
        tableView.setItems(FXCollections.observableList(filtriranaListaArtikla));
    }

    private class GetDataFromTextFields {
        String nazivArtikla = textFieldNaziv.getText();
        String cijena = textFieldCijena.getText();
        String kolicina = textFieldKolicina.getText();
        String opis = textAreaTableColumnOpis.getText();
        String jedinicnaMjera = textFieldJedinicaMjere.getText();
    }

    public RobaEntity setButtonSave() {
        GetDataFromTextFields getDataFromTextFields = new GetDataFromTextFields();

        BigDecimal cijenaAsString = new BigDecimal(getDataFromTextFields.cijena);
        Integer kolicinaAsString = Integer.parseInt(getDataFromTextFields.kolicina);

        RobaEntity newRoba = null;
        String alertData = setInputCheck(getDataFromTextFields.nazivArtikla, getDataFromTextFields.cijena,
                getDataFromTextFields.kolicina, getDataFromTextFields.jedinicnaMjera);
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        } else {
            newRoba = new RobaEntity(nextId(), getDataFromTextFields.nazivArtikla, kolicinaAsString,
                    cijenaAsString, getDataFromTextFields.opis, getDataFromTextFields.jedinicnaMjera);
            try {
                newRoba = robaService.createRoba(newRoba);
            } catch (Exception ex) {
                logger.error("Error in method 'create article'", ex);
            }
            robaObservableList.add(newRoba);
            tableView.setItems(robaObservableList);
            initialize();
            logger.info("Article records saved successfully!");
        }
        return newRoba;
    }

    public RobaEntity setButtonUpdate() {
        GetDataFromTextFields getDataFromTextFields = new GetDataFromTextFields();

        String jedinicnaMjera = textFieldJedinicaMjere.getText();
        BigDecimal cijenaAsString = new BigDecimal(getDataFromTextFields.cijena);
        Integer kolicinaAsString = Integer.parseInt(getDataFromTextFields.kolicina);
        RobaEntity updateRoba = null;

        RobaEntity roba = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        String alertData = setInputCheck(getDataFromTextFields.nazivArtikla, getDataFromTextFields.cijena,
                getDataFromTextFields.kolicina, jedinicnaMjera);
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        }else{
            updateRoba = new RobaEntity(nextId(), getDataFromTextFields.nazivArtikla, kolicinaAsString,
                    cijenaAsString, getDataFromTextFields.opis, jedinicnaMjera);
            try {
                updateRoba = robaService.updateRoba(updateRoba, roba.getIdRobe());
            } catch (Exception ex) {
                logger.error("Error in method 'create roba'", ex);
            }
            robaObservableList.add(updateRoba);
            tableView.setItems(robaObservableList);
            initialize();
            logger.info("Article records updated successfully!");
        }
        utilService.getnformationMessageAlert();
        return updateRoba;
    }

    private Long nextId() {
        return robaObservableList.size() > 0 ?
                robaObservableList.stream().mapToLong(RobaEntity::getIdRobe).max().getAsLong() + 1 : 1;
    }

    private String setInputCheck(String naziv, String kolicina, String cijena, String jmj) {
        List<String> listaProvjere = new ArrayList<>();
        if (naziv.trim().isEmpty()) listaProvjere.add("naziv");
        if (kolicina.trim().isEmpty()) listaProvjere.add("kolicina");
        if (cijena.trim().isEmpty()) listaProvjere.add("cijena");
        if (jmj.trim().isEmpty()) listaProvjere.add("jedinicnaMjera");
        return String.join("\n", listaProvjere);
    }

    public void setButtonDelete() {
        RobaEntity roba = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (roba != null) {
            robaService.deleteRoba(roba.getIdRobe());
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    public void clearRecords() {
        tableView.getSelectionModel().clearSelection();
        textFieldNaziv.clear();
        textFieldCijena.clear();
        textFieldKolicina.clear();
        textFieldJedinicaMjere.clear();
        textAreaTableColumnOpis.clear();
    }
}
