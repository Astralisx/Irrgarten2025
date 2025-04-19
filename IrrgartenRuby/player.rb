# encoding: UTF-8

require_relative 'dice'
require_relative 'weapon'
require_relative 'shield'
require_relative 'directions'

module Irrgarten
    class Player
        @@MAX_WEAPONS = 2
        @@MAX_SHIELDS = 3
        @@INITIAL_HEALTH = 10
        @@HITS2LOSE = 3
        @@INVALID_POS = -1

        def initialize(number, intelligence, strength)
            @number = number.to_s
            @name = "Player #{@number}"
            @intelligence = intelligence.to_f
            @strength = strength.to_f
            @health = @@INITIAL_HEALTH
            @weaponsArray = Array.new
            @shieldsArray = Array.new
            @row = @@INVALID_POS
            @col = @@INVALID_POS
            @consecutive_hits = 0
        end

        def resurrect
            @health = @@INITIAL_HEALTH
            @weaponsArray.clear
            @shieldsArray.clear
            reset_hits
        end

        attr_reader :name
        attr_reader :row
        attr_reader :col
        attr_reader :number

        def set_pos(row, col)
            @row = row
            @col = col
        end

        def dead
            @health <= 0
        end

        def move(direction, valid_moves)
            size = valid_moves.length
            contained = valid_moves.include?(direction)
            if size > 0 && !contained
                valid_moves[0]
            else
                direction
            end
        end

        def attack
            @strength + sum_weapons
        end

        def defend(received_attack)
            manage_hit(received_attack)
        end

        def receive_reward
            wReward = Dice.weapons_reward
            sReward = Dice.shields_reward

            wReward.times { receive_weapon(new_weapon) }
            sReward.times { receive_shield(new_shield) }

            @health += Dice.health_reward
        end

        def to_s
            to_weapons = "[" + @weaponsArray.map(&:to_s).join(", ") + "]"
            to_shields = "[" + @shieldsArray.map(&:to_s).join(", ") + "]"
            formato = '%.10f'

            "#{@name}[i:#{format(formato,@intelligence)}, s:#{format(formato,@strength)}, " +
            "h:#{format(formato,@health)}, ch:#{@consecutive_hits}, p:(#{@row}, #{@col}), " +
            "w: #{to_weapons}, sh: #{to_shields} ]"
        end

        private
        #  &:discard es una forma corta de decir “llama al método discard en cada elemento”.
        def receive_weapon(w)
            @weaponsArray.delete_if(&:discard)
            @weaponsArray.push(w) if @weaponsArray.length < @@MAX_WEAPONS
        end

        def receive_shield(s)
            @shieldsArray.delete_if(&:discard)
            @shieldsArray.push(s) if @shieldsArray.length < @@MAX_SHIELDS
        end

        def new_weapon
            Weapon.new(Dice.weapon_power, Dice.uses_left)
        end

        def new_shield
            Shield.new(Dice.shield_power, Dice.uses_left)
        end

        def sum_weapons
            @weaponsArray.sum(&:attack)
        end

        def sum_shields
            @shieldsArray.sum(&:protect)
        end

        def defensive_energy
            @intelligence + sum_shields
        end

        def manage_hit(received_attack)
            defense = defensive_energy
            if defense < received_attack
                got_wounded
                inc_consecutive_hits
            else
                reset_hits
            end

            lose = (@consecutive_hits == @@HITS2LOSE) || dead
            reset_hits if lose
            lose
        end

        def reset_hits
            @consecutive_hits = 0
        end

        def got_wounded
            @health -= 1
        end

        def inc_consecutive_hits
            @consecutive_hits += 1
        end
    end
end
