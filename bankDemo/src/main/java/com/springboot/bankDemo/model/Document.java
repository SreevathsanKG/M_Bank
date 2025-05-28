package com.springboot.bankDemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "document")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "pan_doc_link", nullable = false)
	private String panDocLink;
	@Column(name = "aadhar_doc_link", nullable = false)
	private String aadharDocLink;
	@ManyToOne(optional = false)
	private Customer customer;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getPanDocLink() { return panDocLink; }
	public void setPanDocLink(String panDocLink) { this.panDocLink = panDocLink; }
	public String getAadharDocLink() { return aadharDocLink; }
	public void setAadharDocLink(String aadharDocLink) { this.aadharDocLink = aadharDocLink; }
	public Customer getCustomer() { return customer; }
	public void setCustomer(Customer customer) { this.customer = customer; }
	
}
