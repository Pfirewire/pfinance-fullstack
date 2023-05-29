package com.pfinance.pfinancefullstack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "buckets")
public class PfBucket {

    public enum RecurringInterval {
        DAILY, WEEKLY, BIWEEKLY, BIMONTHLY, MONTHLY, QUARTERLY, YEARLY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double currentAmount;

    @Column(nullable = false)
    private double recurringAmount;

    @Column(nullable = false)
    private double maximumAmount;

    @Enumerated(EnumType.ORDINAL)
    private RecurringInterval recurringInterval;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private PfCategory pfCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PfBucket() {

    }

    public PfBucket(String name, double recurringAmount, double maximumAmount) {
        this.name = name;
        this.recurringAmount = recurringAmount;
        this.maximumAmount = maximumAmount;
        this.currentAmount = 0;
    }

    public PfBucket(String name, double recurringAmount, double maximumAmount, RecurringInterval recurringInterval) {
        this.name = name;
        this.recurringAmount = recurringAmount;
        this.maximumAmount = maximumAmount;
        this.recurringInterval = recurringInterval;
    }

    public PfBucket(Long id) {
        this.id = id;
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

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
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

    public PfCategory getGroup() {
        return pfCategory;
    }

    public void setGroup(PfCategory pfCategory) {
        this.pfCategory = pfCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
