package resultats;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import algorithms.Stats;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class TestTime extends ApplicationFrame {

  

/**
	 * 
	 */
	private static final long serialVersionUID = -7623812800959203271L;

public TestTime( String applicationTitle , String chartTitle ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Instance of Test File","Execution time (microseconds) ",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 1000 ) );
      setContentPane( chartPanel );
   }

   private static DefaultCategoryDataset createDataset( ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      Map<Integer,Double> resRitter= Stats.timeCircleSamples();
      Map<Integer,Double> resToussaint= Stats.timeRectangleSamples();
      int i=0;
      int j=0;
      for (Map.Entry<Integer,Double> entry : resRitter.entrySet()) {
    	  if(i%10==0)
    		  dataset.addValue( entry.getValue(), "Ritter(minimum Circle)" , entry.getKey() );
    	  i++;
      }
      
      for (Map.Entry<Integer,Double> entry : resToussaint.entrySet()) {
    	  if(j%10==0)
    		  dataset.addValue( entry.getValue(), "Toussaint(minimum rectangle)" , entry.getKey() );
    	  j++;
      } 
      return dataset;
   }
   
   public static void main( String[ ] args ) throws IOException {
      TestTime chart = new TestTime(
         "VaroumasSamples execution time" ,
         "Execution time of each sample: Ritter vs Toussaint (Varoumas dataset)");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
      
	   //générer le fichier
	   DefaultCategoryDataset line_chart_dataset = createDataset();
	   JFreeChart lineChartObject = ChartFactory.createLineChart(
		         "Execution time of each sample: Ritter vs Toussaint (Varoumas dataset)","Instance of Test File(1664)",
		         "Execution time (microseconds) ",
		         line_chart_dataset,PlotOrientation.VERTICAL,
		         true,true,false);
      int width = 1000;    /* Width of the image */
      int height = 1000;   /* Height of the image */ 
      File lineChart = new File( "results/TimeResults.jpeg" ); 
      ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
   }
}