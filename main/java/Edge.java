public class Edge {
	private Vertex target;

	private int distance;

	public Edge(Vertex target, int distance) {
		this.target = target;
		this.distance = distance;
	}

	public Vertex getTarget() {
		return target;
	}

	public int getDistance() {
		return distance;
	}
}