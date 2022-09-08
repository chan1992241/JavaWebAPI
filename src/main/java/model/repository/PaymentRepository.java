package model.repository;

import model.entity.Payment;
import model.entity.PaymentId;
import model.entity.PaymentWrapper;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Dependent
@Transactional
public class PaymentRepository {
    private EntityManager em;
    @Inject
    public PaymentRepository(@PostGresDatabase EntityManager em){
        this.em = em;
    }
    public List<Payment> getPaymentList(){
        return em.createNamedQuery("Payment.findAll").getResultList();
    }
    public List<Payment> getPaymentByName(String firstName, String lastName){
        Query q = em.createNamedQuery("Payment.findByFLName");
        q.setParameter("customerFirstName", firstName);
        q.setParameter("customerLastName", lastName);
        List<Payment> result =  q.getResultList();
        if (!result.isEmpty()){
            return result;
        }
        return null;
    }
    public void addPayment(PaymentWrapper payment){
        java.sql.Date paymentDate = new java.sql.Date(new Date().getTime());
        Payment e = new Payment(new PaymentId(paymentDate, payment.getCustomerNum()));
        e.setAmount(new BigDecimal(payment.getAmount()));
        e.setCustomerFirstName(payment.getCustomerFirstName());
        e.setCustomerLastName(payment.getCustomerLastName());
        e.setAmountAfterDiscount(new BigDecimal(payment.getAmountAfterDiscount()));
        em.persist(e);
   }
}
