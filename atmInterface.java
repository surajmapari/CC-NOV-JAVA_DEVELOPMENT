import java.util.*;

class Account {
    public String userid;
    public int userpin;
    public double balance = 0;
    Deque<String> transactions = new LinkedList<String>();

    public Account() {
    }

    public Account(String userid, int userpin, double userbal) {
        this.userid = userid;
        this.userpin = userpin;
        this.balance = userbal;
    }

    public void checkBalance(Account ac) {
        System.out.println("Current Balance is: $" + ac.balance);
    }

    public void setbalance(Double balance) {
        this.balance = balance;
    }

    public void addDepositTransaction(Account ac, Double n) {
        ac.transactions.addFirst("+" + n + " Deposited");
    }

    public void addWithdrawTransaction(Account ac, Double n) {
        ac.transactions.addFirst("-" + n + " Withdrawed");
    }

    public void addTransferTransactionWith(Account ac, Double n) {
        ac.transactions.addFirst("-" + n + " Transferred");
    }

    public void addTransferTransactionDepo(Account ac, Double n) {
        ac.transactions.addFirst("+" + n + " Transferred");
    }
}

class TransactionHistory {
    public void showTransactions(Account ac) {
        Iterator it = ac.transactions.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}

class Deposit {
    public void deposit(Account ac) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the amount to deposit: $");
        double n = sc.nextDouble();
        ac.balance += n;
        System.out.println("Your new balance is: $" + ac.balance);
        ac.addDepositTransaction(ac, n);
    }
}

class Withdraw {
    public void withdraw(Account ac) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the amount to withdraw: $");
        double n = sc.nextDouble();
        if (ac.balance - n >= 0) {
            ac.balance -= n;
            System.out.println("Your new balance is: $" + ac.balance);
            ac.addWithdrawTransaction(ac, n);
        } else if (ac.balance == 0) {
            System.out.println("Can't withdraw because your balance is: $" + ac.balance);
        } else {
            System.out.println("Can't withdraw because your balance is less: $" + ac.balance);
        }
    }
}

class Transfer {
    public void transfer(Account ac, List<Account> objList) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the userid of other account to transfer: ");
        String tuserid = sc.next();
        System.out.print("Enter the amount to transfer: $");
        double n = sc.nextDouble();
        int idx = -1;
        Boolean found = false;
        if (ac.balance - n >= 0) {
            for (int i = 0; i < objList.size(); i++) {
                String userid = objList.get(i).userid;
                if (userid.equals(tuserid)) {
                    idx = i;
                    found = true;
                }
            }
            if (found) {
                ac.balance -= n;
                objList.get(idx).setbalance(objList.get(idx).balance + n);
                System.out.println("Amount Transferred To " + tuserid + " Successfully");
                System.out.println("Your new balance is: $" + ac.balance);
                ac.addTransferTransactionWith(ac, n);
                ac.addTransferTransactionDepo(objList.get(idx), n);
            } else {
                System.out.println("Userid not found");
            }
        } else if (ac.balance == 0) {
            System.out.println("Can't transfer because your balance is: $" + ac.balance);
        } else {
            System.out.println("Can't transfer because your balance is less: $" + ac.balance);
        }

    }
}

public class atmInterface {
    public static void main(String[] args) {

        String userid, user;
        int userpin, pin;
        double userbal;

        Account ac = new Account();
        Deposit dp = new Deposit();
        Withdraw wd = new Withdraw();
        Transfer tf = new Transfer();
        TransactionHistory th = new TransactionHistory();
        Scanner sc = new Scanner(System.in);
        List<Account> objList = new ArrayList<Account>();

        int choice = 0, idx = -1;
        Boolean login;

        do {
            System.out.println("--------------------------------------------");
            System.out.println("--------WELCOME TO SURAJ ATM MACHINE--------");
            System.out.println("1.Create Bank Account");
            System.out.println("2.Existing User Login");
            System.out.println("3.Exit");
            System.out.println("--------------------------------------------");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            System.out.println("--------------------------------------------");
            switch (choice) {
                case 1:
                    System.out.print("Enter new userid: ");
                    userid = sc.next();
                    System.out.print("Enter new userpin: ");
                    userpin = sc.nextInt();
                    System.out.print("Enter your initial balance: ");
                    userbal = sc.nextDouble();
                    Account newAccount = new Account(userid, userpin, userbal);
                    objList.add(newAccount);
                    System.out.println("Account Created Successfully");
                    break;
                case 2:
                    login = false;
                    do {
                        System.out.println("--------------------LOGIN-------------------");
                        System.out.print("Enter the userid: ");
                        user = sc.next();
                        System.out.print("Enter the userpin: ");
                        pin = sc.nextInt();
                        for (int i = 0; i < objList.size(); i++) {
                            userid = objList.get(i).userid;
                            userpin = objList.get(i).userpin;
                            if ((userid.equals(user)) && (userpin == pin)) {
                                idx = i;
                                login = true;
                            }
                        }
                    } while (login == false);
                    do {
                        System.out.println("------------------LOGGED IN-----------------");
                        System.out.println("Hello " + objList.get(idx).userid);
                        System.out.println("1.Check Balance & Transaction History");
                        System.out.println("2.Deposit");
                        System.out.println("3.Withdraw");
                        System.out.println("4.Transfer");
                        System.out.println("5.Back");
                        System.out.println("--------------------------------------------");
                        System.out.print("Enter your choice: ");
                        choice = sc.nextInt();
                        System.out.println("--------------------------------------------");
                        switch (choice) {
                            case 1:
                                ac.checkBalance(objList.get(idx));
                                th.showTransactions(objList.get(idx));
                                break;
                            case 2:
                                dp.deposit(objList.get(idx));
                                break;
                            case 3:
                                wd.withdraw(objList.get(idx));
                                break;
                            case 4:
                                tf.transfer(objList.get(idx), objList);
                                break;
                            default:
                                break;
                        }
                    } while (choice != 5);
                    System.out.println("------------------LOGGED OUT----------------");
                    break;
                default:
                    break;
            }
        } while (choice != 3);
        System.out.println("------------------THANK YOU-----------------");
        System.out.println("--------------------------------------------");
    }
}