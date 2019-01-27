package net.secudev.auth.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.secudev.auth.controller.dto.JSONCredential;
import net.secudev.auth.model.utilisateur.IUtilisateurRepository;
import net.secudev.auth.model.utilisateur.Utilisateur;

@RestController
@RequestMapping("/ano/")
public class AnonymousController {

	@Value("${token.expiration}")
	private int tokenExpiration;
	
	@Autowired
	private IUtilisateurRepository utilisateurs;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//Mettre en place un controller advice pour préciser l'ereur ds le cas ou le JSON est malformé
	//et se retrouver un erreur 500 sans précisions
	@PostMapping("token")
	public ResponseEntity<?> getAccessToken(@RequestBody JSONCredential cred, HttpServletRequest request) throws Exception {

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

		// 15 jours de validité pour ce token, devrions mettre cette valuer dans la
		// config
		user.createAccessToken(tokenExpiration);		
		user.setDateDernierAcces(LocalDateTime.now());
		user.setDerniereIpConnue(request.getRemoteAddr());

		utilisateurs.save(user);
		logger.trace("Access Token crée pour " + user.getLogin() + " : " + user.getAccessToken());

		return ResponseEntity.ok().body(user.getAccessToken());
	}

	@GetMapping("ping")
	public String ping(HttpServletRequest request) {

		return "Hello anonymous from " + request.getRemoteAddr();
	}

	public void logout(String token) {
	}

	public void inscription(String user, String motDePasse, String email) {
	}

	public boolean validerInscription(String codeValidation) {
		return false;
	}

	public void motDePassePerdu(String email) {
	}

	protected int getTokenExpiration() {
		return tokenExpiration;
	}

	protected void setTokenExpiration(int tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}
}
