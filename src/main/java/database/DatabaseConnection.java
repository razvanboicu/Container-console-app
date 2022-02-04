package database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class DatabaseConnection {
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public DatabaseConnection() {
        initTransaction();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public boolean executeTransaction(Consumer<EntityManager> action) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            action.accept(entityManager);
            entityTransaction.commit();
        } catch (Exception e) {
            System.out.println("Transaction error: " + e.getLocalizedMessage());
            entityTransaction.rollback();
            return false;
        }
        return true;
    }

    public void initTransaction() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("ContainerPersistence");
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            System.out.println("Error at initializing DatabaseManager: " + e.getMessage());
        }
    }
}
