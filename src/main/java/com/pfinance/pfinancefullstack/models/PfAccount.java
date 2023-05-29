package com.pfinance.pfinancefullstack.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pf_accounts")
public class PfAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plaidAccountId;

    @Column(nullable = false)
    private double availableBalance;

    @Column(nullable = false)
    private double currentBalance;

    @Column(nullable = false, length = 10)
    private String isoCurrencyCode;

    @Column(length = 4)
    private String mask;

    @Column(nullable = false)
    private String name;

    @Column
    private String officialName;

    @Column(nullable = false, length = 10)
    private String type;

    @Column(length = 30)
    private String subtype;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pfAccount")
    private List<PfTransaction> pfTransactions;

    public PfAccount() {
    }

    public PfAccount(String plaidAccountId, double availableBalance, double currentBalance, String isoCurrencyCode, String mask, String name, String officialName, String type, String subtype) {
        this.plaidAccountId = plaidAccountId;
        this.availableBalance = availableBalance;
        this.currentBalance = currentBalance;
        this.isoCurrencyCode = isoCurrencyCode;
        this.mask = mask;
        this.name = name;
        this.officialName = officialName;
        this.type = type;
        this.subtype = subtype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaidAccountId() {
        return plaidAccountId;
    }

    public void setPlaidAccountId(String plaidAccountId) {
        this.plaidAccountId = plaidAccountId;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getIsoCurrencyCode() {
        return isoCurrencyCode;
    }

    public void setIsoCurrencyCode(String isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PfTransaction> getPfTransactions() {
        return pfTransactions;
    }

    public void setPfTransactions(List<PfTransaction> pfTransactions) {
        this.pfTransactions = pfTransactions;
    }
}
