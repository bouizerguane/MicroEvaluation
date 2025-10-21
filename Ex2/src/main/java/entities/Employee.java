package entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "findTachesRealisees",
                query = "SELECT et FROM EmployeeTache et WHERE et.employee.id = :employeId AND et.debutReel IS NOT NULL AND et.finReel IS NOT NULL"),
        @NamedQuery(name = "findProjetsGeres",
                query = "SELECT p FROM Projet p WHERE p.employee.id = :employeId")
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String telephone;

    @OneToMany(mappedBy = "employee")
    private List<EmployeeTache> employeeTaches;

    @OneToMany(mappedBy = "employee")
    private List<Projet> projets;

    public Employee() {
    }

    public Employee(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<EmployeeTache> getEmployeeTaches() {
        return employeeTaches;
    }

    public void setEmployeeTaches(List<EmployeeTache> employeeTaches) {
        this.employeeTaches = employeeTaches;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", nom='" + nom + '\'' + ", prenom='" + prenom + '\'' + '}';
    }
}