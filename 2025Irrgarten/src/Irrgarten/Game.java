package Irrgarten;

import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    private Labyrinth labyrinth;
    private ArrayList<Monster> monstersArray = new ArrayList<>();
    private ArrayList<Player> playersArray = new ArrayList<>();
    private Player currentPlayer;


    public Game(int nPlayers){
        this.labyrinth = new Labyrinth(5, 5, 0, 0)
        this.log = "";
        for (int i = 0; i < nPlayers; i++){
            Player player = new Player(Character.forDigit(i, 10), Dice.randomIntelligence(), Dice.randomStrength());
            this.playersArray.add(player);
        }
        this.currentPlayerIndex = Dice.whoStarts(nPlayers);
        this.currentPlayer = this.playersArray.get(this.currentPlayerIndex);

        this.ConfigureLabyrinth();
        this.labyrinth.spreadPLayers(playersArray);
        
    }

    public boolean finished(){
        return this.labyrinth.haveAWinner();
    }

    public boolean nextStep(Directions preferredDirection){
        String log = "";
        Monster monster;
        boolean dead = this.currentPlayer.dead();
        if (!dead){
            Directions direction = actualDirection(preferredDirection);
            if (direction != preferredDirection){
                logPlayerNoOrders();
            }
            monster = this.labyrinth.putPlayer(direction, currentPlayer)
        

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
        Monster m1 = new Monster("m1", 1, 1);
        Monster m2 = new Monster("m2", 10, 10);
        this.labyrinth.addMonster(0, 1, m1);
        this.labyrinth.addMonster(3, 3, m2);
        this.monstersArray.add(m1);
        this.monstersArray.add(m2);
    }

    private void nextPlayer(){
        if(this.currentPlayerIndex < playersArray.size()-1){
            this.currentPlayerIndex+=1;
            this.currentPlayer = this.playersArray.get(this.currentPlayerIndex);
        }
        else{
            this.currentPlayerIndex = 0;
            this.currentPlayer = this.playersArray.get(0);
        }

    }

    private Directions actualDirection(Directions preferredDirection){
        int currentRow = this.currentPlayer.getRow();
        int currentCol = this.currentPlayer.getCol();

        ArrayList<Directions> validMoves = new ArrayList<>();
        validMoves = this.labyrinth.validMoves(currentRow, currentCol);

        Directions output = this.currentPlayer.move(preferredDirection, validMoves)
        return output;
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
                lose = monster.defend(monsterAttack);
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
        this.log+="Acabas de resucitar\n";
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
