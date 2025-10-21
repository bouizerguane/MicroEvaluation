package services;

import dao.GenericDaoImpl;
import entities.Personne;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonneService extends GenericDaoImpl<Personne> {
    public PersonneService() {
        super(Personne.class);
    }
}