package br.feevale.exemplobanco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class RolavelMainActivity extends AppCompatActivity {
    ListView listAlunos;
    ListAlunosAdapter alunosAdapter;
    EditText txtNome;
    EditText txtEmail;
    AlunosDBDAO db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolavel_main);

        listAlunos = (ListView) findViewById(R.id.listUsuarios);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        db = new AlunosDBDAO(this);
        db.abrir();


        alunosAdapter = new ListAlunosAdapter(this);
        listAlunos.setAdapter(alunosAdapter);

        listAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ID",""+id);
                db.deletarAluno((int) id);
                alunosAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void showUsers(View view) {
        alunosAdapter = new ListAlunosAdapter(this);
        listAlunos.setAdapter(alunosAdapter);
    }

    public void addUser(View view) {
        Aluno a = new Aluno();
        a.setNome(txtNome.getText().toString());
        a.setCurso("CICO");
        a.setEmail(txtEmail.getText().toString());
        db.adicionarAluno(a);
        alunosAdapter.notifyDataSetChanged();
    }
}












