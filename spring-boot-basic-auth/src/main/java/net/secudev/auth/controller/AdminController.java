package net.secudev.auth.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.secudev.auth.annotation.Tracable;
import net.secudev.auth.controller.dto.JSONCredentialInfos;

@RestController
@PreAuthorize("hasRole('ROLE_admin')")
@RequestMapping("/admin/")
public class AdminController {
	
	@GetMapping("me")
	@Tracable
	public ResponseEntity<JSONCredentialInfos> me() {
		JSONCredentialInfos me = new JSONCredentialInfos();
		me.setLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		me.setRoles(roles);
		return new ResponseEntity<JSONCredentialInfos>(me, HttpStatus.OK);
	}
	
	@GetMapping("add")
	@Tracable
	public String add() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	
}