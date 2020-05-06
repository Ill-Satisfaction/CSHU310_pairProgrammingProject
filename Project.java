import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class Project{
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
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
                case "GetShipments":
                case "GetPurchases":
                    if (args.length == 2){
                        readFromTable(args);
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
                case "DeleteShipment":
                case "DeletePurchase":
                    if (args.length == 2){
                        purgeFromTable(args);
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






    // METHODS

    private static void readFromTable(String[] a) {

        String url  = "jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC"
        String usr  = "msandbox"
        String pass = "whitemocha"

        Boolean connectionValid = false;
        Connection con = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;

        try {
            // get connection
            con = DriverManager.getConnection(url, usr, pass);
            con.setAutoCommit(false);

            stmt = con.prepareStatement("Call ?(?);");
            stmt.setString(1, a[0].equals("GetItems") ? "getItems" : a[0].equals("GetShipments") ? "getShipments" : a[0].equals("GetPurchases") ? "getPurchases" : "checkDeleteItem");
            stmt.setString(2, a[1]);

            //execute
            resultSet = stmt.executeQuery();

            //print to stdout
            int colsNum = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i=1; i<=colsNum; i++) {
                    if (i>1) System.out.print(", ");
                    String colsVal = resultSet.getString(i);
                    System.out.print(colsVal + " " + rsmd.getColumnName(i));
                }
                System.out.println(" ");
            }

            //close statement
            if (stmt != null) stmt.close();
            stmt = null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            con.rollback();
            connectionValid = false;

        } finally {
            // close connection
            con.setAutoCommit(true);
            con.close();
        }
    }

    private static void purgeFromTable(String[] a) {

        String url  = "jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC"
        String usr  = "msandbox"
        String pass = "whitemocha"

        Boolean connectionValid = false;
        Connection con = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;

        try {
            // get connection
            con = DriverManager.getConnection(url, usr, pass);
            con.setAutoCommit(false);

            if (a[0].equals("DeleteItem")) readFromTable(new String[]{"", a[1]});
            stmt = con.prepareStatement("Call ?(?);");
            stmt.setString(1, a[0].equals("DeleteItem") ? "deleteItem" : a[0].equals("DeleteShipment") ? "deleteShipment" : "deletePurchase");
            stmt.setString(2, a[1]);

            //execure
            resultSet = stmt.executeQuery();

            //print to stdout
            System.out.println(res + " records deleted.");

            //close statement
            if (stmt != null) stmt.close();
            stmt = null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            con.rollback();
            connectionValid = false;

        } finally {
            // close connection
            con.setAutoCommit(true);
            con.close();
        }
    }

    private static void createItem(String itemCode, String itemDescription, String price) throws SQLException, ClassNotFoundException {
        Double dPrice = 0.00;

        try {
            dPrice = Double.parseDouble(price);
        } catch (Exception e) {
            printUsage();
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", "msandbox", "whitemocha");
            con.setAutoCommit(false);

            stmt = con.prepareStatement("Call createItem(?, ?, ?)");
            stmt.setString(1, itemCode);
            stmt.setString(2, itemDescription);
            stmt.setDouble(3, dPrice);

            int res = stmt.executeUpdate();
            System.out.println(res + " records inserted.");
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

    private static void createPurchase(String itemCode, String purchaseQuantity) throws SQLException, ClassNotFoundException {
        int iQuantity = 0;

        try {
            iQuantity = Integer.parseInt(purchaseQuantity);
        } catch (Exception e) {
            printUsage();
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", "msandbox",
            "whitemocha");

            con.setAutoCommit(false);
            stmt = con.prepareStatement("Call createPurchase(?, ?)");
            stmt.setString(1, itemCode);
            stmt.setInt(2, iQuantity);

            int res = stmt.executeUpdate();
            System.out.println(res + " records inserted.");
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
    
    private static void createShipment(String itemCode, String shipmentQuantity, String shipmentDate) throws SQLException {
        int iQuantity = 0;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        java.sql.Date s = null;

        try {
            d = f.parse(shipmentDate);
            s = new java.sql.Date(d.getTime());
            iQuantity = Integer.parseInt(shipmentQuantity);
        } catch (Exception e) {
            printUsage();
        }
        
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", "msandbox",
            "whitemocha");

            con.setAutoCommit(false);
            stmt = con.prepareStatement("Call createShipment(?, ?, ?)");
            stmt.setString(1, itemCode);
            stmt.setInt(2, iQuantity);
            stmt.setDate(3, s);

            int res = stmt.executeUpdate();
            System.out.println(res + " records inserted.");
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

    private static void itemsAvaliable(String itemCode) throws SQLException {
        Connection con = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        
        if (itemCode.equals("%")){
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", "msandbox",
                "whitemocha");

                con.setAutoCommit(false);
                stmt = con.prepareStatement("CALL itemsAvailableAll()");
                resultSet = stmt.executeQuery();

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
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:56115/final?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", "msandbox",
                "whitemocha");

                con.setAutoCommit(false);
                stmt = con.prepareStatement("CALL itemsAvailableOne(?)");
                stmt.setString(1, itemCode);
                
                resultSet = stmt.executeQuery();

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
        }
    }

    private static void updateItem(String itemCode, String price){
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