package net.secudev.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.secudev.auth.model.utilisateur.IUtilisateurRepository;
import net.secudev.auth.model.utilisateur.Utilisateur;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAuthModelApplicationTests {

	@Autowired
	IUtilisateurRepository users;
	
	@Test
	public void truc() {
		
		Utilisateur alice = users.findByLogin("alice");
		System.out.println(alice);
	}

}

