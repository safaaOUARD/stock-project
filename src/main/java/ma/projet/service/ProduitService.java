package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Produit findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Produit.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Produit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Produit").list();
        } finally {
            session.close();
        }
    }

    /** Afficher la liste des produits par catégorie */
    @SuppressWarnings("unchecked")
    public List<Produit> findByCategorie(Categorie categorie) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Produit> query = session.createQuery(
                "FROM Produit p WHERE p.categorie = :cat");
            query.setParameter("cat", categorie);
            return query.list();
        } finally {
            session.close();
        }
    }

    /** Afficher les produits commandés entre deux dates */
    @SuppressWarnings("unchecked")
    public List<Produit> findByDateRange(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Produit> query = session.createQuery(
                "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
                "WHERE lcp.commande.date BETWEEN :debut AND :fin");
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            return query.list();
        } finally {
            session.close();
        }
    }

    /** Afficher les produits commandés dans une commande donnée (avec affichage formaté) */
    @SuppressWarnings("unchecked")
    public List<LigneCommandeProduit> findByCommande(Commande commande) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<LigneCommandeProduit> query = session.createQuery(
                "FROM LigneCommandeProduit lcp WHERE lcp.commande = :cmd");
            query.setParameter("cmd", commande);
            return query.list();
        } finally {
            session.close();
        }
    }

    /** Afficher le détail d'une commande (format attendu dans l'énoncé) */
    public void afficherCommande(Commande commande) {
        List<LigneCommandeProduit> lignes = findByCommande(commande);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", java.util.Locale.FRENCH);
        System.out.println("Commande : " + commande.getId() + "\tDate : " + sdf.format(commande.getDate()));
        System.out.println("Liste des produits :");
        System.out.printf("%-12s %-10s %s%n", "Référence", "Prix", "Quantité");
        for (LigneCommandeProduit lcp : lignes) {
            System.out.printf("%-12s %-10s %d%n",
                lcp.getProduit().getReference(),
                (int) lcp.getProduit().getPrix() + " DH",
                lcp.getQuantite());
        }
    }

    /** Afficher les produits dont le prix > 100 DH via requête nommée */
    @SuppressWarnings("unchecked")
    public List<Produit> findPrixSuperieur100() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createNamedQuery("Produit.prixSuperieur100", Produit.class).list();
        } finally {
            session.close();
        }
    }
}
