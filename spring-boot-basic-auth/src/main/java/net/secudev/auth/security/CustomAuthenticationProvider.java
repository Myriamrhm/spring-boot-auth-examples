package net.secudev.auth.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.secudev.auth.model.role.Role;
import net.secudev.auth.model.utilisateur.IUtilisateurRepository;
import net.secudev.auth.model.utilisateur.Utilisateur;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	IUtilisateurRepository utilisateurs;

	@Autowired
	PasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName().toString();
		

		Utilisateur user = utilisateurs.findByLogin(username);

		if (user == null) {
			String msg = "Utilisateur non trouvé : "+username;
			logger.trace(msg);
			throw new UsernameNotFoundException(msg);
		}

		if (!passwordEncoder.matches((CharSequence) authentication.getCredentials(), user.getHashMotDePasse())) {
			String msg = "Mauvais mot de passe pour "+username;
			logger.trace(msg);
			throw new BadCredentialsException(msg);
		}		

		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();

		for (Role role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority("ROLE_" + role.getLibelle()));			
		}
		
		logger.trace("Authentification OK : " + user.getLogin()+" "+roles.toString());

		return new UsernamePasswordAuthenticationToken(user.getLogin(), user.getHashMotDePasse(), roles);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
