package source;

public class BankAccount {
    private int id;
    private String name;
    private double balance;
    private int password;
    private int number;

    public BankAccount(int id, String name, double balance, int password, int number) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.password = password;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void depositMoney(double amount){
        this.balance+=amount;
        System.out.println("The depositing money was proceeded successfully. Your last balance is $ " + this.balance);
    }

    public void waithdrawalMoney(double amount){
        if(amount>this.balance){
            System.out.println("Can't proceed it. Your balance is $ " + this.balance);
        }else {
            this.balance -= amount;
            System.out.println("The depositing money was proceeded successfully. Last balance is $ " + this.balance);
        }
    }
}
