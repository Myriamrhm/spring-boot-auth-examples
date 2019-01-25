package net.secudev.auth.application;

import java.util.List;

import net.secudev.auth.model.utilisateur.Utilisateur;

public interface IAdminService {
	
	Utilisateur creerOuModifierUtilisateur(Utilisateur utilisateur);
	void supprimerUtilisateurParId(Long id);
	List<Utilisateur> retournerUtilisateurs();
	Utilisateur retournerUtilisateurParId(Long id);
	List<Utilisateur> rechercherUtilisateursParMotCle(String motCle);
	boolean loginExiste(String login);
	boolean emailExiste(String email);
	
	List<Utilisateur> retournerUtilisateursParRole(Long rId);
	void ajouterUtilisateurDansRole(Long uId, Long rId);
	void supprimerUtilisateurDuRole(Long uId, Long rId);
	boolean utilisateurEstDansRole(Long uId, Long rId);
	
	

}
