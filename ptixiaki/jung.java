package ptixiaki;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.picking.PickedInfo;
import edu.uci.ics.jung.visualization.picking.PickedState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
//import javax.xml.transform.Transformer;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;
/**
 *
 * @author eva
 */
public class jung {
    static Graph g;
    static int nodeCount, edgeCount;
    static int Height = 0, maxH = 0;
    static Factory<Integer> vertexFactory;
    static Factory<Integer> edgeFactory;
    static PickedState ps;
    static Shape shape = null;
    static int y = 0, x = 0;
    static VisualizationViewer<String,String> vs;
    static Color COLOR;
    public static int NODES;
    public static String Xcor[][] = new String[10000][2];
    static String nodeHeight[][] = new String[10000][2];
    public static String R3= new String();
    public static String R2= new String();
    static int WIDTH = 900;
    static protected Colors<Integer> Colors;


    /**
     * Zwgrafizei to lattice
     * Topothetei ton kathe kombo
     * Bafei tous kombous me diaforetiko xroma
     * Oloi oi komboi bafontai roz
     * H lisi bafetai portokali
     * Oi episkeumenoi komboi bafontai prasinoi
     */

    public static void draw() {

        jung sgv = new jung();
        int end = Integer.parseInt(gui.s);
        String Str[] = new String[end];
        String Title = new String();
        if(gui.start == 1){
            Title = "Full Lattice";
        }
        else{
            Title = "k: " + gui.k + " maxsupp: " + gui.maxsupp;
        }
        JFrame frame = new JFrame(Title);

        Transformer<String,String> es_none = new ConstantTransformer(null);

        if(end > 3){
            WIDTH = 1400;
        }

        g = new DirectedSparseMultigraph<String,String>();
        nodeCount = 0; edgeCount = 0;
        vertexFactory = new Factory<Integer>(){
            public Integer create(){
                return nodeCount++;
            }
        };
        edgeFactory = new Factory<Integer>(){
            public Integer create(){
                //return "EDGE-"+edgeCount++;
                return edgeCount++;
            }
        };

        try{
            //nodes
            final int nodes = GenerateGraph.nodes();
            NODES = nodes;
            String NodeNames[] = new String[nodes];
            GenerateGraph.nodeNames();
            System.arraycopy(GenerateGraph.N, 0, NodeNames, 0, nodes);

            for(int j = 0; j < nodes; j++ ){
                g.addVertex(NodeNames[j]);
                nodeHeight[j][0] = NodeNames[j];
                nodeHeight[j][1] = Integer.toString(getHeight(NodeNames[j]));
                if(Integer.parseInt(nodeHeight[j][1]) > maxH)
                    maxH = Integer.parseInt(nodeHeight[j][1]);
            }
            //edges
            for(int j = 0; j < nodes; j++ ){
                GenerateGraph.getTheEdges(NodeNames[j]);
                for(int k = 0; k < end; k++){
                    Str[k] = get.Nodes(k);
                    if(Integer.parseInt(Str[k]) < 1 || Str.length < end){ // den thimamai giati to eixa kanei auto
                    }
                    else{
                        g.addEdge(edgeFactory.create(),NodeNames[j],Str[k]);
                    }
                }
            }
        }catch(Exception E){
            System.out.println(E.getMessage()+" draw" );
        }
        getX();
        //topothetisi kombon
        Transformer<String, Point2D> OptimusPrime = new Transformer<String, Point2D>(){
            @Override
            public Point2D transform(String vertex) {
                y = getHeight(vertex);

                if(y == maxH){
                    y = 0 + 20 ;
                }
                else if(y == 0){
                    y = (maxH*70) + 20;
                }
                else{
                    y = ((maxH - y)*70)  + 20;
                }
                for(int i = 0; i < NODES; i++){
                    if(Xcor[i][0].equals(vertex)){
                        x = Integer.parseInt(Xcor[i][1]);
                    }
                }
                return new Point2D.Double((double) x, (double) y);
            }
        };

        final String Vnodes[] = GenerateGraph.VisitedNodes();

        final StaticLayout<String, String> layout = new StaticLayout(g, OptimusPrime);

        //to xroma ton kombon
        Transformer<String,Paint> Megatron = new Transformer<String,Paint>(){
            public Paint transform(String i) {
                int col = 0;
                try{
                    if(get.sid(3) == 0){
                        String eva = GenerateGraph.Solution(0);// i lisi bafete portokali
                        if(i.equals(eva)){
                            col = 1;
                        }
                        else{//koitazei an o kombos i einai episkeumenos
                            for(int j = 0; j < get.vId(); j++){
                                if(i.equals(Vnodes[j])){
                                    col = 2;
                                    break;
                                }
                                else{
                                    col = 0;
                                }
                            }
                        }
                    }
                    else{
                        String eva = GenerateGraph.Solution(0);// i lisi bafete portokali
                        String eva1 = GenerateGraph.Solution(1);
                        String eva2 = GenerateGraph.Solution(2);
                        if(i.equals(eva) || i.equals(eva1) ||i.equals(eva2) ){
                            col = 1;
                        }
                        else{//koitazei an o kombos i einai episkeumenos
                            for(int j = 0; j < get.vId(); j++){
                                if(i.equals(Vnodes[j])){
                                    col = 2;
                                    break;
                                }
                                else{
                                    col = 0;
                                }
                            }
                        }
                    }


                }catch(Exception E){
                    System.out.println(E.getMessage() + " Megatron");
                }
                if(gui.start == 1){
                   COLOR = Color.MAGENTA;
                    return jung.COLOR;
                }
                else{
                    if(col == 1){
                       return Color.ORANGE;
                    }
                    else if(col == 2){
                        return Color.GREEN;
                    }
                    else{
                        COLOR = Color.MAGENTA;
                        return jung.COLOR;
                    }
                }
            }
        };

        // to perigramma otan clikarontai
        Transformer<String,Paint> Jazz = new Transformer<String,Paint>(){
            public Paint transform(String v){
                if(ps.isPicked(v)){
                    return Color.BLUE;
                }
                else{
                    return jung.COLOR;
                }
            }

        };

        //to megethos ton kombon
        Transformer<String,Shape> Gears = new Transformer<String,Shape>(){
            public Shape transform(String vertex){
                Shape lalaki = null;
                Point2D Center = layout.transform(vertex);
                lalaki = new Ellipse2D.Double(-30/2, -30/2, 30, 30);
                return lalaki;
            }
        };


        layout.setSize(new Dimension (WIDTH, 800));
        vs = new VisualizationViewer<String, String>(layout);
        vs.setPreferredSize(new Dimension (WIDTH, 800));
        //EditingModalGraphMouse gm = new EditingModalGraphMouse (vs.getRenderContext(),sgv.vertexFactory, sgv.edgeFactory);
        EditingModalGraphMouse gm = new EditingModalGraphMouse (vs.getRenderContext(),vertexFactory, edgeFactory);
        ps = vs.getPickedVertexState();//mpaaa
        MouseMenu myPlugin = new MouseMenu();
        gm.remove(gm.getPopupEditingPlugin());
        gm.add(myPlugin);
        vs.setGraphMouse(gm);
        vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vs.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
        //vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller()); // labels ton akmwn
        vs.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        vs.getRenderContext().setEdgeLabelTransformer(es_none); //gia na min exoun labels oi akmes
        //vs.getRenderContext().setVertexLabelTransformer(autobot);
        //vs.getRenderer().setVertexRenderer(new Colors()); //thelei tin class pou exw pio katw alla tin xrisimopoiw
        vs.getRenderContext().setVertexFillPaintTransformer(Megatron); //xroma gemismatos
        vs.getRenderContext().setVertexDrawPaintTransformer(Megatron); //xroma grammis
        vs.getRenderContext().setVertexShapeTransformer(Gears);     //megethos kiklou
        vs.getRenderContext().setVertexDrawPaintTransformer(Jazz);  //otan clickarete enas kombos exei perigrama

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getContentPane().add(vs);
        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = gm.getModeMenu();
        modeMenu.setText("Mouse Mode");
        modeMenu.setIcon(null);
        modeMenu.setPreferredSize(new Dimension(110,20));
        menuBar.add(modeMenu);
        frame.setJMenuBar(menuBar);
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Pernei eisodo to onoma enos kombou kai epistrefei to
     * ipsos tou
     * @param Nodename
     * @return height
     */


    public static int getHeight(String Nodename){
        char chars[] = new char[Integer.parseInt(gui.s)];
        int lala = 0;
        Height = 0;
        int height = 0;

        chars = Nodename.toCharArray();
        for(int i = 0; i < Integer.parseInt(gui.s); i++){
            String str1 = String.valueOf(chars[i]);
            lala  = Integer.parseInt(str1);
            Height += lala;
        }
        height = Height;
        return height;
    }

    /**
     * Ypologizei tin x sintetagmeni tou kathe kombou
     * kai tis apothikeuei se pinaka
     */

    public static void getX(){
        int L = 0, max = 0, min = 0, cnt = 0, yeahh = 0, pos = 0, init = 30;
        int numOfnodes = 0, SumX = 0, yiha = 0, width = WIDTH-100, dist = 0;

        try{
            int nodes = NODES;
            max = maxH; min = 0;
            String hell[] = new String[nodes];
            int end = Integer.parseInt(gui.s);
            String Str[] = new String[end];

            //gia tin mmesea grammi
            L = max/2;
            for(int j = 0; j < nodes; j++ ){
                if(Integer.parseInt(nodeHeight[j][1]) == L){
                    cnt++;
                }
            }
            int levels[] = new int [max+1];
            int middle[] = new int [cnt];
            for(int j = 0; j < nodes; j++ ){
                levels[Integer.parseInt(nodeHeight[j][1])]++;
            }

            int sorted[] = new int[cnt];
            int i = 0;
            for(int j = 0; j < nodes; j++ ){
                if(Integer.parseInt(nodeHeight[j][1]) == L){
                    sorted[i] = Integer.parseInt(nodeHeight[j][0]);
                    i++;
                }
            }

            //taksinomisi meseas
            //Arrays.sort(sorted);
            int md = 0;
            for(yeahh = 0; yeahh < cnt; yeahh++ ){
                hell[yeahh] = Integer.toString(sorted[yeahh]);
                if(hell[yeahh].length() < end ){
                    if(end - hell[yeahh].length() == 1){
                    hell[yeahh] = "0"+hell[yeahh];
                    }
                    else if(end - hell[yeahh].length() == 2){
                        hell[yeahh] = "00"+hell[yeahh];
                    }
                    else if(end - hell[yeahh].length() == 3){
                        hell[yeahh] = "000"+hell[yeahh];
                    }
                    else if(end - hell[yeahh].length() == 4){
                        hell[yeahh] = "0000"+hell[yeahh];
                    }
                    else if(end - hell[yeahh].length() == 5){
                        hell[yeahh] = "00000"+hell[yeahh];
                    }
                }
                Xcor[pos][0] = hell[yeahh];
                Xcor[pos][1] = Integer.toString((yeahh + 1) * (width/cnt) ) ;
                //Xcor[pos][1] = Integer.toString(yeahh  * (width/cnt) + init) ;
                middle[md] = Integer.parseInt(Xcor[pos][1]);
                pos++;
                md++;
                //System.out.println("x " + (yeahh + 1) * (width/cnt));
            }
            dist = width/(cnt);
            //apo tin mesea kai katw
            for(int l = L-1; l >= min; l--){
                if(levels[l] == cnt){
                    int neww[] = new int[yeahh];
                    for(i = 0; i < nodes; i++){
                        if(Integer.parseInt(nodeHeight[i][1]) == l){
                            neww[yiha] =  Integer.parseInt(nodeHeight[i][0]);
                            yiha++;
                       }
                    }
                    //Arrays.sort(neww);
                    String lala[] = new String[yiha];
                    for(i = 0; i < yiha; i++){
                        lala[i] = Integer.toString(neww[i]);
                        if(lala[i].length() < end){
                            if(end - lala[i].length() == 1){
                                lala[i] = "0"+neww[i];
                            }
                            else{
                                lala[i] = "00"+neww[i];
                            }
                        }
                    }
                    for(i = 0; i < md; i++){
                        Xcor[pos][0] = lala[i];
                        Xcor[pos][1] = Integer.toString(middle[i]);
                        pos++;
                    }
                }
                else{
                    int oo = 0;
                    for(int j = 0; j < nodes; j++ ){
                        if(Integer.parseInt(nodeHeight[j][1]) == l){
                            Xcor[pos][0] = nodeHeight[j][0];
                            Xcor[pos][1] = Integer.toString((((cnt - levels[l]) *(dist/2))+oo*dist)+ dist);
                            pos++; oo++;
                        }
                   }
                }
            }

            //apo tim mesea k panw

            for(int l = L+1; l <= max; l++){
                if(levels[l] == cnt){
                    int neww[] = new int[yeahh];
                    for(i = 0; i < nodes; i++){
                        if(Integer.parseInt(nodeHeight[i][1]) == l){
                            neww[yiha] =  Integer.parseInt(nodeHeight[i][0]);
                            yiha++;
                       }
                    }
                    //Arrays.sort(neww);
                    String lala[] = new String[yiha];
                    for(i = 0; i < yiha; i++){
                        lala[i] = Integer.toString(neww[i]);
                        if(lala[i].length() < end){
                            if(end - lala[i].length() == 1){

                            lala[i] = "0"+neww[i];
                            }
                            else{

                                lala[i] = "00"+neww[i];
                            }

                            }
                    }
                    for(i = 0; i < md; i++){
                        Xcor[pos][0] = lala[i];
                        Xcor[pos][1] = Integer.toString(middle[i]);
                        pos++;
                        //System.out.println("XX "+ middle[i]);
                    }
                }
                else{
                    int oo = 0;
                      for(int j = 0; j < nodes; j++ ){
                        if(Integer.parseInt(nodeHeight[j][1]) == l){
                            Xcor[pos][0] = nodeHeight[j][0];
                            Xcor[pos][1] = Integer.toString((((cnt - levels[l]) *(dist/2))+oo*dist)+ dist);
                            pos++; oo++;
                        }
                      }
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+" getX");
        }
    }

    static private final class Colors<V> implements Transformer<V,Paint>{
        protected PickedInfo<V> pi;
        protected final static float dark_value = 0.8f;
        protected final static float light_value = 0.2f;
        protected boolean seed_coloring;

        public Colors(PickedInfo<V> pi){
             this.pi = pi;
             seed_coloring = false;
             throw new UnsupportedOperationException("ahhhhrrrrggggg");
        }

        public void setSeedColoring(boolean b){
             this.seed_coloring = b;
         }

        public Paint transform(V v){
             return Color.BLACK;
         }
    }
}