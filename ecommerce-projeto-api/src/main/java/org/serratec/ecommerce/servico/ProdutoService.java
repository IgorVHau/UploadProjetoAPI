package org.serratec.ecommerce.servico;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dominio.Produto;
import org.serratec.ecommerce.dto.ProdutoDTO;
import org.serratec.ecommerce.repositorio.ClienteRepository;
import org.serratec.ecommerce.repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProdutoService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
//	@Autowired
//	private CriptografiaService criptografiaService;
	
//	@Autowired
//	private MailConfig mailConf;
	
	public List<Produto> listar(){
		return produtoRepository.findAll();
	}
	
	public Optional<Produto> getById(long id) {
		return produtoRepository.findById(id);
	}
	
	public ProdutoDTO criar(ProdutoDTO produtoDTO) {
		
		Produto produto = new Produto();
		produto.setNome(produtoDTO.getNome());
		//produto.setDescricao(produto.getDescricao());
		produto.setValorUnitario(produtoDTO.getValorUnitario());
		
		Produto produtoSalvo = produtoRepository.save(produto);
//		Produto produto = new Produto(nome, valorUnitario);
		return new ProdutoDTO(produtoSalvo);
	}

}
