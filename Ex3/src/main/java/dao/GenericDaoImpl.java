package dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GenericDaoImpl<T> implements IDao<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    private Class<T> type;

    public GenericDaoImpl(Class<T> type) {
        this.type = type;
    }


    @Override
    @Transactional
    public boolean create(T o) {
        sessionFactory.getCurrentSession().save(o);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(T o) {
        sessionFactory.getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional
    public boolean update(T o) {
        sessionFactory.getCurrentSession().update(o);
        return true;
    }

    @Override
    @Transactional
    public T findById(int id) {
        return sessionFactory.getCurrentSession().get(type, id);
    }

    @Override
    @Transactional
    public List<T> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from "+type.getName()).list();
    }
}
