package entities;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "findTachesPlanifiees",
                query = "SELECT t FROM Tache t WHERE t.projet.id = :projetId ORDER BY t.dateDebut"),
        @NamedQuery(name = "findTachesRealiseesAvecDates",
                query = "SELECT t.id, t.nom, et.debutReel, et.finReel FROM Tache t " +
                        "JOIN EmployeeTache et ON et.tache.id = t.id WHERE t.projet.id = :projetId " +
                        "AND et.debutReel IS NOT NULL AND et.finReel IS NOT NULL ORDER BY et.debutReel")
})
public class Projet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private Date dateDebut;
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employee employee;

    @OneToMany(mappedBy = "projet")
    private List<Tache> taches;

    public Projet() {
    }

    public Projet(String nom, Date dateDebut, Date dateFin) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "dateFin=" + dateFin +
                ", dateDebut=" + dateDebut +
                ", nom='" + nom + '\'' +
                ", id=" + id +
                '}';
    }
}