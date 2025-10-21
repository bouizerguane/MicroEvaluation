package services;

import dao.GenericDaoImpl;
import entities.Femme;
import entities.Homme;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FemmeService extends GenericDaoImpl<Femme> {
    public FemmeService() {
        super(Femme.class);
    }

    // Nombre d'enfants d'une femme entre deux dates - Version HQL
    public Long getNombreEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        String hql = "SELECT COALESCE(SUM(m.nbEnfants), 0) FROM Mariage m " +
                "WHERE m.femme.id = :femmeId " +
                "AND ((m.dateDebut BETWEEN :dateDebut AND :dateFin) " +
                "OR (m.dateFin BETWEEN :dateDebut AND :dateFin) " +
                "OR (m.dateDebut <= :dateDebut AND (m.dateFin IS NULL OR m.dateFin >= :dateFin)))";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Long.class)
                .setParameter("femmeId", femmeId)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .uniqueResult();
    }

    // Femmes mariées au moins deux fois
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        String hql = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Femme.class)
                .list();
    }

    // Femme la plus âgée
    public Femme getFemmeLaPlusAgee() {
        String hql = "SELECT f FROM Femme f ORDER BY f.dateNaissance ASC";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Femme.class)
                .setMaxResults(1)
                .uniqueResult();
    }

    // Hommes mariés à quatre femmes entre deux dates - Version HQL
    public List<Homme> getHommesMariesQuatreFoisEntreDates(Date dateDebut, Date dateFin) {
        String hql = "SELECT h FROM Homme h WHERE SIZE(h.mariages) >= 4 " +
                "AND EXISTS (SELECT m FROM Mariage m WHERE m.homme = h " +
                "AND ((m.dateDebut BETWEEN :dateDebut AND :dateFin) " +
                "OR (m.dateFin BETWEEN :dateDebut AND :dateFin) " +
                "OR (m.dateDebut <= :dateDebut AND (m.dateFin IS NULL OR m.dateFin >= :dateFin))))";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Homme.class)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .list();
    }
}