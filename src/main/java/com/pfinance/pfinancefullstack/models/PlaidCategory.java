package com.pfinance.pfinancefullstack.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "plaid_categories")
public class PlaidCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "plaidCategories")
    private List<PfTransaction> pfTransactions;

    public PlaidCategory() {
    }

    public PlaidCategory(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PfTransaction> getPfTransactions() {
        return pfTransactions;
    }

    public void setPfTransactions(List<PfTransaction> pfTransactions) {
        this.pfTransactions = pfTransactions;
    }
}
