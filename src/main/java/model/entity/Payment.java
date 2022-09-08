package model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "payment", schema = "customerpaymentsystem")
@NamedQuery(name="Payment.findAll", query = "Select p from Payment p")
@NamedQuery(name="Payment.findByFLName", query = "Select p from Payment p where p.customerFirstName = :customerFirstName and p.customerLastName = :customerLastName")
public class Payment implements Serializable {
    @EmbeddedId
    private PaymentId id;

    @Column(name = "\"customerLastName\"", length = 15)
    private String customerLastName;

    @Column(name = "\"customerType\"", length = 20)
    private String customerType;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "\"amountAfterDiscount\"", precision = 10, scale = 2)
    private BigDecimal amountAfterDiscount;

    @Column(name = "\"customerFirstName\"", length = 15)
    private String customerFirstName;

    public PaymentId getId() {
        return id;
    }

    public void setId(PaymentId id) {
        this.id = id;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountAfterDiscount() {
        return amountAfterDiscount;
    }

    public void setAmountAfterDiscount(BigDecimal amountAfterDiscount) {
        this.amountAfterDiscount = amountAfterDiscount;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public Payment(PaymentId id) {
        this.id = id;
    }

    public Payment() {
    }
}