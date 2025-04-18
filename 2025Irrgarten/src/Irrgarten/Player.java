package Irrgarten;
import java.util.ArrayList;


public class Player {
    private static final int MAX_WEAPONS=2;
    private static final int MAX_SHIELDS=3;
    private static final int INITIAL_HEALTH=10;
    private static final int HIT2LOSE=3;
    private final String name;
    private final char number;  
    private final float intelligence;
    private final float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits=0;
    private static final int INVALID_POS = -1;

    private final ArrayList<Weapon> weaponArray = new ArrayList<>();
    private final ArrayList<Shield> shieldArray = new ArrayList<>();

    public Player(char number, float intelligence, float strength ){
        this.name = String.format("Player #%c",number);
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.consecutiveHits=0;
        this.health=INITIAL_HEALTH;
        this.row = INVALID_POS;
        this.col = INVALID_POS;
    }
    
    public void resurrect(){
        this.resetHits();
        this.health = INITIAL_HEALTH;
        this.shieldArray.clear();
        this.weaponArray.clear();
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public char getNumber(){
        return this.number;
    }

    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean dead(){
        return (this.health<=0);
    }

    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);

        Directions toReturn;

        if ((size > 0) && !contained){ // El jugador se puede mover, pero no en la dirección deseada
            toReturn = validMoves.get(0);
        }
        else{
            toReturn = direction;
        }
        // Si size==0, se devuelve la dirección deseada y ya se comprobará en putPlayer2D que no se puede mover

        return toReturn;
    }
    
    public float attack(){
        return this.strength + sumWeapons();
    }
    public boolean defend(float receivedAttack){
        return manageHit(receivedAttack);
    }

    public void receiveReward(){
        int wReward =Dice.weaponsReward();
        int sReward = Dice.shieldReward();
        for (int i=0; i<wReward;i++){
            this.receiveWeapon(this.newWeapon());
        }
        for (int i=0; i<sReward;i++){
            this.receivedShield(this.newShield());
        }
        this.health += Dice.healthReward();
    }

    @Override
    public String toString(){
        final String FORMAT = "%.6f";
        String toReturn= this.name+"[i:"+String.format(FORMAT, intelligence)+", s:"+String.format(FORMAT, strength);
        toReturn+=", h:"+String.format(FORMAT, health)+", ch:"+this.consecutiveHits +", p:("+this.row+", "+this.col+"), ";
        
        // Bucles para mostrar con un formato determinado el array de
        // armas y escudos del jugador
        String toWeapons="[";
        int tamWeapons=this.weaponArray.size();
        for(int i=0; i<tamWeapons-1; i++){
            toWeapons+=this.weaponArray.get(i).toString()+", ";
        }
        if (tamWeapons>0)
            toWeapons+=this.weaponArray.get(tamWeapons-1);
        toWeapons+="]";
        
        String toShields="[";
        int tamShields=this.shieldArray.size();
        for(int i=0; i<tamShields-1; i++){
            toShields+=this.shieldArray.get(i).toString()+", ";
        }
        if (tamShields>0)
            toShields+=this.shieldArray.get(tamShields-1);
        toShields+="]";
        
        // Definimos el formato final para el toString
        toReturn+="w:" + toWeapons+", sh:"+toShields+" ]";
        
        return toReturn;
    }

  




//--------------------------------------Privados

    private void receiveWeapon(Weapon w){
        for (int i=0; i<weaponArray.size(); i++){
            if (weaponArray.get(i).discard()){
                weaponArray.remove(i);
                i--;
            }
        }

        // Añadimos el escudo al jugador si cabe
        if (weaponArray.size() < MAX_WEAPONS)
            weaponArray.add(w);
    }

    private void receivedShield(Shield s){
        for (int i=0; i<shieldArray.size(); i++){
            if (shieldArray.get(i).discard()){
                shieldArray.remove(i);
                i--;
            }
        }

        // Añadimos el escudo al jugador si cabe
        if (shieldArray.size() < MAX_SHIELDS)
            shieldArray.add(s);

    }

    private Weapon newWeapon(){
        Weapon weapon = new Weapon(Dice.weaponPower(), Dice.usesLeft());
        return weapon;
    }

    private Shield newShield(){
        Shield shield = new Shield(Dice.shieldPower(), Dice.usesLeft());
        return shield;
    }

    private float sumWeapons(){
        float sum = 0;
        for(Weapon weapon : weaponArray){
            sum += weapon.attack();
        }
        return sum;
    }

    private float sumShields(){
        float sum=0;
        for(Shield shield : shieldArray){
            sum += shield.protect();
        }
        return sum;
    }
    
    private float defensiveEnergy(){
        return this.intelligence + sumShields();
    }

    private boolean manageHit(float receivedAttack){
        float defense = defensiveEnergy();
        boolean lose;
        if(defense < receivedAttack){
            gotWounded();
            incConsecutiveHits();
        }
        else{
            resetHits();
        }

        if((consecutiveHits == HIT2LOSE) || dead()){
            resetHits();
            lose=true;
        }
        else{
            lose =false;
        }
        return lose;
    }

    private void resetHits(){
        this.consecutiveHits=0;
    }

    private void gotWounded(){
        this.health--;
    }

    private void incConsecutiveHits(){
        this.consecutiveHits ++;
    }

}
