# encoding: UTF-8

require_relative 'combat_element'

module Irrgarten
  class Weapon < CombatElement
    # Se usa constructor de CombatElement por defecto

    def attack
      return produce_effect
    end

    def to_s
      return "W" + super
    end

  end
end
