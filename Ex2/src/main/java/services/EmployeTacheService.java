package services;

import dao.GenericDaoImpl;
import entities.EmployeeTache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeTacheService extends GenericDaoImpl<EmployeeTache> {
    public EmployeTacheService() {
        super(EmployeeTache.class);
    }

    public List<EmployeeTache> findByEmploye(int employeId) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findByEmploye", EmployeeTache.class)
                .setParameter("employeId", employeId)
                .list();
    }

    public List<EmployeeTache> findByTache(int tacheId) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findByTache", EmployeeTache.class)
                .setParameter("tacheId", tacheId)
                .list();
    }
}
