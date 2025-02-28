package com.ssafy.enjoytrip.tour.model;

public class SidoDto {
	private int sidoCode;
	private String sidoName;
	
	public SidoDto() {}
	
	public int getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	
	@Override
	public String toString() {
		return "SidoDto [sidoCode=" + sidoCode + ", sidoName=" + sidoName + "]";
	}	
}
