package algorithms;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import supportGUI.Circle;

public class DefaultTeam {
    //calculCercleMin: ArrayList<Point> --> Circle
    //renvoie un cercle couvrant tout point de la liste, de rayon minimum.
    //Algorithme Ritter
     public Circle calculCercleMin(ArrayList<Point> points){
         if (points.size()<1) return null;
         @SuppressWarnings("unchecked")
		ArrayList<Point> rest = (ArrayList<Point>)points.clone();
         //1.Prendre un point dummy quelconque appartenant a l’ensemble de points de départ
         Point dummy=rest.get(0);
         Point p=dummy;
         //2.Parcourir l’ensemble de points pour trouver un point P de distance maximum au point dummy
         for (Point s: rest) if (dummy.distance(s)>dummy.distance(p)) p=s;
         //3.Re-parcourir l’ensemble de points pour trouver un point Q de distance maximum au point P
         Point q=p;
         for (Point s: rest) if (p.distance(s)>p.distance(q)) q=s;
         //4. Considerer le point C, le centre du segment PQ
         double cX=.5*(p.x+q.x);
         double cY=.5*(p.y+q.y);
         //5. Considerer le cercle CERCLE centré en C, de rayon |CP| : il passe par P et Q.
         double cRadius=.5*p.distance(q);
         //6. Retirer les points P et Q de l’ensemble des points.
         rest.remove(p);
         rest.remove(q);
         //7. Tant qu’il reste des points dans l’ensemble, considerer un point S quelconque
         while (!rest.isEmpty()){
             Point s=rest.remove(0);
             double distanceFromCToS=Math.sqrt((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY));
             //8. Si S est couvert par CERCLE, retirer S de l’ensemble des points; repéter l’étape 7
             if (distanceFromCToS<=cRadius) continue;
             /*9 Sinon, tracer au brouillon la droite passant par S et C. Celle-ci coupe le perimetre du cercle courant en deux
             points : soit T le point le plus eloigné de  S.*/
             double cPrimeRadius=.5*(cRadius+distanceFromCToS);
             double alpha=cPrimeRadius/(double)(distanceFromCToS);
             double beta=(distanceFromCToS-cPrimeRadius)/(double)(distanceFromCToS);
             //10. Determiner les coordonnées du point C' le centre du segment ST.
             double cPrimeX=alpha*cX+beta*s.x;
             double cPrimeY=alpha*cY+beta*s.y;
             //11.Remplacer CERCLE par le cercle centré en C', de rayon |CT| : il passe par S et T.
             cRadius=cPrimeRadius;
             cX=cPrimeX;
             cY=cPrimeY;
             //12. Répéter l’étape 7 jusqu’à ce qu’il ne reste plus de points dans la liste.
         }
         return new Circle(new Point((int)cX,(int)cY),(int)cRadius);
     }

    // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
    // renvoie l'enveloppe convexe de la liste en utilisant l'algorithme QuickHull
    public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
    	if (points.size()<4) return points;

        Point ouest = points.get(0);
        Point sud = points.get(0);
        Point est = points.get(0);
        Point nord = points.get(0);
        for (Point p: points){
            if (p.x<ouest.x) ouest=p;
            if (p.y>sud.y) sud=p;
            if (p.x>est.x) est=p;
            if (p.y<nord.y) nord=p;
        }
        ArrayList<Point> result = new ArrayList<Point>();
        result.add(ouest);
        result.add(sud);
        result.add(est);
        result.add(nord);

        @SuppressWarnings("unchecked")
		ArrayList<Point> rest = (ArrayList<Point>)points.clone();
        for (int i=0;i<rest.size();i++) {
            if (triangleContientPoint(ouest,sud,est,rest.get(i)) ||
                    triangleContientPoint(ouest,est,nord,rest.get(i))) {
                rest.remove(i);
                i--;
                    }
        }

        for (int i=0;i<result.size();i++) {
            Point a = result.get(i);
            Point b = result.get((i+1)%result.size());
            Point ref = result.get((i+2)%result.size());

            double signeRef = crossProduct(a,b,a,ref);
            double maxValue = 0;
            Point maxPoint = a;

            for (Point p: points) {
                double piki = crossProduct(a,b,a,p);
                if (signeRef*piki<0 && Math.abs(piki)>maxValue) {
                    maxValue = Math.abs(piki);
                    maxPoint = p;
                }
            }
            if (maxValue!=0){
                for (int j=0;j<rest.size();j++) {
                    if (triangleContientPoint(a,b,maxPoint,rest.get(j))){
                        rest.remove(j);
                        j--;
                    }
                }
                result.add(i+1,maxPoint);
                i--;
            }
        }
        return result;
    }
    
    private double crossProduct(Point p, Point q, Point s, Point t){
        return ((q.x-p.x)*(t.y-s.y)-(q.y-p.y)*(t.x-s.x));
    }
    
    private boolean triangleContientPoint(Point a, Point b, Point c, Point x) {
        double l1 = ((b.y-c.y)*(x.x-c.x)+(c.x-b.x)*(x.y-c.y))/(double)((b.y-c.y)*(a.x-c.x)+(c.x-b.x)*(a.y-c.y));
        double l2 = ((c.y-a.y)*(x.x-c.x)+(a.x-c.x)*(x.y-c.y))/(double)((b.y-c.y)*(a.x-c.x)+(c.x-b.x)*(a.y-c.y));
        double l3 = 1-l1-l2;
        return (0<l1 && l1<1 && 0<l2 && l2<1 && 0<l3 && l3<1);
    }
    
    //Algorithme Toussaint pour rectangle min
    public ArrayList<Point2D> rectangleMin(ArrayList<Point> points){
    	ArrayList<Point2D> res= new ArrayList<Point2D>();
    	java.awt.geom.Point2D.Double[] min=RotatingCalipers.getMinimumBoundingRectangle(points);
    	for(int i=0;i<min.length;i++) {
    		double x=min[i].getX();
    		double y=min[i].getY();
    		res.add(new Point2D.Double(x,y));
    	}
    	return res;
    }
    
}