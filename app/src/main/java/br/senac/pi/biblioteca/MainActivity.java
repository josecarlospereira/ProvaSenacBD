package br.senac.pi.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //novos atributos
    private String id;
    private DatabaseHelper helper;
    private EditText nome, autor;
    private List<Map<String, Object>> livros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recuperando novas views
        nome = (EditText) findViewById(R.id.edtNome);
        autor = (EditText) findViewById(R.id.edtAutor);

        //prepara acesso ao banco de dados
        helper = new DatabaseHelper(this);

        id = getIntent().getStringExtra(Constantes.LIVRO_ID);

        if(id != null){
            prepararEdicao();
        }
    findViewById(R.id.btnListar).setOnClickListener(listaarLivros());
    }

    private void prepararEdicao(){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, nome, autor " + "FROM livro WHERE _id = ?", new String[]{id});
        cursor.moveToFirst();

        nome.setText(cursor.getString(1));
        autor.setText(cursor.getString(2));
        cursor.moveToNext();
        cursor.close();
    }

    //public void cadastrarLivro(View view){
    //    SQLiteDatabase db = helper.getWritableDatabase();

    //    ContentValues values = new ContentValues();
    //    values.put("nome", nome.getText().toString());
    //    values.put("autor", autor.getText().toString());

        //prepara o ContentValues
    //    long resultado = db.insert("livro", null, values);

    //    if(resultado != -1){
    //        Toast.makeText(this, getString(R.string.registro_salvo),Toast.LENGTH_SHORT).show();
    //    }else {
    //        Toast.makeText(this, getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
    //    }

    //    if (id == null){
    //        resultado = db.insert("livro", null, values);
    //    }else {
    //        resultado = db.update("viagem", values, "_id = ?", new String[] { id });
    //    }
    //}

    public View.OnClickListener listaarLivros() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BiblioListActivity.class);
                startActivity(intent);
            }
        };
    }

    //private List<Map<String, Object>> listarLivros(){

    //    SQLiteDatabase db = helper.getReadableDatabase();
    //    Cursor cursor = db.rawQuery("SELECT _id, nome, autor", null);

        //move o cursor para o primeiro registro
    //    cursor.moveToFirst();

    //    livros = new ArrayList<Map<String, Object>>();

    //    for (int i = 0; i < cursor.getCount(); i++){
    //        Map<String, Object> item = new HashMap<String, Object>();

    //        String id = cursor.getString(0);
    //        String nome = cursor.getString(1);
    //        String autor = cursor.getString(2);

    //        item.put("id", id);
    //        item.put("imagem", R.drawable.settt);
    //        item.put("nome", nome);
    //        item.put("autor", autor);

    //        livros.add(item);

    //        cursor.moveToNext();

    //    }
    //    cursor.close();

    //    return livros;
    //}

    private void removerLivro(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String where []  = new String[]{id};
        db.delete("LIVRO", "ID = ?", where);
    }

    public void salvarLivro(View view){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NOME", nome.getText().toString());
        values.put("AUTOR", autor.getText().toString());

        long resultado;

        if(id == null){
            resultado = db.insert("LIVRO", null, values);
        }else{
            resultado = db.update("LIVRO", values, "ID = ?", new String[]{ id });
        }

        if(resultado != -1 ){
            Toast.makeText(this, getString(R.string.registro_salvo), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}


