public class Pair<left, right> {

	private final left distance;
	private final right name;

	public Pair(left left, right right) {
		this.distance = left;
		this.name = right;
	}

	public left getDistance() { return distance; }
	public right getName() { return name; }

	@Override
	public int hashCode() { return distance.hashCode() ^ name.hashCode(); }

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Pair)) return false;
		Pair pairo = (Pair) o;
		return this.distance.equals(pairo.getDistance()) &&
				this.name.equals(pairo.getName());
	}

}