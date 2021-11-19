package org.serratec.ecommerce.servico;

import java.io.IOException;
import java.util.Optional;

import org.serratec.ecommerce.dominio.FotoProduto;
import org.serratec.ecommerce.dominio.Produto;
import org.serratec.ecommerce.repositorio.FotoRepository;
import org.serratec.ecommerce.repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;
	
    @Autowired
	private ProdutoRepository produtoRepository;

	public FotoProduto inserir(Produto produto, MultipartFile file) throws IOException {
		FotoProduto foto = new FotoProduto();
		foto.setNome(file.getName());
		foto.setDados(file.getBytes());
		foto.setTipo(file.getContentType());
		foto.setProduto(produto);
		return fotoRepository.save(foto);
	}
	
	public FotoProduto buscar(Long id) {				
		Optional<Produto> produto = produtoRepository.findById(id);
				
		if(produto.isPresent()) {
			FotoProduto foto = produto.get().getFoto();
			return foto;			
		}		
		return null;
		
//		Optional<Foto> foto = fotoRepository.findById(id);
//		if(!foto.isPresent()) {
//			return null;
//		}
//		return foto.get();
	}
	
	public void deletar(Long id) {
		Optional<FotoProduto> foto = fotoRepository.findById(id);
		//Optional<Produto> produto = produtoRepository.findById(id);
		if(foto.isPresent()) {
			fotoRepository.deleteById(id);
		}
	}
	
	public FotoProduto atualizar(Long id, MultipartFile file) {
		//Optional<Produto> produtoPesquisado = produtoRepository.findById(id);
		//Optional<FotoProduto> fotoPesquisada = fotoRepository.findById(id)
		
//		Optional<FotoProduto> foto = fotoRepository.findById(id)
//				.orElseThrow(() -> new ); Legal!!!
		Optional<FotoProduto> foto = fotoRepository.findById(id);
		if(foto.isPresent()) {
			fotoRepository.deleteById(id);
			FotoProduto novaFoto = new FotoProduto();
			novaFoto.setNome(novaFoto.getNome());
			novaFoto.setDados(novaFoto.getDados());
			novaFoto.setTipo(novaFoto.getTipo());
			fotoRepository.save(novaFoto);
			return novaFoto;
		}
		return null;
		
	}

}
