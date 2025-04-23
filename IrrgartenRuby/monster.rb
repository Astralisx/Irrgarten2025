require_relative 'dice'
module Irrgarten
    class Monster
        @@INITIAL_HEALTH = 5
        attr_reader :name , :intelligence, :strength , :health , :row , :col , :INITIAL_HEALTH
        def initialize(name, intelligence , strenght)
            @name=name
            @intelligence = intelligence.to_f
            @strength = strenght.to_f
            @health = @@INITIAL_HEALTH.to_f
            @row = 0
            @col = 0
        end


        def dead
            return @health <= 0
        end
        
        def attack
            return Dice.intensity(@strenght)
        end


        def set_pos(row,col)
            @row = row
            @col = col 
        end


        def to_s
            
            # Formato para mostrar los datos flotantes del monstruo
            formato= '%.10f'

            return "M[#{@name}, i:#{format(formato,@intelligence)}, "+
                    "s:#{format(formato,@strength)}, h:#{format(formato,@health)}, "+
                    "p:(#{@row}, #{@col})]"
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


        def got_wounded
            if @health >= 1 
                @health -=1
            else 
                @health = 0
            end
        end

    end
end