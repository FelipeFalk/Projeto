package com.example.produto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.example.adapter.AdapterProdutosPersonalizado;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.projeto.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ActivityProdutos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        Toolbar toolbar = findViewById(R.id.toolbarProdutos);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override

    protected void onResume(){
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Desabilita a restrição de tráfego não seguro para localhost
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }

        criaLista();

    }

    private void criaLista(){
        List<Produtos> produtos = geraLista();

        Collections.sort(produtos, new Comparator<Produtos>() {
            @Override
            public int compare(Produtos produto1, Produtos produto2) {
                return Integer.compare(produto1.getId(), produto2.getId());
            }
        });

        ListView listaDeProdutos = findViewById(R.id.listaProdutos);

        AdapterProdutosPersonalizado adapter = new AdapterProdutosPersonalizado(produtos, this);
        listaDeProdutos.setAdapter(adapter);

        listaDeProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Produtos produtoSelecionado = produtos.get(position); // Pega o produto selecionado com base na posição do click
                int idProduto = produtoSelecionado.getId(); // Pega o id do produto selecionado para enviar
                Intent i = new Intent(ActivityProdutos.this, ActivityEditar.class);
                i.putExtra("id", idProduto);
                startActivity(i);
            }
        });

        Button botao = findViewById(R.id.botaoCadastrarProduto);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityProdutos.this, ActivityCadastraProduto.class);
                startActivity(i);
            }
        });

    }

    private List geraLista(){

        List<Produtos>  listaProdutos = null;

        try{


            URL url = new URL(getResources().getString(R.string.url_base)+"/produtos");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Produtos>>() {}.getType();
            listaProdutos = gson.fromJson(response.toString(), tipoLista);

            con.disconnect();

            return listaProdutos;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}