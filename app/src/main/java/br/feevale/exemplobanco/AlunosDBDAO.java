package br.feevale.exemplobanco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 11/16/15.
 */
public class AlunosDBDAO {
    private static final String NOME_BANCO = "alunos.db";
    private static final String TAB_ALUNOS = "ALUNOS";
    private static final int VERSAO_BANCO = 10;

    private static final String COL_ID = "ID";
    private static final String COL_NOME = "NOME";
    private static final String COL_CURSO = "CURSO";
    private static final String COL_EMAIL = "EMAIL";

    private static final String SQL_TAB_ALUNOS = "create table " + TAB_ALUNOS +
            "(" + COL_ID + " integer primary key autoincrement," +
            COL_NOME + " text not null," +
            COL_CURSO + " text not null," +
            COL_EMAIL + " text not null)";

    private alunosDBHelper dbHelper;
    private SQLiteDatabase db;
    private Context ctx;

    public AlunosDBDAO(Context ctx){
        dbHelper = new alunosDBHelper(ctx, NOME_BANCO, null, VERSAO_BANCO);
        this.ctx = ctx;
    }

    public void abrir(){
        db = dbHelper.getWritableDatabase();
    }

    public void fechar(){
        db.close();
    }

    public void adicionarAluno(Aluno a){
        ContentValues values = new ContentValues();
        values.put(COL_NOME, a.getNome());
        values.put(COL_CURSO, a.getCurso());
        values.put(COL_EMAIL, a.getEmail());
        db.insert(TAB_ALUNOS, null, values);
    }

    public List<Aluno> recuperarAlunos(){
        List<Aluno> alunos = new ArrayList<Aluno>();
        Aluno aluno;
        String[] colunas = {COL_ID, COL_NOME, COL_CURSO, COL_EMAIL};
        Cursor cursor = db.query(TAB_ALUNOS, colunas,null,null,null,null,COL_ID);
        if(cursor.getCount() == 0){
            return alunos;
        }

        cursor.moveToFirst();
        do{
            aluno = new Aluno();
            aluno.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            aluno.setNome(cursor.getString(cursor.getColumnIndex(COL_NOME)));
            aluno.setCurso(cursor.getString(cursor.getColumnIndex(COL_CURSO)));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
            alunos.add(aluno);
        }while(cursor.moveToNext());
        return alunos;
    }

    public Integer deletarAluno(int id){
        return db.delete(TAB_ALUNOS, COL_ID + "=" + id, null);
    }
    public Integer deletarAlunos(){
        return db.delete(TAB_ALUNOS, null, null);
    }

    private static class alunosDBHelper extends SQLiteOpenHelper{

        public alunosDBHelper(Context ctx, String nome, SQLiteDatabase.CursorFactory factory, int versao){
            super(ctx, nome, factory, versao);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_TAB_ALUNOS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TAB_ALUNOS);
            onCreate(db);
        }
    }
}
