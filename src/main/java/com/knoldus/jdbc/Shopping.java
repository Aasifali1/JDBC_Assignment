package com.knoldus.jdbc;

import java.sql.*;

public class Shopping {
    static Connection con = null;
    static PreparedStatement stmt = null;
    static ResultSet rs;

//    Initializing the connection object by calling the getDbConnection() static method of DatabaseConnection class
    public void dbCon(){
            con = DatabaseConnection.getDbConnection();
    }

//    Here we are getting the products table data
    public void getProductsTable(){
        try {
            stmt = con.prepareStatement("select * from products");
            ResultSet rs = stmt.executeQuery();
            System.out.println("========================== Products Table ========================");
            System.out.print("Product ID\t:\t\tPrice\t\t\t:\t Product Name\n");
            while (rs.next()) {
                System.out.println("\t" + rs.getString(1) + "\t\t:\t\t" + rs.getString(3) + "\t\t\t:\t" +
                        rs.getString(2));
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    Getting all the cart items with quantity and total prices
    public void cartItems() {
        try {
            stmt = con.prepareStatement("select products.product_id, products.product_name, " +
                    "cart.quantity,cart.quantity*products.price as Total from products,cart " +
                    "where products.product_id= cart.product_id ");
            ResultSet rs = stmt.executeQuery();
            System.out.println("========================== Cart Items ========================");
            System.out.print("Product ID\t:\tQuantity\t\t:\tTotal\t\t:\t Product Name\n");
            while (rs.next()) {
                System.out.println("\t" + rs.getString(1) + "\t\t:\t\t" + rs.getString(3) + "\t\t\t:\t" +
                        rs.getString(4) + "\t\t:\t"+rs.getString(2));
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    It will print the total amount and the grand total amount
    public void checkOut(){
        try{
            stmt = con.prepareStatement("select products.product_id, " +
                    "cart.quantity,cart.quantity*products.price as Total from products,cart " +
                    "where products.product_id= cart.product_id");
            rs=stmt.executeQuery();
            System.out.println("=============== Checkout ================");
            System.out.print("Product ID\t:\tQuantity\t:\tTotal\n");
            while(rs.next()){
                System.out.println("\t"+rs.getString(1)+"\t\t:\t\t"+rs.getString(2)+"\t\t:\t" +
                        rs.getString(3)+"");
            }
            stmt = con.prepareStatement("select sum(GrandTotal) from( select cart.quantity*products.price " +
                    "as GrandTotal from products,cart where products.product_id=cart.product_id) as total");
            rs = stmt.executeQuery();
            System.out.println("--------------------------------------");
            while (rs.next()){
                System.out.println("Grand Total\t\t\t\t\t:\t"+rs.getString(1)+"\n");
            }
        }catch(SQLException e){ System.out.println(e);}
    }

//    It will print all items which are not sold
    public void notSold(){
        try {
            stmt = con.prepareStatement("select * from products where product_id not in " +
                    "(select product_id from cart);");
            rs= stmt.executeQuery();
            System.out.println("=============== Products not sold ================");
            System.out.print("Product ID\t:\tPrice\t:\tProduct Name\n");
            while(rs.next()){
                System.out.println("\t"+rs.getString(1)+"\t\t:\t"+rs.getString(3)+"\t:\t" +
                        rs.getString(2));
            }
            System.out.println();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

//    It will print the item which as sold the most
    public void mostSold(){
        try{
            stmt = con.prepareStatement("select products.product_id, products.product_name, cart.quantity " +
                    "from products,cart where products.product_id= cart.product_id order by quantity desc;");
            rs=stmt.executeQuery();
            System.out.println("=============== Most Sold Product ================");
            System.out.print("Product ID\t:\tQuantity\t:\tProduct Name\n");
            while(rs.next()){
                System.out.println("\t"+rs.getString(1)+"\t\t:\t\t"+rs.getString(3)+"\t\t:\t" +
                        rs.getString(2)+"\n");
                break;
            }
        }catch(SQLException e){ System.out.println(e);}
    }

    public static void main(String[] args) {
        Shopping shopping =new Shopping();
//        Init the database variables
        shopping.dbCon();

//        Getting cart itmes
        shopping.cartItems();


//        Getting grand total at the time of checkout
        shopping.checkOut();

//        Getting most sold product
        shopping.mostSold();

//        Getting products that are not sold
        shopping.notSold();

//        Printing products table
        shopping.getProductsTable();

        try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
