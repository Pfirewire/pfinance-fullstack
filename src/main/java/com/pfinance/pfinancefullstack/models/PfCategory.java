package com.pfinance.pfinancefullstack.models;

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
    private double totalAvalableAmount;

    @ManyToOne
    @JoinColumn(name = "pf_budget_id")
    private PfBudget pfBudget;

    @OneToMany(mappedBy = "pfCategory")
    private List<PfBucket> pfBuckets;

    public PfCategory() {
    }

    public PfCategory(String name, double totalAssignedAmount, double totalAvalableAmount, PfBudget pfBudget) {
        this.name = name;
        this.totalAssignedAmount = totalAssignedAmount;
        this.totalAvalableAmount = totalAvalableAmount;
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

    public double getTotalAvalableAmount() {
        return totalAvalableAmount;
    }

    public void setTotalAvalableAmount(double totalAvalableAmount) {
        this.totalAvalableAmount = totalAvalableAmount;
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
