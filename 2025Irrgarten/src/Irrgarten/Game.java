package Irrgarten;

import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    private final Labyrinth labyrinth;
    private final ArrayList<Monster> monstersArray = new ArrayList<>();
    private final ArrayList<Player> playersArray = new ArrayList<>();
    private Player currentPlayer;


    public Game(int nPlayers){
        this.labyrinth = new Labyrinth(5, 5, 0, 0);
        this.log = "";
        for (int i = 0; i < nPlayers; i++){
            Player player = new Player(Character.forDigit(i, 10), Dice.randomIntelligence(), Dice.randomStrength());
            this.playersArray.add(player);
        }
        this.currentPlayerIndex = Dice.whoStarts(nPlayers);
        this.currentPlayer = this.playersArray.get(this.currentPlayerIndex);

        this.ConfigureLabyrinth();
        this.labyrinth.spreadPLayers(playersArray);
        this.log="- Game just started.\n";
        
    }

    public boolean finished(){
        return this.labyrinth.haveAWinner();
    }

    public boolean nextStep(Directions preferredDirection){
        log = "";
        Monster monster;
        boolean dead = this.currentPlayer.dead();
        if (!dead){
            Directions direction = actualDirection(preferredDirection);
            if (direction != preferredDirection){
                logPlayerNoOrders();
            }
            monster = this.labyrinth.putPlayer(direction, currentPlayer);
        

            if (monster == null){
                logNoMonster();
            }
            else {
                GameCharacter winner = combat(monster);
                manageReward(winner);
            }
        }
        else{
            manageResurrection();
        }
        boolean endGame = finished();

        if (!endGame){
            nextPlayer();
        }
        return endGame;
    }

    public GameState getGameState(){
        String cadPlayers = "";
        String cadMonsters = "";
        
        for (Player player : playersArray){
            cadPlayers += player.toString() + "\n";
        }
        for (Monster monster : monstersArray){
            cadMonsters += monster.toString() + "\n";
        }


        GameState gameState = new GameState(this.labyrinth.toString(), cadPlayers, cadMonsters, this.currentPlayerIndex, finished(),this.log);
        return gameState;
    }
// ----------------------------------------------------------------------
    private void ConfigureLabyrinth(){
        this.labyrinth.addBlock(Orientation.VERTICAL, 2, 1,2);
//        Monster m1 = new Monster("Cerbero", 1, 1);
        Monster m2 = new Monster("Medusa", 1000, 1000);
//        this.labyrinth.addMonster(0, 1, m1);
        this.labyrinth.addMonster(3, 3, m2);
//        this.monstersArray.add(m1);
        this.monstersArray.add(m2);
    }

    private void nextPlayer(){
        this.currentPlayerIndex=(this.currentPlayerIndex+1) % this.playersArray.size();
        this.currentPlayer=this.playersArray.get(this.currentPlayerIndex);
        
    }

    private Directions actualDirection(Directions preferredDirection){
        int currentRow = this.currentPlayer.getRow();
        int currentCol = this.currentPlayer.getCol();

        ArrayList<Directions> validMoves = this.labyrinth.validMoves(currentRow, currentCol);

        return this.currentPlayer.move(preferredDirection, validMoves);
    }

    private GameCharacter combat(Monster monster){
        int rounds =0;
        GameCharacter winner = GameCharacter.PLAYER;
        
        float playerAttack = this.currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);

        while (!lose && (rounds < MAX_ROUNDS)){
            winner = GameCharacter.MONSTER;
            rounds++;
            float monsterAttack = monster.attack();
            lose = this.currentPlayer.defend(monsterAttack);
            
            if (!lose){
                playerAttack = this.currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }

        logRounds(rounds, MAX_ROUNDS);
        return winner;
    }
    
    private void manageReward(GameCharacter winner){
        if (winner == GameCharacter.PLAYER){
            this.currentPlayer.receiveReward();
            logPlayerWon();
        }
        else{
            logMonsterWon();
        }
    }

    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();
        if (resurrect){
            this.currentPlayer.resurrect();
            logResurrected();
            
             // Se cambia la posición por el fuzzyplayer
            FuzzyPlayer fuzzy= new FuzzyPlayer(this.currentPlayer);
            this.playersArray.set(this.currentPlayerIndex, fuzzy);
            this.labyrinth.convertToFuzzy(fuzzy);
        }
        else{
            logPlayerSkipTurn();
        }
    }

    private void logPlayerWon(){
        this.log+="Has ganado el combate! \n";
    }
    private void logMonsterWon(){
        this.log+="El monstruo ha ganado el combate! \n";
    }
    private void logResurrected(){
        this.log+= this.currentPlayer.getName() + " resurrected as fuzzy.\n";
    }
    private void logPlayerSkipTurn(){
        this.log+="Has perdido el turno por estar muerto \n";
    }
    private void logPlayerNoOrders(){
        this.log+="No ha sido posible seguir la instrucción\n";
    }
    private void logNoMonster(){
        this.log+="Te has movido a una celda vacia\n";
    }
    private void logRounds(Integer rounds , Integer max){

        this.log+=String.format("Se han jugado %d rondas de %d \n", rounds,max);    
    }
}
