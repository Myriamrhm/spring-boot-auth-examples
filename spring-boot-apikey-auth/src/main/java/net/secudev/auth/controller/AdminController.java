package net.secudev.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ROLE_admin')")
@RequestMapping("/admin/")
public class AdminController {

	@GetMapping("me")
	public String me() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
