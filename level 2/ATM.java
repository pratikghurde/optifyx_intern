import java.util.*;

class User {
    private String userID;
    private String userPIN;

    public User(String userID, String userPIN) {
        this.userID = userID;
        this.userPIN = userPIN;
    }

    public String getUserID() {
        return userID;
    }

    public boolean validatePIN(String pin) {
        return this.userPIN.equals(pin);
    }
}

class Account {
    private double balance;
    private List<String> transactionHistory;

    public Account() {
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            return true;
        } else {
            System.out.println("Insufficient funds!");
            return false;
        }
    }

    public void transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + recipient);
        }
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}

class ATMOperations {
    private Account account;

    public ATMOperations(Account account) {
        this.account = account;
    }

    public void showBalance() {
        System.out.println("Current Balance: $" + account.getBalance());
    }

    public void deposit(double amount) {
        account.deposit(amount);
        System.out.println("Successfully deposited: $" + amount);
    }

    public void withdraw(double amount) {
        if (account.withdraw(amount)) {
            System.out.println("Successfully withdrew: $" + amount);
        }
    }

    public void transfer(Account recipient, double amount) {
        account.transfer(recipient, amount);
    }

    public void transactionHistory() {
        account.printTransactionHistory();
    }
}

class MainATM {
    private static Scanner sc = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        // Initialize users and accounts
        users.put("user1", new User("user1", "1234"));
        accounts.put("user1", new Account());

        users.put("user2", new User("user2", "5678"));
        accounts.put("user2", new Account());

        // Simulate ATM operations
        System.out.println("Welcome to the ATM!");
        System.out.print("Enter user ID: ");
        String userID = sc.nextLine();
        User currentUser = users.get(userID);

        if (currentUser != null) {
            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            if (currentUser.validatePIN(pin)) {
                ATMOperations atmOps = new ATMOperations(accounts.get(userID));
                boolean exit = false;

                while (!exit) {
                    System.out.println("\n1. Transaction History");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Deposit");
                    System.out.println("4. Transfer");
                    System.out.println("5. Quit");

                    System.out.print("Choose an option: ");
                    int option = sc.nextInt();

                    switch (option) {
                        case 1:
                            atmOps.transactionHistory();
                            break;
                        case 2:
                            System.out.print("Enter amount to withdraw: ");
                            double withdrawAmount = sc.nextDouble();
                            atmOps.withdraw(withdrawAmount);
                            break;
                        case 3:
                            System.out.print("Enter amount to deposit: ");
                            double depositAmount = sc.nextDouble();
                            atmOps.deposit(depositAmount);
                            break;
                        case 4:
                            System.out.print("Enter recipient user ID: ");
                            String recipientID = sc.next();
                            Account recipient = accounts.get(recipientID);
                            if (recipient != null) {
                                System.out.print("Enter amount to transfer: ");
                                double transferAmount = sc.nextDouble();
                                atmOps.transfer(recipient, transferAmount);
                            } else {
                                System.out.println("Invalid recipient user ID.");
                            }
                            break;
                        case 5:
                            System.out.println("Thank you for using the ATM. Goodbye!");
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid option! Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid PIN. Exiting...");
            }
        } else {
            System.out.println("Invalid user ID. Exiting...");
        }
    }
}
