package com.pfinance.pfinancefullstack.models;

import jakarta.persistence.*;

@Entity
@Table(name = "buckets")
public class Bucket {

    public enum RecurringInterval {
        DAILY, WEEKLY, BIWEEKLY, BIMONTHLY, MONTHLY, QUARTERLY, YEARLY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double recurringAmount;

    @Column(nullable = false)
    private double maximumAmount;

    @Enumerated(EnumType.ORDINAL)
    private RecurringInterval recurringInterval;

    public Bucket() {

    }

    public Bucket(String name, double recurringAmount, double maximumAmount, RecurringInterval recurringInterval) {
        this.name = name;
        this.recurringAmount = recurringAmount;
        this.maximumAmount = maximumAmount;
        this.recurringInterval = recurringInterval;
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

    public RecurringInterval getRecurringInterval() {
        return recurringInterval;
    }

    public void setRecurringInterval(RecurringInterval recurringInterval) {
        this.recurringInterval = recurringInterval;
    }
}
