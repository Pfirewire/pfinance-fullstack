package com.pfinance.pfinancefullstack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<PfTransaction> pfTransactions;

    public Location() {
    }

    public Location(String address, String city, String state) {
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PfTransaction> getPfTransactions() {
        return pfTransactions;
    }

    public void setPfTransactions(List<PfTransaction> pfTransactions) {
        this.pfTransactions = pfTransactions;
    }
}
