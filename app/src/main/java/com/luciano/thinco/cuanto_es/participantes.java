package com.luciano.thinco.cuanto_es;

public class participantes {
    private String nombre;
    private float monto;

    public participantes(String nombre, float monto) {
        this.nombre = nombre;
        this.monto = monto;
    }

    public String getNombre() {
        return nombre;
    }

    public float getMonto() {
        return monto;
    }

}
