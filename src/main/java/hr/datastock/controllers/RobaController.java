package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.RobaEntity;
import hr.datastock.services.RobaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class RobaController {

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
        this.robaObservableList = FXCollections.observableList(this.robaService.getAll());
        this.setValuesToTableColumns();
        this.setButtonClearFields();
        this.textAreaTableColumnOpis.setWrapText(true);
        this.tableView.setItems(this.robaObservableList);
    }

    public void getAllDataFromTableView() {
        final RobaEntity roba = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (roba != null) {
            final String nazivArtikla = roba.getNazivArtikla();
            this.textFieldNaziv.setText(nazivArtikla);
            final String cijena = String.valueOf(roba.getCijena());
            this.textFieldCijena.setText(cijena);
            final String kolicina = String.valueOf(roba.getKolicina());
            this.textFieldKolicina.setText(kolicina);
            final String jedinicnaMjera = roba.getJmj();
            this.textFieldJedinicaMjere.setText(jedinicnaMjera);
            final String opis = roba.getOpis();
            this.textAreaTableColumnOpis.setText(opis);
        }
    }

    public void setButtonSearch() {
        GetDataFromTextFields getDataFromTextFields = new GetDataFromTextFields();
        this.filteredSerachOf(getDataFromTextFields);
    }

    public RobaEntity setButtonSave() {
        final GetDataFromTextFields getData = new GetDataFromTextFields();

        final BigDecimal cijenaAsString = getData.cijena.equals("") ? null : new BigDecimal(getData.cijena);
        final Integer kolicinaAsString = getData.kolicina.equals("") ? null : Integer.parseInt(getData.kolicina);

        final String alertData = this.setInputCheckingOf(getData.nazivArtikla, getData.cijena,
                getData.kolicina, getData.jedinicnaMjera);
        RobaEntity newRoba = null;
        if (!alertData.isEmpty()) {
            this.utilService.getWarningAlert(alertData);
        } else {
            newRoba = new RobaEntity(this.nextId(), getData.nazivArtikla, kolicinaAsString,
                    cijenaAsString, getData.opis, getData.jedinicnaMjera);
            newRoba = this.robaService.createRoba(newRoba);
            this.robaObservableList.add(newRoba);
            this.tableView.setItems(this.robaObservableList);
            this.initialize();
        }
        return newRoba;
    }

    public RobaEntity setButtonUpdate() {
        final GetDataFromTextFields get = new GetDataFromTextFields();
        final BigDecimal cijenaAsString = get.cijena.equals("") ? null : new BigDecimal(get.cijena);
        final Integer kolicinaAsString = get.kolicina.equals("") ? null : Integer.parseInt(get.kolicina);

        RobaEntity roba = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        final String alertData = this.setInputCheckingOf(get.nazivArtikla, get.cijena,
                get.kolicina, get.jedinicnaMjera);
        RobaEntity updateRoba = null;
        if (!alertData.isEmpty()) {
            this.utilService.getWarningAlert(alertData);
        } else {
            updateRoba = new RobaEntity(this.nextId(), get.nazivArtikla, kolicinaAsString,
                    cijenaAsString, get.opis, get.jedinicnaMjera);
            updateRoba = this.robaService.updateRoba(updateRoba, roba.getIdRobe());
            this.robaObservableList.add(updateRoba);
            this.tableView.setItems(this.robaObservableList);
            this.initialize();
        }
        return updateRoba;
    }

    public void setButtonDelete() {
        final RobaEntity roba = this.tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        if (roba != null && this.utilService.getConfirmForRemoveAlert()) {
            this.robaService.deleteRoba(roba.getIdRobe());
            this.initialize();
        }
    }

    public void setButtonClearFields() {
        clearRecords();
    }

    private void filteredSerachOf(final GetDataFromTextFields getDataFromTextFields) {
        final List<RobaEntity> filtriranaListaArtikla = new ArrayList<>(this.robaObservableList
                .filtered(roba -> roba.getNazivArtikla().toLowerCase().contains(getDataFromTextFields.nazivArtikla))
                .filtered(roba -> getDataFromTextFields.cijena == null
                        || getDataFromTextFields.cijena.equals("")
                        || roba.getCijena().equals(new BigDecimal(getDataFromTextFields.cijena)))
                .filtered(artikl -> getDataFromTextFields.kolicina == null
                        || getDataFromTextFields.kolicina.equals("")
                        || artikl.getKolicina() == Integer.parseInt(getDataFromTextFields.kolicina))
                .filtered(roba -> roba.getOpis().toLowerCase().contains(getDataFromTextFields.opis)));
        this.tableView.setItems(FXCollections.observableList(filtriranaListaArtikla));
    }

    private void setValuesToTableColumns() {
        this.setProperties();
        this.setStyle();
    }

    private void setProperties() {
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idRobe"));
        this.tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivArtikla"));
        this.tableColumnCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        this.tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        this.tableColumnJedinicnaMjera.setCellValueFactory(new PropertyValueFactory<>("jmj"));
    }

    private void setStyle() {
        this.tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnNaziv.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnCijena.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnKolicina.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnJedinicnaMjera.setStyle(FX_ALIGNMENT_CENTER);
    }

    private Long nextId() {
        return !this.robaObservableList.isEmpty() ?
                this.robaObservableList.stream().mapToLong(RobaEntity::getIdRobe).max().getAsLong() + 1 : 1;
    }

    private String setInputCheckingOf(final String naziv, final String kolicina, final String cijena, final String jmj) {
        return this.getDialogData(naziv, kolicina, cijena, jmj);
    }

    private String getDialogData(final String naziv, final String kolicina, final String cijena, final String jmj) {
        final List<String> listaProvjere = new ArrayList<>();
        if (naziv.trim().isEmpty()) listaProvjere.add("naziv");
        if (kolicina.trim().isEmpty()) listaProvjere.add("kolicina");
        if (cijena.trim().isEmpty()) listaProvjere.add("cijena");
        if (jmj.trim().isEmpty()) listaProvjere.add("jedinicnaMjera");
        return String.join("\n", listaProvjere);
    }

    public void clearRecords() {
        this.tableView.getSelectionModel().clearSelection();
        this.textFieldNaziv.clear();
        this.textFieldCijena.clear();
        this.textFieldKolicina.clear();
        this.textFieldJedinicaMjere.clear();
        this.textAreaTableColumnOpis.clear();
    }

    private class GetDataFromTextFields {
        final String nazivArtikla = textFieldNaziv.getText();
        final String cijena = textFieldCijena.getText();
        final String kolicina = textFieldKolicina.getText();
        final String opis = textAreaTableColumnOpis.getText();
        final String jedinicnaMjera = textFieldJedinicaMjere.getText();
    }
}
