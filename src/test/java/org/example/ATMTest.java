package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ATMTest {

    private User bankUser;
    private Bank mockBank;
    private ATM atm;


    @BeforeEach
    void setUp(){
        bankUser = new User("Salim", "4321", 7000.00);
        mockBank = mock(Bank.class);
        atm = new ATM(mockBank);// you want to create real ATM to apply the real logics,
        // just the banks that are mocked to fasten the test
    }


    @Test
    @DisplayName("Inserting Card test")
    void insertingCardTest(){
        when(mockBank.getUserById("Salim")).thenReturn(bankUser);
        assertEquals(true, atm.insertCard("Salim"));
        assertEquals(false, atm.insertCard("Nada"));
    }

    @Test
    @DisplayName("Check the pin if it is correct")
    void logInTest(){
        when(mockBank.getUserById("Salim")).thenReturn(bankUser);
        atm.insertCard("Salim");
        assertEquals("4321", bankUser.getPin());
        String pin = bankUser.getPin();
        String wrongPin = "6728";
        assertEquals(true, atm.enterPin(pin));
        assertEquals(false, atm.enterPin(wrongPin));
    }

    @Test
    @DisplayName("Check out balance in the ATM")
    void checkOutCurrentBalance(){
        when(mockBank.getUserById("Salim")).thenReturn(bankUser);
        atm.insertCard("Salim");
        double currentBalance = bankUser.getBalance();
        assertEquals(currentBalance, atm.checkBalance());
    }

    @Test
    @DisplayName("Make a deposit into ATM")
    void makeDeposit(){
        when(mockBank.getUserById("Salim")).thenReturn(bankUser);
        atm.insertCard("Salim");
        double deposit = 4000.00;
        double amountAfterDeposit = bankUser.getBalance() + deposit;
        atm.deposit(deposit);
        assertEquals(amountAfterDeposit, bankUser.getBalance());
    }

    @Test
    @DisplayName("Withdrawing ATM")
    void withdrawATM(){
        when(mockBank.getUserById("Salim")).thenReturn(bankUser);
        atm.insertCard("Salim");
        double withdrawBiggerThanDeposit = 100000.00;
        double withdrawWithinLimit = 200.00;
        assertEquals(true, atm.withdraw(withdrawWithinLimit));
        assertEquals(false, atm.withdraw(withdrawBiggerThanDeposit));

    }

    @Test
    @DisplayName("Locking account from the Bank")
    void lockingAccountFromTheBank(){
        when(mockBank.getUserById("Salim")).thenReturn(bankUser);
        atm.insertCard("Salim");
        atm.lockFromTheBank();
        assertEquals(true, bankUser.isLocked());
    }

    @Test
    @DisplayName("Unlocking account")
    void unlockAccount(){
        when(mockBank.getUserById("Salim")).thenReturn(bankUser);
        atm.insertCard("Salim");
        atm.unlockFromTheBank();
        assertEquals(false, bankUser.isLocked());
    }

    @Test@DisplayName("Eject suddenly")
    void ejectCard(){
        when(mockBank.getUserById("Salim")).thenReturn(bankUser);
        atm.insertCard("Salim");
        assertEquals(true, atm.ejectcard());
    }

}