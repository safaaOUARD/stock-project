package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestStock {

    public static void main(String[] args) throws Exception {

        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        // ── 1. Création des catégories ──────────────────────────────────────
        System.out.println("=== Test 1 : Création des catégories ===");
        Categorie cat1 = new Categorie("PROC", "Processeurs");
        Categorie cat2 = new Categorie("RAM", "Mémoires");
        categorieService.create(cat1);
        categorieService.create(cat2);
        System.out.println("Catégories créées : " + categorieService.findAll());

        // ── 2. Création des produits ────────────────────────────────────────
        System.out.println("\n=== Test 2 : Création des produits ===");
        Produit p1 = new Produit("ES12", 120f, cat1);
        Produit p2 = new Produit("ZR85", 100f, cat1);
        Produit p3 = new Produit("EE85", 200f, cat2);
        Produit p4 = new Produit("AB50",  80f, cat2);
        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);
        produitService.create(p4);
        System.out.println("Produits créés : " + produitService.findAll());

        // ── 3. Produits par catégorie ───────────────────────────────────────
        System.out.println("\n=== Test 3 : Produits par catégorie (Processeurs) ===");
        List<Produit> parCategorie = produitService.findByCategorie(cat1);
        parCategorie.forEach(System.out::println);

        // ── 4. Création des commandes ───────────────────────────────────────
        System.out.println("\n=== Test 4 : Création des commandes ===");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Commande cmd1 = new Commande(sdf.parse("14/03/2013"));
        Commande cmd2 = new Commande(sdf.parse("20/06/2013"));
        commandeService.create(cmd1);
        commandeService.create(cmd2);

        // Lignes de commande
        ligneService.create(new LigneCommandeProduit(7,  p1, cmd1));
        ligneService.create(new LigneCommandeProduit(14, p2, cmd1));
        ligneService.create(new LigneCommandeProduit(5,  p3, cmd1));
        ligneService.create(new LigneCommandeProduit(3,  p4, cmd2));
        ligneService.create(new LigneCommandeProduit(10, p1, cmd2));

        // ── 5. Affichage formaté d'une commande ────────────────────────────
        System.out.println("\n=== Test 5 : Affichage commande 1 ===");
        produitService.afficherCommande(cmd1);

        // ── 6. Produits commandés entre deux dates ──────────────────────────
        System.out.println("\n=== Test 6 : Produits commandés entre 01/01/2013 et 30/06/2013 ===");
        Date debut = sdf.parse("01/01/2013");
        Date fin   = sdf.parse("30/06/2013");
        List<Produit> parDate = produitService.findByDateRange(debut, fin);
        parDate.forEach(System.out::println);

        // ── 7. Produits d'une commande donnée ──────────────────────────────
        System.out.println("\n=== Test 7 : Produits de la commande 2 ===");
        produitService.afficherCommande(cmd2);

        // ── 8. Produits avec prix > 100 DH (requête nommée) ────────────────
        System.out.println("\n=== Test 8 : Produits dont le prix > 100 DH (NamedQuery) ===");
        List<Produit> chers = produitService.findPrixSuperieur100();
        System.out.printf("%-12s %-10s%n", "Référence", "Prix");
        chers.forEach(p -> System.out.printf("%-12s %.0f DH%n", p.getReference(), p.getPrix()));
    }
}
