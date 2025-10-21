package services;

import dao.GenericDaoImpl;
import entities.Categorie;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategorieService extends GenericDaoImpl<Categorie> {

    public CategorieService() {
        super(Categorie.class);
    }
}
