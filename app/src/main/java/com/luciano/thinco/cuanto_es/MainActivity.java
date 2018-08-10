package com.luciano.thinco.cuanto_es;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    /*
    Declarar instancias globales
    */
    private EditText etNombre;
    private EditText etMonto;
    private EditText etCantidad;
    private ImageButton btnCalcular;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<participantes> participantes;
    private participantes participante;
    private String TAG = "debug";
    private boolean sePuedeBorrar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etMonto = (EditText) findViewById(R.id.etMonto);
        etCantidad = (EditText) findViewById(R.id.etCantidad);
        btnCalcular = findViewById(R.id.btnCalcular);
        participantes = new ArrayList<participantes>();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(false);

        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new participantesAdapter(participantes);
        recycler.setAdapter(adapter);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calcular(view);
            }
        });
    }

    public void agregar(View view) {
        etNombre.requestFocus();
        if (etNombre.getText().toString().length() == 0 || etMonto.getText().toString().length() == 0) {
            Toast.makeText(this, "Faltó el monto o el nombre.", Toast.LENGTH_SHORT).show();
        } else {
            participante = new participantes(etNombre.getText().toString().trim(), Integer.valueOf(etMonto.getText().toString()));
            if (!participantes.contains(participante)) {
                participantes.add(participante);
                etMonto.setText("");
                etNombre.setText("");
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Cambie el nombre del participante", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Calcular(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog, null);

        builder.setView(v);

//            aca se realizan las llamadas al findviewbyid
        TextView tvQuienPusoMas = v.findViewById(R.id.tvQuienPusoMas);
        TextView tvDeudores = v.findViewById(R.id.tvDeudores);
        TextView tvSobrante = v.findViewById(R.id.tvSobrante);
        TextView tvTitulo = v.findViewById(R.id.tvTitulo);
        Button btnCerrarDialog = v.findViewById(R.id.btnCerrarDialog);

//            ahora se realizan los oncliclistener


        int size = participantes.size();
        if (etCantidad.getText().toString().equals("") || size==0) {
            tvTitulo.setText(R.string.el_campo_esta_vacio);
            tvQuienPusoMas.setText(R.string.no_podes_dividir_por_cero);
            tvDeudores.setText("");
            tvSobrante.setText("");
        } else {
            float cant = Float.valueOf(etCantidad.getText().toString());
            if (size > cant ) {
                tvTitulo.setText(R.string.pocos_participantes);
                tvQuienPusoMas.setText(getString(R.string.tamano_minimo) + size);
                tvDeudores.setText("");
                tvSobrante.setText("");
            } else {
                sePuedeBorrar = true;
                //        generamos un nuevo array con todas las personas y su saldo correspondiente
                List<participantes> participantesConSaldo = new ArrayList<participantes>();
                //un acumulador para sacar el promedio
                float acumulador = 0;
//              recorremos una vez para generar el prom
                for (participantes p : participantes) {
                    acumulador += p.getMonto();
                }
                int cantidad = Integer.valueOf(etCantidad.getText().toString());
//              el promedio que tiene que poner cada uno
                float promedio = acumulador / cantidad;

//              recorremos una seg vez con el prom para generar el valor de saldo
//              acumulador para los saldos negativos y las variables para saber cual es el monto mayor
                float saldoNegativo = 0f;
                float mayorMonto = 0f;
                participantes mayorPostor = new participantes();

                int i = 1;
                while (cantidad > participantes.size()) {
                    participantes.add(new participantes("Persona " + i, 0f, 0f));
                    i++;
                }
                for (participantes p : participantes) {
                    participantesConSaldo.add(new participantes(p.getNombre(), p.getMonto(), p.getMonto() - promedio));
//                  buscamos los negativos y los sumamos
                    if (p.getMonto() - promedio < 0) {
                        saldoNegativo += p.getMonto() - promedio;
                    }
//                  buscamos el mayor saldo y guardamos monto y nombre
                    if (p.getMonto() > mayorMonto) {
                        mayorPostor = new participantes(p.getNombre(), p.getMonto(), p.getMonto() - promedio);
                        mayorMonto = p.getMonto();
                    }
                }
//              cuando termina ya tenemos todos los saldos de todas las personas, el mayor postor y el total del saldo negativo
                String resultado = new String("La persona que mas dinero puso fue " + mayorPostor.getNombre() + " tiene que recibir: $" + saldoNegativo * (-1) + " cuando todos los que deben dinero aporten su parte. \n");
                String resultadoNegativo = "Estas personas deben darle dinero a " + mayorPostor.getNombre() + ". \n";
                String resultadoPositivo = "Estas personas van a recibir plata de " + mayorPostor.getNombre() + ". \n";

                for (participantes p : participantesConSaldo) {
                    if (!p.getNombre().equals(mayorPostor.getNombre()) && p.getSaldo() != mayorMonto && p.getSaldo() > 0) {
                        resultadoPositivo += new String(" ൭ " + p.getNombre() + " debe recibir $" + p.getSaldo() + "\n");
                    }
                    if (p.getSaldo() < 0) {
//                      ൭   \n ൭ Juan
                        resultadoNegativo += new String(" ൭ " + p.getNombre() + " debe poner -$" + p.getSaldo() * -1 + "\n");
                    }
                }
                tvTitulo.setText(R.string.las_cuentas_se_dividen_asi);
                tvQuienPusoMas.setText(resultado);
                tvDeudores.setText(resultadoNegativo);
                tvSobrante.setText(resultadoPositivo);
            }
        }
//        ############## FIN  ######################3
        final AlertDialog d = builder.create();
        btnCerrarDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar dialog y reiniciar lista de jugadores, tambien notificar al adapter
                if (sePuedeBorrar){
                    participantes.clear();
                    adapter.notifyDataSetChanged();
                    etCantidad.setText("");
                }
                d.dismiss();
            }
        });
        d.show();

    }
}
