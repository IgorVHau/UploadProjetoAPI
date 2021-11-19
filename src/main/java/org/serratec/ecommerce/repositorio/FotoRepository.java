package org.serratec.ecommerce.repositorio;

import org.serratec.ecommerce.dominio.FotoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<FotoProduto, Long>{	

}
