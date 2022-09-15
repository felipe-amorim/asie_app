package com.asie_back.product;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class ProdStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //@Column
    @NotEmpty(message = "Please provide a name statistics")
    private String name;

    //@Column
    @NotEmpty(message = "Please provide a description statistics")
    private String description;

    @ManyToOne
    private Product product;

    public ProdStatistics(){

    }

    public ProdStatistics( String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

