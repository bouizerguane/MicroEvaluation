package entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "findByEmploye",
                query = "SELECT et FROM EmployeeTache et WHERE et.employee.id = :employeId"),
        @NamedQuery(name = "findByTache",
                query = "SELECT et FROM EmployeeTache et WHERE et.tache.id = :tacheId")
})
public class EmployeeTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date debutReel;
    private Date finReel;

    @ManyToOne()
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;

    public EmployeeTache() {
    }

    public EmployeeTache(Date debutReel, Date finReel) {
        this.debutReel = debutReel;
        this.finReel = finReel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDebutReel() {
        return debutReel;
    }

    public void setDebutReel(Date debutReel) {
        this.debutReel = debutReel;
    }

    public Date getFinReel() {
        return finReel;
    }

    public void setFinReel(Date finReel) {
        this.finReel = finReel;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    @Override
    public String toString() {
        return "EmployeeTache{" +
                "id=" + id +
                ", debutReel=" + debutReel +
                ", finReel=" + finReel +
                '}';
    }
}
