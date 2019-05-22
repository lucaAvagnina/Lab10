
package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private Map<Integer, Author> idMap;
	private Map<Integer, Paper> paperMap;
	private List<CoAuthor> coAuthorList;
	
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
		
		coAuthorList = new ArrayList<CoAuthor>(dao.getcoAutori(idMap, paperMap));
		
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
		
		for(CoAuthor ca : coAuthorList) {
			if(grafo.getEdge(ca.getAuthor2(), ca.getAuthor1()) == null) {
				grafo.addEdge(ca.getAuthor1(), ca.getAuthor2());
			}
		}
		
	}
	
	private List<Author> trovaCamminoMinimo(Author autore1, Author autore2){
		DijkstraShortestPath<Author, DefaultEdge> dijstra = new DijkstraShortestPath<Author, DefaultEdge>(this.grafo);
		GraphPath<Author, DefaultEdge> path = dijstra.getPath(autore1, autore2);
		if(path != null)
			return path.getVertexList();
		
		return new ArrayList<Author>();
	}

	public List<Paper> getPath(Author author1, Author author2) {
		List<Author> pathAuthor = new ArrayList<Author>(this.trovaCamminoMinimo(author1, author2));
		List<Paper> pathPaper = new ArrayList<Paper>(); 
		
		/*for(int i = 0; i < pathAuthor.size(); i++) {
			int j = 0;
			Paper paperTemp = null;
			while(j < coAuthorList.size() && paperTemp == null) {
				paperTemp = coAuthorList.get(j).getPaper(author1, author2);
				}
			pathPaper.add(paperTemp);
			}*/
		
		for(int i = 0; i < pathAuthor.size()-1; i++) {
			CoAuthor found = null;
			for(CoAuthor c : coAuthorList)
				if(c.equals(new CoAuthor(pathAuthor.get(i), pathAuthor.get(i+1), null)))
					found = c;
			if(found != null)
				pathPaper.add(found.getPaper());
		}
		
		return pathPaper;
	}

}
