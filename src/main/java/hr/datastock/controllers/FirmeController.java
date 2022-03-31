package hr.datastock.controllers;

import hr.datastock.controllers.service.FirmeControllerService;
import hr.datastock.entities.FirmeEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class FirmeController {

//    protected final FirmeService firmeService;
//    protected final UtilService utilService;
//    protected ObservableList<FirmeEntity> firmeObservableList;
//    protected TableViewSelectedData tvSelectedData;
//    protected TextFieldsData textFieldsData;
//    protected DataFromPropertiesForFirmeEntity dataPoperty;
//
//    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";

    private final FirmeControllerService firmeControllerService;

    @FXML
    protected TextField textFieldNaziv;
    @FXML
    protected TextField textFieldOIB;
    @FXML
    protected TableView<FirmeEntity> tableView;
    @FXML
    protected TableColumn<FirmeEntity, Long> tableColumnId;
    @FXML
    protected TableColumn<FirmeEntity, String> tableColumnNaziv;
    @FXML
    protected TableColumn<FirmeEntity, String> tableColumnOIB;
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


    public FirmeController(FirmeControllerService firmeControllerService) {
        this.firmeControllerService = firmeControllerService;
    }

    public void initialize() {
        firmeControllerService.initialize();
    }

    public void getAllDataFromTableViewButton() {
       firmeControllerService.getAllDataFromTableViewButton();
    }

    public void setButtonSearch() {
      firmeControllerService.setButtonSearch();
    }

    public FirmeEntity setButtonSave() {
       return firmeControllerService.setButtonSave();
    }

    public FirmeEntity setButtonUpdate() {
       return firmeControllerService.setButtonUpdate();
    }

    public void setButtonDelete() {
      firmeControllerService.setButtonDelete();
    }

    public void setButtonClearFields() {
      firmeControllerService.setButtonClearFields();
    }

}
