package DAO;

import entities.Kitty;
import entities.Owner;
import sessionFactory.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OwnerDaoImpl implements OwnerDao {
    @Override
    public void add(Owner owner) {
        Session session = HibernateUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Owner owner) {
        Session session = HibernateUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void remove(Owner owner) {
        Session session = HibernateUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public Owner getById(int id) {
        Session session = HibernateUtil.getSession().openSession();
        Owner owner = session.get(Owner.class, id);
        session.close();
        return owner;
    }

    @Override
    public List<Owner> getAll() {
        Session session = HibernateUtil.getSession().openSession();
        List<Owner> owners = session.createQuery("FROM Owner").list();
        session.close();
        return owners;
    }

    @Override
    public List<Kitty> getAllKitties(int id) {
        Session session = HibernateUtil.getSession().openSession();
        Query query = session.createQuery("SELECT o.kitties FROM Owner o WHERE o.id=:id");
        query.setParameter("id", id);
        List<Kitty> kitties = (List<Kitty>) query.getResultList();
        session.close();
        return kitties;
    }
}