//package CSE_102.LAB;

import java.text.DecimalFormat;
import java.util.Scanner;

// import java.math.*;
public class Ex1_20190808022 {
    public static void main(String[] args){
        //Test program for Stock
        Stock newstock = new Stock("ORCL", "Oracle Corporation");
        newstock.setCurrentPrice(34.34);
        newstock.setPreviousClosingPrice(34.5);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("Percentage of change is %"+ decimalFormat.format(newstock.getChangePercent(newstock)));
        //Test program for Fan
        Scanner scan = new Scanner(System.in);
        int i;
        System.out.println("Please enter the number of fans:");
        i = scan.nextInt();
        Fan[] myArray = new Fan[i];
        for (int k=0; k<i; k++){
            if (k+1%2 == 1){
                myArray[k]=new Fan();
            }
            else{
                myArray[k]=new Fan(6, "yellow");
            }
        }
    }   //Stock class
    static class Stock{
        String symbol;
        String name;
        double previousClosingPrice;
        double currentPrice;
        Stock(String symbol,String name){
            this.symbol=symbol;
            this.name=name;
        }
        public double getChangePercent(Stock stock){
            double change = Math.abs(stock.currentPrice- stock.previousClosingPrice);
            double percentage = change/stock.previousClosingPrice*100;
            return percentage;
        }
        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }
        public double getCurrentPrice() {
            return currentPrice;
        }
        public void setPreviousClosingPrice(double previousClosingPrice) {
            this.previousClosingPrice = previousClosingPrice;
        }
        public double getPreviousClosingPrice() {
            return previousClosingPrice;
        }
    }
    static class Fan{
        final int SLOW = 1;
        final int MEDIUM = 2;
        final int FAST = 3;
        private int speed = 1;
        private boolean on = false;
        private double radius = 5;
        String color = "blue";
        Fan (){}
        Fan (double radius, String color){
            this.radius=radius;
            this.color=color;
        }
        public void setSpeed(int speed) {
            if (on){
                this.speed = speed;
            }
        }
        public int getSpeed() {
            return speed;
        }
        public void setOn(boolean on) {
            this.on = on;
        }
        public boolean getOn() {
            return on;
        }
        public void setRadius(double radius) {
            this.radius = radius;
        }
        public double getRadius() {
            return radius;
        }
        public void setColor(String color) {
            this.color = color;
        }
        public String getColor() {
            return color;
        }
        public String toString(Fan fan){
            String result;
            if (fan.on == true){
                result = "Fan speed is: "+ fan.getSpeed()+"\nColor is "+ fan.getColor() +"\nRadius is "+fan.getRadius();
            }
            else{
                result = "Color is "+ fan.getColor() + "\nRadius is "+ fan.getRadius()+ "\nfan is off";
            }
            return result;
        }
        //Second challange method
        public void challange (Fan[] fanArray){
            for (int i =0; i<fanArray.length;i++){
                if (i%3==2){
                    if (fanArray[i].getSpeed()==3){
                        fanArray[i].setSpeed(1);
                    }
                    else{
                        fanArray[i].setSpeed(fanArray[i].getSpeed()+1);
                    }
                }
            }
        }
    }
}
