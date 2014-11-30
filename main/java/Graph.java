import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Graph {

	//Every impossible path will be considered as a path of length -1

	private List<Vertex> vertices = new ArrayList<Vertex>();

	public Graph(Vertex... vertices) {
		this.vertices.addAll(Arrays.asList(vertices));
	}

	public int findVertexIndex(String name) {
		Boolean found = false;
		int count = 0;
		while (!found && count < vertices.size()) {
			if (vertices.get(count).getName().equals(name)) {
				found = true;
			}
			count++;
		}
		if (found)
			return count - 1;
		else
			return -1;
	}

	public int getDistance(String from, String to) {
		int distance = -1;
		int vertexFromIndex = findVertexIndex(from);
		if (vertexFromIndex != vertices.size() && vertexFromIndex != -1) {
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

	public Pair<Integer, String> getDistanceByOneVertex(String from, String to) {
		ArrayList<Integer> distances = new ArrayList<Integer>();
		int vertexFromIndex = findVertexIndex(from);
		if (vertexFromIndex == -1) {
			return new Pair<Integer, String>(-1, "");
		}
		Vertex fromVertex = vertices.get(vertexFromIndex);
		ArrayList<Vertex> possibleBy = new ArrayList<Vertex>(); //list of possible vertices to pass by
		for (Edge edge : fromVertex.getEdges()) {
			int distanceFromBy = getDistanceByVertex(from, to, edge.getTarget().getName());
			if (distanceFromBy != -1) {
				possibleBy.add(edge.getTarget());
				distances.add(distanceFromBy);
			}
		}
		if (distances.isEmpty()) {
			return new Pair<Integer, String>(-1, "");
		} else {
			int distanceMin = Collections.min(distances);
			int distanceArgmin = distances.indexOf(distanceMin);
			return new Pair<Integer, String>(Collections.min(distances), possibleBy.get(distanceArgmin).getName());
		}
	}


	public int getDistanceByTwoVertex(String from, String to) { //intermediary function used to understand the induction
		ArrayList<Integer> optimalDistancesBy = new ArrayList<Integer>();
		ArrayList<Vertex> optimalBy = new ArrayList<Vertex>();
		for (Vertex by : vertices) {
			if (getDistance(by.getName(), to) != -1) {
				Pair<Integer, String> byOne = getDistanceByOneVertex(from, by.getName());
				Integer distanceFromBy = byOne.getDistance();
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

	//Gets the optimal path from a city to another with a fixed number of intermediary cities
	public Pair<Integer, ArrayList<String>> getDistanceByNVertices(String from, String to, int intermediary) { //based on the idea that an optimal path with n transitions is an optimal path to the second to last city with n-1 transitions
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> totalPaths;
		if (intermediary == 0) {
			return new Pair<Integer, ArrayList<String>>(getDistance(from, to), names);
		} else if (intermediary == 1) {
			Pair<Integer, String> distanceByOne = getDistanceByOneVertex(from, to);
			names.add(distanceByOne.getName());
			return new Pair<Integer, ArrayList<String>>(distanceByOne.getDistance(), names);
		} else {
			ArrayList<Integer> optimalDistancesBy = new ArrayList<Integer>();
			ArrayList<Vertex> optimalBy = new ArrayList<Vertex>(); //possible cities to pass by as an n-1th
			ArrayList<ArrayList<String>> optimalPathsBy = new ArrayList<ArrayList<String>>();
			for (Vertex by : vertices) {
				if (getDistance(by.getName(), to) != -1) { //if it is possible to go directly from the n-1th city to the last
					Pair<Integer, ArrayList<String>> distanceNamesByNMinus1 = getDistanceByNVertices(from, by.getName(), intermediary - 1);
					int distanceFromBy = distanceNamesByNMinus1.getDistance();
					if (distanceFromBy != -1) {
						optimalPathsBy.add(distanceNamesByNMinus1.getName());
						optimalDistancesBy.add(distanceFromBy);
						optimalBy.add(by);
					}
				}
			}
			ArrayList<Integer> totalDistances = new ArrayList<Integer>();
			ArrayList<String> lastCity = new ArrayList<String>();
			for (int i = 0; i < optimalBy.size(); i++) {
				totalDistances.add(optimalDistancesBy.get(i) + getDistance(optimalBy.get(i).getName(), to));
				lastCity.add(optimalBy.get(i).getName());
			}
			if (totalDistances.isEmpty())
				return new Pair<Integer, ArrayList<String>>(-1, new ArrayList<String>());
			else {
				int distanceMin = Collections.min(totalDistances);
				int distanceArgmin = totalDistances.indexOf(distanceMin);
				totalPaths = optimalPathsBy.get(distanceArgmin);
				totalPaths.add(lastCity.get(distanceArgmin));
				return new Pair<Integer, ArrayList<String>>(Collections.min(totalDistances), totalPaths);
			}
		}
	}


	//Gets the optimal path by testing all the possible numbers of intermediaries
	public Pair<Integer, ArrayList<String>> getOptimalDistance(String from, String to) {
		ArrayList<Integer> optimalDistancesByN = new ArrayList<Integer>();
		ArrayList<ArrayList<String>> optimalPaths = new ArrayList<ArrayList<String>>();
		int distance;
		for (int n = 1; n <= this.vertices.size(); n++) {
			Pair<Integer, ArrayList<String>> optimal = getDistanceByNVertices(from, to, n);
			distance = optimal.getDistance();
			if (distance != -1) {
				optimalDistancesByN.add(distance);
				optimalPaths.add((optimal.getName()));
			}
		}
		if (optimalDistancesByN.isEmpty())
			return new Pair<Integer, ArrayList<String>>(-1, new ArrayList<String>());
		else {
			int distanceMin = Collections.min(optimalDistancesByN);
			int distanceArgmin = optimalDistancesByN.indexOf(distanceMin);
			return new Pair<Integer, ArrayList<String>>(distanceMin, optimalPaths.get(distanceArgmin));
		}
	}
}