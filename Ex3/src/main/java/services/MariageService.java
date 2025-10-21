package services;

import dao.GenericDaoImpl;
import entities.Mariage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MariageService extends GenericDaoImpl<Mariage> {
    public MariageService() {
        super(Mariage.class);
    }
}