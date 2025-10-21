package entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "findHommesMariesQuatreFoisEntreDates",
                query = "SELECT h FROM Homme h WHERE SIZE(h.mariages) >= 4 " +
                        "AND EXISTS (SELECT m FROM Mariage m WHERE m.homme = h " +
                        "AND ((m.dateDebut BETWEEN :dateDebut AND :dateFin) " +
                        "OR (m.dateFin BETWEEN :dateDebut AND :dateFin) " +
                        "OR (m.dateDebut <= :dateDebut AND (m.dateFin IS NULL OR m.dateFin >= :dateFin))))")
})
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private int nbEnfants;

    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    public Mariage() {
    }

    public Mariage(Date dateDebut, Date dateFin, int nbEnfants) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbEnfants = nbEnfants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNbEnfants() {
        return nbEnfants;
    }

    public void setNbEnfants(int nbEnfants) {
        this.nbEnfants = nbEnfants;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }

    @Override
    public String toString() {
        return "Mariage [id=" + id + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", nbEnfants=" + nbEnfants + "]";
    }
}