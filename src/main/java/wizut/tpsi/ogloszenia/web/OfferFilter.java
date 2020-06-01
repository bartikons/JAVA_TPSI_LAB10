/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia.web;

/**
 *
 * @author naeri
 */
public class OfferFilter {
    private Integer manufacturerId;
    private Integer modelId;
    private Integer fuelId;
    private Integer fromyear;
    private Integer toyear;
    private Integer page;
    private Integer maxpage;
    private String sorte;
    private String description;
    
    public String getSorte() {
        return sorte;
    }

    public void setSorte(String sorte) {
        this.sorte = sorte;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getFuelId() {
        return fuelId;
    }

    public void setFuelId(Integer fuelId) {
        this.fuelId = fuelId;
    }

    public Integer getFromyear() {
        return fromyear;
    }

    public void setFromyear(Integer fromyear) {
        this.fromyear = fromyear;
    }

    public Integer getToyear() {
        return toyear;
    }

    public void setToyear(Integer toyear) {
        this.toyear = toyear;
    }

    public Integer getMaxpage() {
        return maxpage;
    }

    public void setMaxpage(Integer maxpage) {
        this.maxpage = maxpage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
