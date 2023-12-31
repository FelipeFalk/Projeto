package com.example.produto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projeto.R;
import com.example.usuario.Usuarios;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ActivityCadastraProduto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_produto);

        botaoCadastrar();

        Toolbar toolbar = findViewById(R.id.toolbarCadastrar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void botaoCadastrar(){
        Produtos produtoNovo = new Produtos();
        Button botao = findViewById(R.id.botaoCadastrar);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText textDesc = findViewById(R.id.textDesc);
                EditText textQuantidade = findViewById(R.id.textQuantidade);
                EditText textPreco = findViewById(R.id.textPreco);
                EditText textGtin = findViewById(R.id.textGtin);

                if((textQuantidade.getText().toString().matches(""))||(textGtin.getText().toString().matches(""))||(textDesc.getText().toString().matches(""))||(textPreco.getText().toString().matches(""))){
                    showNewProductRequest(view);
                }else {
                    produtoNovo.setQuantidade(Integer.parseInt(textQuantidade.getText().toString()));
                    produtoNovo.setGTIN(Long.parseLong((textGtin.getText().toString())));
                    produtoNovo.setNome(textDesc.getText().toString());
                    produtoNovo.setPreco(Double.parseDouble(textPreco.getText().toString()));
                    cadastraProduto(produtoNovo);
                    finish();
                }
            }
        });
    }

    private void showNewProductRequest(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Erro");
        builder.setMessage("Necessário Preencher Todos os Valores");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void cadastraProduto(Produtos produtoNovo){
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(produtoNovo);

        String urlString = getResources().getString(R.string.url_base)+"/produtos";
        String requestBody = json;

        try{
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + Usuarios.getJwtToken());
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

}