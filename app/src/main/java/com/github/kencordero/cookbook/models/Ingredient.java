package com.github.kencordero.cookbook.models;

import java.util.UUID;

public class Ingredient {
	private long mId;
	private UUID mUuid;
	private String mName;

	// TODO - when adding ingredients, make sure one doesn't already exist
	public Ingredient(long id, String name) {
		mId = id;
		mName = name;
		mUuid = UUID.randomUUID();
	}

	public Ingredient() {
		this(-1, "");
	}

	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public UUID getUuid() {
		return mUuid;
	}
}
