package com.luciano.thinco.cuanto_es;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<participantes> participantes;
    private participantes participante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etMonto = (EditText) findViewById(R.id.etMonto);
        etCantidad = (EditText) findViewById(R.id.etCantidad);
        participantes = new ArrayList<participantes>();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new participantesAdapter(participantes);
        recycler.setAdapter(adapter);
    }

    public void agregar(View view) {
        if(etNombre.getText().toString().length()==0 || etMonto.getText().toString().length()==0){
            Toast.makeText(this,"Falto el monto o el nombre.", Toast.LENGTH_SHORT).show();
        }else{
            participante = new participantes(etNombre.getText().toString(), Integer.valueOf(etMonto.getText().toString()));
            if (!participantes.contains(participante)){
                participantes.add(participante);
                etMonto.setText("");
                etNombre.setText("");
                adapter.notifyDataSetChanged();
            } else{
                Toast.makeText(this, " Cambie el nombre del participante", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Calcular(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        int size = participantes.size();
        if (size<2){
            if (size==0){
                dialog.setTitle("Vos tenes problemitas");
                dialog.setMessage("Ingresa al menos dos personas");
            }
            if (size==1){
                dialog.setTitle("Ah pero vos sos loco");
                dialog.setMessage("Poné uno mas, por favor.");
            }
            dialog.setCancelable(true);
            dialog.setPositiveButton("Esta bien", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });
        }else{
            dialog.setTitle("Las cuentas se divien así");
            dialog.setMessage("");
        }
        dialog.show();
    }

    public participantes mayor(List<participantes> p){
        //Perdon ya me maree yo tmb
        com.luciano.thinco.cuanto_es.participantes pMayorMonto = new participantes(p.get(0).getNombre(), p.get(0).getMonto());

        return pMayorMonto;
    }
}
