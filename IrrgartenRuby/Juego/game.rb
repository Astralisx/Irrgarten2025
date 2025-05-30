# encoding: UTF-8

require_relative 'dice'
require_relative 'player'
require_relative 'monster'
require_relative 'labyrinth'
require_relative 'game_state'
require_relative 'game_character'
require_relative 'orientation'
require_relative 'fuzzy_player'
module Irrgarten
    class Game
        @@MAX_ROUNDS = 10

        # Número de filas del laberinto
        @@ROWS = 4

        # Número de columnas del laberinto
        @@COLS = 4

        def initialize(nplayer)
            @current_player_index = Dice.who_starts(nplayer)
            @log = ""
            @labyrinth = Labyrinth.new(@@ROWS, @@COLS, Dice.random_pos(@@ROWS), Dice.random_pos(@@COLS))
            @monsters = Array.new
            @players = Array.new(nplayer)
            for i in 0...nplayer 
                @players[i] = Player.new(i,Dice.random_intelligence,Dice.random_strength)
            end
            @current_player = @players[@current_player_index]
            self.configure_labyrinth
            @labyrinth.spread_players(@players)
        end
        def finished
            @labyrinth.have_a_winner
        end
        def next_step(preferred_direction)
            @log = ""

            if(!@current_player.dead)
                direction = self.actual_direction(preferred_direction)

                # Se comprueba si la dirección a moverse ha sido la querida
                if(direction != preferred_direction)
                    self.log_player_no_orders
                end

                monster = @labyrinth.put_player(direction, @current_player)
                if(monster == nil)
                    self.log_no_monster
                else
                    winner = self.combat(monster)
                    self.manage_reward(winner)
                end
            else
                self.manage_resurrection
            end

            # Se comprueba si ha ganado ya alguien
            end_game = self.finished()
            if(!end_game)
                self.next_player
            end

            return end_game
    end


        def game_state
            cplayers = ""
            for i in 0...@players.size
                cplayers.concat("#{@players[i].to_s}\n")
            end
            cmonsters = ""

            for i in 0...@monsters.size
                cmonsters.concat("#{@monsters[i].to_s}\n") 
            end

            return GameState.new(@labyrinth.to_s,cplayers,cmonsters,@current_player_index,self.finished,@log)
        end

        
        private 

        def configure_labyrinth
            @labyrinth.add_block(Orientation::HORIZONTAL,1,1,3)
            monster  = Monster.new('m1',10000,10000)
            # monster2  = Monster.new('m2',4,10)
            # monster3  = Monster.new('m3',80,90)
            @labyrinth.add_monster(2,2,monster)
            # @labyrinth.add_monster(3,3,monster2)
            # @labyrinth.add_monster(4,4,monster3)
            @monsters.push(monster)
            # @monsters.push(monster2)
            # @monsters.push(monster3)
            

        end


        def next_player 

            @current_player_index = (@current_player_index + 1) % @players.size
            @current_player = @players[@current_player_index]  
        end


        def actual_direction(preferred_direction)
            row = @current_player.row
            col = @current_player.col
            directions = @labyrinth.valid_moves(row,col)
            return @current_player.move(preferred_direction,directions)
        end 


        def manage_resurrection
            if (Dice::resurrect_player())
                @current_player.resurrect()
                log_resurrected()

                #Conversión a fuzzy
                fuzzy = FuzzyPlayer.new(@current_player)

                @players[@current_player_index] = fuzzy

                @labyrinth.convert_to_fuzzy(fuzzy)
            else 
                log_player_skip_turn()
            end
        end


        def manage_reward (winner)
            if winner == GameCharacter::PLAYER
                @current_player.receive_reward()
                log_player_won
            else 
                log_monster_won
            end

        end


        def combat(monster)
            # Inicializamos los valores
            rounds = 0
            winner = GameCharacter::PLAYER
            player_attack = @current_player.attack()
            # Comienza el jugador atacando
            lose = monster.defend(player_attack)
            # Bucle hasta que finalice el número de rondas posible o haya perdido
            # el monstruo
            while ( (!lose) && (rounds < @@MAX_ROUNDS) )
                winner = GameCharacter::MONSTER
                rounds += 1
                monster_attack = monster.attack()
                # Turno del monstruo de atacar al jugador
                lose = @current_player.defend(monster_attack)
                if(!lose)
                    player_attack = @current_player.attack()
                    winner = GameCharacter::PLAYER
                    lose = monster.defend(@current_player.attack())
                end
            end

            self.log_rounds(rounds, @@MAX_ROUNDS)
            # Devolvemos el ganador
            return winner
        end


        def log_player_won
            @log.concat("¡#{@current_player.number} Ha ganado el combate!\n")
        end
        def log_monster_won
            @log.concat("!El monstruo ha ganado el combate¡\n")
        end
        def log_resurrected
            @log.concat("Acabas de resucitar como Fuzzy\n")
        end
        def log_player_skip_turn
            @log.concat("Has perdido el turno por estar muerto\n")
        end
        def log_player_no_orders()
            @log.concat("No ha sido posible seguir las instrucciones\n")
        end
        def log_no_monster()
            @log.concat("Te has movido a una celda vacía \n")
        end
        def log_rounds(rounds ,max)
            @log += "Se han producido " + rounds.to_s + " rondas de " + max.to_s + "\n"
        end
    end
end