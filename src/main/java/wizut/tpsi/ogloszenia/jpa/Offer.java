/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia.jpa;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import wizut.tpsi.ogloszenia.BodyStyle;
import wizut.tpsi.ogloszenia.CarModel;
import wizut.tpsi.ogloszenia.FuelType;

/**
 *
 * @author naeri
 */

@Entity
@Table(name = "offer")
public class Offer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "title")
    @NotNull
    @Size(max = 255, min = 5)
    private String title;

    @Column(name = "year")
    @NotNull
    @Min(1900)
    private Integer year;

    @Column(name = "mileage")
    @NotNull
    @Min(0)
    private Integer mileage;

    @Column(name = "engine_size")
    @Min(0)
    private BigDecimal engineSize;

    @Column(name = "engine_power")
    @Min(0)
    private Integer enginePower;

    @Column(name = "doors")
    @NotNull
    @Min(0)
    @Max(5)
    private Integer doors;

    @Size(max = 30,min=3)
    @Column(name = "colour")
    @NotNull
    private String colour;

    @Lob
    @Size(max = 65535,min =5)
    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "price")
    @NotNull
    @Min(0)
    private Integer price;

    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private CarModel model;

    @JoinColumn(name = "body_style_id", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private BodyStyle bodyStyle;

    @JoinColumn(name = "fuel_type_id", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private FuelType fuelType;
    
    public Offer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(BigDecimal engineSize) {
        this.engineSize = engineSize;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Integer enginePower) {
        this.enginePower = enginePower;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public BodyStyle getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(BodyStyle bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}
