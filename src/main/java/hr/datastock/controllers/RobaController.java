package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
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
        robaObservableList = FXCollections.observableList(robaService.getAll());
        setValuesToTableColumns();
        setButtonClearFields();
        textAreaTableColumnOpis.setWrapText(true);
        tableView.setItems(robaObservableList);
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
            textAreaTableColumnOpis.setText(opis);
        }
    }

    public void setButtonSearch() {
        GetDataFromTextFields getDataFromTextFields = new GetDataFromTextFields();
        filteredSerachOf(getDataFromTextFields);
    }

    public RobaEntity setButtonSave() {
        GetDataFromTextFields getData = new GetDataFromTextFields();

        final BigDecimal cijenaAsString = getData.cijena.equals("") ? null : new BigDecimal(getData.cijena);
        final Integer kolicinaAsString = getData.kolicina.equals("") ? null : Integer.parseInt(getData.kolicina);

        final String alertData = setInputCheckingOf(getData.nazivArtikla, getData.cijena,
                getData.kolicina, getData.jedinicnaMjera);
        RobaEntity newRoba = null;
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        } else {
            newRoba = new RobaEntity(nextId(), getData.nazivArtikla, kolicinaAsString,
                    cijenaAsString, getData.opis, getData.jedinicnaMjera);
            newRoba = robaService.createRoba(newRoba);
            robaObservableList.add(newRoba);
            tableView.setItems(robaObservableList);
            initialize();
        }
        return newRoba;
    }

    public RobaEntity setButtonUpdate() {
        GetDataFromTextFields get = new GetDataFromTextFields();
        final BigDecimal cijenaAsString = get.cijena.equals("") ? null : new BigDecimal(get.cijena);
        final Integer kolicinaAsString = get.kolicina.equals("") ? null : Integer.parseInt(get.kolicina);

        RobaEntity roba = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        final String alertData = setInputCheckingOf(get.nazivArtikla, get.cijena,
                get.kolicina, get.jedinicnaMjera);
        RobaEntity updateRoba = null;
        if (!alertData.isEmpty()) {
            utilService.getWarningAlert(alertData);
        } else {
            updateRoba = new RobaEntity(nextId(), get.nazivArtikla, kolicinaAsString,
                    cijenaAsString, get.opis, get.jedinicnaMjera);
            updateRoba = robaService.updateRoba(updateRoba, roba.getIdRobe());
            robaObservableList.add(updateRoba);
            tableView.setItems(robaObservableList);
            initialize();
        }
        return updateRoba;
    }

    public void setButtonDelete() {
        RobaEntity roba = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (roba != null && utilService.getConfirmForRemoveAlert()) {
            robaService.deleteRoba(roba.getIdRobe());
            initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void filteredSerachOf(GetDataFromTextFields getDataFromTextFields) {
        List<RobaEntity> filtriranaListaArtikla = new ArrayList<>(robaObservableList
                .filtered(roba -> roba.getNazivArtikla().toLowerCase().contains(getDataFromTextFields.nazivArtikla))
                .filtered(roba -> getDataFromTextFields.cijena == null || getDataFromTextFields.cijena.equals("") ||
                        roba.getCijena().equals(new BigDecimal(getDataFromTextFields.cijena)))
                .filtered(artikl -> getDataFromTextFields.kolicina == null || getDataFromTextFields.kolicina.equals("")
                        || artikl.getKolicina() == Integer.parseInt(getDataFromTextFields.kolicina))
                .filtered(roba -> roba.getOpis().toLowerCase().contains(getDataFromTextFields.opis)));
        tableView.setItems(FXCollections.observableList(filtriranaListaArtikla));
    }

    private void setValuesToTableColumns() {
        setProperties();
        setStyle();
    }

    private void setProperties() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idRobe"));
        tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivArtikla"));
        tableColumnCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        tableColumnJedinicnaMjera.setCellValueFactory(new PropertyValueFactory<>("jmj"));
    }

    private void setStyle() {
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnNaziv.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnCijena.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnJedinicnaMjera.setStyle(FX_ALIGNMENT_CENTER);
    }

    private Long nextId() {
        return robaObservableList.size() > 0 ?
                robaObservableList.stream().mapToLong(RobaEntity::getIdRobe).max().getAsLong() + 1001 : 1001;
    }

    private String setInputCheckingOf(String naziv, String kolicina, String cijena, String jmj) {
        return getDialogData(naziv, kolicina, cijena, jmj);
    }

    private String getDialogData(String naziv, String kolicina, String cijena, String jmj) {
        List<String> listaProvjere = new ArrayList<>();
        if (naziv.trim().isEmpty()) listaProvjere.add("naziv");
        if (kolicina.trim().isEmpty()) listaProvjere.add("kolicina");
        if (cijena.trim().isEmpty()) listaProvjere.add("cijena");
        if (jmj.trim().isEmpty()) listaProvjere.add("jedinicnaMjera");
        return String.join("\n", listaProvjere);
    }

    public void clearRecords() {
        tableView.getSelectionModel().clearSelection();
        textFieldNaziv.clear();
        textFieldCijena.clear();
        textFieldKolicina.clear();
        textFieldJedinicaMjere.clear();
        textAreaTableColumnOpis.clear();
    }

    private class GetDataFromTextFields {
        final String nazivArtikla = textFieldNaziv.getText();
        final String cijena = textFieldCijena.getText();
        final String kolicina = textFieldKolicina.getText();
        final String opis = textAreaTableColumnOpis.getText();
        final String jedinicnaMjera = textFieldJedinicaMjere.getText();
    }
}
