package com.example.produto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projeto.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ActivityEditar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        int id = extra.getInt("id");

        Produtos produtoSelecionado;
        produtoSelecionado = getItemById(id);

        gerenciaEdicao(produtoSelecionado);

    }

    private void gerenciaEdicao(Produtos produtoSelecionado){

            EditText textDesc = findViewById(R.id.textDesc);
            EditText textQuantidade = findViewById(R.id.textQuantidade);
            EditText textPreco = findViewById(R.id.textPreco);
            EditText textGtin = findViewById(R.id.textGtin);

            String desc = produtoSelecionado.getNome();
            int quantidade = produtoSelecionado.getQuantidade();
            double preco = produtoSelecionado.getPreco();
            long gtin = produtoSelecionado.getGTIN();

            textDesc.setText(desc);
            textQuantidade.setText(String.valueOf(quantidade));
            textPreco.setText(Double.toString(preco));
            textGtin.setText(String.valueOf(gtin));

            Button botaoVoltarEditar = findViewById(R.id.botaoVoltarEditar);
            botaoVoltarEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            Button botaoSalvarEditar = findViewById(R.id.botaoSalvarEditar);
            botaoSalvarEditar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    Log.d("Falk", "Falkk");
                    produtoSelecionado.setQuantidade(Integer.parseInt(textQuantidade.getText().toString()));
                    produtoSelecionado.setGTIN(Long.parseLong((textGtin.getText().toString())));
                    produtoSelecionado.setNome(textDesc.getText().toString());
                    produtoSelecionado.setPreco(Double.parseDouble(textPreco.getText().toString()));
                    salvarProduto(produtoSelecionado);
                    finish();
                }
            });

            Button botaoExcluirEditar = findViewById(R.id.botaoExcluirEditar);
            botaoExcluirEditar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //exclir info
                    finish();
                }
            });

    }

    private void salvarProduto(Produtos produtoSelecionado){
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(produtoSelecionado);
        Log.d("Falk", String.valueOf(json));

        String urlString = "http://192.168.0.22:8080/produtos/"+produtoSelecionado.getId();
        String requestBody = json;

        try{
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Produtos getItemById(int id){

        Produtos produto = new Produtos();

        try{
            URL url = new URL("http://192.168.0.22:8080/produtos/"+id);
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
            produto = gson.fromJson(response.toString(), Produtos.class);

            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return produto;
    }
}