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
}
