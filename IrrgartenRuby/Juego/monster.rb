require_relative 'dice'
require_relative 'labyrinth_character'
module Irrgarten
    class Monster < LabyrinthCharacter
        @@INITIAL_HEALTH = 5
        
        attr_reader :name , :intelligence, :strength , :health , :row , :col , :INITIAL_HEALTH
        def initialize(name, intelligence , strength)
           super(name, intelligence, strength, @@INITIAL_HEALTH)
        end


        def attack
            return Dice.intensity(@strength)
        end


        def to_s
            
            # Formato para mostrar los datos flotantes del monstruo
            formato= '%.10f'

            return super
        end


        def defend(recieved_attack)
            is_dead = self.dead

            if(!is_dead)
                defensive_energy = Dice.intensity(@intelligence)
                if(defensive_energy < recieved_attack)
                    self.got_wounded
                    is_dead = self.dead
                end
            end
            return is_dead

        end

        private

    end
end