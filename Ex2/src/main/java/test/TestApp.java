package test;

import config.AppConfig;

import entities.Employee;
import entities.EmployeeTache;
import entities.Projet;
import entities.Tache;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.EmployeTacheService;
import services.ProjetService;
import services.EmployeeService;

import services.TacheService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestApp {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        EmployeeService EmployeeService = context.getBean(EmployeeService.class);
        ProjetService projetService = context.getBean(ProjetService.class);
        TacheService tacheService = context.getBean(TacheService.class);
        EmployeTacheService employeTacheService = context.getBean(EmployeTacheService.class);

        // 1. CRÉATION DES DONNÉES
        System.out.println("1. CRÉATION DES DONNÉES");
        System.out.println("========================");

        // Employés
        Employee emp1 = new Employee("ALAOUI", "Ahmed", "0612345678");
        Employee emp2 = new Employee("BENNANI", "Fatima", "0623456789");
        Employee emp3 = new Employee("CHRAIBI", "Karim", "0634567890");

        EmployeeService.create(emp1);
        EmployeeService.create(emp2);
        EmployeeService.create(emp3);

        // Projets
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.JANUARY, 14);
        Projet p1 = new Projet("Gestion de stock", cal.getTime(), null);

        cal.set(2013, Calendar.MARCH, 1);
        Projet p2 = new Projet("Site E-commerce", cal.getTime(), null);

        p1.setEmployee(emp1);
        p2.setEmployee(emp2);

        projetService.create(p1);
        projetService.create(p2);

        // Tâches
        Tache t1 = new Tache("Analyse", null, null, 1500.0);
        Tache t2 = new Tache("Conception", null, null, 2000.0);
        Tache t3 = new Tache("Développement", null, null, 3500.0);
        Tache t4 = new Tache("Test", null, null, 800.0);

        t1.setProjet(p1);
        t2.setProjet(p1);
        t3.setProjet(p1);
        t4.setProjet(p2);

        tacheService.create(t1);
        tacheService.create(t2);
        tacheService.create(t3);
        tacheService.create(t4);

        // EmployeeTache (affectations)
        cal.set(2013, Calendar.FEBRUARY, 10);
        EmployeeTache et1 = new EmployeeTache(cal.getTime(), null);
        et1.setEmployee(emp1);
        et1.setTache(t1);

        cal.set(2013, Calendar.FEBRUARY, 20);
        et1.setFinReel(cal.getTime());

        cal.set(2013, Calendar.MARCH, 10);
        EmployeeTache et2 = new EmployeeTache(cal.getTime(), null);
        et2.setEmployee(emp2);
        et2.setTache(t2);

        cal.set(2013, Calendar.MARCH, 15);
        et2.setFinReel(cal.getTime());

        cal.set(2013, Calendar.APRIL, 10);
        EmployeeTache et3 = new EmployeeTache(cal.getTime(), null);
        et3.setEmployee(emp3);
        et3.setTache(t3);

        cal.set(2013, Calendar.APRIL, 25);
        et3.setFinReel(cal.getTime());

        employeTacheService.create(et1);
        employeTacheService.create(et2);
        employeTacheService.create(et3);

        System.out.println("✓ Données créées avec succès!\n");

        // 2. TEST DE TOUTES LES MÉTHODES
        System.out.println("2. TEST DES MÉTHODES");
        System.out.println("==============================");

        // Méthode 1: Tâches réalisées par un employé
        System.out.println("=== 1. getTachesRealiseesParEmploye() ===");
        System.out.println("Tâches réalisées par l'employé " + emp1.getNom() + " " + emp1.getPrenom() + ":");
        List<EmployeeTache> tachesEmp1 = EmployeeService.getTachesRealiseesParEmploye(emp1.getId());
        tachesEmp1.forEach(et -> System.out.println("- " + et.getTache().getNom() +
                " (Début: " + dateFormat.format(et.getDebutReel()) +
                ", Fin: " + dateFormat.format(et.getFinReel()) + ")"));
        System.out.println();

        // Méthode 2: Projets gérés par un employé
        System.out.println("=== 2. getProjetsGeresParEmploye() ===");
        System.out.println("Projets gérés par l'employé " + emp1.getNom() + " " + emp1.getPrenom() + ":");
        List<Projet> projetsEmp1 = EmployeeService.getProjetsGeresParEmploye(emp1.getId());
        projetsEmp1.forEach(p -> System.out.println("- " + p.getNom() +
                " (Début: " + dateFormat.format(p.getDateDebut()) + ")"));
        System.out.println();

        // Méthode 3: Tâches planifiées pour un projet
        System.out.println("=== 3. getTachesPlanifieesPourProjet() ===");
        System.out.println("Tâches planifiées pour le projet '" + p1.getNom() + "':");
        List<Tache> tachesProjet1 = projetService.getTachesPlanifieesPourProjet(p1.getId());
        tachesProjet1.forEach(t -> System.out.println("- " + t.getNom() + " (" + t.getPrix() + " DH)"));
        System.out.println();

        // Méthode 4: Tâches réalisées avec dates réelles (affichage formaté)
        System.out.println("=== 4. afficherTachesRealiseesPourProjet() ===");
        projetService.afficherTachesRealiseesPourProjet(p1.getId());
        System.out.println();

        // Méthode 5: Tâches avec prix > 1000 DH
        System.out.println("=== 5. getTachesPrixSuperieur1000() ===");
        System.out.println("Tâches avec prix > 1000 DH:");
        List<Tache> tachesCouteuses = tacheService.getTachesPrixSuperieur1000();
        tachesCouteuses.forEach(t -> System.out.println("- " + t.getNom() + " (" + t.getPrix() + " DH)"));
        System.out.println();

        // Méthode 6: Tâches réalisées entre deux dates
        System.out.println("=== 6. getTachesRealiseesEntreDates() ===");
        Calendar debut = Calendar.getInstance();
        debut.set(2013, Calendar.FEBRUARY, 1);
        Calendar fin = Calendar.getInstance();
        fin.set(2013, Calendar.MARCH, 31);

        System.out.println("Tâches réalisées entre " + dateFormat.format(debut.getTime()) +
                " et " + dateFormat.format(fin.getTime()) + ":");
        List<Tache> tachesEntreDates = tacheService.getTachesRealiseesEntreDates(debut.getTime(), fin.getTime());
        tachesEntreDates.forEach(t -> System.out.println("- " + t.getNom() + " (" + t.getPrix() + " DH)"));

        context.close();
    }
}