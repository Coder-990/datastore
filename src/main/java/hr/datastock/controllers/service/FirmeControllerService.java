package hr.datastock.controllers.service;

import hr.datastock.entities.FirmeEntity;

public interface FirmeControllerService {

    void initialize();

    void getAllDataFromTableViewButton();

    void setButtonSearch();

    FirmeEntity setButtonSave();

    FirmeEntity setButtonUpdate();

    void setButtonDelete();

    void setButtonClearFields();

    void setProperty();

    void setStyle();

    FirmeEntity getFirmaFromTableView();
}
