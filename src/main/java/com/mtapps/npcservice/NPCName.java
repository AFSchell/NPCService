package com.mtapps.npcservice;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class NPCName {
	  @BsonProperty(value = "name")
	  private String name;

	public String getName() {
		return name;
	}

}
