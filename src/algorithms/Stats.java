package algorithms;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

import supportGUI.Circle;

public class Stats {

	public static double areaCircle(Circle c) {
		return c.getRadius()*c.getRadius()*Math.PI;
	}
	public static double areaConvex(ArrayList<Point> enveloppe) {
		double sumA=0;
		double sumB=0;
		for(int i=0;i<enveloppe.size();i++) {
			double x1=enveloppe.get(i).getX();
			double y2=enveloppe.get((i+1)%enveloppe.size()).getY();
			double x2=enveloppe.get((i+1)%enveloppe.size()).getX();
			double y1=enveloppe.get(i).getY();
			sumA+=x1*y2;
			sumB+=y1*x2;
		}
		return Math.abs(0.5*(sumA-sumB));
	
	}
	
	public static double areaRectangle(ArrayList<Point2D> corners) {
		double deltaXAB = corners.get(0).getX() - corners.get(1).getX();
        double deltaYAB = corners.get(0).getY() - corners.get(1).getY();

        double deltaXBC = corners.get(1).getX() - corners.get(2).getX();
        double deltaYBC = corners.get(1).getY()  - corners.get(2).getY() ;

        double lengthAB = Math.sqrt((deltaXAB * deltaXAB) + (deltaYAB * deltaYAB));
        double lengthBC = Math.sqrt((deltaXBC * deltaXBC) + (deltaYBC * deltaYBC));

        return lengthAB * lengthBC;
	}
	
	
	 
	public static double qualityCircle(Circle cRitter,ArrayList<Point> convexHull) {
		return  (areaCircle(cRitter)/areaConvex(convexHull))-1;
	}
	
	public static double qualityRectangle(ArrayList<Point2D> corners,ArrayList<Point> convexHull) {
		return  (areaRectangle(corners)/areaConvex(convexHull))-1;
	}
	
	
	
	//listPointsFromFile:String --> ArrayList<Point>
	public static ArrayList<Point> listPointsFromFile(String filename){
		ArrayList<Point> points = new ArrayList<>();
        BufferedReader input;
		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		    String line;
			try {
				while((line = input.readLine()) != null) {
					String[] coordinates = line.split("\\s+");
					 points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));	
				}
				 input.close();
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return points;
		
	}
	
    //qualityCircleSamples: void-->Map<Integer,Double>
	// samples from  Steven Varoumas
	// Integer: numéro de l'instance de test, Double: qualité de cercle minimum l'instance de test
	public static Map<Integer,Double> qualityCircleSamples(){
		Algorithmes dt= new Algorithmes();
		Map<Integer,Double> res= new HashMap<>();
		for(int i=2;i<=1664;i++) {
			String filename="F:\\Programmation\\ALGAVRENDU\\Varoumas_benchmark\\samples\\test-"+i+".points";
			ArrayList<Point> points=listPointsFromFile(filename);
			Circle cMinRitter= dt.calculCercleMin(points);
			ArrayList<Point> envConvexHull=dt.enveloppeConvexe(points);
			double quality= qualityCircle(cMinRitter,envConvexHull);
			res.put(i, quality);
		}
		return res;
	}
	
	//qualityRectangleSamples: void-->Map<Integer,Double>
	// samples from  Steven Varoumas
	// Integer: numéro de l'instance de test, Double: qualité de rectangle minimum sur l'instance de test 
	public static Map<Integer,Double> qualityRectangleSamples(){
		Algorithmes dt= new Algorithmes();
		Map<Integer,Double> res= new HashMap<>();
		for(int i=2;i<=1664;i++) {
			String filename="F:\\Programmation\\ALGAVRENDU\\Varoumas_benchmark\\samples\\test-"+i+".points";
			ArrayList<Point> points=listPointsFromFile(filename);
			ArrayList<Point2D> MinRecToussaint= dt.rectangleMin(points);
			ArrayList<Point> envConvexHull=dt.enveloppeConvexe(points);
			double quality= qualityRectangle(MinRecToussaint,envConvexHull);
			res.put(i, quality);
		}
		return res;
	}
	
	//getAverageValue: ArrayList<Double> --> Double
	// valeur moyenne d'une liste de Double
	public static Double getAverageValue(ArrayList<Double> q) {
		OptionalDouble average = q
	            .stream()
	            .mapToDouble(a -> a)
	            .average();
		return average.getAsDouble();
	}
	//timeCircleSamples: void-->Map<Integer,Double>
	//map qui associe chaque fichier avec leur temps d'execution(en microsecondes) pour cercle minimum(Ritter)
	public static Map<Integer,Double> timeCircleSamples(){
		Algorithmes dt= new Algorithmes();
		Map<Integer,Double> res= new HashMap<>();
		for(int i=2;i<=1664;i++) {
			String filename="F:\\Programmation\\ALGAVRENDU\\Varoumas_benchmark\\samples\\test-"+i+".points";
			ArrayList<Point> points=listPointsFromFile(filename);
			long startTime = System.nanoTime();
			Circle cMinRitter= dt.calculCercleMin(points);
			long stopTime = System.nanoTime();
			res.put(i, (stopTime - startTime)/1000.0);
		}
		return res;
	}
	
	//timeRectangleSamples: void-->Map<Integer,Double>
	//map qui associe chaque fichier avec leur temps d'execution(en microsecondes) pour rectangle minimum(Toussaint)
		public static Map<Integer,Double> timeRectangleSamples(){
			Algorithmes dt= new Algorithmes();
			Map<Integer,Double> res= new HashMap<>();
			for(int i=2;i<=1664;i++) {
				String filename="F:\\Programmation\\ALGAVRENDU\\Varoumas_benchmark\\samples\\test-"+i+".points";
				ArrayList<Point> points=listPointsFromFile(filename);
				long startTime = System.nanoTime();
				ArrayList<Point2D> MinRecToussaint= dt.rectangleMin(points);
				long stopTime =System.nanoTime();
				res.put(i, (stopTime - startTime)/1000.0);
			}
			return res;
		}
	
	public static void main(String[] args) {
		 Map<Integer,Double> timeToussaint=timeRectangleSamples();
		 Map<Integer,Double> timeRitter=timeCircleSamples();
		 ArrayList<Double> averageTimeRitter = new ArrayList<Double>(timeRitter.values());
		 ArrayList<Double> averageTimeToussaint = new ArrayList<Double>(timeToussaint.values());
		 System.out.println("average execution time Ritter = "+ getAverageValue(averageTimeRitter)+" microseconds");
		 System.out.println("average execution time Toussaint = "+getAverageValue(averageTimeToussaint)+" microseconds");
		
		 Map<Integer,Double> mpR=qualityRectangleSamples();
		 Map<Integer,Double> mpC=qualityCircleSamples();
	     ArrayList<Double> qualityRectList = new ArrayList<Double>(mpR.values());
	     ArrayList<Double> qualityCircleList = new ArrayList<Double>(mpC.values());
	     double averageQualityCircle=getAverageValue(qualityCircleList);
	     double averageQualityRectangle=getAverageValue(qualityRectList);
	     System.out.println("average Quality Circle = "+averageQualityCircle);
	     System.out.println("average Quality Rectangle = "+averageQualityRectangle);

		 
		
		 
	}
	
}
