package com.example.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.produto.Produtos;
import com.example.projeto.R;

import java.util.List;

public class AdapterProdutosPersonalizado extends BaseAdapter {

    private final List<Produtos> produtosList;
    private final Activity act;

    public AdapterProdutosPersonalizado(List<Produtos> produtosList, Activity act) {
        this.produtosList = produtosList;
        this.act = act;
    }

    @Override public int getCount() { return produtosList.size(); }

    @Override public Object getItem(int position) { return produtosList.get(position); }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(act);
            view = inflater.inflate(R.layout.item_produto, parent, false);
        }

        Produtos produto = produtosList.get(position);

        TextView textViewDesc = view.findViewById(R.id.textViewDesc);
        TextView textViewPreco = view.findViewById(R.id.textViewPreco);
        TextView textViewQuantidade = view.findViewById(R.id.textViewQuantidade);
        TextView textViewGtin = view.findViewById(R.id.textGtin);

        textViewDesc.setText("Descrição: " + produto.getNome());
        textViewPreco.setText("Preço: " + produto.getPreco());
        textViewQuantidade.setText("Quantidade: " + produto.getQuantidade());
        textViewGtin.setText("GTIN: " + produto.getGTIN());

        return view;
    }
}
