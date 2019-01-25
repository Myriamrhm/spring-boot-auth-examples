package net.secudev.auth.model.utilisateur;

import java.time.LocalDateTime;
import java.util.Collections;
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
	private String apiKey;

	@Getter
	@Setter
	LocalDateTime dateExpirationApiKey;

	@Getter
	@Setter
	private String codeValidationInscription;

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

	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(roles);
		// return new ArrayList<Role>(roles);
	}

	public String genererCodeValidation() {
		this.codeValidationInscription = UUID.randomUUID().toString();
		return this.codeValidationInscription;
	}

	public String createApiKey() {
		this.apiKey = UUID.randomUUID().toString();
		this.dateExpirationApiKey = LocalDateTime.now().plusDays(30);
		return this.apiKey;
	}

	public void revoquerAcces() {
		this.codeValidationInscription = "";
		this.apiKey = "";
		this.dateExpirationApiKey = LocalDateTime.now().minusMinutes(5);
	}
}
