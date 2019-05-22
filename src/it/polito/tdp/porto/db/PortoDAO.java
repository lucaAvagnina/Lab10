package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.CoAuthor;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";
		
		Author autore = null;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				
			}
			conn.close();
			return autore;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";
		
		Paper paper = null;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				
			}
			conn.close();
			return paper;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Author> getAutori(){
		
		final String sql = "SELECT * FROM author";
		
		List<Author> result = new ArrayList();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		

			ResultSet rs = st.executeQuery();

			while(rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				result.add(autore);
	
			}
			conn.close();
		
			return result;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Paper> getArticoli() {

		final String sql = "SELECT * FROM paper";
	
		List<Paper> result = new ArrayList<Paper>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				result.add(paper);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<CoAuthor> getcoAutori(Map<Integer, Author> idMap, Map<Integer, Paper> paperMap) {
		final String sql = "SELECT c1.authorid AS a1, c2.authorid AS a2, c1.eprintid AS p "
				+ "FROM creator AS c1, creator AS c2 "
				+ "WHERE c1.eprintid = c2.eprintid AND c1.authorid != c2.authorid"; 
				
		List<CoAuthor> result = new ArrayList<CoAuthor>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author a1 = idMap.get(rs.getInt("a1"));
				Author a2 = idMap.get(rs.getInt("a2"));
				Paper p = paperMap.get(rs.getInt("p"));
				CoAuthor cTemp = new CoAuthor(a1, a2, p);
				result.add(cTemp);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
}