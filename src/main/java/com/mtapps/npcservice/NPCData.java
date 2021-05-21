package com.mtapps.npcservice;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class NPCData {
	  
	  private ObjectId id;
	  @BsonProperty(value = "name")
	  private String name;
	  @BsonProperty(value = "ST")
	  private String ST;
	  @BsonProperty(value = "DX")
	  private String DX;
	  @BsonProperty(value = "IQ")
	  private String IQ;
	  @BsonProperty(value = "MA")
	  private String MA;
	  
	  public NPCData() {
		  // Eventually hit the database here for the named NPC's data
		  System.out.println( "in generic ctor" );
		  
	  }
	  
//	  public NPCData( Document doc ) {
//		  populate( doc );
//	  }
	  
	  public NPCData( ObjectId id, String name, String ST, String DX, String IQ, String MA ) {
		  this.name = name;
//		  // Eventually hit the database here for the named NPC's data
		  System.out.println( "in detail ctor");
//		  this.id = id;
		  this.ST = ST;
		  this.DX = DX;
//		  this.DX = new Integer(DX).intValue();
//		  this.IQ = new Integer(IQ).intValue();
//		  this.MA = new Integer(MA).intValue();
		  
	  }
	  
//	  private void populate( Document doc ) {
//		  
//		  this.name = (String) doc.get( "name" );
//		  System.out.println( "in nPC --> name = " + doc.get( "name" ) );
//		  this.id = (String) doc.get ("_id" );
//		  this.ST = new Integer( (String) doc.get( "ST" ) ).intValue();
//		  this.DX = new Integer( (String) doc.get( "DX" ) ).intValue();
//		  this.IQ = new Integer( (String) doc.get( "IQ" ) ).intValue();
//		  this.MA = new Integer( (String) doc.get( "MA" ) ).intValue();
//	  }
	  
	  public void setId(ObjectId id) {
		this.id = id;
	}

	public void setName(String name) {
		System.out.println( "setting name --> " + name);
		this.name = name;
	}

	public void setST(String sT) {
		System.out.println( "setting ST --> " + sT);
		this.ST = sT;
	}

	public void setDX(String dX) {
		System.out.println( "setting DX --> " + dX);
		DX = dX;
	}

	public void setIQ(String iQ) {
		IQ = iQ;
	}

	public void setMA(String mA) {
		MA = mA;
	}

	public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getST() {
		return ST;
	}

	public String getDX() {
		return DX;
	}

	public String getIQ() {
		return IQ;
	}

	public String getMA() {
		return MA;
	}

	@Override
	  public String toString() {
	    return "NPC [id=" + id + ", name=" + name + ", ST=" + ST + ", DX=" + DX + ", IQ=" + IQ + ", MA=" + MA + "]";
	  }
}


