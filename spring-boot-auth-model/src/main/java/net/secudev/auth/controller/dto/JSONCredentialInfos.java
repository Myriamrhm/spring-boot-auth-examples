package net.secudev.auth.controller.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class JSONCredentialInfos {
	
	private String login;
	private Set<String> roles = new HashSet<String>();	

}