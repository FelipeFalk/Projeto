package com.example.produto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projeto.R;
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

            String desc = produtoSelecionado.Nome;
            int quantidade = produtoSelecionado.Quantidade;
            double preco = produtoSelecionado.Preco;

            textDesc.setText(desc);
            textQuantidade.setText(String.valueOf(quantidade));
            textPreco.setText(Double.toString(preco));

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
                    //salvar info
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

    private Produtos getItemById(int id){
        Produtos produto = new Produtos();
        produto.setDescricao("Batata");
        produto.setQuantidade(10);
        produto.setPreco(2.5);
        return produto;
    }
}