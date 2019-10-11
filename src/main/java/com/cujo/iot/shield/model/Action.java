package com.cujo.iot.shield.model;

public enum Action {
  ALLOW ("allow"),
  BLOCK ("block"),
  QUARANTINE ("quarantine");

	private String name;

	public String getName() {
		return name;
	}

     Action(final String name) {
	    this.name = name;
	}
}
