package org.serratec.ecommerce.servico;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.config.MailConfig;
import org.serratec.ecommerce.dominio.Cliente;
import org.serratec.ecommerce.dominio.Endereco;
import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.dto.EnderecoDTO;
import org.serratec.ecommerce.exception.CEPInvalidoException;
import org.serratec.ecommerce.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BuscarEnderecoPorCEPService bepcs;
	
	@Autowired
	private MailConfig mailConfiguracao;
	
	public List<Cliente> listar(){
		return clienteRepository.findAll();
	}
	
	public Optional<Cliente> getById(long id) {
		return clienteRepository.findById(id);
	}
	
	public ClienteDTO criar(ClienteDTO clienteDTO) throws CEPInvalidoException {
		String cep = clienteDTO.getCep();
		ResponseEntity<EnderecoDTO> enderecoDto = bepcs.getEndereco(cep);
		if(enderecoDto.getStatusCode() == HttpStatus.OK) {
			EnderecoDTO endereco = enderecoDto.getBody();
			if(endereco.obteveErro()) {
				throw new CEPInvalidoException("CEP inválido");
			}
			
			Endereco endDominio = endereco.generateEndereco();
			endDominio.setNumero(clienteDTO.getNumero());
			endDominio.setComplemento(clienteDTO.getComplemento());
			
			
			Cliente cliente = new Cliente();
			cliente.setCpf(clienteDTO.getCpf());
			cliente.setEmail(clienteDTO.getEmail());
			cliente.setNome(clienteDTO.getNome());
			cliente.setSenha(clienteDTO.getSenha());
			cliente.setSobrenome(clienteDTO.getSobrenome());
			cliente.setDataNascimento(clienteDTO.getDataNascimento()); 
			/*DID: Fazer o get/set de DataNascimento em Cliente DTO*/
			
			cliente.setEndereco(endDominio);
			
			Cliente clienteSalvo = clienteRepository.save(cliente);
			
			return new ClienteDTO(clienteSalvo);
						
		}else {
			throw new CEPInvalidoException("CEP não foi localizado.");
		}
		
		//Implemetação da busca do endereço através do VIACEP.
	}
	
	//Método deletar a ser invocado na classe ClienteController
	public void deletar(long id) {
		//Optional<Cliente> cliente = clienteRepository.findById(id);
		if(clienteRepository.existsById(id)) {
			clienteRepository.deleteById(id);
		}
	}
	

}
