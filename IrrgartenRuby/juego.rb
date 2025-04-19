
require_relative 'Control/controller'
require_relative 'UI/textUI'
require_relative 'game'

module Irrgarten
    juego = Irrgarten::Game.new(2)
    vista = UI::TextUI.new
    controlador = Control::Controller.new(juego,vista)
    controlador.play
   
end
