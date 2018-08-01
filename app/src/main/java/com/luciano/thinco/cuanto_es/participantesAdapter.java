package com.luciano.thinco.cuanto_es;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            final android.app.AlertDialog.Builder dialogConfirmacion = new android.app.AlertDialog.Builder(V.getContext());
            dialogConfirmacion.setTitle("Eliminar a "+nombre.getText().toString());
            dialogConfirmacion.setCancelable(true);
            dialogConfirmacion.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.itemLongClick(V, getAdapterPosition());
                }
            });
            dialogConfirmacion.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogConfirmacion.show();
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
