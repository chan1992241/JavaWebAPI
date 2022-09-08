package model.webservices;

import model.entity.Payment;
import model.entity.PaymentWrapper;
import model.repository.PaymentRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("payment")
@RequestScoped
public class PaymentService {
    @Inject
    private PaymentRepository paymentBean;

    @GET
    @Path("getPaymentList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentList(){
        return Response.ok(paymentBean.getPaymentList()).build();
    }

    @GET
    @Path("getPaymentFLName/{fname: [A-Z][a-zA-Z]*}-{lname: [A-Z][a-zA-Z]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentFLName(@PathParam("fname") String fname, @PathParam("lname") String lname ){
        if (paymentBean.getPaymentByName(fname, lname) != null){
            return Response.ok(paymentBean.getPaymentByName(fname, lname)).build();
        }else{
            return Response.status(Response.Status.OK).entity("Payment Record Not Found").type(MediaType.TEXT_PLAIN).build();
        }
    }

    @POST
    @Path("addPayment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPayment(PaymentWrapper payment){
        BigDecimal amountAfterDiscount = null;
        BigDecimal amount1 = new BigDecimal(payment.getAmount());
        if (payment.getCustomerType().equals("silver")){
            amountAfterDiscount = amount1.subtract(amount1.multiply(new BigDecimal(0.05)));
        }else{
            amountAfterDiscount = amount1.subtract(amount1.multiply(new BigDecimal(0.10)));
        }
        payment.setAmountAfterDiscount(amountAfterDiscount.toString());
        paymentBean.addPayment(payment);
        List<Payment> list = paymentBean.getPaymentByName(payment.getCustomerFirstName(), payment.getCustomerLastName());
        if (list != null) {
            return Response.ok(list.get(0)).build();
        }else{
            return Response.status(Response.Status.OK).entity("Payment Record not added").type(MediaType.TEXT_PLAIN).build();
        }
    }
}
