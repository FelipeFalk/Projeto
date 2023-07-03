package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.produto.ActivityProdutos;
import com.example.projeto.R;
import com.example.usuario.Usuarios;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Desabilita a restrição de tráfego não seguro para localhost
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }

        botaoEntrar();
    }

    private void botaoEntrar(){
        Button botao = findViewById(R.id.botaoEntrar);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText textUser = findViewById(R.id.textUsuario);
                EditText textPassword = findViewById(R.id.textSenha);

                if(textUser.getText().toString().matches("")||(textPassword.getText().toString().matches(""))){
                    showLoginRequest(view, "Necessário preencher todos os valores");
                }else {

                    ArrayList<String> tokenStatus = resgataToken(textUser.getText().toString(), textPassword.getText().toString());

                    if (tokenStatus.get(1).equals("200")) {
                        Intent i = new Intent(LoginActivity.this, ActivityProdutos.class);
                        Usuarios.setJwtToken(tokenStatus.get(0));
                        startActivity(i);
                    }else{
                        showLoginRequest(view, "Problema de validação do Login, tente novamente");
                    }
                }
            }
        });
    }

    private void showLoginRequest(View view, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Erro");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            //Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private ArrayList<String> resgataToken(String user, String pass) {

        Usuarios usuario = new Usuarios();
        usuario.setUsername(user);
        usuario.setPassword(pass);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(usuario);

        ArrayList<String> tokenStatus = new ArrayList<String>();

        String urlString = getResources().getString(R.string.url_base) + "/auth/login";
        String requestBody = json;
        int responseCode;

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(requestBody.length()));
            con.setRequestProperty("Host", "localhost:8080");
            con.setDoOutput(true);
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Extrair o token da resposta
                JSONObject jsonObject = new JSONObject(response.toString());
                String token = jsonObject.getString("token");
                tokenStatus.add(token);
                tokenStatus.add(String.valueOf(responseCode));
                return tokenStatus;
            } else {
                // Tratar erro de resposta inválida
                throw new RuntimeException("Erro na requisição. Código de resposta: " + responseCode);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}