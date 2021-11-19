package org.serratec.ecommerce.servico;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.config.MailConfig;
import org.serratec.ecommerce.dominio.Cliente;
import org.serratec.ecommerce.dominio.Pedido;
import org.serratec.ecommerce.dominio.Produto;
import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.exception.PedidoNaoExisteException;
import org.serratec.ecommerce.repositorio.ClienteRepository;
import org.serratec.ecommerce.repositorio.PedidoRepository;
import org.serratec.ecommerce.repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private MailConfig mailconfiguracao;
	
	public PedidoDTO criar(PedidoDTO pedidoDTO, UserDetails user) {
		
		Cliente cliente = clienteRepository.findByNome(user.getUsername());
		List<Produto> produtos = produtoRepository.findAllById(pedidoDTO.getProdutos());
		
		Pedido pedido = new Pedido(produtos, cliente);
		
		Pedido pedidoSalvo = pedidoRepository.save(pedido);
		
		return new PedidoDTO(pedidoSalvo);							
		
	}

	
	public void finalizar(long id) throws PedidoNaoExisteException {
		Optional<Pedido> pedidoDoBanco = pedidoRepository.findById(id);
		if(pedidoDoBanco.isPresent())
		{			
			Pedido pedido = pedidoDoBanco.get();
			if(pedido.isFinalizado()) {
				return;
			}
			pedido.setFinalizado(true);
			//Enviar email para o cliente que fez o pedido
			Cliente cliente = pedidoDoBanco.get().getCliente();
			mailconfiguracao.sendEmail(cliente.getEmail(), "Pedido enviado", pedidoDoBanco.toString());
			//Email enviado
			//Salvar o pedido
			pedidoRepository.save(pedido);
		}
		
		throw new PedidoNaoExisteException("Pedido " + id + " não encontrado no banco");		
	}
}
