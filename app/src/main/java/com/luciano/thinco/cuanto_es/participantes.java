package com.luciano.thinco.cuanto_es;

public class participantes {
    private String nombre;
    private float monto;
    private float saldo;

//    generador sin saldo
    public participantes(String nombre, float monto) {
        this.nombre = nombre;
        this.monto = monto;
        this.saldo = 0f;
    }
//    generador con saldo
    public participantes(String nombre, float monto, float saldo) {
        this.nombre = nombre;
        this.monto = monto;
        this.saldo = saldo;
    }
//    generador vacio
    public participantes(){
        this.nombre = "";
        this.monto = 0f;
        this.saldo = 0f;
    }

    public String getNombre() {
        return nombre;
    }

    public float getMonto() {
        return monto;
    }
    public float getSaldo() {return saldo;}
    public void setSaldo(float s) {this.saldo=s;}

    @Override
    public boolean equals(Object obj) {
        participantes o = (participantes) obj;
//        es una locura decir que son iguales solo por el nombre pero a nosotros no nos importa el resto, solo queremos que se pongan nombres distintos.
        if (o.getNombre().equals(this.nombre) ){
            return true;
        }else{
            return false;
        }
//        return super.equals(obj);
    }
}
