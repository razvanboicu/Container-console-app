import com.sun.security.jgss.GSSUtil;
import logic.ContainerLogic;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Objects;

public class ClientMenu {
    static public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String option = null;
        while (!Objects.equals(option, "0")) {
            printOptionList();
            option = reader.readLine();
            switch (option) {
                case "0":
                    System.out.println("Have a nice day!");
                    break;
                case "1":
                    addItem();
                    break;
                case "2":
                    searchItem();
                    break;
                default:
                    printOptionList();
            }
        }
    }

    static private void searchItem() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("[SEARCH] Item name: ");
            String itemName = reader.readLine();
            System.out.print("[SEARCH] Item details: ");
            String itemDetails = reader.readLine();
            if (ContainerLogic.searchIfExistsItemByNameDetails(itemName, itemDetails)) {
                System.out.println("Do you want the item? ");
                String answer = null;
                while (!Objects.equals(answer, "EXIT")) {
                    System.out.print("Choose between [YES] / [NO]: ");
                    answer = reader.readLine();
                    switch (answer) {
                        case "YES":
                            ContainerLogic.returnItemWithDetailsById(ContainerLogic.getIdFromSearchItemWithNameDetails(itemName, itemDetails));
                            answer = "EXIT";
                            break;
                        case "NO":
                            ContainerLogic.updateAccess(ContainerLogic.getIdFromSearchItemWithNameDetails(itemName, itemDetails));
                            answer = "EXIT";
                            break;
                        default:
                            System.out.println("Choose between [YES] / [NO]");
                    }
                }
            } else {
                System.out.println("The item doesn't exists! ");
                addItem();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private void addItem() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("[NEW ITEM] Name: ");
            String itemName = reader.readLine();
            System.out.print("[NEW ITEM] Details: ");
            String detailsItem = reader.readLine();
            if (!ContainerLogic.isFull()) {
                ContainerLogic.addNewItem(itemName, detailsItem);
            } else {
                if (ContainerLogic.checkIfAreMoreItemsWithMin()) {
                    //pe cel mai din trecut
                    ContainerLogic.returnItemWithDetailsById(ContainerLogic.getItemWithMinimumAndLastAccesesdTime());
                    ContainerLogic.addNewItem(itemName, detailsItem);
                } else {
                    //elimin pe cel cu nr acces minim si il pun pe cel nou
                    ContainerLogic.returnItemWithDetailsById(ContainerLogic.getIdItemMinimumNumberAccess());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private void printOptionList() {
        System.out.println("[ Option list ]");
        System.out.println("0 = EXIT");
        System.out.println("1 = Add item in container");
        System.out.println("2 = Search Item ");
        System.out.print("Your option: ");
    }

    static private void newItemFromAdmin() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("[NEW ITEM] Name: ");
            String itemName = reader.readLine();
            System.out.print("[NEW ITEM] Details: ");
            String detailsItem = reader.readLine();
            ContainerLogic.addNewItem(itemName, detailsItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
