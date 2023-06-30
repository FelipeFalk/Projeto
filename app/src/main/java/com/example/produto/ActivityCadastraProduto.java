package com.example.produto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projeto.R;

public class ActivityCadastraProduto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_produto);

        botaoCadastrar();
        botaoVoltarCadastrarProduto();
    }

    private void botaoCadastrar(){
        Button botao = findViewById(R.id.botaoCadastrar);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void botaoVoltarCadastrarProduto(){
        Button botao = findViewById(R.id.botaoVoltarCadastrar);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}