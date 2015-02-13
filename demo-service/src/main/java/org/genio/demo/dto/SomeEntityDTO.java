package org.genio.demo.dto;

import java.io.Serializable;

public class SomeEntityDTO implements Serializable {

	private static final long serialVersionUID = -2114164805423922642L;
	
	private Integer id = null;
	private String code = null;
	private String name = null;
	private String description = null;

	public SomeEntityDTO() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SomeEntityDTO)) {
			return false;
		}
		SomeEntityDTO other = (SomeEntityDTO)obj;
		if (this.getId() != null && other.getId() != null) {
			return this.getId().equals(other.getId());
		}
		if (this.getCode() != null && this.getCode().trim().length() != 0 && other.getCode() != null && other.getCode().trim().length() != 0) {
			return this.getCode().trim().equals(other.getCode().trim());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "SomeEntityDTO ["
				+ "id=" + id
				+ ", code=" + code
				+ ", name=" + name
				+ ", description=" + description
				+ "]";
	}
	
}
