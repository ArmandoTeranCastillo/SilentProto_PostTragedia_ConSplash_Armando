package com.example.silentproto;

public class Lugares {
    private String lugar, desc;

    public Lugares() {
        //this.imagen = R.drawable.sunny;
        this.lugar = "Casa";
        this.desc = "Casita del gerundio jsjsjs";
    }

    public Lugares(String lugar, String desc) {
        this.lugar = lugar;
        this.desc=desc;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLugar() {
        return lugar;
    }

    public String getDesc() {
        return desc;
    }
}
