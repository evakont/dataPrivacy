package ptixiaki;

import com.mysql.jdbc.Connection;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.sql.SQLException;


/**
 *
 * @author eva
 */
public class MouseMenu<V, E> extends AbstractPopupGraphMousePlugin{


    static PickedState ps;
    public static String NodeName;


    public MouseMenu(){
        this(MouseEvent.BUTTON3_DOWN_MASK);
    }

    public MouseMenu(int modifiers){
        super(modifiers);
    }

    public void handlePopup(MouseEvent e){
       
        final VisualizationViewer<V,E> vv = (VisualizationViewer<V,E>)e.getSource();
        Point2D p = e.getPoint();
        ps = vv.getPickedVertexState();

        GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
     
        if(pickSupport != null){
            final V v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if(v != null) {
                NodeName = v.toString();
                 try{
                    caller();
                }catch(Exception E){
                System.out.println("mouse menu "+E.getMessage());
            }
            }else{
                final E edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
                if(edge != null) {
                    System.out.println("Edge " + edge + " was clicked");
                }
            }
        }
    }
    public static void caller()throws SQLException{
        try{
            if(Full_Lattice.conn != null){
                parser pars = new parser((Connection) Full_Lattice.conn);
                pars.GetData(NodeName);
                histog.callMe();
            }
            }catch(Exception E){
                System.out.println("callerrr "+E.getMessage());
            }
    }
}


