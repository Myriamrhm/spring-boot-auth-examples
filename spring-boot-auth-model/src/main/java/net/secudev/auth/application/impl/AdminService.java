package net.secudev.auth.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.secudev.auth.application.IAdminService;
import net.secudev.auth.model.role.IRoleRepository;
import net.secudev.auth.model.utilisateur.IUtilisateurRepository;
import net.secudev.auth.model.utilisateur.Utilisateur;

public class AdminService implements IAdminService {

	@Autowired
	IUtilisateurRepository utilisateurs;
	
	@Autowired
	IRoleRepository roles;	
	
	@Override
	public Utilisateur creerOuModifierUtilisateur(Utilisateur utilisateur) {
		
		return utilisateurs.save(utilisateur);
	}

	@Override
	public void supprimerUtilisateurParId(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Utilisateur> retournerUtilisateurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateur retournerUtilisateurParId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utilisateur> rechercherUtilisateursParMotCle(String motCle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loginExiste(String login) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean emailExiste(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Utilisateur> retournerUtilisateursParRole(Long rId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ajouterUtilisateurDansRole(Long uId, Long rId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void supprimerUtilisateurDuRole(Long uId, Long rId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean utilisateurEstDansRole(Long uId, Long rId) {
		// TODO Auto-generated method stub
		return false;
	}

}
