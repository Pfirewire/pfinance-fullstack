package com.pfinance.pfinancefullstack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pf_categories")
public class PfCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private double totalAssignedAmount;

    @Column
    private double totalAvailableAmount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pf_budget_id")
    private PfBudget pfBudget;

    @OneToMany(mappedBy = "pfCategory")
    private List<PfBucket> pfBuckets;

    public PfCategory() {
    }

    public PfCategory(String name, PfBudget pfBudget) {
        this.name = name;
        this.pfBudget = pfBudget;
    }

    public PfCategory(String name, double totalAssignedAmount, double totalAvailableAmount, PfBudget pfBudget) {
        this.name = name;
        this.totalAssignedAmount = totalAssignedAmount;
        this.totalAvailableAmount = totalAvailableAmount;
        this.pfBudget = pfBudget;
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

    public double getTotalAssignedAmount() {
        return totalAssignedAmount;
    }

    public void setTotalAssignedAmount(double totalAssignedAmount) {
        this.totalAssignedAmount = totalAssignedAmount;
    }

    public double getTotalAvailableAmount() {
        return totalAvailableAmount;
    }

    public void setTotalAvailableAmount(double totalAvailableAmount) {
        this.totalAvailableAmount = totalAvailableAmount;
    }

    public PfBudget getPfBudget() {
        return pfBudget;
    }

    public void setPfBudget(PfBudget pfBudget) {
        this.pfBudget = pfBudget;
    }

    public List<PfBucket> getPfBuckets() {
        return pfBuckets;
    }

    public void setPfBuckets(List<PfBucket> pfBuckets) {
        this.pfBuckets = pfBuckets;
    }
}
