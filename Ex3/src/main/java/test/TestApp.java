package test;

import config.AppConfig;
import entities.*;
import services.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestApp {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        HommeService hommeService = context.getBean(HommeService.class);
        FemmeService femmeService = context.getBean(FemmeService.class);
        PersonneService personneService = context.getBean(PersonneService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        try {
            // 1. CRÉATION DES DONNÉES
            System.out.println("1. CRÉATION DES DONNÉES");
            System.out.println("========================");

            // Création de 10 femmes
            Femme[] femmes = new Femme[10];
            Calendar cal = Calendar.getInstance();

            String[] nomsFemmes = {"SAFI", "ALAOUI", "BENNANI", "CHRAIBI", "AMRANI",
                    "EL FILALI", "IDRISSI", "ZEROUAL", "TAZI", "MOUNA"};
            String[] prenomsFemmes = {"Fatima", "Khadija", "Aicha", "Zineb", "Nadia",
                    "Samira", "Leila", "Houda", "Sanaa", "Wafaa"};

            for (int i = 0; i < 10; i++) {
                cal.set(1980 + i, Calendar.JANUARY, 15 + i);
                femmes[i] = new Femme(nomsFemmes[i], prenomsFemmes[i],
                        "06" + (10000000 + i), "Adresse " + (i + 1), cal.getTime());
                femmeService.create(femmes[i]);
            }

            // Création de 5 hommes
            Homme[] hommes = new Homme[5];
            String[] nomsHommes = {"SAFI", "ALAMI", "BERADA", "DOUKKALI", "EL MANSOURI"};
            String[] prenomsHommes = {"Said", "Mohamed", "Hassan", "Rachid", "Karim"};

            for (int i = 0; i < 5; i++) {
                cal.set(1975 + i, Calendar.MARCH, 10 + i);
                hommes[i] = new Homme(nomsHommes[i], prenomsHommes[i],
                        "06" + (20000000 + i), "Adresse H" + (i + 1), cal.getTime());
                hommeService.create(hommes[i]);
            }

            // Création des mariages
            // Homme 1 (SAFI Said) - 4 mariages
            cal.set(1989, Calendar.SEPTEMBER, 3);
            Mariage m1 = new Mariage(cal.getTime(), cal.getTime(), 0);
            m1.setHomme(hommes[0]);
            m1.setFemme(femmes[3]); // KARIMA ALAMI
            mariageService.create(m1);

            cal.set(1990, Calendar.SEPTEMBER, 3);
            Mariage m2 = new Mariage(cal.getTime(), null, 4);
            m2.setHomme(hommes[0]);
            m2.setFemme(femmes[0]); // SALIMA RAMI
            mariageService.create(m2);

            cal.set(1995, Calendar.SEPTEMBER, 3);
            Mariage m3 = new Mariage(cal.getTime(), null, 2);
            m3.setHomme(hommes[0]);
            m3.setFemme(femmes[1]); // AMAL ALI
            mariageService.create(m3);

            cal.set(2000, Calendar.NOVEMBER, 4);
            Mariage m4 = new Mariage(cal.getTime(), null, 3);
            m4.setHomme(hommes[0]);
            m4.setFemme(femmes[2]); // WAFA ALAOUI
            mariageService.create(m4);

            // Homme 2 - 2 mariages
            cal.set(1998, Calendar.JUNE, 10);
            Mariage m5 = new Mariage(cal.getTime(), null, 1);
            m5.setHomme(hommes[1]);
            m5.setFemme(femmes[4]);
            mariageService.create(m5);

            cal.set(2005, Calendar.AUGUST, 15);
            Mariage m6 = new Mariage(cal.getTime(), null, 2);
            m6.setHomme(hommes[1]);
            m6.setFemme(femmes[5]);
            mariageService.create(m6);

            // Femme 6 mariée 2 fois
            cal.set(2010, Calendar.JANUARY, 20);
            Mariage m7 = new Mariage(cal.getTime(), null, 1);
            m7.setHomme(hommes[2]);
            m7.setFemme(femmes[6]);
            mariageService.create(m7);

            System.out.println("✓ Données créées avec succès!");
            System.out.println("✓ 10 femmes créées");
            System.out.println("✓ 5 hommes créés");
            System.out.println("✓ 7 mariages créés\n");

            // 2. TEST DE TOUTES LES MÉTHODES
            System.out.println("2. TEST DES MÉTHODES");
            System.out.println("==============================");

            // 2.1 Afficher la liste des femmes
            System.out.println("=== 2.1 Liste des femmes ===");
            List<Femme> toutesFemmes = femmeService.findAll();
            for (int i = 0; i < toutesFemmes.size(); i++) {
                Femme f = toutesFemmes.get(i);
                System.out.println((i + 1) + ". " + f.getNom() + " " + f.getPrenom() +
                        " - Tél: " + f.getTelephone() + " - Née le: " + dateFormat.format(f.getDateNaissance()));
            }
            System.out.println();

            // 2.2 Afficher la femme la plus âgée
            System.out.println("=== 2.2 Femme la plus âgée ===");
            Femme femmePlusAgee = femmeService.getFemmeLaPlusAgee();
            if (femmePlusAgee != null) {
                System.out.println("Femme la plus âgée : " + femmePlusAgee.getNom() + " " + femmePlusAgee.getPrenom() +
                        " (née le " + dateFormat.format(femmePlusAgee.getDateNaissance()) + ")");
            }
            System.out.println();

            // 2.3 Afficher les épouses d'un homme donné
            System.out.println("=== 2.3 Épouses d'un homme donné ===");
            Calendar debut = Calendar.getInstance();
            debut.set(1990, Calendar.JANUARY, 1);
            Calendar fin = Calendar.getInstance();
            fin.set(2005, Calendar.DECEMBER, 31);

            List<Femme> epouses = hommeService.getEpousesEntreDates(hommes[0].getId(), debut.getTime(), fin.getTime());
            System.out.println("Épouses de " + hommes[0].getNom() + " " + hommes[0].getPrenom() +
                    " entre " + dateFormat.format(debut.getTime()) + " et " + dateFormat.format(fin.getTime()) + ":");
            if (epouses.isEmpty()) {
                System.out.println("Aucune épouse trouvée dans cette période");
            } else {
                for (int i = 0; i < epouses.size(); i++) {
                    Femme epouse = epouses.get(i);
                    System.out.println((i + 1) + ". " + epouse.getNom() + " " + epouse.getPrenom());
                }
            }
            System.out.println();

            // 2.4 Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("=== 2.4 Nombre d'enfants d'une femme entre deux dates ===");
            debut.set(1990, Calendar.JANUARY, 1);
            fin.set(2000, Calendar.DECEMBER, 31);

            Long nbEnfants = femmeService.getNombreEnfantsEntreDates(femmes[0].getId(), debut.getTime(), fin.getTime());
            System.out.println("Nombre d'enfants de " + femmes[0].getNom() + " " + femmes[0].getPrenom() +
                    " entre " + dateFormat.format(debut.getTime()) + " et " + dateFormat.format(fin.getTime()) + ": " + nbEnfants);
            System.out.println();

// 2.5 Afficher les femmes mariées deux fois ou plus
            System.out.println("=== 2.5 Femmes mariées deux fois ou plus ===");
            List<Femme> femmesMarieesDeuxFois = femmeService.getFemmesMarieesAuMoinsDeuxFois();
            if (femmesMarieesDeuxFois.isEmpty()) {
                System.out.println("Aucune femme mariée deux fois ou plus");
            } else {
                for (int i = 0; i < femmesMarieesDeuxFois.size(); i++) {
                    Femme f = femmesMarieesDeuxFois.get(i);
                    System.out.println((i + 1) + ". " + f.getNom() + " " + f.getPrenom() +
                            " (" + f.getMariages().size() + " mariages)");
                }
            }
            System.out.println();

// 2.6 Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("=== 2.6 Hommes mariés à quatre femmes entre deux dates ===");
            debut.set(1985, Calendar.JANUARY, 1);
            fin.set(2010, Calendar.DECEMBER, 31);

            List<Homme> hommesQuatreFemmes = femmeService.getHommesMariesQuatreFoisEntreDates(debut.getTime(), fin.getTime());
            System.out.println("Hommes mariés à 4 femmes ou plus entre " +
                    dateFormat.format(debut.getTime()) + " et " + dateFormat.format(fin.getTime()) + ":");
            if (hommesQuatreFemmes.isEmpty()) {
                System.out.println("Aucun homme trouvé");
            } else {
                for (int i = 0; i < hommesQuatreFemmes.size(); i++) {
                    Homme h = hommesQuatreFemmes.get(i);
                    System.out.println((i + 1) + ". " + h.getNom() + " " + h.getPrenom() +
                            " (" + h.getMariages().size() + " mariages)");
                }
            }
            System.out.println();

            // 2.6 Afficher les mariages d'un homme avec tous les détails
            System.out.println("=== 2.6 Mariages d'un homme avec tous les détails ===");
            hommeService.afficherMariagesHomme(hommes[0].getId());

        } catch (Exception e) {
            System.err.println("Erreur lors du test: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }
}