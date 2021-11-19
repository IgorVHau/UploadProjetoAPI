package org.serratec.ecommerce.dto;

import java.math.BigDecimal;

import org.serratec.ecommerce.dominio.Produto;
import org.serratec.ecommerce.repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProdutoInserirDTO {
	@Autowired
	public ProdutoRepository produtoRepository;

	private String nome;
	private BigDecimal valorUnitario;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}


	public Produto Produtoinserido() {
		Produto produto = new Produto(this.nome, this.valorUnitario);
		return produto;
	}
	
}
