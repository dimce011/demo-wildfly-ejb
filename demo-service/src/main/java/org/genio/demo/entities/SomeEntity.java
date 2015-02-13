package org.genio.demo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "some_entity")
public class SomeEntity implements Serializable {

	private static final long serialVersionUID = -8670423723423349936L;
	
	private Integer id = null;
	private String code = null;
	private String name = null;
	private String description = null;

	public SomeEntity() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "code", unique = true, nullable = false, length = 16)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SomeEntity)) {
			return false;
		}
		SomeEntity other = (SomeEntity)obj;
		if (this.getId() != null && other.getId() != null) {
			return this.getId().equals(other.getId());
		}
		if (this.getCode() != null && other.getCode() != null) {
			return this.getCode().equals(other.getCode());
		}
		return false;
	}

	@Override
	public String toString() {
		return "SomeEntity ["
				+ "id=" + id
				+ ", code=" + code
				+ ", name=" + name
				+ ", description=" + description
				+ "]";
	}

}
