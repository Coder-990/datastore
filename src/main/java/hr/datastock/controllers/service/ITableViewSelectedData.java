package hr.datastock.controllers.service;

import hr.datastock.entities.FirmeEntity;

public interface ITableViewSelectedData {

    void setProperty();

    void setStyle();

    FirmeEntity getFirmaFromTableView();
}
