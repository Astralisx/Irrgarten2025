require_relative '../Control/controller'
require_relative '../UI/textUI'
require_relative '../Juego/game'

module Main
    class Main
        @@NPLAYERS = 1
        def self.play
            juego = Irrgarten::Game.new(@@NPLAYERS)
            vista = UI::TextUI.new
            controlador = Control::Controller.new(juego,vista)
            controlador.play
        end
    end

    Main.play
end