package net.secudev.auth.controller.dto;

import lombok.Data;

@Data
public class JSONCredentialInfos {
	
	private String login;
	private String[] roles;	

}