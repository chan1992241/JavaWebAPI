package model.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class PaymentId implements Serializable {
    private static final long serialVersionUID = -5693943958190615200L;
    @Column(name = "\"paymentDate\"", nullable = false)
    private Date paymentDate;

    @Column(name = "\"customerNum\"", nullable = false, length = 10)
    private String customerNum;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentId entity = (PaymentId) o;
        return Objects.equals(this.customerNum, entity.customerNum) &&
                Objects.equals(this.paymentDate, entity.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNum, paymentDate);
    }

    public PaymentId(Date paymentDate, String customerNum) {
        this.paymentDate = paymentDate;
        this.customerNum = customerNum;
    }

    public PaymentId() {
    }
}