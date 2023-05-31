package com.pfinance.pfinancefullstack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "plaid_links")
public class PlaidLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plaidAccessToken;

    @Column(nullable = false)
    private String plaidItemId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "plaidLink")
    private List<PfAccount> pfAccounts;

    public PlaidLink() {
    }

    public PlaidLink(String plaidAccessToken, String plaidItemId, User user) {
        this.plaidAccessToken = plaidAccessToken;
        this.plaidItemId = plaidItemId;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaidAccessToken() {
        return plaidAccessToken;
    }

    public void setPlaidAccessToken(String plaidAccessToken) {
        this.plaidAccessToken = plaidAccessToken;
    }

    public String getPlaidItemId() {
        return plaidItemId;
    }

    public void setPlaidItemId(String plaidItemId) {
        this.plaidItemId = plaidItemId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PfAccount> getPfAccounts() {
        return pfAccounts;
    }

    public void setPfAccounts(List<PfAccount> pfAccounts) {
        this.pfAccounts = pfAccounts;
    }
}
