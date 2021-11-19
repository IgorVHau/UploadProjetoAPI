package org.serratec.ecommerce.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.serratec.ecommerce.dominio.Cliente;
import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.exception.CEPInvalidoException;
import org.serratec.ecommerce.servico.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/ler")
//	public List<Cliente> listar(){
//		
//		return clienteService.listar();
//	}
	public ResponseEntity<?> listar() {
		if(!clienteService.listar().isEmpty()) {
			List<Cliente> lista = clienteService.listar();
			return ResponseEntity.ok().body(lista);
		}
		return ResponseEntity.noContent().build();
	}
	
	//Buscar cliente por id
	@GetMapping("/ler/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<Object> buscar(@PathVariable long id,
			@AuthenticationPrincipal UserDetails user) {
		//Sistema de login
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		//Buscar usuário por Id
		Optional<Cliente> cliente = clienteService.getById(id);
		//Se o cliente existir no BD
		if(cliente.isPresent()) {
			return ResponseEntity.ok().body(cliente);
		}
		//Se o cliente não existir no BD
		return ResponseEntity.notFound().build();
	}
	
	//Inserir cliente
	@PostMapping("/criar")
	@ApiOperation(value="Inserir cliente no E-Commerce.")
	@ApiResponse(code = 201, message="Cliente inserido com sucesso!")
	public ResponseEntity<Object> criar(@Valid @RequestBody ClienteDTO clienteDto) {
		try {
			ClienteDTO cliente = clienteService.criar(clienteDto);
			
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}").buildAndExpand(cliente.getId())
					.toUri();
			
			return ResponseEntity.created(uri).body(cliente);	
		} catch(CEPInvalidoException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}					
	}
	
	//Deletar cliente por id
	@DeleteMapping("/deletar/{id}")
	@ApiOperation(value="Remover o cliente")
	@ApiResponse(code = 204, message="Cliente não encontrado no servidor.")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		//Optional<Cliente> cliente = clienteService.getById(id);
		if(clienteService.getById(id).isPresent()) {
			clienteService.deletar(id);
			return ResponseEntity.ok().body("Operação realizada com sucesso!");
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/atualizar/{id}")
	@ApiOperation(value="Atualizar dados do cliente", notes="Atualizando dados do cliente")
	@ApiResponses(value= {
			@ApiResponse(code = 201, message="Dados do cliente atualizado com sucesso!"),
			@ApiResponse(code = 400, message="Ocorreu um erro no request.")
	})
	public ResponseEntity<?> atualizar(@PathVariable long id, 
			@RequestBody ClienteDTO clienteDto) {
		try {
			if(clienteService.getById(id).isPresent()) {
				clienteService.deletar(id);
				ClienteDTO clienteAtualizado = clienteService.criar(clienteDto);
				
				
				URI uri = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}").buildAndExpand(clienteAtualizado.getId())
						.toUri();
				
				return ResponseEntity.accepted().body(clienteDto);
			}
		}
		catch(CEPInvalidoException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.notFound().build();
		
	}

}
