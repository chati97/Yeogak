package com.ssafy.enjoytrip.tour.model;

import java.util.Arrays;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "InfoParameterDto : 관광지 검색 파라미터 정보", description = "관광지의 정보를 얻기위한 부가적인 파라미터.")
public class InfoParameterDto {

	@ApiModelProperty(value = "현재 페이지 번호")
	private int pg;
	@ApiModelProperty(value = "페이지당 글갯수")
	private int spp;
	@ApiModelProperty(value = "페이지의 시작 번호")
	private int start;
	@ApiModelProperty(value = "선택된 시/도의 코드번호")
	private int sidoCode;
	@ApiModelProperty(value = "선택된 구/군의 코드번호")
	private int gugunCode;
	@ApiModelProperty(value = "선택된 관광지 유형의 리스트")
	private int[] selectedAttTypes;
	@ApiModelProperty(value = "검색어")
	private String word;

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		this.pg = pg;
	}

	public int getSpp() {
		return spp;
	}

	public void setSpp(int spp) {
		this.spp = spp;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}
	
	public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	public int[] getSelectedAttTypes() {
		return selectedAttTypes;
	}

	public void setSelectedAttTypes(int[] selectedAttTypes) {
		this.selectedAttTypes = selectedAttTypes;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return "InfoParameterDto [pg=" + pg + ", spp=" + spp + ", start=" + start + ", sidoCode=" + sidoCode
				+ ", gugunCode=" + gugunCode + ", selectedAttTypes=" + Arrays.toString(selectedAttTypes) + ", word="
				+ word + "]";
	}
}
