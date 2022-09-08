package model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentWrapper {
    private PaymentId id;

    private String customerLastName;

    private String customerType;

    private String amount;

    private String amountAfterDiscount;

    private String customerFirstName;

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    private String customerNum;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountAfterDiscount() {
        return amountAfterDiscount;
    }

    public void setAmountAfterDiscount(String amountAfterDiscount) {
        this.amountAfterDiscount = amountAfterDiscount;
    }

    @Override
    public String toString() {
        return "PaymentWrapper{" +
                "id=" + id +
                ", customerLastName='" + customerLastName + '\'' +
                ", customerType='" + customerType + '\'' +
                ", amount=" + amount +
                ", amountAfterDiscount=" + amountAfterDiscount +
                ", customerFirstName='" + customerFirstName + '\'' +
                '}';
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }
}
