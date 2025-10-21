package services;

import dao.GenericDaoImpl;
import entities.Employee;
import entities.EmployeeTache;
import entities.Projet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService extends GenericDaoImpl<Employee> {
    public EmployeeService() {
        super(Employee.class);
    }

    public List<EmployeeTache> getTachesRealiseesParEmploye(int employeId) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findTachesRealisees", EmployeeTache.class)
                .setParameter("employeId", employeId)
                .list();
    }

    public List<Projet> getProjetsGeresParEmploye(int employeId) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findProjetsGeres", Projet.class)
                .setParameter("employeId", employeId)
                .list();
    }
}
