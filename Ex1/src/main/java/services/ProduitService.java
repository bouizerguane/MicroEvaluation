package services;

import dao.GenericDaoImpl;
import entities.Categorie;
import entities.Commande;
import entities.Produit;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProduitService extends GenericDaoImpl<Produit> {

    @Autowired
    private SessionFactory sessionFactory;

    public ProduitService() {
        super(Produit.class);
    }

    public List<Produit> produitParCategorie(Categorie cat) {
        return this.sessionFactory.getCurrentSession().createNamedQuery("produitParCategorie",
                Produit.class).setParameter("cat", cat).getResultList();
    }

    public List<Produit> produitParPrix(double prix) {
        return this.sessionFactory.getCurrentSession().createNamedQuery("produitParPrix",
                Produit.class).setParameter("prix", prix).getResultList();
    }

    public List<Produit> produitsEntreDates(Date d1, Date d2) {
        return this.sessionFactory.getCurrentSession().createNamedQuery("produitsEntreDates",
                Produit.class).setParameter("d1", d1).setParameter("d2", d2).getResultList();

    }

    public List<Object[]> produitsParCommande(Commande commande) {
        return this.sessionFactory.getCurrentSession()
                .createNamedQuery("produitsParCommande", Object[].class)
                .setParameter("comm", commande)
                .getResultList();
    }


}
