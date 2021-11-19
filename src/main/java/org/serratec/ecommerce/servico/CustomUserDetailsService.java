package org.serratec.ecommerce.servico;

import java.util.Optional;

import org.serratec.ecommerce.dominio.Cliente;
import org.serratec.ecommerce.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Cliente> usuario = clienteRepository.findByEmail(username);
		if(usuario.isPresent()) {
			AuthorityUtils.createAuthorityList("ROLE_admin", "ROLE_usuario");
			
			return new User(
					usuario.get().getEmail(), 
					usuario.get().getSenha(), 
					AuthorityUtils.createAuthorityList("ROLE_admin", "ROLE_usuario")
					);
		}
		throw new UsernameNotFoundException("Usuário"+ username +"não encontrado.");
	}
}
