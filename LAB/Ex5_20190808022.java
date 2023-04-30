import java.util.ArrayList;


public class Ex5_20190808022 {
    public static void main(String[] args){

    }
}
class Account{
    private String accountNumber;
    private double balance;
    public Account (String accountNumber, double balance){
        if (balance < 0 ){
            throw new InsufficientFundsException(balance);
        }
        else {
            this.accountNumber=accountNumber;
            this.balance=balance;
        }
    }
    public String getAccountNumber(){
        return accountNumber;
    }
    public double getBalance(){
        return balance;
    }
    public void deposit(double amount) throws InvalidTransactionException{
        if (amount <= 0){
            throw new InvalidTransactionException(amount);
        }
        else {
            balance += amount;
        }
    }
    public void withdraw(double amount) throws InvalidTransactionException, InsufficientFundsException{
        if (amount <= 0){
            throw new InvalidTransactionException(amount);
        }
        else if (amount > balance){
            throw new InsufficientFundsException(balance, amount);
        }
        else {
            balance -= amount;
        }
    }
    public String toString(){
       return "Account: " + accountNumber + ", Balance: " + balance;
    }
}
class Customer {
    private String name;
    private ArrayList<Account> accounts = new ArrayList<Account>();
    public Customer(String name){
        this.name=name;
    }
     Account getAccount(String accountNumber){
        for (Account account: accounts){
            if (account.getAccountNumber().equals(accountNumber)){
                return account;
            }
        }
        throw new AccountNotFoundException(accountNumber);
    }
    public void addAccount(Account account){
        try{
            getAccount(account.getAccountNumber());
            throw new AccountAlreadyExistsException(account.getAccountNumber());
        }
        catch(AccountNotFoundException e){
            accounts.add(account);
            System.out.println("Added account: "+ account.getAccountNumber()+ "with "+ account.getBalance());
        }
        catch (AccountAlreadyExistsException e){
            throw new AccountAlreadyExistsException(account.getAccountNumber());
        }
        finally{
            System.out.println(account);
        }
    }
    public void removeAccount(String accountNumber){
        for(Account account : accounts){
                if (accountNumber.equals(account.getAccountNumber())){
                    accounts.remove(accounts.indexOf(account));
            }
        }
        
    }
    public void transfer (Account fromAccount, Account toAccount, double amount) throws InvalidTransactionException{
        try{
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        }
        catch(InvalidTransactionException e){
            throw new InvalidTransactionException(e, "cannot transfer funds from account" +
            " "+ fromAccount.getAccountNumber() + " to " +toAccount.getAccountNumber());
        }
    }
    public String toString(){
        String customer = "";
        for (Account account : accounts){
            customer += "Customer "+ name + "\t" + account +"\n";
        }
        return customer;
    }
}
class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException (double balance){
        super("Wrong balance: " + balance);
    }
    public InsufficientFundsException (double balance, double amount){
        super("Required amount is "+ amount + " but only "+balance);
    }
}
class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String accountNumber){
        super("Account number "+ accountNumber + " already exists.");
    }
}
class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String accountNumber){
        super("Account number " + accountNumber + " already exists.");
    }
}
class InvalidTransactionException extends Exception{
    public InvalidTransactionException(double amount){
        super("Invalid amount: "+amount);
    }
    public InvalidTransactionException (InvalidTransactionException e, String message){
        super(message + ":\n\t" + e.getMessage());
    }
}