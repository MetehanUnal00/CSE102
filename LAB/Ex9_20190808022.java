public class Ex9_20190808022 {
    
}
interface Sellable {
    public String getName();
    public double getPrice();
}
interface Package <T>{
    public T extract();
    public boolean pack (T item);
    public boolean isEmpty();
    public double getPriority();
}
interface Wrappable extends Sellable{

}
interface Common <T> {
    public boolean isEmpty();
    public T peek();
    public int size();
}

interface Stack <T> extends Common<T> {
    public boolean push(T item);
    public T pop();
}

interface Node <T> {
    public static final int DEFAULT_CAPACITY = 2;
    public void setNext(T item);
    public T getNext();
    public double getPriority();
}

interface PriorityQueue <T> extends Common<T> {
    public static final int FLEET_CAPACITY = 3;
    public boolean enqueue(T item);
    public T dequeue();
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
    @Override
    public double getPriority() {
        return ((Package<T>) item).getPriority();
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
    @Override
    public double getPriority() {
        return ((Package<T>) item).getPriority();
    }
}
class Container implements Stack<Box<Sellable>>, Node<Container>, Comparable<Container> {
    private Box<Sellable>[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;
    
    public Container() {
        this.boxes = new Box[Node.DEFAULT_CAPACITY];
        this.top = -1;
        this.next = null;
        this.priority = 0;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public Box<Sellable> peek() {
        if (isEmpty()) {
            return null;
        }
        return boxes[top];
    }

    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public boolean push(Box<Sellable> item) {
        if (size() == boxes.length) {
            return false;
        }
        boxes[++top] = item;
        priority += item.getPriority();
        return true;
    }

    @Override
    public Box<Sellable> pop() {
        if (isEmpty()) {
            return null;
        }
        Box<Sellable> result = boxes[top];
        boxes[top--] = null;
        priority -= result.getPriority();
        return result;
    }

    @Override
    public void setNext(Container item) {
        next = item;
    }

    @Override
    public Container getNext() {
        return next;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Container o) {
        return Double.compare(this.priority, o.getPriority());
    }

    @Override
    public String toString(){
        return "Container with priority: " + priority;
    }
}

class CargoFleet implements PriorityQueue<Container>{
    private Container head;
    private int size;

    public CargoFleet() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Container peek() {
        return head;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean enqueue(Container item) {
        if (size == PriorityQueue.FLEET_CAPACITY) {
            return false;
        }
        if (isEmpty() || item.compareTo(head) < 0) {
            item.setNext(head);
            head = item;
        } else {
            Container current = head;
            while (current.getNext() != null && current.getNext().compareTo(item) < 0) {
                current = current.getNext();
            }
            item.setNext(current.getNext());
            current.setNext(item);
        }
        size++;
        return true;
    }

    @Override
    public Container dequeue() {
        if (isEmpty()) {
            return null;
        }
        Container result = head;
        head = head.getNext();
        result.setNext(null);
        size--;
        return result;
    }
}

class CargoCompany {
    private Container stack;
    private CargoFleet queue;

    public CargoCompany() {
        this.stack = new Container();
        this.queue = new CargoFleet();
    }

    public void add(Box<Sellable> box) {
        if (!stack.push(box)) {
            if (!queue.enqueue(stack)) {
                ship(queue);
                queue.enqueue(stack);
            }
            stack = new Container();
            add(box);
        }
    }

    private void ship(CargoFleet fleet) {
        while (!fleet.isEmpty()) {
            empty(fleet.dequeue());
        }
    }

    private void empty(Container container) {
        while (!container.isEmpty()) {
            System.out.println(deliver(container.pop()));
        }
    }

    private Sellable deliver(Box<Sellable> box) {
        return box.extract();
    }
}
