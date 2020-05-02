import java.io.*;
import java.sql.*;
import java.util.*;

public class Project{
    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
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
        try {
            Integer.parseInt(itemCode);
            Double.parseDouble(price);
        } catch (Exception e) {
            printUsage();
        }

        String query = "CALL createItem ('" + itemCode + "', '" + itemDescription + "', '" + price + "')";

        

        try {
            modify(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //me
    private static void createPurchase(String itemCode, String purchaseQuantity){
        try {
            Integer.parseInt(itemCode);
            Integer.parseInt(purchaseQuantity);
        } catch (Exception e) {
            printUsage();
        }

        String query = "INSERT INTO Purchase(ItemCode, PurchaseQuantity) " +
        "VALUES ('" + itemCode + "', '" + purchaseQuantity + "')";

        try {
            modify(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //me
    private static void createShipment(String itemCode, String shipmentQuantity, String shipmentDate){
        try {
            Integer.parseInt(itemCode);
            Integer.parseInt(shipmentQuantity);

        } catch (Exception e) {
            printUsage();
        }

        // TODO: verify date format?

        String query = "INSERT INTO Shipment(ItemCode, ShipmentQuantity, ShipmentDate) " +
        "VALUES ('" + itemCode + "', '" + shipmentQuantity + "', '" + shipmentDate + "')";

        try {
            modify(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getItems(String itemCode)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        if (itemCode.equals("%")){
            read("SELECT * FROM Item;");
        } else {
            read("SELECT * FROM Item WHERE ItemCode = '" + itemCode + "';");
        }
    }

    private static String getShipments(String itemCode)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //TODO
        if (itemCode.equals("%")){
            read("SELECT * FROM Shipment;");
        } else {
            read("SELECT * FROM Shipment WHERE ItemID = '" + itemCode + "';");
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
    // me
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

    private static void deleteItem(String itemCode) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        read("CALL checkDeleteItem ('" + itemCode + "')");
        modify("CALL deleteItem ('" + itemCode + "')");
    }

    private static void deleteShipment(String itemCode){
        //TODO
    }

    private static void deletePurchase(String itemCode){
        //TODO
    }

    private static void modify(String query) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", "msandbox",
            "whitemocha");

            con.setAutoCommit(false);
            stmt = con.createStatement();

            int res = stmt.executeUpdate(query);
            con.commit();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            con.rollback();
        } finally {
            if (stmt != null){
                stmt.close();
            }

            con.setAutoCommit(true);
            con.close();
        }
    }

    private static ResultSet read(String query) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Connection con = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", "msandbox",
            "whitemocha");

            con.setAutoCommit(false);
            stmt = con.createStatement();
            
            resultSet = stmt.executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // Prints to stdout
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println(" ");
            }
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            con.rollback();
        } finally {
            if (stmt != null){
                stmt.close();
            }

            con.setAutoCommit(true);
            con.close();
        }
        return resultSet;
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