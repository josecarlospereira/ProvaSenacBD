package br.senac.pi.biblioteca;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import br.senac.pi.biblioteca.dao.BomLivroDAO;

import br.senac.pi.biblioteca.br.senac.pi.biblioteca.domin.Livro;

public class BiblioListActivity extends ListActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener, SimpleAdapter.ViewBinder {

    private List<Map<String, Object>> livros;
    private AlertDialog alertDialog;
    private int livroSelecionado;
    private boolean modoSelecionarLivro;
    private BomLivroDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new BomLivroDAO(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        getListView().setOnItemClickListener(this);

        if (getIntent().hasExtra(Constantes.MODO_SELECIONAR_LIVRO)) {
            modoSelecionarLivro = getIntent().getExtras().getBoolean(Constantes.MODO_SELECIONAR_LIVRO);
        }
        new Task().execute();
    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        return false;
    }

    private class Task extends AsyncTask<Void, Void, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            return listarLivros();
        }
        @Override
        protected void onPostExecute(List<Map<String, Object>> result) {
            String[] de = { "imagem", "nome", "autor" };

            int[] para = { R.id.txtnome, R.id.txtautor };

            SimpleAdapter adapter = new SimpleAdapter(BiblioListActivity.this, result, R.layout.lista_livro, de, para);

            adapter.setViewBinder(BiblioListActivity.this);

            setListAdapter(adapter);
        }
    }

    private List<Map<String, Object>> listarLivros(){
        livros = new ArrayList<Map<String,Object>>();

        List<Livro> listaLivros = dao.listarLivros();

        for (Livro livro : listaLivros) {

            Map<String, Object> item = new HashMap<String, Object>();

            item.put("id", livro.getId());
            item.put("nome", livro.getNome());
            item.put("autor", livro.getAutor());

            livros.add(item);
        }
        return livros;
    }

    private AlertDialog criaAlertDialog(){
        final  CharSequence[] items = { getString(R.string.editar), getString(R.string.novo_livro), getString(R.string.livros_realizados), getString(R.string.remover)};
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(items, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int item){
        Intent intent;
        String id = (String) livros.get(livroSelecionado).get("id");

        switch (item){
            case 0://editar  livro
                intent = new Intent(this, MainActivity.class);
                intent.putExtra(Constantes.LIVRO_ID, id);
                startActivity(intent);
                break;
            case 1://novo livro
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 2://listar livros
                startActivity(new Intent(this, BiblioListActivity.class));
                break;
            case 3://remover livros
                livros.remove(this.livroSelecionado);
                getListView().invalidateViews();
                break;
        }

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        if (modoSelecionarLivro){
            String nome = (String) livros.get(position).get("nome");
            String idLivro = (String) livros.get(position).get("id");

            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);

        }else {
            this.livroSelecionado = position;
            alertDialog.show();
        }
    }

    @Override
    protected void  onDestroy(){
        dao.close();
        super.onDestroy();
    }
}
