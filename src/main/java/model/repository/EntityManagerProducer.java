package model.repository;
import javax.ejb.EJBException;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "default")
    @PostGresDatabase
    private EntityManager em;

    public EntityManagerProducer() {
        super();
        // TODO Auto-generated constructor stub
    }
}
