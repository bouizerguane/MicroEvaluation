package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "findEpousesEntreDates",
                query = "SELECT DISTINCT m.femme FROM Mariage m " +
                        "WHERE m.homme.id = :hommeId " +
                        "AND ((m.dateDebut BETWEEN :dateDebut AND :dateFin) " +
                        "OR (m.dateFin BETWEEN :dateDebut AND :dateFin) " +
                        "OR (m.dateDebut <= :dateDebut AND (m.dateFin IS NULL OR m.dateFin >= :dateFin)))"),
        @NamedQuery(name = "findMariagesByHomme",
                query = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut")
})
public class Homme extends Personne {

    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    private List<Mariage> mariages = new ArrayList<>();

    public Homme() {
    }

    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }

    public void addMariage(Mariage mariage) {
        mariages.add(mariage);
        mariage.setHomme(this);
    }
}