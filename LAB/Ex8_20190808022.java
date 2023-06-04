import javax.swing.Box;

public class Ex8_20190808022 {
    
}
interface Sellable {
    public String getName();
    public double getPrice();
}
interface Package <T>{
    public T extract();
    public boolean pack (T item);
    public boolean isEmpty();
}
interface Wrappable extends Sellable{

}
abstract class Product implements Sellable{
    private String name;
    private double price;
    public Product(String name, double price){
        this.name=name;
        this.price=price;
    }
    @Override
    public String toString(){
        return getClass().getName() + "(" + name +"," + price + ")";
    }
}
class Mirror extends Product{
    private int width;
    private int height;
    public Mirror (int width, int height){
        super("mirror", 2);
        this.width=width;
        this.height=height;
    }
    public int getArea(){
        return width*height;
    }
    public <T> T reflect (T item){
        System.out.println("Reflecting " + item + " on " + this);
        return item;
    }
    @Override
    public String getName() {
        return "Mirror";
    }
    @Override
    public double getPrice() {
        return 2;
    }
    
}
class Paper extends Product implements Wrappable{
    private String note;
    public Paper(String note){
        super("A4", 3);
        this.note=note;
    }
    public String getNote(){
        return note;
    }
    public void setNote (String note){
        this.note=note;
    }
    @Override
    public String getName() {
        return "Paper";
    }
    @Override
    public double getPrice() {
        return 3;
    }
}
class Matroschka <T extends Wrappable> extends Product implements Wrappable, Package<T>{
    private T item;
    public Matroschka (T item){
        super("Matroschka", 5);
        this.item=item;
    }
    public T extract(){
        if (isEmpty()){
            return null;
        }
        else{
            T result = item;
            item=null;
            return result;
        }
    }
    public boolean pack (T item){
        if (isEmpty()){
            this.item=item;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isEmpty(){
        return item==null;
    }
    @Override
    public String getName() {
        return "Matroschka";
    }
    @Override
    public double getPrice() {
        return 5;
    }
    @Override
    public String toString(){
        return super.toString() + "{" + item + "}";
    }

}
class Box<T extends Sellable> implements Package<T>{
    private T item;
    private boolean seal;
    public Box ( ){
        item=null;
        seal=false; 
    }
    public Box (T item){
        this.item=item;
        this.seal=true;
    }
    public T extract(){
        if (isEmpty()){
            return null;
        }
        else{
            T result = item;
            item=null;
            this.seal= false;
            return result;
        }
    }
    @Override
    public String toString(){
        return getClass().getName() + "{" + item + "} Seal: " + seal;
    }
    @Override
    public boolean pack(T item) {
        if (isEmpty()){
            this.item=item;
            this.seal=true;
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean isEmpty() {
        return item==null;
    }
}
