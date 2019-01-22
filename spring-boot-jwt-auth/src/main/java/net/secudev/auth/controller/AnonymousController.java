package net.secudev.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.secudev.auth.controller.dto.JSONCredential;
import net.secudev.auth.model.role.Role;
import net.secudev.auth.model.utilisateur.IUtilisateurRepository;
import net.secudev.auth.model.utilisateur.Utilisateur;
import net.secudev.auth.security.JWTService;

@RestController
@RequestMapping("/ano/")
public class AnonymousController {
	
	@Autowired
	private IUtilisateurRepository utilisateurs;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JWTService jwtService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("jwt")
	public ResponseEntity<?> getJWT(@RequestBody JSONCredential cred, HttpServletRequest request) throws Exception {

		if (!utilisateurs.existsByLogin(cred.getLogin())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad user");
		}

		Utilisateur user = utilisateurs.findByLogin(cred.getLogin());

		if (!user.isActif()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Disabled");
		}

		if (!passwordEncoder.matches(cred.getMotDePasse(), user.getHashMotDePasse())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad password");
		}

		List<String> roles = new ArrayList<String>();
		for (Role role : user.getRoles()) {
			roles.add(role.getLibelle());
		}

		String jwt = jwtService.createJWT(user.getLogin(), roles);

		logger.trace("JWT crée pour " + user.getLogin() + " : " + jwt);

		return ResponseEntity.ok().body(jwt);

	}

}
