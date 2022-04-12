package hr.datastock.controllers;

import hr.datastock.controllers.controllerutil.UtilService;
import hr.datastock.entities.FirmeEntity;
import hr.datastock.services.FirmeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class FirmeController {

    private static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";

    private final FirmeService firmeService;
    private final UtilService utilService;

    private ObservableList<FirmeEntity> firmeObservableList;

    @FXML
    private TextField textFieldNaziv;
    @FXML
    private TextField textFieldOIB;
    @FXML
    private TableView<FirmeEntity> tableView;
    @FXML
    private TableColumn<FirmeEntity, Long> tableColumnId;
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnNaziv;
    @FXML
    private TableColumn<FirmeEntity, String> tableColumnOIB;
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

    @FXML
    public void initialize() {
        this.firmeObservableList = FXCollections.observableList(this.firmeService.getAll());
        this.setValuesToTableColumns();
        this.setButtonClear();
        this.tableView.setItems(this.firmeObservableList);
    }

    public void getAllDataFromTableViewButton() {
        if (this.getTableViewFirma() != null) {
            this.textFieldOIB.setText(this.getTableViewFirma().getOibFirme());
            this.textFieldNaziv.setText(this.getTableViewFirma().getNazivFirme());
        } else if (this.getTableViewFirma() == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        }
    }

    public FirmeEntity setButtonSearch() {
        if ((this.textFieldNaziv != null || this.textFieldOIB != null) && this.getTextFieldDataForDialogCheck().isEmpty()) {
            final List<FirmeEntity> filteredListOfCompanies = new ArrayList<>(this.firmeObservableList
                    .filtered(company -> company.getNazivFirme().toLowerCase().contains(this.textFieldNaziv.getText()))
                    .filtered(company -> company.getOibFirme().toLowerCase().contains(this.textFieldOIB.getText())));
            this.tableView.setItems(FXCollections.observableList(filteredListOfCompanies));
        }
        return this.utilService.getWarningAlert(this.getTextFieldDataForDialogCheck());
    }

    public FirmeEntity setButtonSave() {
        if (!this.getTextFieldDataForDialogCheck().isEmpty()) {
            return this.utilService.getWarningAlert(this.getTextFieldDataForDialogCheck());
        }
        return this.firmeService.createFirma(this.createNewFirma());
    }

    public FirmeEntity setButtonUpdate() {
        if (!this.getTextFieldDataForDialogCheck().isEmpty()) {
            return this.utilService.getWarningAlert(this.getTextFieldDataForDialogCheck());
        }
        return firmeService.updateFirma(this.updateExistingFirma(), this.updateExistingFirma().getIdFirme());
    }

    public void setButtonDelete() {
        if (this.getTableViewFirma() != null && this.utilService.isEntityRemoved()) {
            this.firmeService.deleteFirma(this.getTableViewFirma().getIdFirme());
            this.initialize();
        }else if (this.getTableViewFirma() == null) {
            this.utilService.isDataPickedFromTableViewAlert();
        }else {
            this.utilService.isEntityUnableToRemove();
        }
    }

    public void setButtonClear() {
        this.textFieldNaziv.clear();
        this.textFieldOIB.clear();
        this.tableView.getSelectionModel().clearSelection();
    }

    private FirmeEntity createNewFirma() {
        FirmeEntity newFirma = FirmeEntity.builder()
                .idFirme(this.nextId())
                .oibFirme(this.textFieldOIB.getText())
                .nazivFirme(this.textFieldNaziv.getText()).build();
        this.setNewData(newFirma);
        return newFirma;
    }

    private FirmeEntity updateExistingFirma() {
        FirmeEntity updateFirme = FirmeEntity.builder()
                .idFirme(this.getTableViewFirma().getIdFirme())
                .oibFirme(this.textFieldOIB.getText())
                .nazivFirme(this.textFieldNaziv.getText()).build();
        this.setNewData(updateFirme);
        return updateFirme;
    }

    private void setNewData(FirmeEntity firmeEntity) {
        this.firmeObservableList.add(firmeEntity);
        this.tableView.setItems(this.firmeObservableList);
        this.initialize();
    }

    private void setValuesToTableColumns() {
        this.setProperty();
        this.setStyle();
    }

    private void setProperty() {
        this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idFirme"));
        this.tableColumnOIB.setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
        this.tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
    }

    private void setStyle() {
        this.tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnOIB.setStyle(FX_ALIGNMENT_CENTER);
        this.tableColumnNaziv.setStyle(FX_ALIGNMENT_CENTER);
    }

    private Long nextId() {
        return !this.firmeObservableList.isEmpty() ?
                this.firmeObservableList.stream().mapToLong(FirmeEntity::getIdFirme).max().getAsLong() + 1 : 1;
    }

    private String getTextFieldDataForDialogCheck() {
        final List<String> checkList = new ArrayList<>();
        if (this.textFieldOIB.getText().trim().isEmpty()) checkList.add("Company name!");
        if (this.textFieldNaziv.getText().trim().isEmpty()) checkList.add("Company identity number!");
        return String.join("\n", checkList);
    }

    private FirmeEntity getTableViewFirma() {
        return tableColumnId.getTableView().getSelectionModel().getSelectedItem();
    }
}
