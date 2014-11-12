import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Graph {
	private List<Vertex> vertices = new ArrayList<Vertex>();

	public Graph(Vertex... vertices) {
		this.vertices.addAll(Arrays.asList(vertices));
	}

	public int findVertexIndex(String name) {
		Boolean trouve = false;
		int compteur = 0;
		while (!trouve && compteur < vertices.size()) {
			if (vertices.get(compteur).getName().equals(name)) {
				trouve = true;
			}
			compteur++;
		}
		return compteur - 1;
	}

	public int getDistance(String from, String to) {
		int distance = -1;
		int vertexFromIndex = findVertexIndex(from);
		if (vertexFromIndex != vertices.size()) {
			Vertex fromVertex = vertices.get(vertexFromIndex);
			for (Edge edge : fromVertex.getEdges()) {
				if (edge.getTarget().getName().equals(to)) {
					distance = edge.getDistance();
				}
			}
		}
		return distance;
	}

	public int getDistanceByVertex(String from, String to, String by) {
		int distanceFromBy = this.getDistance(from, by);
		int distanceByTo = this.getDistance(by, to);
		int totalDistance = -1;
		if (distanceFromBy != -1 && distanceByTo != -1)
			totalDistance = distanceByTo + distanceFromBy;
		return totalDistance;
	}

	public int getDistanceByOneVertex(String from, String to) {
		ArrayList<Integer> distances = new ArrayList<Integer>();
		int vertexFromIndex = findVertexIndex(from);
		Vertex fromVertex = vertices.get(vertexFromIndex);
		ArrayList<Vertex> possibleBy = new ArrayList<Vertex>();
		for (Edge edge : fromVertex.getEdges()) {
			if (getDistanceByVertex(from, to, edge.getTarget().getName()) != -1) {
				possibleBy.add(edge.getTarget());
				distances.add(getDistanceByVertex(from, to, edge.getTarget().getName()));
			}
		}
		if (distances.isEmpty())
			return -1;
		else
			return Collections.min(distances);
	}

	public int getDistanceByTwoVertex(String from, String to) {
		ArrayList<Integer> optimalDistancesBy = new ArrayList<Integer>();
		ArrayList<Vertex> optimalBy = new ArrayList<Vertex>();
		for (Vertex by : vertices) {
			if (getDistance(by.getName(), to) != -1) {
				int distanceFromBy = getDistanceByOneVertex(from, by.getName());
				if (distanceFromBy != -1) {
					optimalDistancesBy.add(distanceFromBy);
					optimalBy.add(by);
				}
			}
		}
		ArrayList<Integer> totalDistances = new ArrayList<Integer>();
		for (int i = 0; i < optimalBy.size(); i++) {
			totalDistances.add(optimalDistancesBy.get(i) + getDistance(optimalBy.get(i).getName(), to));
		}
		if (totalDistances.isEmpty())
			return -1;
		else
			return Collections.min(totalDistances);
	}


	public int getDistanceByNVertices(String from, String to, int intermediary) {
		if (intermediary == 0){
			return getDistance(from,to);
		}
		else if (intermediary == 1){
			return getDistanceByOneVertex(from,to);
		}
		else {
			ArrayList<Integer> optimalDistancesBy = new ArrayList<Integer>();
			ArrayList<Vertex> optimalBy = new ArrayList<Vertex>();
			for (Vertex by : vertices) {
				if (getDistance(by.getName(), to) != -1) {
					int distanceFromBy = getDistanceByNVertices(from, by.getName(), intermediary - 1);
					if (distanceFromBy != -1) {
						optimalDistancesBy.add(distanceFromBy);
						optimalBy.add(by);
					}
				}
			}
			ArrayList<Integer> totalDistances = new ArrayList<Integer>();
			for (int i = 0; i < optimalBy.size(); i++) {
				totalDistances.add(optimalDistancesBy.get(i) + getDistance(optimalBy.get(i).getName(), to));
			}
			if (totalDistances.isEmpty())
				return -1;
			else
				return Collections.min(totalDistances);
		}
	}


	public int getOptimalDistance(String from, String to) {
		ArrayList<Integer> optimalDistancesByN = new ArrayList<Integer>();
		int distance = -1;
		for (int n = 1; n <= this.vertices.size(); n++){
			distance = getDistanceByNVertices(from, to, n);
			if (distance != -1){
				optimalDistancesByN.add(distance);
			}
		}
		if (optimalDistancesByN.isEmpty())
			return -1;
		else
			return Collections.min(optimalDistancesByN);
	}
}