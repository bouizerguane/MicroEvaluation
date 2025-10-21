package test;

import config.AppConfig;
import entities.Categorie;
import entities.Commande;
import entities.LigneCommande;
import entities.Produit;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.CategorieService;
import services.CommandeService;
import services.LigneCommandeService;
import services.ProduitService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestApp {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CategorieService categorieService = context.getBean(CategorieService.class);
        ProduitService produitService = context.getBean(ProduitService.class);
        CommandeService commandeService = context.getBean(CommandeService.class);
        LigneCommandeService ligneCommandeService = context.getBean(LigneCommandeService.class);

        // 1. CRÉATION DES DONNÉES
        System.out.println("1. CRÉATION DES DONNÉES");
        System.out.println("========================");

        // Catégories
        Categorie electronique = new Categorie(1001, "Électronique");
        Categorie informatique = new Categorie(1002, "Informatique");
        categorieService.create(electronique);
        categorieService.create(informatique);

        // Produits
        Produit p1 = new Produit("ES12", 120.0);
        p1.setCategorie(electronique);
        Produit p2 = new Produit("ZR85", 100.0);
        p2.setCategorie(electronique);
        Produit p3 = new Produit("EE85", 200.0);
        p3.setCategorie(informatique);
        Produit p4 = new Produit("AB50", 50.0);
        p4.setCategorie(informatique);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);
        produitService.create(p4);

        // Commandes
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 10);
        Commande cmd1 = new Commande(cal.getTime());

        cal.set(2013, Calendar.MARCH, 14);
        Commande cmd2 = new Commande(cal.getTime());

        cal.set(2013, Calendar.MARCH, 18);
        Commande cmd3 = new Commande(cal.getTime());

        commandeService.create(cmd1);
        commandeService.create(cmd2);
        commandeService.create(cmd3);

        // Lignes de commande
        LigneCommande lc1 = new LigneCommande(7, cmd1, p1);
        LigneCommande lc2 = new LigneCommande(14, cmd2, p2);
        LigneCommande lc3 = new LigneCommande(5, cmd2, p3);
        LigneCommande lc4 = new LigneCommande(3, cmd3, p1);
        LigneCommande lc5 = new LigneCommande(2, cmd3, p4);

        ligneCommandeService.create(lc1);
        ligneCommandeService.create(lc2);
        ligneCommandeService.create(lc3);
        ligneCommandeService.create(lc4);
        ligneCommandeService.create(lc5);

        System.out.println("✓ Données créées avec succès!\n");

        // 2. TEST DE TOUTES LES MÉTHODES
        System.out.println("2. TEST DES MÉTHODES");
        System.out.println("==============================");

        // Méthode 1: produitParCategorie
        System.out.println("=== 1. produitParCategorie() ===");
        System.out.println("Produits de la catégorie Électronique:");
        List<Produit> produitsElectronique = produitService.produitParCategorie(electronique);
        produitsElectronique.forEach(p -> System.out.println("- " + p.getReference() + " (" + p.getPrix() + " DH)"));
        System.out.println();

        // Méthode 2: produitParPrix (prix > 100)
        System.out.println("=== 2. produitParPrix() ===");
        System.out.println("Tous les produits avec prix > 100 DH:");
        List<Produit> produitsSuperieurs100 = produitService.produitParPrix(100.0);
        produitsSuperieurs100.forEach(p -> System.out.println("- " + p.getReference() + " (" + p.getPrix() + " DH)"));


        // Méthode 3: produitsEntreDates
        System.out.println("=== 3. produitsEntreDates() ===");
        Calendar debut = Calendar.getInstance();
        debut.set(2013, Calendar.MARCH, 12);
        Calendar fin = Calendar.getInstance();
        fin.set(2013, Calendar.MARCH, 16);

        System.out.println("Produits commandés entre " + dateFormat.format(debut.getTime()) +
                " et " + dateFormat.format(fin.getTime()) + ":");
        List<Produit> produitsEntreDates = produitService.produitsEntreDates(debut.getTime(), fin.getTime());

            produitsEntreDates.forEach(p -> System.out.println("- " + p.getReference() + " (" + p.getPrix() + " DH)"));
         System.out.println();


        // Méthode 4: produitsParCommande (avec affichage formaté)
        System.out.println("=== 4. produitsParCommande() ===");

        System.out.println("--- Commande 1");

        List<Object[]> produitsCommandes = produitService.produitsParCommande(cmd1);

        System.out.println("Commande : " + cmd1.getId() + "\tDate : " + dateFormat.format(cmd1.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\tQuantité");
        System.out.println("---------\t----\t---------");

        for (Object[] result : produitsCommandes) {
            String reference = (String) result[0];
            Double prix = (Double) result[1];
            Integer quantite = (Integer) result[2];

            System.out.println(reference + "\t\t" + prix + " DH\t" + quantite);
        }


    }
}