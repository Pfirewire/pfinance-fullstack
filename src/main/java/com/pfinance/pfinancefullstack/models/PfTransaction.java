package com.pfinance.pfinancefullstack.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pf_transactions")
public class PfTransaction {

    public enum ChannelType {
        ONLINE, IN_STORE, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plaidAccountId;

    @Column
    private String plaidPendingTransactionId;

    @Column
    private String plaidTransactionId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String name;

    @Column
    private String merchantName;

    @Column(length = 10)
    private String isoCurrencyCode;

    @Column
    private String datetime;

    @Column
    private String authorizedDatetime;

    @Column
    private boolean pending;

    @Enumerated(EnumType.ORDINAL)
    private ChannelType channelType;

    @ManyToOne
    @JoinColumn(name = "pf_account_id")
    private PfAccount pfAccount;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany
    @JoinTable(
            name = "transactions_plaid_categories",
            joinColumns = {@JoinColumn(name = "pf_transaction_id")},
            inverseJoinColumns = {@JoinColumn(name = "plaid_category_id")}
    )
    private List<PlaidCategory> plaidCategories;

    public PfTransaction() {
    }

    public PfTransaction(
            String plaidAccountId,
            String plaidPendingTransactionId,
            String plaidTransactionId,
            double amount,
            String name,
            String merchantName,
            String isoCurrencyCode,
            String datetime,
            String authorizedDatetime,
            boolean pending,
            ChannelType channelType,
            PfAccount pfAccount,
            Location location
    ) {
        this.plaidAccountId = plaidAccountId;
        this.plaidPendingTransactionId = plaidPendingTransactionId;
        this.plaidTransactionId = plaidTransactionId;
        this.amount = amount;
        this.name = name;
        this.merchantName = merchantName;
        this.isoCurrencyCode = isoCurrencyCode;
        this.datetime = datetime;
        this.authorizedDatetime = authorizedDatetime;
        this.pending = pending;
        this.channelType = channelType;
        this.pfAccount = pfAccount;
        this.location = location;
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

    public String getPlaidPendingTransactionId() {
        return plaidPendingTransactionId;
    }

    public void setPlaidPendingTransactionId(String plaidPendingTransactionId) {
        this.plaidPendingTransactionId = plaidPendingTransactionId;
    }

    public String getPlaidTransactionId() {
        return plaidTransactionId;
    }

    public void setPlaidTransactionId(String plaidTransactionId) {
        this.plaidTransactionId = plaidTransactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getIsoCurrencyCode() {
        return isoCurrencyCode;
    }

    public void setIsoCurrencyCode(String isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAuthorizedDatetime() {
        return authorizedDatetime;
    }

    public void setAuthorizedDatetime(String authorizedDatetime) {
        this.authorizedDatetime = authorizedDatetime;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public PfAccount getPfAccount() {
        return pfAccount;
    }

    public void setPfAccount(PfAccount pfAccount) {
        this.pfAccount = pfAccount;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<PlaidCategory> getPlaidCategories() {
        return plaidCategories;
    }

    public void setPlaidCategories(List<PlaidCategory> plaidCategories) {
        this.plaidCategories = plaidCategories;
    }
}
