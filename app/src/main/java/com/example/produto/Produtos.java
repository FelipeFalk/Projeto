package com.example.produto;

public class Produtos {

    public int IdProduto;
    public long GTIN;
    public String Nome;
    public int Quantidade;
    public double Preco;

    //////////////////////////////////////////////////////

    public int getId() {
        return IdProduto;
    }

    public void setId(int id) {
        IdProduto = id;
    }

    public long getGTIN() {
        return GTIN;
    }

    public void setGTIN(long GTIN) {
        this.GTIN = GTIN;
    }

    public void setDescricao(String descricao) {
        this.Nome = descricao;
    }

    public String getDescricao() {
        return Nome;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.Quantidade = quantidade;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double preco) {
        this.Preco = preco;
    }

    @Override public String toString() {
        return IdProduto + ", " + Nome + ", " + Quantidade + ", " + Preco;
    }
}


