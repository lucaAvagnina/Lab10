package it.polito.tdp.porto.model;

public class CoAuthor {

	private Author author1;
	private Author author2;
	private Paper paper;
	
	public CoAuthor(Author author1, Author author2, Paper paper) {
		super();
		this.author1 = author1;
		this.author2 = author2;
		this.paper = paper;
	}

	public Author getAuthor1() {
		return author1;
	}

	public Author getAuthor2() {
		return author2;
	}

	public Paper getPaper() {
		return paper;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author1 == null) ? 0 : author1.hashCode());
		result = prime * result + ((author2 == null) ? 0 : author2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoAuthor other = (CoAuthor) obj;
		if (author1 == null) {
			if (other.author1 != null)
				return false;
		} else if (!author1.equals(other.author1))
			return false;
		if (author2 == null) {
			if (other.author2 != null)
				return false;
		} else if (!author2.equals(other.author2))
			return false;
		return true;
	}
	
	public Paper getPaper(Author author1, Author author2) {
		if(author1.equals(this.author1) && author2.equals(this.author2))
			return this.paper;
		return null;
	}
}
