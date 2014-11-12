import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class GraphTest {
	private Vertex lille = new Vertex("Lille");
	private Vertex paris = new Vertex("Paris");
	private Vertex reims = new Vertex("Reims");
	private Vertex nancy = new Vertex("Nancy");
	private Vertex lyon = new Vertex("Lyon");
	private Vertex marseille = new Vertex("Marseille");
	private Vertex lemans = new Vertex("Le Mans");
	private Vertex nantes = new Vertex("Nantes");
	private Vertex bordeaux = new Vertex("Bordeaux");
	private Vertex toulouse = new Vertex("Toulouse");
	private Vertex clermont = new Vertex("Clermont Ferrant");
	private Vertex montpellier = new Vertex("Montpellier");

	@Before
	public void setup() {
		lille.connectTo(reims, 206);
		lille.connectTo(paris, 222);
		lille.connectTo(nancy, 418);

		reims.connectTo(paris, 144);
		reims.connectTo(nancy, 245);
		reims.connectTo(lyon, 489);

		paris.connectTo(lyon, 465);
		paris.connectTo(lemans, 208);
		paris.connectTo(clermont, 423);

		lyon.connectTo(clermont, 166);
		lyon.connectTo(marseille, 313);
		lyon.connectTo(montpellier, 304);

		lemans.connectTo(nantes, 189);
		lemans.connectTo(bordeaux, 443);

		nantes.connectTo(bordeaux, 347);

		bordeaux.connectTo(toulouse, 243);

		toulouse.connectTo(montpellier, 245);

		montpellier.connectTo(marseille, 169);
		montpellier.connectTo(toulouse, 245);

		marseille.connectTo(montpellier, 169);

		clermont.connectTo(lyon, 166);
		clermont.connectTo(montpellier, 333);
		clermont.connectTo(marseille, 474);
	}

	@Test
	public void findVertex(){
		Graph graph = new Graph(paris,lyon);

		Assert.assertEquals(graph.findVertexIndex("Paris"),0);
		Assert.assertEquals(graph.findVertexIndex("Lyon"),1);
	}

	@Test
	public void getDistanceForTwoAdjacentVertices() {
		Graph graph = new Graph(paris, lyon);

		Assert.assertEquals(graph.getDistance("Paris", "Lyon"), 465);
	}

	@Test
	public void getDistanceForTwoVerticesByVertex() {
		Graph graph = new Graph(paris, lyon, lille);

		Assert.assertEquals(graph.getDistanceByVertex("Lille", "Lyon", "Paris"), 687);
	}

	@Test
	public void getDistanceForTwoVerticesByOneVertex(){
		Graph graph = new Graph(lille,lyon,paris,reims,nancy,nantes,lemans,bordeaux,clermont,marseille,montpellier,toulouse);

		Assert.assertEquals(graph.getDistanceByOneVertex("Lille","Lyon"),687);
	}

/*	@Test(expected = NoSuchElementException.class)
	public void getDistanceForTwoVerticesByOneVertexImpossible(){
		Graph graph = new Graph(lille,lyon,paris,reims,nancy,nantes,lemans,bordeaux,clermont,marseille,montpellier,toulouse);

		graph.getDistanceByOneVertex("Nancy", "Bordeaux");
	}*/

	@Test
	public void getDistanceForTwoVerticesByTwoVertex(){
		Graph graph = new Graph(lille,lyon,paris,reims,nancy,nantes,lemans,bordeaux,clermont,marseille,montpellier,toulouse);

		Assert.assertEquals(graph.getDistanceByTwoVertex("Lille","Marseille"),1000);
	}

	@Test
	public void getDistanceForTwoVerticesByNVertices(){
		Graph graph = new Graph(lille,lyon,paris,reims,nancy,nantes,lemans,bordeaux,clermont,marseille,montpellier,toulouse);

		Assert.assertEquals(graph.getDistanceByNVertices("Lille","Lyon",1),687);
		Assert.assertEquals(graph.getDistanceByNVertices("Lille","Marseille",2),1000);
	}

}