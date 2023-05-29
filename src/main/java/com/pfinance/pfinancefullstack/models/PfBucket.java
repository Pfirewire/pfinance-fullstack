package com.pfinance.pfinancefullstack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pf_buckets")
public class PfBucket {

    public enum RecurringType {
        PERCENT, DOLLAR_AMOUNT
    }

    public enum RecurringInterval {
        DAILY, WEEKLY, BIWEEKLY, BIMONTHLY, MONTHLY, QUARTERLY, YEARLY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean autofill;

    @Enumerated(EnumType.ORDINAL)
    private RecurringType recurringType;

    @Enumerated(EnumType.ORDINAL)
    private RecurringInterval recurringInterval;

    @Column(nullable = false)
    private double recurringAmount;

    @Column(nullable = false)
    private double maximumAmount;

    @Column(nullable = false)
    private double availableAmount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pf_category_id")
    private PfCategory pfCategory;

    @OneToMany(mappedBy = "pfBucket")
    private List<TransactionAutoAssign> transactionAutoAssigns;

    public PfBucket() {
    }

    public PfBucket(String name, boolean autofill, RecurringType recurringType, RecurringInterval recurringInterval, double recurringAmount, double maximumAmount, double availableAmount, PfCategory pfCategory) {
        this.name = name;
        this.autofill = autofill;
        this.recurringType = recurringType;
        this.recurringInterval = recurringInterval;
        this.recurringAmount = recurringAmount;
        this.maximumAmount = maximumAmount;
        this.availableAmount = availableAmount;
        this.pfCategory = pfCategory;
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

    public boolean isAutofill() {
        return autofill;
    }

    public void setAutofill(boolean autofill) {
        this.autofill = autofill;
    }

    public RecurringType getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(RecurringType recurringType) {
        this.recurringType = recurringType;
    }

    public RecurringInterval getRecurringInterval() {
        return recurringInterval;
    }

    public void setRecurringInterval(RecurringInterval recurringInterval) {
        this.recurringInterval = recurringInterval;
    }

    public double getRecurringAmount() {
        return recurringAmount;
    }

    public void setRecurringAmount(double recurringAmount) {
        this.recurringAmount = recurringAmount;
    }

    public double getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(double maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public PfCategory getPfCategory() {
        return pfCategory;
    }

    public void setPfCategory(PfCategory pfCategory) {
        this.pfCategory = pfCategory;
    }

    public List<TransactionAutoAssign> getTransactionAutoAssigns() {
        return transactionAutoAssigns;
    }

    public void setTransactionAutoAssigns(List<TransactionAutoAssign> transactionAutoAssigns) {
        this.transactionAutoAssigns = transactionAutoAssigns;
    }
}
