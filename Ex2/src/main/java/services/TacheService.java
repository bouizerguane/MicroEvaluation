package services;

import dao.GenericDaoImpl;
import entities.Tache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TacheService extends GenericDaoImpl<Tache> {
    public TacheService() {
        super(Tache.class);
    }

    public List<Tache> getTachesPrixSuperieur1000() {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findByPrixSuperieur", Tache.class)
                .setParameter("prixMin", 1000.0)
                .list();
    }

    public List<Tache> getTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findRealiseesEntreDates", Tache.class)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .list();
    }

    public List<Tache> getTachesPrixSuperieur(double prixMin) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findByPrixSuperieur", Tache.class)
                .setParameter("prixMin", prixMin)
                .list();
    }
}
