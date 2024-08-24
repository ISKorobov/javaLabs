package sessionFactory;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory instance = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSession() {
        return instance;
    }

    public static void close() {
        instance.close();
    }
}
