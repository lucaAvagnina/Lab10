
package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private Map<Integer, Author> idMap;
	private Map<Integer, Paper> paperMap;
	private List<CoAuthor> coAutorList;
	
	private PortoDAO dao;
	
	private Graph<Author, DefaultEdge> grafo;
	
	public Model() {
		this.idMap = new HashMap<Integer, Author>();
		
		dao = new PortoDAO(); 
	}

	public Collection<Author> getAutori() {
		
		List<Author> tempList = new ArrayList<Author>(dao.getAutori());
		
		for(Author a : tempList) {
			idMap.put(a.getId(), a);
		}
		Collections.sort(tempList);
		return tempList;
	}

	public List<Author> getCoAutori(Author author) {
		
		List<Paper> tempList = new ArrayList<Paper>(dao.getArticoli());
		this.paperMap = new HashMap<Integer, Paper>();
		
		for(Paper p : tempList) {
			paperMap.put(p.getEprintid(), p);
		}
		
		coAutorList = new ArrayList<CoAuthor>(dao.getcoAutori(idMap));
		
		this.createGraph();
		List<Author> result = new ArrayList<Author>();
		for(Author a : Graphs.neighborListOf(grafo, author)) {
			result.add(a);
			}
		
		return result;
		
	}

	private void createGraph() {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		//Aggiungo i vertici
		
		Graphs.addAllVertices(grafo, idMap.values());
		
		//Aggiungo archi
		
		for(CoAuthor ca : coAutorList) {
			if(grafo.getEdge(ca.getAuthor2(), ca.getAuthor1()) == null) {
				grafo.addEdge(ca.getAuthor1(), ca.getAuthor2());
			}
		}
		
	}

}
