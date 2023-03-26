/**
 * @(#)Assignment01_20190808022.java
 * @Since 26.03.2023
 * @author Metehan Ünal
 * @version 1.00
 */
// package Assignments;

import java.util.ArrayList;

public class Assignment01_20190808022 {
    public static void main(String[] args){
        Store s = new Store("Migros", "www.migros.com.tr");
        Customer c = new Customer("CSE102");
        System.out.println(c);
        ClubCustomer cc = new ClubCustomer("Club CSE 102", "05551234567");
        cc.addPoints(20);   //20 points       
        cc.addPoints(30);
        System.out.println(cc.getPhone());
        System.out.println(cc);
        Product p = new Product("1234", "Computer", 20, 1000.00);
        FoodProduct fp = new FoodProduct("3456", "Snickers", 100, 2.00, 250, true, true, true, false);
        CleaningProduct cp = new CleaningProduct("5678", "Mop", 28, 99, false, "Multi-room");
        s.addProduct(p);
        s.addProduct(fp);
        s.addProduct(cp);
        for (int i=0; i<s.getInventorySize(); i++){
            System.out.println(s.getProduct(i));
        }
        s.addProduct(new Product("4321", "İphone", 50, 99.00));
        System.out.println(s.getProductIndex(new FoodProduct("8888", "Apples", 500, 1, 50, false, false, false, false)));
        System.out.println(cp.purchase(2)   );
        if(fp.containsGluten()){
            System.out.println("My wife cannot eat this "+fp.getName());
        }
        else{
            System.out.println("My wife can eat this "+fp.getName());
        }
        s.getProduct(0).addToInventory(3);
        for (int i=0; i<s.getInventorySize(); i++){
            Product cur=s.getProduct(i);
            System.out.println(cur);
            for(int j=i+1;j<s.getInventorySize();j++){
                if(cur.equals(s.getProduct(j)))
                System.out.println(cur.getName()+ " is the same price as "+s.getProduct(j).getName());
            }
        }
    }
}
class Product {
    private String Id;
    private String Name;
    private int Quantity;
    private double Price;
    public Product(String Id, String Name, int Quantity, double Price) {
        this.Id = Id;
        this.Name = Name;
        this.Quantity = Quantity;
        this.Price = Price;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
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
    public void setPrice(double price){
        Price = price;
    }
    public int addToInventory(int quantity){
        return Quantity += quantity;
    }
    public double purchase (int quantity){
        if (quantity > Quantity){
            System.out.println("Not enough quantity");
            return 0;
        }
        else{
            Quantity -= quantity;
            return quantity*Price;
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
    public FoodProduct(String Id, String Name, int Quantity, double Price, int Calories, boolean Dairy, boolean Gluten, boolean Eggs, boolean Peanuts) {
        super(Id, Name, Quantity, Price);
        this.Calories = Calories;
        this.Dairy = Dairy;
        this.Gluten = Gluten;
        this.Eggs = Eggs;
        this.Peanuts = Peanuts;
    }
    public int getCalories() {
        return Calories;
    }
    public void setCalories(int calories) {
        Calories = calories;
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
    public CleaningProduct(String Id, String Name, int Quantity, double Price, boolean Liquid, String WhereToUse) {
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
class Customer {
    private String Name;
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
    public String toString() {
        return super.toString() + " has " + Points + " points";
    }
}
class Store{
    private String Name;
    private String Website;
    ArrayList<Product> products = new ArrayList<Product>();
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
    public void addProduct (Product product, int index){
        if (index < 0 || index > products.size()){
            products.add(product);
        }
        else{
            products.add(index, product);
        }
    }
    public void addProduct(Product product){
        products.add(product);
    }
    public Product getProduct(int index){
        if (index < 0 || index > products.size()){
            return null;
        }
        else{
            return products.get(index);
        }
    }
    public int getProductIndex(Product p){
        if (products.indexOf(p) == -1){
            return -1;
        }
        else
        return products.indexOf(p);
    }
}
