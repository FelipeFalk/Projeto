package com.example.produto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.projeto.R;

import java.lang.reflect.Type;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ActivityProdutos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        criaLista();
        sair();

    }

    private void criaLista(){
        List<Produtos> produtos = geraLista(); //MUDAR PARA UMA LÓGICA QUE RECEBE OS PRODUTOS DO BANCO

        ArrayAdapter<Produtos> adapter = new ArrayAdapter<Produtos>(this, android.R.layout.simple_list_item_1, produtos);

        ListView listaDeProdutos = findViewById(R.id.listaProdutos);

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

    private void sair(){
        Button botao = findViewById(R.id.botaoDeslogar);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List geraLista(){

        String json = "[\n" +
                "    {\n" +
                "        \"IdProduto\": 1,\n" +
                "        \"GTIN\": 7891234567890,\n" +
                "        \"Nome\": \"Arroz Parboilizado 5kg\",\n" +
                "        \"Quantidade\": 50,\n" +
                "        \"Preco\": 29.9\n" +
                "    },\n" +
                "    {\n" +
                "        \"IdProduto\": 2,\n" +
                "        \"GTIN\": 7894561237890,\n" +
                "        \"Nome\": \"Feijão Carioca 1kg\",\n" +
                "        \"Quantidade\": 100,\n" +
                "        \"Preco\": 7.99\n" +
                "    },\n" +
                "    {\n" +
                "        \"IdProduto\": 3,\n" +
                "        \"GTIN\": 7897894567890,\n" +
                "        \"Nome\": \"Açúcar Cristal 2kg\",\n" +
                "        \"Quantidade\": 80,\n" +
                "        \"Preco\": 6.5\n" +
                "    },\n" +
                "    {\n" +
                "        \"IdProduto\": 4,\n" +
                "        \"GTIN\": 7899876543210,\n" +
                "        \"Nome\": \"Óleo de Soja 900ml\",\n" +
                "        \"Quantidade\": 120,\n" +
                "        \"Preco\": 4.99\n" +
                "    }\n" +
                "]";

        //BufferedReader br = new BufferedReader(new FileReader("caminho/do/json"));

        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Produtos>>() {}.getType();
        List<Produtos> listaProdutos = gson.fromJson(json, tipoLista);

        String j = gson.toJson(listaProdutos);

        Log.d("Falk Json", j);

        return listaProdutos;
    }
}