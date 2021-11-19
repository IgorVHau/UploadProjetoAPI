package org.serratec.ecommerce.repositorio;

import org.serratec.ecommerce.dominio.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

}
