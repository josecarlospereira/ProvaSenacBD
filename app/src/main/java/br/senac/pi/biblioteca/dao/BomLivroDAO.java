package br.senac.pi.biblioteca.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import br.senac.pi.biblioteca.DatabaseHelper;
import br.senac.pi.biblioteca.br.senac.pi.biblioteca.domin.Livro;

/**
 * Created by JoséCarlos on 25/11/2015.
 */
public class BomLivroDAO {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public  BomLivroDAO(Context context){
        helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDb(){
        if (db == null){
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public void close(){
        helper.close();
    }

    public List<Livro> listarLivros(){
        Cursor cursor = getDb().query(DatabaseHelper.Livro.TABELA, DatabaseHelper.Livro.COLUNAS, null, null, null, null, null);

        List<Livro> livros = new ArrayList<Livro>();
        while(cursor.moveToNext()){
            Livro livro = criarLivro(cursor);
            livros.add(livro);
        }
        cursor.close();
        return livros;
    }

    public  Livro buscarLivroPorId(Integer id){
        Cursor cursor = getDb().query(DatabaseHelper.Livro.TABELA, DatabaseHelper.Livro.COLUNAS, DatabaseHelper.Livro._ID + "= ?", new String[]{id.toString()}, null, null, null);

        if (cursor.moveToNext()){
            Livro livro = criarLivro(cursor);
            cursor.close();
            return livro;
        }
        return  null;
    }

    public long inserir(Livro livro){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Livro.NOME, livro.getNome());
        values.put(DatabaseHelper.Livro.AUTOR, livro.getAutor());
        return getDb().insert(DatabaseHelper.Livro.TABELA, null, values);
    }

    public boolean removerLivro(Long id){
        String whereClause = DatabaseHelper.Livro._ID + " = ?";
        String[] whereArgs = new String[]{id.toString()};
        int removidos = getDb().delete(DatabaseHelper.Livro.TABELA, whereClause, whereArgs);
        return removidos > 0;
    }

    private Livro criarLivro(Cursor cursor){
        Livro livro = new Livro(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Livro._ID)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.Livro.NOME)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.Livro.AUTOR)));
        return livro;
    }

}
