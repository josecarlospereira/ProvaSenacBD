package br.senac.pi.biblioteca;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

/**
 * Created by JoséCarlos on 20/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "BiblioTeca";
    private static int VERSAO = 1;

    public static class Livro{
        public static final String TABELA = "livro";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String AUTOR = "autor";

        public  static final String[] COLUNAS = new String[]{
                _ID, NOME, AUTOR
        };
    }

    public DatabaseHelper(Context context){
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE livro (_id INTERGER PRIMARY KEY," + "nome TEXT, autor TEXT);");
    }

    @Override
    public void  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
