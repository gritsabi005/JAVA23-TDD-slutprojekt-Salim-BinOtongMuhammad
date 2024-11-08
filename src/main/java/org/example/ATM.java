package org.example;

public class ATM {
    private Bank bank;
    private User currentUser;

    public ATM (Bank bank){
        this.bank = bank;
    }

    public boolean insertCard(String userId) {
        User user = bank.getUserById(userId);
        if(user != null){
            if(!user.isLocked()){
            currentUser = user;
            return true;
            } else {
                System.out.println("Oopsie! You maxxed out your pin three times. " +
                        "Contact your issuance bank, Xoxo");
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean enterPin(String pin) {

        int maxNumberOfTries = 3;
        if (currentUser != null) {
            if (!currentUser.isLocked()) {
                if (pin.equals(currentUser.getPin())) {
                    return true;
                } else {
                    currentUser.incrementFailedAttempts();
                    if (currentUser.getFailedAttempts() >= maxNumberOfTries) {
                        currentUser.lockCard();
                        System.out.println("Oopsie! You maxxed out your pin three times. " +
                                "Contact your issuance bank, Xoxo");
                    }
                    return false;
                }
            } else {
                System.out.println("Your card is locked. Contact your bank");
                return false;
            }
        } else {
            return false;
        }
    }

    public double checkBalance() {
        if (currentUser != null) {
            if (!currentUser.isLocked()) {
                return currentUser.getBalance();
            } else {
                System.out.println("Your card is locked. Contact your bank");
            }
        }
        return 0.0;
    }


    public void deposit(double amount) {
        if(currentUser != null){
            if (!currentUser.isLocked()) {
                    currentUser.deposit(amount);
            } else {
                System.out.println("Your card is locked. Contact your bank");
            }
        }
    }

    public boolean withdraw(double amount) {
        if(currentUser != null){
            if (!currentUser.isLocked()) {
                if (amount <= currentUser.getBalance()){
                    double newSaldo = currentUser.getBalance() - amount;
                    currentUser.setBalance(newSaldo);
                    return true;
                } else {
                    System.out.println("Sorry, your current balance is " + currentUser.getBalance());
                    return false;
                }
            } else {
                System.out.println("Your card is locked. Contact your bank");
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean lockFromTheBank(){
        if(currentUser != null){
            currentUser.lockCard();
            return true;
        }
        return false;
    }

    public boolean unlockFromTheBank() {
        if(currentUser != null){
            if (!currentUser.isLocked()) {
                currentUser.resetFailedAttempts();
                return true;
            }
        }
        return false;
    }

    public boolean ejectcard() {
        if(currentUser != null){
                currentUser = null;
                return true;
        }
        return false;
    }
}
