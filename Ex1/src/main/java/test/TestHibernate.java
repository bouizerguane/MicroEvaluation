package test;

import config.AppConfig;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

public class TestHibernate {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        if (sessionFactory != null)
            System.out.println("SessionFactory configuré avec succès!");
        else
            System.out.println("SessionFactory non configuré!");

        HibernateTransactionManager txManager = context.getBean(HibernateTransactionManager.class);
        if (txManager != null)
            System.out.println("Transaction configurée avec succès!");
        else
            System.out.println("Transaction non configurée!");
    }
}