# Practical Test Practice
**Setup Persistence Layer**
1. Create Database in pgadmin
2. Load data to database
```
psql -U postgres customerpaymentsystem < [path to sql file]
```
3. Configure standalone file
```
<datasource jndi-name="java:/[jndi]" pool-name="[pool name]">
    <connection-url>jdbc:postgresql://localhost:5432/[database name]</connection-url>
    <driver-class>org.postgresql.Driver</driver-class>
    <driver>postgresql</driver>
    <security>
        <user-name>postgres</user-name>
        <password>jinyeeU</password>
    </security>
</datasource>
```
4. Configure database connection to Intellij
    1. new db connections
    2. data source
    3. add new postgresSQL driver (not Postgres Driver)
    4. enter username, password, database name, and change schema to default schema with public
    5. then test connection and add connection
5. Create entity, repository and webservices package inside model package
6. Create Entity java object from database
   1. Go to Persistence units
   2. New JPA entities from DB
   3. Select db connection with "current schema" keyword
   4. Select table and adjust datatype then add.
7. Implement serializable in JPA entity (both id entity and non id entity)
8. Generate constructor for entity ID and constructor for none (non id entity)
9. Generate constructor for composite ID and constructor for none (id entity)
10. Change local date to date
11. Create Named Query
```
@NamedQuery(name="Payment.findAll", query = "Select p from Payment p")
@NamedQuery(name="Payment.findByFLName", query = "Select p from Payment p where p.customerFirstName = :customerFirstName and p.customerLastName = :customerLastName")
```
12. Make sure bean discovery mode equal to all in beans.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://xmlns.jcp.org/xml/ns/javaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                      http://xmlns.jcp.org/xml/ns/javaee/beans_2_0.xsd"
       bean-discovery-mode="all">
</beans>
```
13. Configure Persistence.xml
```
<persistence-unit name="default">
   <jta-data-source>java:/payment</jta-data-source>
   <class>model.entity.Payment</class>
   <class>model.entity.PaymentId</class>
</persistence-unit>
```
14. Create repository file <ins>PostGresDatabase</ins> and <ins>EntityManagerProducer</ins>

<ins>PostGresDatabase</ins>
```
package model.repository;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD,
        ElementType.TYPE, ElementType.PARAMETER})
public @interface PostGresDatabase {}
// @interface mean create annotation
```
<ins>EntityManagerProducer</ins>
```
package model.repository;
import javax.ejb.EJBException;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "Default")
    @PostGresDatabase
    private EntityManager em;

    public EntityManagerProducer() {
        super();
        // TODO Auto-generated constructor stub
    }
}
```
15. Create repository file (business logic file)
16. Create Wrapper to wrap data from client and pass to repository file

**Business Logic**
1. Add necessary dependency to pom.xml
```
<dependency>
   <groupId>org.jboss.resteasy</groupId>
   <artifactId>resteasy-jaxrs</artifactId>
   <version>3.6.2.Final</version>
   <scope>provided</scope>
</dependency>
```
2. Modify service file
<ins>Example</ins>
```
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
```
3. Create APIApplication class
```
package model.webservices;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class APIApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(PaymentService.class );
        return set;
    }
}
```