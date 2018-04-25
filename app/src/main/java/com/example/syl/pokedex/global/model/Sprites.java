package com.example.syl.pokedex.global.model;

class Sprites {

    String front_default, front_shiny;

    Sprites(String front_default, String front_shiny) {
        this.front_default = front_default;
        this.front_shiny = front_shiny;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public String getFront_shiny() {
        return front_shiny;
    }

    public void setFront_shiny(String front_shiny) {
        this.front_shiny = front_shiny;
    }

    @Override
    public String toString() {
        return "Sprites{" +
                "front_default='" + front_default + '\'' +
                ", front_shiny='" + front_shiny + '\'' +
                '}';
    }
}
