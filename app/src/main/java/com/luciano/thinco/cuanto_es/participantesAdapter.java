package com.luciano.thinco.cuanto_es;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class participantesAdapter extends RecyclerView.Adapter<participantesAdapter.participantesViewHolder> {
    private List<participantes> participantesList;

    @NonNull
    @Override
    public participantesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.participante_card, viewGroup, false);
        return new participantesViewHolder(v);
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

    public static class participantesViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public TextView monto;

        public participantesViewHolder(View V){
            super (V);
            nombre = (TextView) V.findViewById(R.id.tvnombre);
            monto = (TextView) V.findViewById(R.id.tvmonto);

        }
    }

    public participantesAdapter(List<participantes> items){
        this.participantesList = items;
    }


}
