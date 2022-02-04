package database.dao;

import database.DatabaseConnection;
import database.model.ItemsEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class ItemDao implements IDao<ItemsEntity> {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    public ItemsEntity get(long id) {
        return connection.getEntityManager().find(ItemsEntity.class, id);
    }

    @Override
    public List<ItemsEntity> getAll() {
        TypedQuery<ItemsEntity> query = connection.getEntityManager().createQuery("SELECT a FROM ItemsEntity a", ItemsEntity.class);
        return query.getResultList();
    }

    public List<ItemsEntity> searchByNameAndDetails(String name ,String details){
        TypedQuery<ItemsEntity> query = connection.getEntityManager().createQuery("SELECT a FROM ItemsEntity a WHERE a.name='"+name+"' AND a.details ='"+details+"'", ItemsEntity.class);
        return query.getResultList();
    }

    @Override
    public void create(ItemsEntity itemsEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(itemsEntity));
    }

    public ItemsEntity minimumOfNumberAcceses(){
        TypedQuery<ItemsEntity> query = connection.getEntityManager().createQuery("SELECT a FROM ItemsEntity a ORDER BY a.nrAccesses desc ", ItemsEntity.class);
        return query.getResultList().get(0);
    }

    public int numberOfItemsWithMinimumNrAcc(int minimumAccesses){
        TypedQuery<ItemsEntity> query = connection.getEntityManager().createQuery("SELECT a FROM ItemsEntity a WHERE a.nrAccesses = '"+minimumAccesses+"'", ItemsEntity.class);
        return query.getResultList().size();
    }

    public void delete(long id){
        connection.executeTransaction(entityManager -> {
           Query query = connection.getEntityManager().createQuery("DELETE FROM ItemsEntity a WHERE a.id ='"+id+"'");
           query.executeUpdate();
        });
    }

    public ItemsEntity lastAccesedItemWithIdOfMin(long id){
        TypedQuery<ItemsEntity> query = connection.getEntityManager().createQuery("SELECT a FROM ItemsEntity a WHERE a.id = '"+id+"'ORDER BY a.nrAccesses desc ", ItemsEntity.class);
        return query.getResultList().get(0);
    }

    public void updateAccessNumberFromItemId(long id){
        int newAccessNumber = get(id).getNrAccesses() + 1;
        connection.executeTransaction(entityManager -> {
            Query query = connection.getEntityManager().createQuery("UPDATE ItemsEntity SET nrAccesses ='"+newAccessNumber+"' WHERE id='"+id+"'");
            query.executeUpdate();
        });
    }

    public void updateTimeAccessFromItemId(long id){
        connection.executeTransaction(entityManager -> {
            Query query = connection.getEntityManager().createQuery("UPDATE ItemsEntity SET lastAccessed ='"+ Time.valueOf(LocalTime.now())+"' WHERE id='"+id+"'");
            query.executeUpdate();
        });
    }



}
