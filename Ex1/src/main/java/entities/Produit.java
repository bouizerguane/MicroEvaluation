package entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "produitParCategorie", query = "from Produit p WHERE p.categorie = :cat"),
        @NamedQuery(name = "produitParPrix", query = "FROM Produit p WHERE p.prix > :prix"),
        @NamedQuery(name = "produitsEntreDates", query = "select distinct p from Produit p " +
                "JOIN p.LigneCommandes l " +
                "JOIN l.commande c " +
                "where c.date between :d1 AND :d2"),
        @NamedQuery(name = "produitsParCommande", query = "SELECT p.reference, p.prix, l.quantite FROM Produit p " +
                "JOIN p.LigneCommandes l WHERE l.commande = :comm")
})
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;

    private double prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommande> LigneCommandes;

    public Produit() {
    }

    public Produit(String reference, double prix) {
        this.reference = reference;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<LigneCommande> getLigneCommandes() {
        return LigneCommandes;
    }

    public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
        LigneCommandes = ligneCommandes;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", prix=" + prix +
                '}';
    }
}
