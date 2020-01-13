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

public class TestQuality {

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
	/*
	public static void readFile(String filename) {
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
		
		DefaultTeam dt= new DefaultTeam();
		Circle cMinRitter= dt.calculCercleMin(points);
		ArrayList<Point> envConvexHull=dt.enveloppeConvexe(points);
		double quality= qualityCircle(cMinRitter,envConvexHull);
		
		//Path path = Paths.get(filename);
		//System.out.println("file: "+path.getFileName() +" | Quality= "+quality);


    }
    */
	// samples from  Steven Varoumas
	// integer: numéro de l'instance de test, Double: qualité de l'instance de test
	public static Map<Integer,Double> qualityCircleSamples(){
		DefaultTeam dt= new DefaultTeam();
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
	
	public static Map<Integer,Double> qualityRectangleSamples(){
		DefaultTeam dt= new DefaultTeam();
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
	// valeur moyenne d'une liste de double
	public static double getAverageValue(ArrayList<Double> q) {
		OptionalDouble average = q
	            .stream()
	            .mapToDouble(a -> a)
	            .average();
		return average.getAsDouble();
	}
	public static void main(String[] args) {
		
		 Map<Integer,Double> mpR=qualityRectangleSamples();
		 Map<Integer,Double> mpC=qualityCircleSamples();
	     ArrayList<Double> qualityRectList = new ArrayList<Double>(mpR.values());
	     ArrayList<Double> qualityCircleList = new ArrayList<Double>(mpC.values());
	     
	     double averageQualityCircle=getAverageValue(qualityCircleList);
	     double averageQualityRectangle=getAverageValue(qualityRectList);
	     
	     System.out.println("average Quality Circle = "+averageQualityCircle);
	     System.out.println("average Quality Rectangle = "+averageQualityRectangle);

		 
		/*
		DefaultTeam dt= new DefaultTeam();
		ArrayList<Point> points=listPointsFromFile("F:\\Programmation\\ALGAVRENDU\\Varoumas_benchmark\\samples\\test-162.points");
		Point2D.Double[] minimum =RotatingCalipers.getMinimumBoundingRectangle(points);
		ArrayList<Point2D> res=dt.rectangleMin(points);
		System.out.println(areaRectangle(res));
		double quality= qualityRectangle(res,dt.enveloppeConvexe(points));
		System.out.println("quality: "+quality);
		*/
		 
	}
	
}
