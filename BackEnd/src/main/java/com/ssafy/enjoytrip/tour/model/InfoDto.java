package com.ssafy.enjoytrip.tour.model;

import java.util.List;

// attraction_info와 attraction_description의 join결과에 해당하는 DTO
public class InfoDto {
	private int contentId;
	private String title;
	private String addr1;
	private String addr2;
	private String tel;
	private String firstImage;
	private double latitude;
	private double longitude;
	private double distance;
	private String homepage;
	private String overview;
	private double ratingAvg;
	private List<ReviewDto> reviewList;
	// 첫번째 인덱스: 0은 리뷰수, 1은 리뷰 총점
	// 두번째 인덱스: 나이대
	// 0: 0~19
	// 1: 20~29
	// 2: 30~39
	// 3: 40~49
	// 4: 50~59
	// 5: 60대 이상
	private int[][] maleRating;
	private int[][] femaleRating;

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public double getRatingAvg() {
		return ratingAvg;
	}

	public void setRatingAvg(double ratingAvg) {
		this.ratingAvg = ratingAvg;
	}
	
	public List<ReviewDto> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<ReviewDto> reviewList) {
		this.reviewList = reviewList;
	}

	public int[][] getMaleRating() {
		return maleRating;
	}

	public void setMaleRating(int[][] maleRating) {
		this.maleRating = maleRating;
	}

	public int[][] getFemaleRating() {
		return femaleRating;
	}

	public void setFemaleRating(int[][] femaleRating) {
		this.femaleRating = femaleRating;
	}

	@Override
	public String toString() {
		return "InfoDto [contentId=" + contentId + ", title=" + title + ", addr1=" + addr1 + ", addr2=" + addr2
				+ ", tel=" + tel + ", firstImage=" + firstImage + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", description=" + ", distance=" + distance + ", homepage=" + homepage + ", overview="
				+ overview + ", ratingAvg=" + ratingAvg + "]";
	}

}
