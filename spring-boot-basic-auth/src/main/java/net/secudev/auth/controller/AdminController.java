package net.secudev.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.secudev.auth.annotation.Tracable;

@RestController
@PreAuthorize("hasRole('ROLE_admin')")
@RequestMapping("/admin/")
public class AdminController {
	
	@GetMapping("me")
	@Tracable
	public String me() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@GetMapping("add")
	@Tracable
	public String add() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	
}