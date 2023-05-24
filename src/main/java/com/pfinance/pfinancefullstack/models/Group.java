package com.pfinance.pfinancefullstack.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`groups`")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private double totalCurrentAmount;

    @Column
    private double totalRecurringAmount;

    @Column
    private double totalMaximumAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "group")
    private List<Bucket> buckets;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
        this.totalCurrentAmount = 0;
        this.totalRecurringAmount = 0;
        this.totalMaximumAmount = 0;
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

    public double getTotalCurrentAmount() {
        return totalCurrentAmount;
    }

    public void setTotalCurrentAmount(double totalCurrentAmount) {
        this.totalCurrentAmount = totalCurrentAmount;
    }

    public double getTotalRecurringAmount() {
        return totalRecurringAmount;
    }

    public void setTotalRecurringAmount(double totalRecurringAmount) {
        this.totalRecurringAmount = totalRecurringAmount;
    }

    public double getTotalMaximumAmount() {
        return totalMaximumAmount;
    }

    public void setTotalMaximumAmount(double totalMaximumAmount) {
        this.totalMaximumAmount = totalMaximumAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Bucket> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
    }
}
