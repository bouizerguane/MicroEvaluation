package services;

import dao.GenericDaoImpl;
import entities.Projet;
import entities.Tache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjetService extends GenericDaoImpl<Projet> {
    public ProjetService() {
        super(Projet.class);
    }

    public List<Tache> getTachesPlanifieesPourProjet(int projetId) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findTachesPlanifiees", Tache.class)
                .setParameter("projetId", projetId)
                .list();
    }

    public List<Object[]> getTachesRealiseesAvecDatesReelles(int projetId) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findTachesRealiseesAvecDates", Object[].class)
                .setParameter("projetId", projetId)
                .list();
    }

    public void afficherTachesRealiseesPourProjet(int projetId) {
        Projet projet = findById(projetId);
        List<Object[]> tachesRealisees = getTachesRealiseesAvecDatesReelles(projetId);

        System.out.println("Projet : " + projet.getId() +
                "\tNom : " + projet.getNom() +
                "\tDate début : " + projet.getDateDebut());
        System.out.println("Liste des tâches:");
        System.out.println("Num Nom\t\t\tDate Début Réelle\tDate Fin Réelle");

        for (Object[] tache : tachesRealisees) {
            System.out.println(tache[0] + "  " + tache[1] + "\t\t" +
                    tache[2] + "\t\t" + tache[3]);
        }
    }
}
