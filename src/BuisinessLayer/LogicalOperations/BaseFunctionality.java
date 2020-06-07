package BuisinessLayer.LogicalOperations;

import ServiceLayer.SearchFilterType;
import ServiceLayer.SearchOptionsType;

import java.util.List;

public class BaseFunctionality {

    //Fields
    protected AMEDYSystem system;

    public BaseFunctionality(AMEDYSystem system) {
        this.system = system;
    }

    //Getters
    public AMEDYSystem getSystem() {
        return system;
    }

    //Setters
    public boolean setSystem(AMEDYSystem system) {

        if(system != null && this.system == null) {
            this.system = system;

            return true;
        }

        return false;
    }

    public List searchDataBase(SearchOptionsType choosenSearchOption, List<SearchFilterType> choosenFilters, String freeTextFromUser) {

        return null;
    }


}
