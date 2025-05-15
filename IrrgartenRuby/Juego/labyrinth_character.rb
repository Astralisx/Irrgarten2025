module Irrgarten
    class LabyrinthCharacter
        @@INVALID_POS = -1
        def initialize (name, intelligence, strenght, health)
            @name = name.to_s
            @intelligence = intelligence.to_f
            @strength = strenght.to_f
            @health = health.to_f

            @row = @@INVALID_POS
            @col = @@INVALID_POS
        end

        protected
        attr_reader :name
        attr_reader :intelligence
        attr_reader :strength
        attr_accessor :health

        public
        attr_reader :row
        attr_reader :col

        def set_pos(row, col)
            @row = row
            @col = col
        end

        def copy (other)
            @name = other.name
            @intelligence = other.intelligence
            @strength = other.strength
            @health = other.health
            set_pos(other.row, other.col)
        end

        def dead
            return @health <= 0
        end

        protected
        def got_wounded
            @health -= 1
        end

        public 

        def to_s
            formato= '%.6f'
           
            return "#{@name}[i:#{format(formato,@intelligence)}, s:#{format(formato,@strength)}, " +"h:#{format(formato,@health)}, p:(#{@row}, #{@col})]"
        end
    end
end