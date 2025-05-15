# encoding: UTF-8

require_relative 'combat_element'

module Irrgarten
  class Shield < CombatElement
    # Se usa constructor de CombatElement por defecto
    def protect
      produce_effect
    end

    def to_s
      return "S"+ super
    end

  end
end
