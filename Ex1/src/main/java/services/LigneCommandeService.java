package services;

import dao.GenericDaoImpl;
import entities.LigneCommande;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class LigneCommandeService extends GenericDaoImpl<LigneCommande> {

    public LigneCommandeService() {
        super(LigneCommande.class);
    }
}
