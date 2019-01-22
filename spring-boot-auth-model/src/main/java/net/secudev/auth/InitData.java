package net.secudev.auth;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.secudev.auth.model.role.IRoleRepository;
import net.secudev.auth.model.role.Role;
import net.secudev.auth.model.utilisateur.IUtilisateurRepository;
import net.secudev.auth.model.utilisateur.Utilisateur;

@Component
public class InitData implements CommandLineRunner{

	@Autowired
	private IRoleRepository roles;
	@Autowired
	private IUtilisateurRepository utilisateurs;
	
	@Autowired
	PasswordEncoder encoder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run(String... args) throws Exception {
		
		//Si les users et les roles sont vides
		if(roles.count() == 0 && utilisateurs.count()==0){
			
			logger.trace("Data vides, création ...");
			
			Role admin = new Role("admin");
			Role regular = new Role("regular");
			Role vip = new Role("vip");
			
			logger.trace("Ajout des rôles regular, admin et vip ...");
			roles.saveAll(Arrays.asList(regular, vip, admin));			
			
			Utilisateur root = new Utilisateur("root", encoder.encode("password"), "root@secudev.net");
			root.genererCodeValidation();
			root.setDateDernierAcces(LocalDateTime.now().plusDays(2));
			root.createApiKey();
			root.setActif(true);
			root.ajouterRole(admin);			
			utilisateurs.save(root);				
			
			Utilisateur bob = new Utilisateur("bob", encoder.encode("password"), "bob@secudev.net");
			bob.genererCodeValidation();
			bob.setDateDernierAcces(LocalDateTime.now().plusDays(2));
			bob.createApiKey();
			bob.setActif(true);
			bob.ajouterRole(regular);			
			utilisateurs.save(bob);
			
			
			Utilisateur alice = new Utilisateur("alice", encoder.encode("password"), "alice@secudev.net");
			alice.genererCodeValidation();
			alice.setDateDernierAcces(LocalDateTime.now().plusDays(2));
			alice.createApiKey();
			alice.setActif(true);
			alice.ajouterRole(vip);	
			alice.ajouterRole(regular);	
			utilisateurs.save(alice);
			
			logger.trace("Ajout des utilisateurs admin, bob et alice ...");
			utilisateurs.saveAll(Arrays.asList(root, bob, alice));
		}		
	}
}
