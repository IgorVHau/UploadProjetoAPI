package org.serratec.ecommerce.dominio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String nome;
	
	@Column
	private String descricao;
	
	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;
		
	//TODO: Lembrar de relacionar one to one. ou many to one	
	//Utensilios domésticos
	//Celular
	//Eletrodomesticos
	
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	/*
	 * Se fosse um relacionamento 1 para muitos
	 * ou seja, um produto pudesse ter varias categorias
	 * esta propriedade ficaria assim:
	 * private List<Categoria> categoria;
	 */
//	@Transient	
//	private List<Pedido> pedidos;
	
	
    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL,
    fetch = FetchType.LAZY, optional = true)
	private FotoProduto foto;
	
	public Produto(String nome, BigDecimal valorUnitario) {
		
	}
	
	public Produto(String nome, String descricao, BigDecimal valorUnitario, Categoria categoria) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.valorUnitario = valorUnitario;
		this.categoria = categoria;		
	}
	
	public FotoProduto getFoto() {
		return foto;
	}

	public void setFoto(FotoProduto foto) {
		this.foto = foto;
	}
	
	public Produto() {		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return id == other.id;
	}
	
	

//	public List<Pedido> getPedidos() {
//		return pedidos;
//	}


}
