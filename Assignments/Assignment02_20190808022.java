/**
 * @(#)Assignment02_20190808022.java
 * @Since 26.03.2023
 * @author Metehan Ünal
 * @version 1.00
 */
// package Assignments;

import java.util.ArrayList;

public class Assignment02_20190808022 {
    public static void main(String[] args){
            Store s = new Store("Migros", "www.migros.com.tr");
    
            Customer c = new Customer("CSE 102");
    
            ClubCustomer cc = new ClubCustomer("Club CSE 102", "05551234567");
    //        s.addCustomer(c);
            s.addCustomer(cc);
    
            Product p = new Product(123456L, "Computer", 20, 1000.00);
            FoodProduct fp = new FoodProduct(456798L, "snickers", 100, 2, 250, true, true, true, false);
            CleaningProduct cp = new CleaningProduct(31654L, "Mop", 28, 99, false, "Multi-room");
    
            s.addProduct(p);
            s.addProduct(fp);
            s.addProduct(cp);
    
            System.out.println(s.getInventorySize());
    //        System.out.println(s.getProduct("shoes"));
    
            System.out.println(cp.purchase(2));
            s.getProduct("Computer").addToInventory(3);
    //        System.out.println(fp.purchase(200));
    
            c.addToCart(p, 2);
            c.addToCart(s.getProduct("snickers"),-2);
            c.addToCart(s.getProduct("snickers"), 1);
            System.out.println("Total due - " + c.getTotalDue());
            System.out.println("\n\nReceipt:\n" + c.receipt());
    
    //        System.out.println("After paying: "+c.pay(2000));
    
            System.out.println("After paying: " + c.pay(2020));
    
            System.out.println("Total due - " + c.getTotalDue());
            System.out.println("\n\nReceipt 1:\n" + c.receipt());
    
    //        Customer c2 = s.getCustomer("05551234568");
            cc.addToCart(s.getProduct("snickers"), 2);
            cc.addToCart(s.getProduct("snickers"), 1);
            System.out.println("\n\nReceipt 2:\n" + cc.receipt());
    
            Customer c3 = s.getCustomer("05551234567");
            c3.addToCart(s.getProduct("snickers"), 10);
            System.out.println("\n\nReceipt 3:\n" + cc.receipt());
    
            System.out.println(((ClubCustomer) c3).pay(26, false));
    
            c3.addToCart(s.getProduct(31654L),3);
            System.out.println(c3.getTotalDue());
            System.out.println(c3.receipt());
            System.out.println(cc.pay(3 * 99, false));
    
            c3.addToCart(s.getProduct(31654L), 3);
            System.out.println(c3.getTotalDue());
            System.out.println(c3.receipt());
            System.out.println(cc.pay(3 * 99, true));
    
        }
    }
class Product {
    private long Id;
    private String Name;
    private int Quantity;
    private double Price;
    public Product (long Id, String Name, int Quantity, double Price) throws RuntimeException, InvalidAmountException {
        this.Id = Id;
        this.Name = Name;
        if (Quantity < 0){
            throw new InvalidAmountException(Quantity);
        }
        else{
            this.Quantity = Quantity;
        }
        if (Price < 0){
            throw new InvalidPriceException();
        }
        else{
            this.Price = Price;
        }
    }
    public long getId() {
        return Id;
    }
    public void setId(long id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public int remaining() {
        return Quantity;
    }
    public double getPrice(){
        return Price;
    }
    public void setPrice(double Price) throws InvalidPriceException{
        if (Price < 0){
            throw new InvalidPriceException();
        }
        else{
            this.Price = Price;
        }
    }
    public int addToInventory(int amount) throws InvalidAmountException{
        if (amount < 0){
            System.out.println("Invalid Quantity");
            return this.Quantity;
        }
        else{
            return this.Quantity += amount;
        }
    }
    public double purchase (int amount) throws InvalidAmountException{
        if (amount < 0){
            throw new InvalidAmountException(amount);
        }
        else if (amount > Quantity){
            throw new InvalidAmountException(Quantity, this.Quantity);
        }
        else{
            Quantity -= amount;
            return amount*Price;
        }
    }
    public String toString(){
        return " Product Name: " + Name + " has "  + Quantity + " remaining";
    }   
    public boolean equals(Object obj){
        if (obj instanceof Product){
            Product product = (Product)obj;
            return this.Price == product.Price;
        }
        else{
            return false;
        }
    }
}
class FoodProduct extends Product{
    private int Calories;
    private boolean Dairy;
    private boolean Gluten;
    private boolean Eggs;
    private boolean Peanuts;
    public FoodProduct(long Id, String Name, int Quantity, double Price, int Calories, boolean Dairy, boolean Gluten, boolean Eggs, boolean Peanuts) {
        super(Id, Name, Quantity, Price);
        if (Calories < 0){
            throw new InvalidAmountException(Calories);
        }
        else{
            this.Calories = Calories;
        }
        this.Dairy = Dairy;
        this.Gluten = Gluten;
        this.Eggs = Eggs;
        this.Peanuts = Peanuts;
    }
    public int getCalories() {
        return Calories;
    }
    public void setCalories(int Calories) throws InvalidAmountException {
        if (Calories < 0){
            throw new InvalidAmountException(Calories);
        }
        else{
            this.Calories = Calories;
        }
    }
    public boolean containsDairy() {
        return Dairy;
    }
    public boolean containsEggs() {
        return Eggs;
    }
    public boolean containsGluten() {
        return Gluten;
    }
    public boolean containsPeanuts() {
        return Peanuts;
    }
}
class CleaningProduct extends Product{
    private boolean Liquid;
    private String WhereToUse;
    public CleaningProduct(long Id, String Name, int Quantity, double Price, boolean Liquid, String WhereToUse) {
        super(Id, Name, Quantity, Price);
        this.Liquid = Liquid;
        this.WhereToUse = WhereToUse;
    }
    public boolean isLiquid() {
        return Liquid;
    }
    public String getWhereToUse() {
        return WhereToUse;
    }
    public void setWhereToUse(String whereToUse) {
        WhereToUse = whereToUse;
    }
}
class CartItem {
    private Product product;
    private int count;
    private double total;
    public CartItem (Product product, int count){
        this.product = product;
        this.count = count;
        this.total = product.getPrice()*count;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}
class Customer {
    private String Name;
    private int count;
    private ArrayList<CartItem> Cart = new ArrayList<CartItem>();
    public Customer (String Name){
        this.Name = Name;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String toString(){
        return Name;
    }
    public void addToCart (Product product, int count){
        try {
            product.purchase(count);
            Cart.add(new CartItem(product, count));
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: Invalid Amount");
        }
    }
    public void clearCart(){
        Cart.clear();
    }
    public String receipt (){
        double total=0;
        String receipt = "";
        for (CartItem cartitem : Cart){
            total += cartitem.getTotal();
            receipt += "\n"+ cartitem.getProduct().getName() + " - " + cartitem.getProduct().getPrice() 
            + " X " + cartitem.getCount() +" = "+ cartitem.getTotal();
        }
        receipt += "\n-------------------------------------";
        receipt += "\n" + "Total due = "+total;
        return receipt;
    } 
    public double getTotalDue(){
        double total = 0;
        for (CartItem cartitem : Cart){
            total += cartitem.getTotal();
        }
        return total;
    }
    public double pay (double amount) throws InsufficientFundsException{
        double change = 0;
        if (amount < getTotalDue()){
            throw new InsufficientFundsException();
        }
        else{
            System.out.println("Thank you for your purchase");
            change = amount - getTotalDue();
            Cart.clear();
        }
        return change;
    }
}
class ClubCustomer extends Customer{
    private String Phone;
    private int Points;
    public ClubCustomer(String Name, String Phone) {
        super(Name);
        this.Phone = Phone;
        this.Points = 0;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }
    public int getPoints() {
        return Points;
    }
    public void addPoints(int points) {
        if (points > 0){
            Points += points;
        }
    }
    public double pay(double amount, boolean usePoints) throws InsufficientFundsException{
        double total = this.getTotalDue();
        double change;
        if (usePoints&&Points>0){
            if (total*100<Points){
                Points -= total*100;
                Points += Math.floor(total);
                total=0;
                change=amount;
            }
            else if (total*100>Points) {
                total -= Points * 0.01;
                Points = 0;
                change = amount-total;
            }

        }
        if (amount <total){
            throw new InsufficientFundsException();
        }
        else{
            System.out.println("Thank you for your purchase");
            change = amount - total;
            Points += Math.floor(total);
            this.clearCart();
        }
        return change;
    }
    public String toString() {
        return super.toString() + " has " + Points + " points";
    }
}

class Store{
    private String Name;
    private String Website;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<ClubCustomer> clubCustomers = new ArrayList<ClubCustomer>();
    public Store(String Name, String Website){
        this.Name = Name;
        this.Website = Website;
        products = new ArrayList<Product>();
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getWebsite() {
        return Website;
    }
    public void setWebsite(String website) {
        Website = website;
    }
    public int getInventorySize(){
        return products.size();
    }
    public void addProduct(Product product){
        products.add(product);
    }
    public Product getProduct(long ID)throws ProductNotFoundException{
            for (Product product : products){
                if (product.getId() == ID){
                    return product;
                }
            }
        throw new ProductNotFoundException(ID);
    }
    public Product getProduct (String name) throws ProductNotFoundException{
        for (Product product : products){
            if (product.getName()==name){
                return product;
            }
        }
        throw new ProductNotFoundException(name);
    }
    public int getProductIndex(Product p){
        if (products.indexOf(p) == -1){
            return -1;
        }
        else
        return products.indexOf(p);
    }
    public void addCustomer(ClubCustomer customer){
        clubCustomers.add(customer);
    }
    public ClubCustomer getCustomer (String phone){
        for (ClubCustomer clubcustomer : clubCustomers){
            if(clubcustomer.getPhone() == phone){
                return clubcustomer;
            }
        }
        throw new CustomerNotFoundException();
    }
    public void removeProduct(long ID)throws ProductNotFoundException{
        for (Product product : products){
            if (ID == product.getId()){
                products.remove(products.indexOf(product));
                return;
            }
        }
        throw new ProductNotFoundException(ID);
    }
    public void removeProduct(String name) throws ProductNotFoundException{
        for (Product product : products){
            if (name == product.getName()){
                products.remove(products.indexOf(product));
                return;
            }
        }
        throw new ProductNotFoundException(name);
    }
    public void removeCustomer (String phone) throws CustomerNotFoundException{
        for (ClubCustomer clubcustomer : clubCustomers){
            if(phone == clubcustomer.getPhone()){
                clubCustomers.remove(clubCustomers.indexOf(clubcustomer));
                return;
            }
        }
        throw new CustomerNotFoundException();
    }
}
//Custom Exceptions
class CustomerNotFoundException extends IllegalArgumentException{
    private String phone;
    @Override
    public String toString(){
        return "CustomerNotFoundException" + phone;
    }
}
class InsufficientFundsException extends RuntimeException{
    private double total;
    private double payment;
    @Override
    public String toString(){
        return "InsufficientFundsException" + total + " due but only " + payment + " given";
    }
}
class InvalidAmountException extends RuntimeException{
    private int amount;
    private int quantity;
    private boolean hasQuantity;
    public InvalidAmountException(int amount){
        this.amount = amount;
        this.hasQuantity = false;
    }
    public InvalidAmountException(int amount, int quantity){
        this.amount = amount;
        this.quantity = quantity;
        this.hasQuantity = true;
    }
    @Override
    public String toString(){
        if (hasQuantity==false){
            return "InvalidAmountException" + amount;
        }
        else{
            return "InvalidAmountException" + amount + " was requested, but "
            + "only " + quantity + " remaining";
        }
    }
}
class InvalidPriceException extends RuntimeException{
    private double price;
    @Override
    public String toString(){
        return "InvalidPriceException" + price;
    }
}
class ProductNotFoundException extends IllegalArgumentException{
    private long id;
    private String name;
    public ProductNotFoundException(long id){
        this.id = id;
        this.name = null;
    }
    public ProductNotFoundException(String name){
        this.name = name;
        this.id = -1;
    }
    @Override
    public String toString(){
        if (name != null){
            return "ProductNotFoundException: Name - " + name;
        }
        else
        return "ProductNotFoundException: ID - " + id;
    }
}
class CartIsEmptyException extends RuntimeException{
    public String toString(){
        return "Your cart is empty. Please look at what we got in store for you!";
    }
}