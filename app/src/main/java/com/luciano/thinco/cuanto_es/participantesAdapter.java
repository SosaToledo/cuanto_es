package com.luciano.thinco.cuanto_es;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class participantesAdapter extends RecyclerView.Adapter<participantesAdapter.participantesViewHolder> implements itemClickListenerUnity {
    private List<participantes> participantesList;

    @NonNull
    @Override
    public participantesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.participante_card, viewGroup, false);
        return new participantesViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull participantesViewHolder participantesViewHolder, int i) {
        participantesViewHolder.nombre.setText(participantesList.get(i).getNombre());
        participantesViewHolder.monto.setText("$"+String.valueOf(participantesList.get(i).getMonto()));
    }

    @Override
    public int getItemCount() {
        return participantesList.size();
    }


    public static class participantesViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        public TextView nombre;
        public TextView monto;
        public itemClickListenerUnity listener;

        public participantesViewHolder(View V, itemClickListenerUnity listener){
            super (V);
            nombre = V.findViewById(R.id.tvnombre);
            monto = V.findViewById(R.id.tvmonto);
            this.listener = listener;
            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(final View V) {

            AlertDialog.Builder builder = new AlertDialog.Builder(V.getContext()).setCancelable(false);

            LayoutInflater inflater = ((Activity)V.getContext()).getLayoutInflater();

            View v = inflater.inflate(R.layout.dialog_eliminar, null);

            builder.setView(v);

//            aca se realizan las llamadas al findviewbyid
            TextView tvTitulo = v.findViewById(R.id.tvPersonaAEliminar);
            Button btnCerrarDialog = v.findViewById(R.id.btnCancelarDialog);
            Button btnEliminarDialog = v.findViewById(R.id.btnEliminarDialog);
//¿Queres eliminar a Frank ?
            tvTitulo.setText("¿Eliminar a " + nombre.getText().toString() + "?");

            final AlertDialog d = builder.create();
            btnCerrarDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d.dismiss();
                }
            });
            btnEliminarDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemLongClick(V, getAdapterPosition());
                    d.dismiss();
                }
            });
            d.show();
            return true;
        }
    }

    public participantesAdapter(List<participantes> items){
        this.participantesList = items;
    }

    @Override
    public void itemLongClick(View v, int position) {
        participantesList.remove(position);
        notifyDataSetChanged();
    }
}

interface itemClickListenerUnity{
        void itemLongClick(View v, int position);
}
