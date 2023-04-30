import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
// import java.security.NoSuchAlgorithmException;

public class Ex6_20190808022 {
    public static void main(String[] args) throws Exception{
        Paypal p = new Paypal("Vecta", "cse102!@");
        System.out.println(p.getPassword());
        System.out.println(p.pay(100));
    }
}
abstract class Product implements Comparable<Product>{
    private String name;
    private double price;
    public Product(String name, double price){
        this.name = name;
        this.price=price;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int compareTo(Product other){
        if (this.price < other.price){
            return -1;
        }
        else if (this.price > other.price){
            return 1;
        }
        else {
            return 0;
        }
    }
    @Override
    public String toString(){
        return getClass().getSimpleName() + "[name=" + name + ", price=" + price + "]";
    }

}
abstract class Book extends Product{
    private String author;
    private int pageCount;
    public Book(String name, double price, String author, int pageCount){
        super(name, price);
        this.author=author;
        this.pageCount=pageCount;
    }
    public String getAuthor(){
        return author;
    }
    public int getPageCount(){
        return pageCount;
    }
}
class ReadingBook extends Book{
    private String genre;
    public ReadingBook(String name, double price, String author, int pageCount, String genre){
        super(name, price, author, pageCount);
        this.genre=genre;
    }
    public String getGenre(){
        return genre;
    }

}
class ColoringBook extends Book implements Colorable{
    private String color;
    public ColoringBook(String name, double price, String author, int pageCount){
        super(name, price, author, pageCount);
    }
    public String getColor(){
        return color;
    }
    @Override
    public void paint(String color) {
        this.color=color;
    }
}
class ToyHorse extends Product implements Rideable{

    public ToyHorse(String name, double price) {
        super(name, price);
    }

    @Override
    public void ride() {
        System.out.println("You are riding a toy horse");
    }
}
class Bicycle extends Product implements Rideable, Colorable{
    private String color;
    public Bicycle(String name, double price, String color) {
        super(name, price);
        this.color=color;
    }
    public String getColor(){
        return color;
    }
    @Override
    public void ride() {
        System.out.println("You are riding a bicycle");
    }
    @Override
    public void paint(String color) {
        this.color = color;
        System.out.println("You painted your bicycle to " + color);
    }
}
class User {
    private String username;
    private String email;
    private PaymentMethod payment;
    private ArrayList<Product> cart = new ArrayList<Product>();
    public User(String username, String email){
        this.username=username;
        this.email=email;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public void setPayment(PaymentMethod payment){
        this.payment=payment;
    }
    public Product getProduct(int index){
        return cart.get(index);
    }
    public void addProduct(Product product){
        cart.add(product);
    }
    public void removeProduct(int index){
        cart.remove(index);
    }
    public void purchase(){
        double total=0;
        for (Product product : cart){
            total+=product.getPrice();
        }
        if (payment.pay(total)){
            cart.clear();
        }
    }
}
class CreditCard implements PaymentMethod{
    private long cardNumber;
    private String cardHolderName;
    private java.util.Date expiryDate;
    private int cvv;
    public CreditCard(long cardNumber, String cardHolderName, java.util.Date expiryDate, int cvv){
        this.cardNumber=cardNumber;
        this.cardHolderName=cardHolderName;
        this.expiryDate=expiryDate;
        this.cvv=cvv;
    }
    @Override
    public boolean pay(double amount) {
        if (pay(amount));
        return true;
    }
}
class Paypal implements PaymentMethod{
    private String username;
    private String password;
    private static final String KEY = "aesEncryptionKey";
    private static final String ALGORITHM = "AES";
    public Paypal(String username, String password) throws Exception{
        this.username=username;
        this.password=encrypt(password);
    }
    public String getPassword() throws Exception {
        return decrypt(password);
    }

    @Override
    public boolean pay(double amount) {
        return true;
    }

    private String encrypt(String value) throws Exception {
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private String decrypt(String encryptedValue) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}

interface Colorable{
    public void paint(String color);
}
interface Rideable{
    public void ride();
}
interface PaymentMethod{
    public boolean pay(double amount);
}