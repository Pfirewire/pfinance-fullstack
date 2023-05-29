package com.pfinance.pfinancefullstack.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pf_budgets")
public class PfBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String month;

    @Column(nullable = false)
    private String year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PfBudget() {
    }

    public PfBudget(String month, String year, User user) {
        this.month = month;
        this.year = year;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
