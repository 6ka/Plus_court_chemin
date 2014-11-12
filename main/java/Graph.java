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
			if (vertices.get(compteur).getName() == name) {
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
				if (edge.getTarget().getName() == to) {
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

	public int getDistanceByOneVertex(String from, String to) { //à modifier pour ne pas ajouter si c'est -1
		ArrayList<Integer> distances = new ArrayList<Integer>();
		int vertexFromIndex = findVertexIndex(from);
		Vertex fromVertex = vertices.get(vertexFromIndex);
		ArrayList<Vertex> possibleBy = new ArrayList<Vertex>();
		for (Edge edge : fromVertex.getEdges()) {
			possibleBy.add(edge.getTarget());
		}
		for (Vertex by : possibleBy) {
			distances.add(getDistanceByVertex(from, to, by.getName()));
		}
		for (int i = possibleBy.size() - 1; i >= 0; i--) {
			if (distances.get(i) == -1) {
				distances.remove(i);
				possibleBy.remove(i);
			}
		}
		while (distances.contains(-1)) {
			distances.remove(-1);
		}
		if(distances.isEmpty())
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
		for (int i = 0; i < optimalBy.size(); i++){
			totalDistances.add(optimalDistancesBy.get(i) + getDistance(optimalBy.get(i).getName(), to));
		}
		return Collections.min(totalDistances);
	}


	public int getDistanceByNVertices(String from, String to, int intermediary) {
		return 0;
	}
}