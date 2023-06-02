/**
 * @(#)Assignment03_20190808022.java
 * @Since 01.06.2023
 * @author Metehan Ünal
 * @version 1.00
 */
// package Assignments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Assignment03_20190808022 {
    public static void main(String[] args){
        Store s1 = new Store("Migros","www.migros.com.tr");
        Store s2 = new Store("Bim","www.bim.com.tr");
        Customer c = new Customer("CSE 102");
        Customer cc = new Customer("Club CSE 102");
        s1.addCustomer(cc);
        s2.addCustomer(cc);
        Product p = new Product(123456L,"Computer",1000.00);
        FoodProduct fp = new FoodProduct(456789L,"Snickers",2,250,true,true,true,false);
        CleaningProduct cp = new CleaningProduct(31654L,"Mop",99,false,"Multi-room");
        System.out.println(cp);
        s1.addToInventory(p,20);
        s2.addToInventory(p,10);
        s2.addToInventory(fp,100);
        s1.addToInventory(cp,28);
        System.out.println(s1.getName()+" has "+ s1.getCount() +" products");
        System.out.println(s1.getProductCount(p));
        System.out.println(s1.purchase(p,2));
        s1.addToInventory(p,3);
        System.out.println(s1.getProductCount(p));
        System.out.println(s2.getProductCount(p));
        c.addToCart(s1,p,2);
        c.addToCart(s1,fp,1);
        c.addToCart(s1,cp,1);
        System.out.println("Total due - " +c.getTotalDue(s1));
        System.out.println("\n \nReceipt: " +c.receipt(s1));
        System.out.println("After paying:  "+c.pay(s1,2102,true));
        cc.addToCart(s2,fp,2);
        cc.addToCart(s2,fp,1);
        System.out.println(cc.receipt(s2));
        cc.addToCart(s2,fp,10);
        System.out.println(cc.receipt(s2));
        }
    }
class Product {
    private long Id;
    private String Name;
    private double Price;
    public Product (long Id, String Name, double Price) throws RuntimeException, InvalidAmountException {
        this.Id = Id;
        this.Name = Name;
        // if (Quantity < 0){
        //     throw new InvalidAmountException(Quantity);
        // }
        // else{
        //     this.Quantity = Quantity;
        // }
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
    // public int remaining() {
    //     return Quantity;
    // }
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
    // public int addToInventory(int amount) throws InvalidAmountException{
    //     if (amount < 0){
    //         System.out.println("Invalid Quantity");
    //         return this.Quantity;
    //     }
    //     else{
    //         return this.Quantity += amount;
    //     }
    // }

    // public double purchase (int amount) throws InvalidAmountException{
    //     if (amount < 0){
    //         throw new InvalidAmountException(amount);
    //     }
    //     else if (amount > Quantity){
    //         throw new InvalidAmountException(Quantity, this.Quantity);
    //     }
    //     else{
    //         Quantity -= amount;
    //         return amount*Price;
    //     }
    // }
    public String toString(){
        return Id + Name + " @ "  +Price;
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
    public FoodProduct(long Id, String Name, double Price, int Calories, boolean Dairy, boolean Gluten, boolean Eggs, boolean Peanuts) {
        super(Id, Name, Price);
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
    public CleaningProduct(long Id, String Name, double Price, boolean Liquid, String WhereToUse) {
        super(Id, Name, Price);
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
    private Map<Store, Map<Product, Integer>> cart;
    public Customer (String Name){
        this.Name = Name;
        this.cart = new HashMap<Store, Map<Product, Integer>>();
    }
    public void addToCart(Store store, Product product, int count) {
        if (!cart.containsKey(store)) {
            cart.put(store, new HashMap<>());
        }
        Map<Product, Integer> storeCart = cart.get(store);
        storeCart.put(product, count);
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
    public void clearCart(){
        Cart.clear();
    }
public String receipt(Store store) {
        if (!cart.containsKey(store)) {
            throw new StoreNotFoundException(store.getName());
        }
        StringBuilder receipt = new StringBuilder();
        double total = 0;
        for (Map.Entry<Product, Integer> entry : cart.get(store).entrySet()) {
            Product product = entry.getKey();
            int count = entry.getValue();
            double totalForProduct = product.getPrice() * count;
            receipt.append(product.getId()).append(" - ").append(product.getName()).append(" @")
                    .append(product.getPrice()).append(" X ").append(count).append(" ... ")
                    .append(totalForProduct).append("\n");
            total += totalForProduct;
        }
        receipt.append("Total Due: ").append(total);
        return receipt.toString();
    }
    public double getTotalDue(Store store) {
        if (!cart.containsKey(store)) {
            throw new StoreNotFoundException(store.getName());
        }
        double total = 0;
        for (Map.Entry<Product, Integer> entry : cart.get(store).entrySet()) {
            Product product = entry.getKey();
            int count = entry.getValue();
            total += product.getPrice() * count;
        }
        return total;
    }
    public double pay(Store store, double amount, boolean usePoints) {
        double totalDue = getTotalDue(store);
        if (amount >= totalDue) {
            System.out.println("Thank you");
            return amount - totalDue;
        } else {
            throw new InsufficientFundsException();
        }
    }
    public int getPoints(Store store) {
        return store.getCustomerPoints(this);
    }
}
class Store{
    private String Name;
    private String Website;
    // private ArrayList<Product> products = new ArrayList<Product>();
    // private ArrayList <Customer> customers = new ArrayList<Customer>();
    private Map<Product, Integer> products = new HashMap<>();
    private Map<Customer, Integer> customers = new HashMap<>();
    public Store(String Name, String Website){
        this.Name = Name;
        this.Website = Website;
        products = new HashMap<Product, Integer>();
    }
    public int getCount() {
        int count = 0;
        for (int value : products.values()) {
            count += value;
        }
        return count;
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
    public void addCustomer(Customer customer) {
        customers.put(customer, 0);
    }
    public int getProductCount(Product product) {
        if (!products.containsKey(product)) {
            throw new ProductNotFoundException(product.getId(), product.getName());
        }
        return products.get(product);
    }
    public int getCustomerPoints(Customer customer) {
        if (!customers.containsKey(customer)) {
            throw new CustomerNotFoundException(customer.getName());
        }
       return customers.get(customer);
    }
    public void removeProduct(Product product) {
        if (!products.containsKey(product)) {
            throw new ProductNotFoundException(product.getId(), product.getName());
        }
        products.remove(product);
    }
    public void addToInventory(Product product, int amount) {
        if (amount < 0) {
            throw new InvalidAmountException(amount);
        }
        products.put(product, products.getOrDefault(product, 0) + amount);
    }
    public double purchase(Product product, int amount) {
        if (!products.containsKey(product)) {
            throw new ProductNotFoundException(product.getId(), product.getName());
        }
        int available = products.get(product);
        if (amount < 0 || amount > available) {
            throw new InvalidAmountException(amount);
        }
        products.put(product, available - amount);
        return product.getPrice() * amount;
    }
    
    public Customer getCustomer(String name) {
        for (Customer customer : customers.keySet()) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        throw new CustomerNotFoundException(name);
    }

    public void removeCustomer(String name) {
        Customer customerToRemove = null;
        for (Customer customer : customers.keySet()) {
            if (customer.getName().equals(name)) {
                customerToRemove = customer;
                break;
            }
        }
        if (customerToRemove != null) {
            customers.remove(customerToRemove);
        } else {
            throw new CustomerNotFoundException(name);
        }
    }
}
//Custom Exceptions
class CustomerNotFoundException extends RuntimeException {
    private String name;

    public CustomerNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustomerNotFoundException: Name - " + name;
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
class ProductNotFoundException extends RuntimeException {
    private long ID;
    private String name;

    public ProductNotFoundException(long ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductNotFoundException: ID - " + ID + " Name - " + name;
    }
}
class CartIsEmptyException extends RuntimeException{
    public String toString(){
        return "Your cart is empty. Please look at what we got in store for you!";
    }
}
class StoreNotFoundException extends RuntimeException {
    private String name;

    public StoreNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StoreNotFoundException: " + name;
    }
}