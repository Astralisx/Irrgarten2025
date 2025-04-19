# encoding: UTF-8

require_relative 'dice'

module Irrgarten
  class Weapon
    def initialize(power, uses)
      @power = power.to_f
      @uses = uses.to_i
    end

    def attack
      if @uses > 0
        @uses -= 1
        return @power
      else
        return 0.0
      end
    end

    def discard
      return Dice.dicard_element(@uses)
    end

    def to_s
      return "W[#{@power}, #{@uses}]"
    end
  end
end
