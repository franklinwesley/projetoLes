package com.example.diego.myapplication.Adaptadores;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.diego.myapplication.Atividades.EditarActivity;
import com.example.diego.myapplication.Entidades.Atividade;
import com.example.diego.myapplication.Persistencia.BDHelper;
import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;

import java.util.List;

public class TarefaAdapater extends RecyclerView.Adapter<TarefaAdapater.TarefaViewHolder> {

    private List<Atividade> atividades;
    private Context context;
    private String id;
    DataBaseHelper db;

    public TarefaAdapater(Context context, List<Atividade> atividades, String id){
        BDHelper bd = new BDHelper(context);
        DataBaseHelper db = bd.getBD(id);
        this.id = id;
        this.db = bd.getBD(id);
        this.context = context;
        this.atividades = atividades;
    }
    public void remove(Atividade item) {
        BDHelper bd = new BDHelper(context);
        DataBaseHelper db = bd.getBD(id);
        int id = item.getId();
        int position = atividades.indexOf(item);
        atividades.remove(position);
        notifyItemRemoved(position);
        db.removerAtividade(id);
    }

    public class TarefaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nome;
        public TextView prioridade;
        public Context mContext;

        public TarefaViewHolder(View itemView, Context mContext) {

            super(itemView);
            this.mContext = mContext;
            itemView.setOnClickListener(this);
            nome =  (TextView) itemView.findViewById(R.id.txt_nome);
        }
        @Override

        public void onClick(View v) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this.mContext);

            alert.setTitle("Selecione:");

            alert.setPositiveButton("EDITAR", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent i = new Intent(context, EditarActivity.class);

                    Atividade atividade = atividades.get(getPosition());

                    i.putExtra("nome", atividade.getNome());
                    i.putExtra("ano", atividade.getData().getAno());
                    i.putExtra("mes", atividade.getData().getMes());
                    i.putExtra("dia", atividade.getData().getDia());
                    i.putExtra("hora",atividade.getTempo().getHora());
                    i.putExtra("minuto", atividade.getTempo().getMinuto());
                    i.putExtra("prioridade", atividade.getPrioridade());
                    i.putExtra("categoria", atividade.getCategoria());
                    i.putExtra("id", atividade.getId());

                    context.startActivity(i);

                }
            });

            alert.setNegativeButton("DELETAR", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    remove(atividades.get(getPosition()));
                }
            });
            alert.show();
        }
    }
    @Override
    public TarefaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        TarefaViewHolder viewHolder = new TarefaViewHolder(v, v.getContext());
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(TarefaViewHolder holder, int position) {
        holder.nome.setText(atividades.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return atividades.size();
    }
}
