package org.serratec.ecommerce.dto;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.serratec.ecommerce.dominio.Produto;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ProdutoDTO {
	
	List<Long> produtos;
	
	private long id;
	private String nome;
	private BigDecimal valorUnitario;

	private String url;
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO(String nome, BigDecimal valorUnitario) {
		super();
		this.nome = nome;
		this.valorUnitario = valorUnitario;
		
	}
	
	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.valorUnitario = produto.getValorUnitario();
		this.url = generateUrlFoto(produto);
	}
	
	public List<Long> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Long> produtos) {
		this.produtos = produtos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	//INserir
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	//Inserido
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public static List<ProdutoDTO> convert(List<Produto> produtos){
		List<ProdutoDTO> produtosDto = new ArrayList<>();
		for (Produto produto : produtos) {
			ProdutoDTO produtoDto = new ProdutoDTO(produto);			
			produtosDto.add(produtoDto);			
		}
		return produtosDto;
	}	
	
	public static String generateUrlFoto(Produto produto) {
		return ProdutoDTO.generatedUrlFoto(produto.getId());	
	}
	
	public static String generatedUrlFoto(long idProduto) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/produto/{id}/foto")
				.buildAndExpand(idProduto)
				.toUri();
		
		return uri.toString();	
	}
	//Inserir
	public long getId() {
		return id;
	}
	//Inserido
	

}
