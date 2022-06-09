package com.spring.board.domain;

import java.util.Date;

public class Board {
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private Integer view_cnt;
	private Date reg_date;
	private Date up_date;
	
	@Override
	public String toString() {
		return "Board [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer + ", view_cnt="
				+ view_cnt + ", reg_date=" + reg_date + ", up_date=" + up_date + "]";
	}
	
	public Integer getBno() {
		return bno;
	}
	
	public void setBno(Integer bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Integer getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(Integer view_cnt) {
		this.view_cnt = view_cnt;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getUp_date() {
		return up_date;
	}
	public void setUp_date(Date up_date) {
		this.up_date = up_date;
	}
}
