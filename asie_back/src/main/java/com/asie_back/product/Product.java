package com.asie_back.product;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    @NotEmpty(message = "Please provide a name")
    private String name;

    @NotEmpty(message = "Please provide a description")
    private String description;

    @ElementCollection
    private List<String> prodStatistics = new ArrayList<>();

    @OneToMany(/*mappedBy = "product",*/ cascade=CascadeType.ALL)
    private List<ProdStatistics> prodStatisticsList;

    public void setProdStatisticsList(List<ProdStatistics> prodStatisticsList) {
        this.prodStatisticsList = prodStatisticsList;
    }

    public List<String> getProdStatistics() {
        return prodStatistics;
    }

    public List<ProdStatistics> getProdStatisticsList() {
        return prodStatisticsList;
    }

    public Integer getId() {
        return productId;
    }

    public void setId(Integer id) {
        this.productId = id;
    }

    public Product(){

    }

    public Product(String name, String description, List<String> prodStatistics){
        this.name = name;
        this.description = description;
        this.prodStatistics = prodStatistics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

