package com.woobe.model;

import java.io.Serializable;

public class ProposalSessions implements Serializable{
	private static final long serialVersionUID = 5268446291471764064L;
	int from_id;
	int to_id;
	java.sql.Timestamp start_time;
	java.sql.Timestamp end_time;
	String proposal_status;
	
	public int getFrom_id() {
		return from_id;
	}
	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}
	public int getTo_id() {
		return to_id;
	}
	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}
	public java.sql.Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(java.sql.Timestamp start_time) {
		this.start_time = start_time;
	}
	public java.sql.Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(java.sql.Timestamp end_time) {
		this.end_time = end_time;
	}
	public String getProposal_status() {
		return proposal_status;
	}
	public void setProposal_status(String proposal_status) {
		this.proposal_status = proposal_status;
	}
  
}
