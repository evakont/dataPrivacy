package ptixiaki;

import java.awt.Color;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;


/**
 *
 * @author eva
 */
public class histog extends javax.swing.JFrame{
/**
 *draws the histogramm
 * 
 */
    public histog(final String title){
        super(title);
        final CategoryDataset dataset = getDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        histog.this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 575));
        setContentPane(chartPanel);
    }

    // the data
    public static CategoryDataset getDataset() {
        final Number data[][] = new Number [10][1];
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Number DATA[] = new Number [10];
//        Comparable numbs[] = new Comparable [10];  //column keys
//        Comparable nubs[] = new Comparable [50];  //row keys
        int cnt = 0, cn = 0;
        int pinakas [] = new int [11];
        pinakas[0] = 0;
        for(int i = 0; i < 10; i++){                //mono ta 10 prota
            if(parser.values[i][1] > 0.0){
                data[i][0] = parser.values[i][1];
                DATA[i] = data[i][0];
                cnt += (i+1)*data[i][0].intValue();
                pinakas[i + 1] = cnt;
            }
        }
        for(int i = 0; i < 10; i++){
            dataset.addValue(DATA[i], Integer.toString(pinakas[i]), Integer.toString(i+1));
        }
        //return DatasetUtilities.createCategoryDataset("","", D);
        return dataset;
    }
    //create the histogram
    public static JFreeChart createChart(final CategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createBarChart(
            "Histogram for node " + MouseMenu.NodeName,    // chart title
            "# of tuples",                              // domain axis label
            "# of groups",                          // range axis label
            dataset,                                // data
            PlotOrientation.VERTICAL,               // orientation
            true,                                   // ta koutakia sto katw meros gia kathe mpara
            true,                                   // tooltips 
            false                                   // urls
        );

        chart.setBackgroundPaint(Color.PINK);

        // get a reference to the plot for further customisation
        final CategoryPlot plot = chart.getCategoryPlot();
        //xroma mparas
        plot.getRenderer().setSeriesPaint(0, new Color(0, 0, 255));
        // change the auto tick unit selection to integer units
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        //afinw xwro 10 panw apo to megisto
        int max = parser.values[1][1];
        for(int i = 0; i < parser.values.length; i++){
            if(parser.values[i][1] > max){
                max = parser.values[i][1];
            }
        }
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer(); renderer.setItemMargin(-6);
        rangeAxis.setRange(0.0, max+10);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }

    public static void callMe(){
        final histog demo = new histog("histogram");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        demo.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
}
