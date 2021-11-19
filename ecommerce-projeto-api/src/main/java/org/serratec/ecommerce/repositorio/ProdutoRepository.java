package org.serratec.ecommerce.repositorio;

import org.serratec.ecommerce.dominio.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	Produto findByNome(String nome);

}
