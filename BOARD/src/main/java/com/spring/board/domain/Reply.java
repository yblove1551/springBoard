package com.spring.board.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.json.simple.JSONObject;
public class Reply {
	private Integer rno;
	private Integer bno;
	private Integer prno = 0;
	private String reply;
	private String replyer;
	private Date reg_date;
	private Date up_date;
	private Integer level = 1;
	
	@Override
	public String toString() {
		return "Reply [rno=" + rno + ", bno=" + bno + ", prno=" + prno + ", reply=" + reply + ", replyer=" + replyer
				+ ", reg_date=" + reg_date + ", up_date=" + up_date + "]";
	}
		
	@Override
	public int hashCode() {
		return Objects.hash(bno, rno, prno, reg_date, reply, replyer, up_date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reply other = (Reply) obj;
		return bno == other.bno && rno == other.rno && prno == other.prno && Objects.equals(reg_date, other.reg_date)
				&& Objects.equals(reply, other.reply) && Objects.equals(replyer, other.replyer)
				&& Objects.equals(up_date, other.up_date);
	}

	public Integer getRno() {
		return rno;
	}
	public void setRno(Integer rno) {
		this.rno = rno;
	}
	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
	}
	public Integer getPrno() {
		return prno;
	}
	public void setPrno(Integer prno) {
		this.prno = prno;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getReplyer() {
		return replyer;
	}
	public void setReplyer(String replyer) {
		this.replyer = replyer;
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
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public JSONObject toJSONObj() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String regDate = sf.format(reg_date);
		String update = sf.format(up_date);
		
		JSONObject obj = new JSONObject();
		obj.put("rno", "" + rno);
		obj.put("bno", "" + bno);
		obj.put("prno", "" + prno);		
		obj.put("reply", reply);	
		obj.put("replyer", replyer);
		obj.put("reg_date", "" + regDate);
		obj.put("up_date",  "" + update);
		obj.put("level",  "" + level);
		return obj;
	}

	
	
}
