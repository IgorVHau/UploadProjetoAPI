package org.serratec.ecommerce.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dominio.FotoProduto;
import org.serratec.ecommerce.dominio.Produto;
import org.serratec.ecommerce.dto.FotoDTO;
import org.serratec.ecommerce.dto.ProdutoDTO;
import org.serratec.ecommerce.repositorio.ProdutoRepository;
import org.serratec.ecommerce.servico.FotoService;
import org.serratec.ecommerce.servico.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoService fotoService;
	
	//----------------- FOTO --------------------
	// api/produto/1/foto - retorna a imagem
	@GetMapping("{id}/foto")
	public ResponseEntity<byte[]> buscarPorFoto(@PathVariable long id) {
		FotoProduto foto = fotoService.buscar(id);
		if (foto != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("content-type", foto.getTipo());
			headers.add("content-length", String.valueOf(foto.getDados().length));
			return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
		}

		return ResponseEntity.notFound().build();

	}
	
	@PostMapping("{id}/foto")
	public ResponseEntity<FotoDTO> salvarFoto(@PathVariable long id, @RequestParam MultipartFile file){
		Optional<Produto> produto = produtoService.getById(id);
		if(produto.isPresent()) {
			try {
				FotoProduto foto = fotoService.inserir(produto.get(), file);	
				FotoDTO fotodto = new FotoDTO(foto, produto.get());
				return ResponseEntity.ok(fotodto);				
				
			}catch(IOException ex) {
				ex.printStackTrace();
			}			
		}		
		return ResponseEntity.badRequest().build();
		
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deletarFoto(@PathVariable long id){
		//Optional<Produto> produto = produtoService.getById(id);
		FotoProduto foto = fotoService.buscar(id);
		if(foto != null) {
			fotoService.deletar(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<?> atualizarFoto(@PathVariable long id, @RequestParam MultipartFile file){
		FotoProduto foto = fotoService.buscar(id);
		if(foto != null) {
			try {
				FotoProduto novaFoto = fotoService.atualizar(id, file);
				return ResponseEntity.ok(novaFoto);
			}
			catch(Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.badRequest().body("Ocorreu um erro!");
			}
			
		}
		return ResponseEntity.notFound().build();
	}
	
	//----------------- PRODUTO --------------------
	@GetMapping("/ler")
	public ResponseEntity<?> listar(){
		List<Produto> lista = produtoService.listar();
		if(lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(lista);
	}
	
//	@GetMapping("/ler/{id}")
//	public ResponseEntity<Produto> buscar(@PathVariable long id) {
//		Optional<Produto> produto = produtoService.getById(id);
//		if(produto.isPresent()) {
//			return ResponseEntity.ok(produto.get());
//		}
//		return ResponseEntity.notFound().build();
//	}
	@GetMapping("/ler/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable long id) {
		Optional<Produto> produto = produtoService.getById(id);
		if(produto.isPresent()) {
			return ResponseEntity.ok(produto.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	//public ResponseEntity<Produto> buscarPorNome(@RequestParam String nome)
	
	@PostMapping("/criar")
	// Retornar 201 - Created
	// Retornar 422 - Não foi possivel processar a requisição.
	//@PreAuthorize("hasRole('admin') && hasRole('usuario')")
	public ResponseEntity<Object> criar(@RequestBody ProdutoDTO produtoDTO) {
		//TODO Refazer o método criar
		//produtoRepository.save(produto);
		ProdutoDTO produto = produtoService.criar(produtoDTO);
		
		// Gerar uma URI /api/produto/{id} - valor do Id que foi criado.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.getId())
				.toUri();

		return ResponseEntity.created(uri).body(produto);

	}
	
	//TODO: Refazer isso...
//	@PostMapping("/criar")
//	public ResponseEntity<?> create(@RequestBody ProdutoInserirDTO produtoInserirDTO) {
//		Produto produto = produtoInserirDTO.createProduto();
//		return ResponseEntity.ok(produto);
//	}

}
