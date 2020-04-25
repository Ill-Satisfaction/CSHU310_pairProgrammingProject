import java.io.*;
import java.sql.*;
import java.util.*;

public class Project{
    public static void main(String[] args) {
        if (args.length >= 1){
            switch (args[0]){
                case "CreateItem":
                    if (args.length == 4){
                        createItem(args[1], args[2], args[3]);
                    } else {
                        printUsage();
                    }
                    break;

                case "CreatePurchase":
                    if (args.length == 3){
                        createPurchase(args[1], args[2]);
                    } else {
                        printUsage();
                    }
                    break;

                case "CreateShipment":
                    if (args.length == 4){
                        createShipment(args[1], args[2], args[3]);
                    } else {
                        printUsage();
                    }
                    break;

                case "GetItems":
                    if (args.length == 2){
                        getItems(args[1]);
                    } else {
                        printUsage();
                    }
                    break;

                case "GetShipments":
                    if (args.length == 2){
                        getShipments(args[1]);
                    } else {
                        printUsage();
                    }
                    break;

                case "GetPurchases":
                    if (args.length == 2){
                        getPurchases(args[1]);
                    } else {
                        printUsage();
                    }
                    break;

                case "ItemsAvailable":
                    if (args.length == 2){
                        itemsAvaliable(args[1]);
                    } else {
                        printUsage();
                    }
                    break;

                case "UpdateItem":
                    if (args.length == 3){
                        updateItem(args[1],args[2]);
                    } else {
                        printUsage();
                    }
                    break;

                case "DeleteItem":
                    if (args.length == 2){
                        deleteItem(args[1]);
                    } else {
                        printUsage();
                    }
                    break;

                case "DeleteShipment":
                    if (args.length == 2){
                        deleteShipment(args[1]);
                    } else {
                        printUsage();
                    }
                    break;

                case "DeletePurchase":
                    if (args.length == 2){
                        deletePurchase(args[1]);
                    } else {
                        printUsage();
                    }
                    break;

                default:
                    printUsage();

            }
        } else {
            printUsage();
        }
    }

    private static void createItem(String itemCode, String itemDescription, String price){
        //TODO
    }

    private static void createPurchase(String itemCode, String purchaseQuantity){
        //TODO
    }

    private static void createShipment(String itemCode, String shipmentQuantity, String shipmentDate){
        //TODO
    }

    private static String getItems(String itemCode){
        //TODO
        if (itemCode.equals("%")){

        } else {

        }

        return "";
    }

    private static String getShipments(String itemCode){
        //TODO
        if (itemCode.equals("%")){

        } else {
            
        }

        return "";
    }

    private static String getPurchases(String itemCode){
        //TODO
        if (itemCode.equals("%")){

        } else {
            
        }

        return "";
    }

    private static String itemsAvaliable(String itemCode){
        //TODO
        if (itemCode.equals("%")){

        } else {
            
        }

        return "";
    }

    private static void updateItem(String itemCode, String price){
        //TODO
    }

    private static void deleteItem(String itemCode){
        //TODO
    }

    private static void deleteShipment(String itemCode){
        //TODO
    }

    private static void deletePurchase(String itemCode){
        //TODO
    }

    private static void printUsage(){
        System.out.println("Invalid arguments. Usage:");
        System.out.println("\tProject CreateItem <itemCode> <itemDescription> <price>");
        System.out.println("\tProject CreatePurchase <itemCode> <purchaseQuantity>");
        System.out.println("\tProject CreateShipment <itemCode> <shipmentQuantity> <shipmentDate>");
        System.out.println("\tProject GetItems <itemCode>");
        System.out.println("\tProject GetShipments <itemCode>");
        System.out.println("\tProject GetPurchases <itemCode>");
        System.out.println("\tProject ItemsAvailable <itemCode>");
        System.out.println("\tProject UpdateItem <itemCode> <price>");
        System.out.println("\tProject DeleteItem <itemCode>");
        System.out.println("\tProject DeleteShipment <itemCode>");
        System.out.println("\tProject DeletePurchase <itemCode>");
        System.out.println("\titemCode, purchaseQuantity, and shipmentQuantity must be an integer.");
        System.out.println("\tshipmentDate must be in the format yyyy-mm-dd. price must be a double.");
        System.exit(1);
    }
}