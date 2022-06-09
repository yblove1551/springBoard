package com.spring.board.domain;

import java.util.Objects;

public class MailKeyword {
	private String keyword;
	private String descript;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	@Override
	public int hashCode() {
		return Objects.hash(descript, keyword);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailKeyword other = (MailKeyword) obj;
		return Objects.equals(descript, other.descript) && Objects.equals(keyword, other.keyword);
	}
	@Override
	public String toString() {
		return "MailKeyword [keyword=" + keyword + ", descript=" + descript + "]";
	}
	
	
}
