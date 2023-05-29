package com.pfinance.pfinancefullstack.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction_auto_assign")
public class TransactionAutoAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transactionName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pf_bucket_id")
    private PfBucket pfBucket;

    public TransactionAutoAssign() {
    }

    public TransactionAutoAssign(String transactionName, PfBucket pfBucket) {
        this.transactionName = transactionName;
        this.pfBucket = pfBucket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public PfBucket getPfBucket() {
        return pfBucket;
    }

    public void setPfBucket(PfBucket pfBucket) {
        this.pfBucket = pfBucket;
    }
}
