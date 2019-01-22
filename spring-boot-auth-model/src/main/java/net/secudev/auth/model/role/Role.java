package net.secudev.auth.model.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.secudev.auth.model.common.AbstractEntity;

@Entity
@ToString(callSuper = true, includeFieldNames = true)
public class Role extends AbstractEntity {
		
	@Getter
	@Setter
	@NotEmpty
	@Size(min = 1, max = 40)
	@Column(nullable = false, unique = true)
	private String libelle;

	protected Role() {}
	
	public Role(String libelle)
	{
		this.setLibelle(libelle);
	}
	
}
