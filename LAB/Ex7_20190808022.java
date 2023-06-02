import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ex7_20190808022 {
    public static void main(String[] args){
    }
}
interface Damageable{
    public void takeDamage(int damage);
    public void takeHealing(int healing);
    public boolean isAlive();
}
interface Caster{
    public void castSpell (Damageable target);
    public  void learnSpell(Spell spell);
}
interface Combat extends Damageable{
    public void attack(Damageable target);
    public void lootWeapon(Weapon weapon);
}
interface Useable {
    public int use();
}
class Spell implements Useable{
    private int minHeal;
    private int maxHeal;
    private String name;
    public Spell(String name, int minHeal, int maxHeal){
        this.name=name;
        this.minHeal=minHeal;
        this.maxHeal=maxHeal;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    private int heal(){
        int r = (int) (Math.random()*((maxHeal-minHeal)+1))+minHeal;
        return r;
    }
    @Override
    public int use() {
        return this.heal();
    }
    
}
class Weapon implements Useable{
    private int minDamage;
    private int maxDamage;
    private String name;
    public Weapon(String name, int minDamage, int maxDamage){
        this.name=name;
        this.maxDamage=maxDamage;
        this.minDamage=minDamage;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    private int attack(){
        int r = (int) (Math.random()*((maxDamage-minDamage)+1))+minDamage;
        return r;
    }
    @Override
    public int use() {
        return this.attack();
    }
}
class Attributes {
    private int strength;
    private int intelligence;
    public Attributes(){
        intelligence = 3;
        strength = 3;
    }
    public Attributes(int strength, int intelligence){
        this.intelligence = intelligence;
        this.strength = strength;
    }
    public void increaseStrength(int amount){
        strength += amount;
    }
    public void increaseIntelligence(int amount){
        intelligence += amount;
    }
    public int getStrength(){
        return strength;
    }
    public int getIntelligence(){
        return intelligence;
    }
    @Override
    public String toString(){
        return "Attributes [Strength = "+ strength+", intelligence = " + intelligence;
    }
}
abstract class Character implements Comparable <Character> {
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;
    public Character(String name, Attributes attributes){
        this.name = name;
        this.attributes = attributes;
    }
    public String getName(){
        return name;
    }
    public int getLevel(){
        return level;
    }
    public abstract void levelUp();
    @Override
    public String toString(){
        return getClass().getSimpleName() + "LvL" + level + " - " + attributes;
    }
}
abstract class PlayableCharacter extends Character implements Damageable{
    private boolean inParty;
    private Party party;
    public PlayableCharacter(String name, Attributes attributes){
        super(name, attributes);
        inParty = false;
    }
    public void joinParty(Party party){
        //TODO
    }
    public void quitParty(){
        //TODO
    }
}
abstract class NonPlayableCharacter extends Character{
    public NonPlayableCharacter(String name, Attributes attributes){
        super(name, attributes);
    }
}
class Merchant extends NonPlayableCharacter {
    public Merchant(String name){
        super(name, new Attributes(0,0));
    }
    @Override
    public void levelUp() {
    }
    @Override
    public int compareTo(Character o) {
        return 0;
    }
}
class Skeleton extends NonPlayableCharacter implements Combat{
    public Skeleton(String name, Attributes attributes) {
        super(name, attributes);
        //TODO Auto-generated constructor stub
    }
    public void lootWeapon(Weapon weapon){
        //TODO
    }
    @Override
    public void levelUp(){
        level++;
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(1);
    }
    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }
    @Override
    public void takeHealing(int healing) {
        health--;
    }
    @Override
    public boolean isAlive() {
        if (health > 0){
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public int compareTo(Character o) {
        if (this.getLevel()> o.getLevel()){
            return 1;
        }
        else if (this.getLevel()< o.getLevel()){
            return -1;
        }
        else{
            return 0;
        }
    }
    @Override
    public void attack(Damageable target) {
        target.takeDamage(1);
    }
    
}
class Warrior extends PlayableCharacter implements Combat{
    private Useable weapon;
    public Warrior(String name){
        super(name, new Attributes(4,2));
        this.health = 35;
    }
    @Override
    public void levelUp(){
        level++;
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(1);
    }
    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }
    @Override
    public void takeHealing(int healing) {
        health += healing;
    }
    @Override
    public boolean isAlive() {
        if (health > 0){
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public int compareTo(Character o) {
        if (this.getLevel()> o.getLevel()){
            return 1;
        }
        else if (this.getLevel()< o.getLevel()){
            return -1;
        }
        else{
            return 0;
        }
    }
    @Override
    public void attack(Damageable target) {
        int r = weapon.use();
        target.takeDamage(r);
    }
    @Override
    public void lootWeapon(Weapon weapon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lootWeapon'");
    }
    
}
class Cleric extends PlayableCharacter implements Caster{
    private Useable spell;
    public Cleric(String name){
        super(name, new Attributes(2,4));
        this.health = 25;
    }
    @Override
    public void levelUp(){
        level++;
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(2);
    }
    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }
    @Override
    public void takeHealing(int healing) {
        health += healing;
    }
    @Override
    public boolean isAlive() {
        if (health > 0){
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public int compareTo(Character o) {
        if (this.getLevel()> o.getLevel()){
            return 1;
        }
        else if (this.getLevel()< o.getLevel()){
            return -1;
        }
        else{
            return 0;
        }
    }
    @Override
    public void castSpell(Damageable target) {
        int r = spell.use();
        target.takeHealing(r);
    }
    @Override
    public void learnSpell(Spell spell) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'learnSpell'");
    }
    
}
class Paladin extends PlayableCharacter implements Combat, Caster{
    private Useable weapon;
    private Useable spell;
    public Paladin(String name){
        super(name, new Attributes());
        this.health = 30;
    }
    @Override
    public void levelUp(){
        if (level % 2 == 0){
        level++;
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(2);
        }
        else{
            level++;
            attributes.increaseStrength(2);
            attributes.increaseIntelligence(1);
        }
    }
    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }
    @Override
    public void takeHealing(int healing) {
        health += healing;
    }
    @Override
    public boolean isAlive() {
        if (health > 0){
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public int compareTo(Character o) {
        if (this.getLevel()> o.getLevel()){
            return 1;
        }
        else if (this.getLevel()< o.getLevel()){
            return -1;
        }
        else{
            return 0;
        }
    }
    @Override
    public void castSpell(Damageable target) {
        int r = spell.use();
        target.takeHealing(r);
    }
    @Override
    public void learnSpell(Spell spell) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'learnSpell'");
    }
    @Override
    public void attack(Damageable target) {
        int r = weapon.use();
        target.takeDamage(r);
    }
    @Override
    public void lootWeapon(Weapon weapon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lootWeapon'");
    }
}
class Party {
    private final int partyLimit = 8;
    private ArrayList<Combat> fighters = new ArrayList<Combat>();
    private ArrayList <Caster> casters = new ArrayList<Caster>();
    private int mixedCount;
    public void addCharacter(PlayableCharacter character) throws AlreadyInPartyException, PartyLimitReachedException{
        if (fighters.contains(character)){
            throw new AlreadyInPartyException("ups");
        }
        else if (casters.contains(character)){
            throw new AlreadyInPartyException("ups");
        }
        if (fighters.size() + casters.size() - mixedCount >= partyLimit){
            throw new PartyLimitReachedException("ups");
        }
        else {
        if (character instanceof Combat){
            fighters.add((Combat) character);
            if(character instanceof Paladin){
                mixedCount++;
            }
        }
        if (character instanceof Caster){
            casters.add((Caster) character);
        }
    }
    }
    public void removeCharacter (PlayableCharacter character) throws CharacterIsNotInPartyException{
        if (fighters.contains(character)){
            fighters.remove(character);
        }
        if (casters.contains(character)){
            casters.remove(character);
        }
        if (!casters.contains(character) && !fighters.contains(character)){
            throw new CharacterIsNotInPartyException("ups");
        }
    }
    public void partyLevelUp(){
        for (Combat fighter : fighters){
            if(!(fighter instanceof Paladin)){
                ((Warrior) fighter).levelUp();
            }
            else if (fighter instanceof Paladin){
                ((Paladin) fighter).levelUp();
            }
        }
        for (Caster caster : casters){
            if (!(caster instanceof Paladin)){
                ((Cleric) caster).levelUp();
            }
        }
    }
    public String toString() {
        List<PlayableCharacter> allCharacters = Stream.concat(fighters.stream(), casters.stream())
                .distinct()
                .map(character -> (PlayableCharacter) character)
                .sorted(Comparator.comparing(PlayableCharacter::getLevel))
                .collect(Collectors.toList());
    
        StringBuilder sb = new StringBuilder();
        for (PlayableCharacter character : allCharacters) {
            sb.append(character.toString());
            sb.append("\n");
        }
    
        return sb.toString();
    }
}
class Barrel implements Damageable{
    private int health=30;
    private int capacity=10;
    public void explode(){
        System.out.println("Explodes");
    }
    public void repair(){
        System.out.println("Repairing");
    }
    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0){
            explode();
        }
    }
    @Override
    public void takeHealing(int healing) {
        repair();
        health += healing;
        if (health > capacity){
            health = capacity;
        }
   }
    @Override
    public boolean isAlive() {
        if (health > 0){
            return true;
        }
        else {
            return false;
        }
    }
}
class TrainingDummy implements Damageable{
    private int health = 25;

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
    }

    @Override
    public boolean isAlive() {
        if (health > 0){
            return true;
        }
        else {
            return false;
        }
    }
    
}
class PartyLimitReachedException extends Exception{
    public PartyLimitReachedException(String string){
        super(string);
    }
}
class AlreadyInPartyException extends Exception{
    public AlreadyInPartyException(String string){
        super(string);
    }
}
class CharacterIsNotInPartyException extends Exception{
    public CharacterIsNotInPartyException(String string){
        super(string);
    }
}