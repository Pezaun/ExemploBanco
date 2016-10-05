package br.feevale.exemplobanco;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 0101765 on 12/04/2016.
 */
public class ListAlunosAdapter extends BaseAdapter {

    private List<Aluno> alunos;
    private RolavelMainActivity ctx;
    AlunosDBDAO db;
    public ListAlunosAdapter(RolavelMainActivity ctx){
        this.ctx = ctx;
        db = ctx.db;
        alunos = db.recuperarAlunos();
    }

    @Override
    public int getCount() {
        alunos = db.recuperarAlunos();
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        alunos = db.recuperarAlunos();
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        alunos = db.recuperarAlunos();
        if(alunos.size() == 0){
            return 0;
        }
        return alunos.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        alunos = db.recuperarAlunos();
        LayoutInflater inflater = ctx.getLayoutInflater();
        View row;
        if(position % 2 == 0){
            row = inflater.inflate(R.layout.list_usuarios_item, parent, false);
        }else{
            row = inflater.inflate(R.layout.list_usuarios_item_left, parent, false);
        }

        TextView txtNome = (TextView)row.findViewById(R.id.txt_nome);
        TextView txtEmail = (TextView)row.findViewById(R.id.txt_email);
        txtNome.setText(alunos.get(position).getNome());
        txtEmail.setText(alunos.get(position).getEmail());
        return row;
    }
}
