package model;

public class User {
    private String cardNumber, pin, accountNumber, ebankingPin, name;
    private double balance;

    public User(String cardNumber, String pin, String accountNumber, String ebankingPin, String name, double balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.accountNumber = accountNumber;
        this.ebankingPin = ebankingPin;
        this.name = name;
        this.balance = balance;
    }

    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public String getAccountNumber() { return accountNumber; }
    public String getEbankingPin() { return ebankingPin; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
