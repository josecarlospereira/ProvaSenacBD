package br.senac.pi.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //novos atributos
    private DatabaseHelper helper;
    private EditText nome, autor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cadastrarButton = (Button) findViewById(R.id.btnCadastrar);
        //listarButton = (Button) findViewById(R.id.btnListar);

        //recuperando novas views
        nome = (EditText) findViewById(R.id.edtNome);
        autor = (EditText) findViewById(R.id.edtAutor);

        //prepara acesso ao banco de dados
        helper = new DatabaseHelper(this);

//findViewById(R.id.btnListarCarros).setOnClickListener(listarLivros());

    }

    public void cadastrarLivro(View view){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", nome.getText().toString());
        values.put("autor", autor.getText().toString());

        //prepara o ContentValues
        long resultado = db.insert("livro", null, values);

        if(resultado != -1){
            Toast.makeText(this, getString(R.string.registro_salvo),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
        }
    }

    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }



//public View.OnClickListener listarLivros(){
//  return View.OnClickListener(){
//}
//}

}

