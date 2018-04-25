package com.example.syl.pokedex.global.model;

public class Pokemon {

    String name;
    Sprites sprites;

    Pokemon(String name, Sprites sprites) {
        this.name = name;
        this.sprites = sprites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", sprites='" + sprites.toString() + '\'' +
                '}';
    }
}
