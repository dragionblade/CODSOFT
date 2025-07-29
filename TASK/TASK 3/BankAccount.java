import java.util.Scanner;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        if(initialBalance < 0) {
            balance = 0;
        } else {
            balance = initialBalance;
        }
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Enter an amount greater than zero.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds! Withdrawal failed.");
            return false;
        }
        balance -= amount;
        System.out.printf("Withdrawal successful! Amount withdrawn: ₹%.2f\n", amount);
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Enter an amount greater than zero.");
            return false;
        }
        balance += amount;
        System.out.printf("Deposit successful! Amount deposited: ₹%.2f\n", amount);
        return true;
    }
}


class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        scanner = new Scanner(System.in);
    }

    
    public void start() {
        System.out.println("Welcome to the ATM!");

        boolean exit = false;
        while (!exit) {
            showMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    performWithdraw();
                    break;
                case 2:
                    performDeposit();
                    break;
                case 3:
                    showBalance();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please select 1-4.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Check Balance");
        System.out.println("4. Exit");
        System.out.print("Enter choice (1-4): ");
    }

    private int getUserChoice() {
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = -1; 
        }
        return choice;
    }

    private void performWithdraw() {
        System.out.print("Enter amount to withdraw: ₹");
        double amount = getPositiveDoubleInput();
        if (amount > 0) {
            account.withdraw(amount);
        }
    }

    private void performDeposit() {
        System.out.print("Enter amount to deposit: ₹");
        double amount = getPositiveDoubleInput();
        if (amount > 0) {
            account.deposit(amount);
        }
    }

    private void showBalance() {
        System.out.printf("Your current balance is: ₹%.2f\n", account.getBalance());
    }

    private double getPositiveDoubleInput() {
        double amount = -1;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                amount = -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
        }
        return amount;
    }
    public static void main(String[] args) {
        BankAccount account = new BankAccount(100.0); 
        ATM atm = new ATM(account);
        atm.start();
    }
}

