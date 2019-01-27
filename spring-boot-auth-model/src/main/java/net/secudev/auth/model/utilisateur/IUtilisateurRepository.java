package net.secudev.auth.model.utilisateur;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Long> {

Boolean existsByLogin(String login);
	
	Utilisateur findByLogin(String login);

	Utilisateur findByAccessToken(String accessToken);

	Utilisateur findByEmail(String email);

	Utilisateur findByDerniereIpConnue(String ip);

	Utilisateur findByCodeValidationInscription(String code);

	List<Utilisateur> findByActifTrueOrderByLoginAsc();

	List<Utilisateur> findByActifFalseOrderByLoginAsc();

	List<Utilisateur> findByDateDernierAccesBetweenOrderByLoginAsc(LocalDateTime debut, LocalDateTime fin);

	List<Utilisateur> findByDateExpirationAccessTokenBetweenOrderByLoginAsc(LocalDateTime debut, LocalDateTime fin);

}
