package logic;

import database.dao.ItemDao;
import database.model.ItemsEntity;

import java.sql.Time;
import java.time.LocalTime;

public class ContainerLogic {
    private static ItemDao itemDao = new ItemDao();

    static public void printContainerItems() {
        System.out.println("Container contains: ");
        for (ItemsEntity itemsEntity : itemDao.getAll()) {
            System.out.println("{" + itemsEntity.getId() + "} [" + itemsEntity.getName() + "] [" + itemsEntity.getDetails() + "] NrOfAcceses[" + itemsEntity.getNrAccesses() + "] last accessed time [" + itemsEntity.getLastAccessed() + "]");
        }
    }

    static public long getIdItemMinimumNumberAccess() {
        return itemDao.minimumOfNumberAcceses().getId();
    }

    static public boolean searchIfExistsItemByNameDetails(String name, String details) {
        if (itemDao.searchByNameAndDetails(name, details).isEmpty())
            return false;
        return true;
    }

    static public long getIdFromSearchItemWithNameDetails(String name, String details) {
        return itemDao.searchByNameAndDetails(name, details).get(0).getId();
    }

    static public void returnItemWithDetailsById(long id) {
        try {
            System.out.println("{" + itemDao.get(id).getId() + "} [" + itemDao.get(id).getName() + "] [" + itemDao.get(id).getDetails() + "] NrOfAcceses[" + itemDao.get(id).getNrAccesses() + "] last accessed time [" + itemDao.get(id).getLastAccessed() + "]");
            itemDao.delete(id);
            System.out.println("Item successfully returned and removed from container");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while returning and removing!");
        }

    }

    static public int getItemMinimumAccess() {
        return itemDao.minimumOfNumberAcceses().getNrAccesses();
    }

    static public void updateAccess(long id) {
        try {
            itemDao.updateAccessNumberFromItemId(id);
            itemDao.updateTimeAccessFromItemId(id);
            System.out.println("Accessed number and time successfully updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public boolean checkIfAreMoreItemsWithMin() {
        if (itemDao.numberOfItemsWithMinimumNrAcc(getItemMinimumAccess()) > 1)
            return true;
        return false;
    }

    static public long getItemWithMinimumAndLastAccesesdTime() {
        return itemDao.lastAccesedItemWithIdOfMin(getIdItemMinimumNumberAccess()).getId();
    }

    static public void addNewItem(String name, String details) {
        try {
            ItemsEntity itemsEntity = new ItemsEntity();
            itemsEntity.setName(name);
            itemsEntity.setDetails(details);
            itemsEntity.setLastAccessed(Time.valueOf(LocalTime.now()));
            itemDao.create(itemsEntity);
            System.out.println("Success");
        } catch (Exception e) {
            System.out.println("Error while creating a new item");
            e.printStackTrace();
        }
    }

    static public boolean isFull() {
        if (itemDao.getAll().size() > 6)
            return true;
        return false;
    }
}
