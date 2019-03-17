package ru.otus.java.hw07.atm;

public class User {

    private String firstName;
    private String lastName;
    private String cardNo;
    private String pin;
    private CashAmount balance;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    CashAmount getBalance() {
        return balance;
    }

    public void setBalance(CashAmount balance) {
        this.balance = balance;
    }

    void withdrawCash(int value) throws InsufficientFundsException {
        if (this.getBalance().getValue() < value) {
            throw new InsufficientFundsException("Cannot withdraw " + value + "!");
        }
        this.setBalance(new CashAmount(this.balance.getValue() - value));
    }

    void depositCash(int value) {
        this.setBalance(new CashAmount(this.balance.getValue() + value));
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                '}';
    }
}
