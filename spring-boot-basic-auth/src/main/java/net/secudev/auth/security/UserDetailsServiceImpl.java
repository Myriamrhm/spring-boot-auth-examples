package net.secudev.auth.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.secudev.auth.model.role.Role;
import net.secudev.auth.model.utilisateur.IUtilisateurRepository;
import net.secudev.auth.model.utilisateur.Utilisateur;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	IUtilisateurRepository utilisateurs;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		Utilisateur user = utilisateurs.findByLogin(username);		

		if (user == null) {throw new UsernameNotFoundException(username);}
		
		logger.trace("Essai d'authentification par : "+user.getLogin());

		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();

		for (Role role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority("ROLE_"+role.getLibelle()));
			logger.trace(role.getLibelle());
		}		
		
		return new User(user.getLogin(), user.getHashMotDePasse(), roles);
	}
}
