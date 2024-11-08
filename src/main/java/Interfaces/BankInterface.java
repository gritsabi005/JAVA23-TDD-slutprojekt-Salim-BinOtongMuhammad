package Interfaces;

import org.example.User;

public interface BankInterface {
    public User getUserById(String id);
    public boolean isCardLocked(String userId);
}
