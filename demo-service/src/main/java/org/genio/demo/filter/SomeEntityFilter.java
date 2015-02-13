package org.genio.demo.filter;

import java.io.Serializable;

public class SomeEntityFilter implements Serializable {

	private static final long serialVersionUID = -6198833231361862864L;

	private String code = null;
	private String name = null;
	private String description = null;

	public SomeEntityFilter() {
		
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
	public String toString() {
		return "SomeEntityFilter ["
				+ "code=" + code
				+ ", name=" + name
				+ ", description=" + description
				+ "]";
	}

}
