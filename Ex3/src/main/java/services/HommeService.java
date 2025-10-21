package services;

import dao.GenericDaoImpl;
import entities.Femme;
import entities.Homme;
import entities.Mariage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HommeService extends GenericDaoImpl<Homme> {
    public HommeService() {
        super(Homme.class);
    }

    // Afficher les épouses d'un homme entre deux dates
    public List<Femme> getEpousesEntreDates(int hommeId, Date dateDebut, Date dateFin) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("findEpousesEntreDates", Femme.class)
                .setParameter("hommeId", hommeId)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .list();
    }

    // Afficher les mariages d'un homme avec les détails
    public void afficherMariagesHomme(int hommeId) {
        Homme homme = findById(hommeId);
        List<Mariage> mariages = sessionFactory.getCurrentSession()
                .createNamedQuery("findMariagesByHomme", Mariage.class)
                .setParameter("hommeId", hommeId)
                .list();

        System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
        System.out.println("Mariages En Cours :");

        int countEnCours = 1;
        int countEchoues = 1;

        for (Mariage m : mariages) {
            if (m.getDateFin() == null) {
                // Mariage en cours
                System.out.println(countEnCours + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                        "   Date Début : " + m.getDateDebut() + "    Nbr Enfants : " + m.getNbEnfants());
                countEnCours++;
            }
        }

        System.out.println("\nMariages échoués :");
        for (Mariage m : mariages) {
            if (m.getDateFin() != null) {
                // Mariage échoué
                System.out.println(countEchoues + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                        "  Date Début : " + m.getDateDebut() + "    Date Fin : " + m.getDateFin() +
                        "    Nbr Enfants : " + m.getNbEnfants());
                countEchoues++;
            }
        }
    }
}