
require_relative '../game'
require_relative '../UI/textUI'
require_relative '../directions'

module Control
  class Controller
    def initialize(game,view)
      @game = game
      @view = view
    end

    def play
      end_of_game = false
      while (!end_of_game)
        @view.show_game(@game.game_state) 
        direction = @view.next_move 
        end_of_game = @game.next_step(direction)
      end
      @view.show_game(@game.game_state)
      if (@game.game_state.winner)
        puts "Ha ganado el jugador: " + @game.game_state.current_player.to_s
      end
    end
  end # class   
end # module        