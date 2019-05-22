package it.polito.tdp.porto.model;

public class CoAuthor {

	private Author author1;
	private Author author2;
	
	public CoAuthor(Author author1, Author author2) {
		super();
		this.author1 = author1;
		this.author2 = author2;
	}

	public Author getAuthor1() {
		return author1;
	}

	public Author getAuthor2() {
		return author2;
	}
	
	
}
