package services;

import dao.GenericDaoImpl;
import entities.Commande;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class CommandeService extends GenericDaoImpl<Commande> {

    public CommandeService() {
        super(Commande.class);
    }
}
