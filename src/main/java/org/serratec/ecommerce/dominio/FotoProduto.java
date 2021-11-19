package org.serratec.ecommerce.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;



	@Entity
	public class FotoProduto {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="id_foto")
		private long id;
		
		@Lob
		private byte[] dados;
		
		private String nome;
		private String tipo;
		
		@OneToOne
		@MapsId
		@JoinColumn(name="id_produto")
		private Produto produto;	
		
		public Produto getProduto() {
			return produto;
		}
		public void setProduto(Produto produto) {
			this.produto = produto;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public byte[] getDados() {
			return dados;
		}
		public void setDados(byte[] dados) {
			this.dados = dados;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		
		public FotoProduto(long id, byte[] dados, String nome, String tipo) {
			super();
			this.id = id;
			this.dados = dados;
			this.nome = nome;
			this.tipo = tipo;
		}
		
		public FotoProduto() {		
		}
		

	}

