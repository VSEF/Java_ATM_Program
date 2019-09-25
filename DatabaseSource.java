package source;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSource {

    private static final String RED_COLOR = "\u001B[31m";
    private static final String BLUE_COLOR = "\u001B[34m";
    private static final String DB_NAME = "tundraBank.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:C:/Users/User/Desktop/ud282-master/ud282-master/TundraBank/" + DB_NAME;

    private  Connection connection;

    public boolean connectDB(){

        try{
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        }catch (SQLException e){
            System.out.println(RED_COLOR + "Can't connect to database: " + e.getMessage());
            return false;
        }

    }

    public void closeDB(){
        try{
            if(connection!=null){
                connection.close();
                System.out.println(BLUE_COLOR+"Thanks for being with us!\nHave a nice day!");
            }
        }catch (SQLException e){
            System.out.println(RED_COLOR+"Couldn't close connection with DB " + e.getMessage());
        }
    }

    public void createTable() {

        try (Statement statement = connection.createStatement()) {
        //    statement.execute("DROP TABLE accounts");
            statement.execute("CREATE TABLE IF NOT EXISTS accounts(name text, number integer, balance double, password integer, email text)");
        //    System.out.println(BLUE_COLOR+"The table was created successfully.");

        }catch (SQLException e){
            System.out.println(RED_COLOR+"Couldn't create table " + e.getMessage());

        }
    }

    public void addNewUser(String userNname, int number, double balance, int password, String userEmail){

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE password=" + "'" + password + "'")){

            if(resultSet.next()){
                System.out.println(RED_COLOR+"You can't use provided password. Please use another one.");
            }else {

                statement.execute("INSERT INTO accounts" + " VALUES('" + userNname + "'," + number + "," + balance + "," + password + ",'" +userEmail + "'" + ")");
                System.out.println(BLUE_COLOR + userNname + ", your account was created successfully with following credentials: " +
                        "name: " + userNname + ", account number: " + number + ", balance: $ " + balance + ", password: " + password + ", email: " + userEmail + ".");
            }
        }catch (SQLException e){
            System.out.println(RED_COLOR+"Couldn't add new User " + e.getMessage());
            //  e.printStackTrace();
        }

    }

    public void getAccountNumber(int password) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE password=" + "'" + password + "'")) {
            if(resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    System.out.println(BLUE_COLOR + "Your account number is " + resultSet.getInt(2));
                }
            }else{
                System.out.println(RED_COLOR+"Not found data. Please check PASSWORD and TRY again...");
            }

        }catch (SQLException e){
            System.out.println(RED_COLOR+"Query failed: " + e.getMessage());
        }
    }

    public void getLastBalance(int password){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE password='" + password + "'")){
            if(resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    System.out.println(BLUE_COLOR + "Your balance is $ " + resultSet.getDouble(3));
                }
            }else{
                System.out.println(RED_COLOR+"Not found data. Please check PASSWORD and TRY again...");
            }
        }catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public String getEmail(int password){
        String email=null;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE password='" + password + "'")){
            if(resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    email = resultSet.getString(5);
                }
            }else{
                return email= "not found";
            }
        }catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return email= "not found";
        }
        return email;
    }

    public double getDataBalance(int password){
        double balance=0.00;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE password='" + password + "'")){
            while (resultSet.next()) {
                balance = resultSet.getDouble(3);
            }
        }catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return 0.00;
        }
        return balance;
    }

    public void depositMoney(int password, double amount){
        double balance;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE password='" + password + "'")){
            if(resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    balance = resultSet.getDouble(3);
                    balance += amount;
                    statement.execute("UPDATE accounts SET balance='" + balance + "' WHERE password='" + password + "'");
                }
            }else{
                System.out.println(RED_COLOR+"Not found data. Please check PASSWORD and TRY again...");
            }
        }catch (SQLException e){
            System.out.println(RED_COLOR+"Query failed: " + e.getMessage());
        }
    }

    public void withdrawalMoney(int password, double amount){
        double balance;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE password='" + password + "'")){
            if(resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    balance = resultSet.getDouble(3);
                    if (amount <= balance) {
                        balance -= amount;
                        statement.execute("UPDATE accounts SET balance='" + balance + "' WHERE password='" + password + "'");
                        System.out.println(BLUE_COLOR + "You have been successfully withdrawn $ " + amount);
                    } else {
                        System.out.println(RED_COLOR + "Insufficient funds, your last balance is $ " + balance);
                        break;
                    }
                }
            }else{
                System.out.println(RED_COLOR+"Not found data. Please check PASSWORD and TRY again...");
            }
        }catch (SQLException e){
            System.out.println(RED_COLOR+"Query failed: " + e.getMessage());
        }
    }
}
