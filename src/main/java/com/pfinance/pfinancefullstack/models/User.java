package com.pfinance.pfinancefullstack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 75, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PlaidLink> plaidLinks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PfBudget> pfBudgets;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PfAccount> pfAccounts;


    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User (User copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.email = copy.email;
        this.password = copy.password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PlaidLink> getPlaidLinks() {
        return plaidLinks;
    }

    public void setPlaidLinks(List<PlaidLink> plaidLinks) {
        this.plaidLinks = plaidLinks;
    }

    public List<PfBudget> getPfBudgets() {
        return pfBudgets;
    }

    public void setPfBudgets(List<PfBudget> pfBudgets) {
        this.pfBudgets = pfBudgets;
    }

    public List<PfAccount> getPfAccounts() {
        return pfAccounts;
    }

    public void setPfAccounts(List<PfAccount> pfAccounts) {
        this.pfAccounts = pfAccounts;
    }
}
