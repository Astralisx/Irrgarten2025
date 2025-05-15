require_relative 'player'

module Irrgarten
    class FuzzyPlayer < Player
        def initialize(other)
            copy(other)
        end

        def move(direction, valid_moves)
            #Si no se pone paréntesis en el super se pasan los argumentos de la funcion automaticamente
            Dice.nextStep(super, valid_moves, intelligence)
        end

        def attack
            return self.sum_weapons + Dice.intensity(@strength)
        end

        
        def to_s
            return "(Fuzzy) " + super
        end

        protected
        def defensive_energy
            return self.sum_shields + Dice.intensity(@intelligence)
        end

    end
end