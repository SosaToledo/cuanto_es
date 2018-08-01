package com.luciano.thinco.cuanto_es;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button btnCalcular;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<participantes> participantes;
    private participantes participante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Muy importante que esta linea se ejecute antes del super oncreate.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etMonto = (EditText) findViewById(R.id.etMonto);
        etCantidad = (EditText) findViewById(R.id.etCantidad);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        participantes = new ArrayList<participantes>();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(false);

        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(this,2);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new participantesAdapter(participantes);
        recycler.setAdapter(adapter);
    }

    public void agregar(View view) {
        if(etNombre.getText().toString().length()==0 || etMonto.getText().toString().length()==0){
            Toast.makeText(this,"Falto el monto o el nombre.", Toast.LENGTH_SHORT).show();
        }else{
            participante = new participantes(etNombre.getText().toString().trim(), Integer.valueOf(etMonto.getText().toString()));
            if (!participantes.contains(participante)){
                participantes.add(participante);
                etMonto.setText("");
                etNombre.setText("");
                adapter.notifyDataSetChanged();
                etNombre.requestFocus();
            } else{
                Toast.makeText(this, "Cambie el nombre del participante", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Calcular(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        int size = participantes.size();
        if (etCantidad.getText().toString().equals("")) {
            dialog.setTitle("El campo de participantes esta vacio");
            dialog.setMessage("No se puede dividir por 0, matemática de primaria");
        }else{
            float cant = Float.valueOf(etCantidad.getText().toString());
            if (size > cant){
                dialog.setTitle("la cantidad de participantes es poca");
                dialog.setMessage("debe ser mayor o igual a "+size);
            }else{
                dialog.setTitle("la cuenta se divide así");
                dialog.setMessage("");
            }
        }
        dialog.show();
    }
}
