package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import es.ucm.fdi.extra.graphlayout.Dot;
import es.ucm.fdi.extra.graphlayout.Edge;
import es.ucm.fdi.extra.graphlayout.Graph;
import es.ucm.fdi.extra.graphlayout.GraphComponent;
import es.ucm.fdi.extra.graphlayout.Node;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.simobject.Junction;
import es.ucm.fdi.model.simobject.Road;
import es.ucm.fdi.model.simobject.Vehicle;

@SuppressWarnings("serial")
public class RoadMapPaint extends JPanel{
	protected RoadMap roadMap;
	protected GraphComponent graph;
	public RoadMapPaint(Dimension size){
		super(new BorderLayout());
		roadMap=new RoadMap();
		graph=new GraphComponent(size);
		add(graph);
	}
	public void setRoadMap(RoadMap roadMap){
		this.roadMap=roadMap;
		generateGraph();
	}
	public void generateGraph(){

		Graph g = new Graph();
		Map<Junction,Node> junctionNode=new HashMap<>();
		for(Junction junction:roadMap.getJunctions()){
			Node n=new Node(junction.getId());
			g.addNode(n);
			junctionNode.put(junction,n);
		}
		for(Road road:roadMap.getRoads()){
			Edge edge=new Edge(road.getId(),junctionNode.get(road.getStartJunction()),junctionNode.get(road.getEndJunction()),road.getLength());
			for(Vehicle vehicle:road.getVehiclesOfRoad()){
				edge.addDot(new Dot(vehicle.getId(),vehicle.getLocation()));
			}
			g.addEdge(edge);
		}
		graph.setGraph(g);
	}
	 
}
