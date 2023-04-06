import java.util.ArrayList;
import java.util.Arrays;

public class Ex4_20190808022 {
    public static void main(String[] args){
    }
}
class Computer {
    protected CPU cpu;
    protected RAM ram;
    public Computer (CPU cpu, RAM ram){
        this.cpu = cpu;
        this.ram = ram;
    }
    public void run(){
        int DiagonalSum = 0;
        for (int i = 0; i < ram.getCapacity(); i++){
            for (int j = 0; j < ram.getCapacity(); j++){
                if (i == j){
                    int value = ram.getValue(i, j);
                    DiagonalSum = cpu.compute(DiagonalSum, value);
                }
            }
        }
        ram.setValue(0, 0, DiagonalSum);
    }
    public String toString(){
        return "Computer: " + cpu + " " +ram;
    }
}
class Laptop extends Computer{
    private int milliAmp;
    private int battery;
    public Laptop(CPU cpu, RAM ram, int milliAmp) {
        super(cpu, ram);
        this.milliAmp = milliAmp;
        this.battery = 30;
    }
    public int batteryPercentage(){
        return battery;
    }
    public void charge () {
        while (battery <= 90){
            battery += 2;
        }
    }
    @Override
    public void run(){
        if (battery > 5){
            super.run();
            battery -= 3;
        }
        else{
            charge();
        }
    }
    public String toString(){
        return super.toString() + " " + battery;
    }
}
class Desktop extends Computer{
    private ArrayList<String> peripherals;
    public Desktop(CPU cpu, RAM ram, String... peripherals) {
        super(cpu, ram);
        this.peripherals = new ArrayList<>(Arrays.asList(peripherals));
    }
    @Override
    public void run(){
        super.run();
    }
    public void plugIn(String peripheral){
        peripherals.add(peripheral);
    }
    public String plugOut(){
        if (peripherals.size() == 0){
            return null;
        }
        else{
            String peripheral = peripherals.get(peripherals.size()-1);
            peripherals.remove(peripherals.size()-1);
            return peripheral;
        }
    }
    public String plugOut(int index){
        if (index < 0 || index > peripherals.size()){
            return null;
        }
        else{
            String peripheral = peripherals.get(index);
            peripherals.remove(index);
            return peripheral;
        }
    }
    public String toString(){
        return super.toString() + " " + peripherals;
    }
}
class CPU {
    private String name;
    private double clock;
    public CPU(String name, double clock) {
        this.name = name;
        this.clock = clock;
    }
    public String getName() {
        return name;
    }
    public double getClock() {
        return clock;
    }
    public int compute (int a, int b){
        return a+b;
    }
    public String toString(){
        return "CPU: "  + name + " " + clock + "Ghz";
    }
}
class RAM {
    private String type;
    private int capacity;
    private int[][] memory;
    public RAM(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
        initMemory();
    }
    public String getType() {
        return type;
    }
    public int getCapacity() {
        return capacity;
    }
    public String toString(){
        return "RAM: " + type + " " + capacity + "GB";
    }
    private void initMemory(){
        memory = new int[capacity][capacity];
        for (int i = 0; i < capacity; i++){
            for (int j = 0; j < capacity; j++){
                memory[i][j] = (int) Math.random()*(10)+1;
            }
        }
    }
    private boolean check (int i, int j){
        if (i < 0 || i >= capacity || j < 0 || j >= capacity)
            return false;
        return true;
    }
    public int getValue (int i, int j){
        if (check(i, j))
            return memory[i][j];
        return -1;
    }
    public void setValue (int i, int j, int value){
        if (check(i, j))
            memory[i][j] = value;
    }
}