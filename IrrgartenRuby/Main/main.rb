require_relative '../Control/controller'
require_relative '../UI/textUI'
require_relative '../Juego/game'

module Main
    class Main
        def self.play
            juego = Irrgarten::Game.new(2)
            vista = UI::TextUI.new
            controlador = Control::Controller.new(juego,vista)
            controlador.play
        end
    end

    Main.play
end