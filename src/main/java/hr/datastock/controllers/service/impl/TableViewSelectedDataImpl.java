package hr.datastock.controllers.service.impl;

import hr.datastock.controllers.service.FirmeControllerService;
import hr.datastock.controllers.service.ITableViewSelectedData;
import hr.datastock.entities.FirmeEntity;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TableViewSelectedDataImpl extends FirmeControllerServiceImpl implements FirmeControllerService {

    public static final String FX_ALIGNMENT_CENTER = "-fx-alignment: CENTER";

    public TableViewSelectedDataImpl(FirmeControllerService firmeControllerService) {
        super(firmeControllerService);
    }

    @Override
    public void setProperty() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idFirme"));
        tableColumnOIB.setCellValueFactory(new PropertyValueFactory<>("oibFirme"));
        tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));
    }

    @Override
    public void setStyle() {
        tableColumnId.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnOIB.setStyle(FX_ALIGNMENT_CENTER);
        tableColumnNaziv.setStyle(FX_ALIGNMENT_CENTER);
    }

    @Override
    public FirmeEntity getFirmaFromTableView() {
        return tableColumnId.getTableView().getSelectionModel().getSelectedItem();
    }
}
