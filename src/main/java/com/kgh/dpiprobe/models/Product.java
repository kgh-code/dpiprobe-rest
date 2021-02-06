package com.kgh.dpiprobe.models;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Try and keep this pojo as implementation agnostic as possible - createdDate is mongo specific - candidate for abstraction.
 */
@Document(collection = "products")
@ApiModel(value="Product", description="A subset of product fields available for display in the Xcelvie online order service")
public class Product{

    @Id
    private String id;

    @CreatedDate
    private Date createdDate;

    private String skuid;
    private boolean published;
    private String title;
    private String description;
    private String classification;
    private String compound;
    private Integer dose;
    private Double price;


    public Product() {

    }

    public Product(String title, String description,String compound, String classification, Integer dose, Double price) {
        this.title = title;
        this.description = description;
        this.classification = classification;
        this.compound = compound;
        this.dose = dose;
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public Date getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getSkuid() { return skuid; }

    public void setSkuid(String skuid) { this.skuid = skuid; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Integer getDose() {
        return dose;
    }

    public void setDose(Integer dose) {
        this.dose = dose;
    }

    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}


    @Override
    public String toString() {
        return "Product [id=" + id
                + ", createdData=" + createdDate
                + ", title=" + title
                + ", desc=" + description
                + ", classification=" + classification
                + ", compound=" + compound
                + ", price=" + price
                + ", dose=" + dose
                + "]";
    }
}