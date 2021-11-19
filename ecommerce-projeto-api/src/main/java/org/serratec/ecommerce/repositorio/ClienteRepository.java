package org.serratec.ecommerce.repositorio;

import java.util.Optional;

import org.serratec.ecommerce.dominio.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByEmail(String email);

	Cliente findByNome(String nome);

}
