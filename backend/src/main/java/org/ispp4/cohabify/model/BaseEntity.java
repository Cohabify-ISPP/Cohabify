package org.ispp4.cohabify.model;

import org.springframework.data.annotation.Id;

public class BaseEntity {

	@Id
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}