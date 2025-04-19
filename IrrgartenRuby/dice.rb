# encoding: UTF-8
module Irrgarten
    class Dice
        @@MAX_USES = 5
        @@MAX_INTELLIGENCE = 10.0
        @@MAX_STRENGTH = 10.0
        @@RESURRECT_PROB = 0.3
        @@WEAPONS_REWARD = 2
        @@SHIELDS_REWARD = 3
        @@HEALTH_REWARD = 5
        @@MAX_ATTACK = 3
        @@MAX_SHIELD = 2

        @@generator = Random.new

        def self.random_pos(max)
            @@generator.rand(max.to_i)
        end

        def self.who_starts(nplayers)
            @@generator.rand(nplayers.to_i)
        end
        #Nunca se toma el valor escrito como parametro, es por este motivo
        #que se suma uno en los reward. En el caso de no escribir to_f, nos
        #devolveria un int porque el valor de los objetos de clase son enteros
        def self.random_intelligence
            @@generator.rand(@@MAX_INTELLIGENCE.to_f)
        end

        def self.random_strength
            @@generator.rand(@@MAX_STRENGTH.to_f)
        end

        def self.resurrect_player
            (@@generator.rand <= @@RESURRECT_PROB)
        end

        def self.weapons_reward
            @@generator.rand(@@WEAPONS_REWARD.to_i + 1)
        end

        def self.shields_reward
            @@generator.rand(@@SHIELDS_REWARD.to_i + 1)
        end

        def self.health_reward
            @@generator.rand(@@HEALTH_REWARD.to_i + 1)
        end

        def self.weapon_power
            @@generator.rand(@@MAX_ATTACK.to_f)
        end

        def self.shield_power
            @@generator.rand(@@MAX_SHIELD.to_f)
        end

        def self.uses_left
            @@generator.rand(@@MAX_USES.to_i + 1)
        end

        def self.intensity(competence)
            @@generator.rand(competence.to_f)
        end

        def self.dicard_element(uses_left)
            probabilidad = uses_left.to_f / @@MAX_USES.to_f
            (@@generator.rand >= probabilidad)
        end
    end
end
