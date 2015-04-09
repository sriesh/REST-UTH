package com.restuth.entity;

import java.util.LinkedHashMap;
import java.util.List;

public class Statistics {
	private String reportid;
	private String reportname;
	private LinkedHashMap<String, BlogPost> popularPost;
	private int usercurday;
	private int usercurweek;
	private int usercurmonth;
	private int maxperday;
	private int maxperweek;
	private int maxpermonth;
	private String reportdate;

	public Statistics() {
	}

	public Statistics(String reportid, String reportname, int usercurday,
			int usercurweek, int usercurmonth,int maxperday, int maxperweek,int maxpermonth) {
		this.reportid = reportid;
		this.reportname = reportname;
		this.usercurday = usercurday;
		this.usercurweek = usercurweek;
		this.usercurmonth = usercurmonth;
		this.maxperday=maxperday;
		this.maxpermonth=maxpermonth;
		this.maxperweek=maxperweek;

	}

	public String getReportdate() {
		return reportdate;
	}

	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}

	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public LinkedHashMap<String, BlogPost> getPopularPost() {
		return popularPost;
	}

	public void setPopularPost(LinkedHashMap<String, BlogPost> popularPost) {
		this.popularPost = popularPost;
	}

	public int getusercurday() {
		return usercurday;
	}

	public void setusercurday(int usercurday) {
		this.usercurday = usercurday;
	}

	public int getusercurweek() {
		return usercurweek;
	}

	public void setusercurweek(int usercurweek) {
		this.usercurweek = usercurweek;
	}

	public int getusercurmonth() {
		return usercurmonth;
	}

	public void setusercurmonth(int usercurmonth) {
		this.usercurmonth = usercurmonth;
	}

	public int getMaxperday() {
		return maxperday;
	}

	public void setMaxperday(int maxperday) {
		this.maxperday = maxperday;
	}

	public int getMaxperweek() {
		return maxperweek;
	}

	public void setMaxperweek(int maxperweek) {
		this.maxperweek = maxperweek;
	}

	public int getMaxpermonth() {
		return maxpermonth;
	}

	public void setMaxpermonth(int maxpermonth) {
		this.maxpermonth = maxpermonth;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
