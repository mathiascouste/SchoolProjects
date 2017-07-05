package model;

import java.io.Serializable;
import java.util.Date;

public class Publication implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String writer;
	private String message;
	private String date;
	
	public Publication() {
		this("","");
	}
	public Publication(String writer, String message) {
		this.writer = writer;
		this.message = message;
		this.date = new Date().toString();
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String toString() {
		return writer + ", le " + date + " : " + message;
	}
}
