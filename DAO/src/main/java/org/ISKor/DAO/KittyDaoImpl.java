package org.ISKor.DAO;

import org.ISKor.entities.Kitty;
import org.ISKor.entities.Breed;
import org.ISKor.entities.Color;
import sessionFactory.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class KittyDaoImpl implements KittyDao{
    @Override
    public void add(Kitty kitty) {
        Session session = HibernateUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(kitty);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Kitty kitty) {
        Session session = HibernateUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(kitty);
        transaction.commit();
        session.close();
    }

    @Override
    public void remove(Kitty kitty) {
        Session session = HibernateUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(kitty);
        transaction.commit();
        session.close();
    }

    @Override
    public Kitty getById(int id) {
        Session session = HibernateUtil.getSession().openSession();
        Kitty kitty = session.get(Kitty.class, id);
        session.close();
        return kitty;
    }

    @Override
    public List<Kitty> getAll() {
        Session session = HibernateUtil.getSession().openSession();
        List<Kitty> kitties = (List<Kitty>) session.createQuery("FROM Kitty").list();
        session.close();
        return kitties;
    }

    @Override
    public List<Kitty> getAllFriends(int id) {
        Session session = HibernateUtil.getSession().openSession();
        Query query = session.createQuery("SELECT k.friends FROM Kitty k WHERE k.id=:id");
        query.setParameter("id", id);
        List<Kitty> friends = (List<Kitty>) query.getResultList();
        session.close();
        return friends;
    }

    @Override
    public List<Kitty> getByBreed(Breed breed) {
        Session session = HibernateUtil.getSession().openSession();
        Query query = session.createQuery("FROM Kitty k WHERE k.breed=:breed");
        query.setParameter("breed", breed);
        List<Kitty> kitties = (List<Kitty>) query.getResultList();
        session.close();
        return kitties;
    }

    @Override
    public List<Kitty> getByColor(Color color) {
        Session session = HibernateUtil.getSession().openSession();
        Query query = session.createQuery("FROM Kitty k WHERE k.color=:color");
        query.setParameter("color", color);
        List<Kitty> kitties = (List<Kitty>) query.getResultList();
        session.close();
        return kitties;
    }
}