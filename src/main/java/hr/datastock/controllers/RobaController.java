package hr.datastock.controllers;

import hr.datastock.controllers.dialogutil.UtilService;
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
    private TableViewSelectedData tvSelectedData;
    private TextFieldsData textFieldsData;
    private DataFromPropertiesForRobaEntity dataPoperty;

    @FXML
    public void initialize() {
        this.robaObservableList = FXCollections.observableList(this.robaService.getAll());
        this.setValuesToTableColumns();
        this.setButtonClearFields();
        this.textAreaTableColumnOpis.setWrapText(true);
        this.tableView.setItems(this.robaObservableList);
    }

    public void getAllDataFromTableView() {
        this.tvSelectedData = new TableViewSelectedData();
        if (this.tvSelectedData.roba != null) {
            this.setDataToTextFields(this.tvSelectedData);
        }
    }

    public void setButtonSearch() {
        this.textFieldsData = new TextFieldsData();
        this.filteredSerachOf(this.textFieldsData);
    }

    public RobaEntity setButtonSave() {
        this.dataPoperty = new DataFromPropertiesForRobaEntity();
        this.textFieldsData = new TextFieldsData();
        RobaEntity newRoba = null;
        if (!this.dataPoperty.alertData.isEmpty()) {
            this.utilService.getWarningAlert(this.dataPoperty.alertData);
        } else {
            newRoba = this.robaService.createRoba(new RobaEntity(this.nextId(), this.textFieldsData.nazivArtikla,
                    this.dataPoperty.kolicinaAsString, this.dataPoperty.cijenaAsString,
                    this.textFieldsData.opis, this.textFieldsData.jedinicnaMjera));
            this.setNewData(newRoba);
        }
        return newRoba;
    }

    public RobaEntity setButtonUpdate() {
        this.tvSelectedData = new TableViewSelectedData();
        this.textFieldsData = new TextFieldsData();
        this.dataPoperty = new DataFromPropertiesForRobaEntity();
        RobaEntity updateRoba = null;
        if (!this.dataPoperty.alertData.isEmpty()) {
            this.utilService.getWarningAlert(this.dataPoperty.alertData);
        } else {
            updateRoba = this.robaService.updateRoba(new RobaEntity(this.tvSelectedData.roba.getIdRobe(),
                    this.textFieldsData.nazivArtikla, this.dataPoperty.kolicinaAsString, this.dataPoperty.cijenaAsString,
                    this.textFieldsData.opis, this.textFieldsData.jedinicnaMjera), this.tvSelectedData.roba.getIdRobe());
            this.setNewData(updateRoba);
        }
        return updateRoba;
    }

    public void setButtonDelete() {
        if (this.tvSelectedData.roba != null && this.utilService.isEntityRemoved()) {
            this.robaService.deleteRoba(this.tvSelectedData.roba.getIdRobe());
            this.initialize();
        }
    }

    public void setButtonClearFields() {
        setClearAllRecords();
    }

    private void filteredSerachOf(final TextFieldsData tfData) {
        final List<RobaEntity> filtriranaListaArtikla = new ArrayList<>(this.robaObservableList
                .filtered(roba -> roba.getNazivArtikla().toLowerCase().contains(tfData.nazivArtikla))
                .filtered(roba -> tfData.cijena == null || tfData.cijena.equals("")
                        || roba.getCijena().equals(new BigDecimal(tfData.cijena)))
                .filtered(artikl -> tfData.kolicina == null || tfData.kolicina.equals("")
                        || artikl.getKolicina() == Integer.parseInt(tfData.kolicina))
                .filtered(roba -> roba.getOpis().toLowerCase().contains(tfData.opis)));
        this.tableView.setItems(FXCollections.observableList(filtriranaListaArtikla));
    }

    private void setNewData(RobaEntity robaEntity) {
        this.robaObservableList.add(robaEntity);
        this.tableView.setItems(this.robaObservableList);
        this.initialize();
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

    protected String setInputCheckingOf(final String naziv, final String kolicina, final String cijena, final String jmj) {
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

    private void setClearAllRecords() {
        this.tableView.getSelectionModel().clearSelection();
        this.textFieldNaziv.clear();
        this.textFieldCijena.clear();
        this.textFieldKolicina.clear();
        this.textFieldJedinicaMjere.clear();
        this.textAreaTableColumnOpis.clear();
    }

    private void setDataToTextFields(TableViewSelectedData selectedData) {
        this.textFieldNaziv.setText(selectedData.nazivArtikla);
        this.textFieldCijena.setText(selectedData.cijena);
        this.textFieldKolicina.setText(selectedData.kolicina);
        this.textFieldJedinicaMjere.setText(selectedData.jedinicnaMjera);
        this.textAreaTableColumnOpis.setText(selectedData.opis);
    }

    private class TableViewSelectedData {
        final RobaEntity roba = tableColumnId.getTableView().getSelectionModel().getSelectedItem();
        final String nazivArtikla = roba.getNazivArtikla();
        final String cijena = String.valueOf(roba.getCijena());
        final String kolicina = String.valueOf(roba.getKolicina());
        final String jedinicnaMjera = roba.getJmj();
        final String opis = roba.getOpis();
    }

    private class TextFieldsData {
        final String nazivArtikla = textFieldNaziv.getText();
        final String cijena = textFieldCijena.getText();
        final String kolicina = textFieldKolicina.getText();
        final String opis = textAreaTableColumnOpis.getText();
        final String jedinicnaMjera = textFieldJedinicaMjere.getText();
    }

    private class DataFromPropertiesForRobaEntity {
        final TextFieldsData getData = new TextFieldsData();
        final BigDecimal cijenaAsString = getData.cijena.equals("") ? null : new BigDecimal(getData.cijena);
        final Integer kolicinaAsString = getData.kolicina.equals("") ? null : Integer.parseInt(getData.kolicina);
        final String alertData = setInputCheckingOf(getData.nazivArtikla, getData.cijena,
                getData.kolicina, getData.jedinicnaMjera);
    }
}
