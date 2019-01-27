package net.secudev.auth.model.utilisateur;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.secudev.auth.model.common.AbstractEntity;
import net.secudev.auth.model.role.Role;

@Entity
@ToString(callSuper = true, includeFieldNames = true)
public class Utilisateur extends AbstractEntity {

	@NotEmpty
	@Size(min = 1, max = 20)
	@Column(nullable = false, unique = true)
	@Getter
	@Setter
	private String login;

	@NotEmpty
	@Getter
	@Setter
	private String hashMotDePasse;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<Role>();

	@Email
	@Column(unique = true)
	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	@Column(unique = true)
	private String accessToken;

	@Getter
	private LocalDateTime dateExpirationAccessToken;

	@Getter
	private String codeValidationInscription;
	
	@Getter
	private LocalDateTime dateExpirationCodeInscription;

	@Getter
	@Setter
	private boolean actif;

	@Getter
	@Setter
	private String derniereIpConnue;

	@Getter
	@Setter
	LocalDateTime dateDernierAcces;

	protected Utilisateur() {
	}

	public Utilisateur(String login, String hashMotDePasse, String email) {
		this.setLogin(login);
		this.setHashMotDePasse(hashMotDePasse);
		this.setEmail(email);
	}

	public Utilisateur(String login, String hashMotDePasse, String email, List<Role> roles) {
		this(login, hashMotDePasse, email);
		if (roles != null) {
			for (Role role : roles) {				
				this.ajouterRole(role);
			}
		}
	}

	public void ajouterRole(Role role) {
		if (role != null)
			this.roles.add(role);
	}

	public void supprimerRole(Role role) {
		if (role != null)
			this.roles.remove(role);
	}

	public List<Role> getRoles() {	
		return new ArrayList<Role>(roles);
	}

	public String genererCodeValidation(int joursExpiration) {
		this.codeValidationInscription = UUID.randomUUID().toString();
		this.dateExpirationCodeInscription = LocalDateTime.now().plusDays(joursExpiration);
		return this.codeValidationInscription;
	}

	public String createAccessToken(int joursExpiration) {
		this.accessToken = UUID.randomUUID().toString();
		this.dateExpirationAccessToken = LocalDateTime.now().plusDays(joursExpiration);
		return this.accessToken;
	}

	public void revoquerAcces() {
		this.codeValidationInscription = "";
		this.dateExpirationCodeInscription=LocalDateTime.now().minusMinutes(5);
		this.accessToken = "";
		this.dateExpirationAccessToken = LocalDateTime.now().minusMinutes(5);
	}
}
