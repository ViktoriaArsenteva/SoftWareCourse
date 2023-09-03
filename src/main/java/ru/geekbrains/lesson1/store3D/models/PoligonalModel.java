package ru.geekbrains.lesson1.store3D.models;

import java.util.List;

public class PoligonalModel {


    private List<Poligon> poligins;

    private List<Texture> textures;

    public List<Poligon> getPoligins() {
        return poligins;
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public PoligonalModel(List<Poligon> poligins) {
        this.poligins = poligins;
    }

    public PoligonalModel(List<Poligon> poligins, List<Texture> textures) {
        this.poligins = poligins;
        this.textures = textures;
    }
}
