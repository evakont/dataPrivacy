package ptixiaki;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.TreeMap;
import java.util.Vector;


/**
 * @author aliki
 * @version 13/10/09
 *
 */
public class Full_Lattice {


    public static Connection conn = null;
    static int k=0;
    static int maxsupp=0;
    static int[] h;
    public static int numOfAttr=0;
    static int NUM=0;
    static int choise=1;

    public static void main() {
	int maxAttr=6;
	String TableName="C";
	int size=1;
	TreeMap<Integer, Node> komvoi;
	Suggestion sug;
	try{
            String userName = "root";
            String password = "wycoce./";
            //String url = "jdbc:mysql:http://127.0.0.1";
            String url = "jdbc:mysql://127.0.0.1/adult_no_dublic"; //SOSTOOOOOO;
            //String url = "jdbc:mysql://127.0.0.1/Ipums_Adult";  //Gia Ipums-tao peiramata
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection (url, userName, password);
        }
        catch (Exception E){
            System.out.println(E.getMessage());
        }
        finally{
            if (conn != null){
        	System.out.println ("Database connection established");
        	try{
        	    
        	}catch (Exception e){
        	    System.out.println(e.getMessage());
        	}
            }
        }
    }
    public static void incog(int numOfAttr,int size)throws SQLException{
	
	Statement statement =conn.createStatement();
	//Dhmiourgia ton pinakon C1 kai E1
        //System.out.println(numOfAttr);
	GenerateGraph CrTables=new GenerateGraph(conn,size,numOfAttr);
	CrTables.CreateNodeTable();
	CrTables.CreateEdgeTable();
	GenerateGraph.InitializeTables();
	for(int i=1;i<numOfAttr;i++){
	    //System.out.println ("Mphkq sthn k-anonymity gia i:"+i);
            
	    //Antigrafh toy c1 ston pinka S1 epeidh den exo pruning mporei kai na mhn xreiastei
	    String copyQuery="CREATE table S"+i+" SELECT * FROM C"+i;
	    statement.executeUpdate(copyQuery);
	    // O opoion einai piankas pou prokeiptei aop pruning pou sthn dikia mas periptosh den yparxei   
	    //Dhmiourgia toy grafou katastevazontaw ta Ci+1 kai Ei+1 (apo Ei+Si)
	    GenerateGraph CrTables1=new GenerateGraph(conn,i+1,numOfAttr);
	    CrTables1.CreateNodeTable();
	    CrTables1.CreateEdgeTable();
	    CrTables1.NodeGeneration();
	    CrTables1.EndgeGeneration();
	}
	statement.close();
    }
    public static void drop_tables(int maxAttr)throws SQLException{
	
	Statement statement3=conn.createStatement();
	
	for(int i=1;i<=maxAttr;i++){
	    String DeleteView="DROP TABLE IF EXISTS C"+i;
	    statement3.executeUpdate(DeleteView);
	}
	for(int i=1;i<=maxAttr;i++){
	    String DeleteView="DROP TABLE IF EXISTS E"+i;
	    statement3.executeUpdate(DeleteView);
	}
	for(int i=1;i<maxAttr;i++){
	    String DeleteView="DROP TABLE IF EXISTS S"+i;
	    statement3.executeUpdate(DeleteView);
	}
        String DeleteView="DROP TABLE IF EXISTS Hist";
        statement3.executeUpdate(DeleteView);
	statement3.close();
    }
    public static void user_initialization()throws IOException{
	
	//InputStreamReader reader = new InputStreamReader(System.in);
	//BufferedReader input = new BufferedReader(reader);
	String sa = gui.s;
        String ca = gui.c;

	//System.out.println("The attributes are: Age, Work_class, Occupation, Education, Race, Marital Status");
	//System.out.println("Give me the number of attributes you want to create the lattice:");
	//s=input.readLine();
	numOfAttr=Integer.parseInt(sa);
        NUM = numOfAttr;
	//System.out.println("Give me the 1 for anonymity or anything else for l-diversity:");
	//s=input.readLine();
	choise=Integer.parseInt(ca);
    }
    public static void importance_of_nodes(TreeMap<Integer, Node> komvoi)throws SQLException,IOException{
	
	FileWriter fstream2 = new FileWriter("Import_attr.txt"); //Gia na apothikevo to iportance tou kathe komvou	
	BufferedWriter out_imp_attr = new BufferedWriter(fstream2);
	Node test;
	Collection c = komvoi.values();
	Iterator itr = c.iterator();
	while(itr.hasNext()){
	    test=(Node) itr.next();
	    if(test.getid()!=1)
		get_importance(test.getid(),komvoi,out_imp_attr);
	    
	}
	out_imp_attr.close();
	
    }
 static void get_importance(int id,TreeMap<Integer, Node> komvoi,BufferedWriter out_imp_attr)throws SQLException,IOException{
	
	int supp_tupl,min_supp,temp_id,id_min,k_in=25;
	double importance=0;
	Vector<Integer> child;
	Node n_node;
	//System.out.println("Mphka sthn importance_of_nodes");
	try{
	    //id=lv.getid();
	    n_node=komvoi.get(id);
	    supp_tupl=suppressed_tuples(id,k_in,komvoi);
	    //System.out.println("Id:"+id);
	    child=getchild(id);
	    ListIterator iter1 = child.listIterator();
	    id_min=(Integer) iter1.next(); 
	    min_supp=suppressed_tuples(id_min,k_in,komvoi);
	    //System.out.println("MIN SUPP:"+min_supp);
	    while(iter1.hasNext()){
		temp_id=(Integer) iter1.next();
		if((suppressed_tuples(temp_id,k_in,komvoi))<min_supp){
		    min_supp=suppressed_tuples(temp_id,k_in,komvoi);
		}    
	    }
	    if(min_supp!=0)
		importance=1-((double )supp_tupl/min_supp);
	    else
		importance=-1;
	
	    //System.out.println(id+"\t"+n_node.getlevels()+"\t"+n_node.return_height()+"\t"+importance+"\n");
	    out_imp_attr.write(id+"\t"+n_node.getlevels()+"\t"+n_node.return_height()+"\t"+importance+"\n");
	}catch(Exception E){
		System.out.println(E.getMessage());		
	 }		
	
	
	
    }
   public static Vector<Integer> getchild(int id) throws SQLException,IOException{
	
	Statement statement =conn.createStatement();
	String SelectQuery="Select start From E"+numOfAttr+" e Where e.end="+id;
	Vector<Integer> childs=new Vector<Integer>();
	ResultSet rs= statement.executeQuery(SelectQuery);
	while(rs.next()){
	   childs.add(rs.getInt(1));
	    
	}
	statement.close(); ///neeeew
	rs.close(); //neww
	return childs;
	
    }
   public static int suppressed_tuples(int id,int k_in,TreeMap<Integer, Node> komvoi)
   {
	Node test;
	int supp_tuples=0,grp;
	Boolean Find=true;
	Vector<Integer> groups;
	Vector<Integer> plithos;
	test=komvoi.get(id);
	groups=test.getgroups();
	plithos=test.getplithos();
	ListIterator iter = groups.listIterator();
	ListIterator iter2=plithos.listIterator();
	while(Find) {
	    grp=(Integer) iter.next();
	    if(grp<k_in)
	    {
		   supp_tuples+=grp*((Integer)iter2.next()); //Otan exoume k anonymity		
	    }
	    else
		Find=false;	
	}
	return(supp_tuples);
   }
   public static void test(TreeMap<Integer, Node> komvoi) throws IOException{
       //System.out.println("Mphkaaaa sto test");
       
	   int height;
	   int []maxheight={9,11,15,18};
       Node tmp,temp;
       FileWriter fstream = new FileWriter("nodes_supp_per_level.txt"); //Apothikevo to Histogram	
   	   BufferedWriter out = new BufferedWriter(fstream);
   	
       Vector<Node>[] node= new Vector[maxheight[numOfAttr-3]+1];
       
       for(int i=0;i<=maxheight[numOfAttr-3];i++)
    	  node[i]=new Vector<Node>();
       
       Collection<Node> c = komvoi.values();
       Iterator<Node> itr = c.iterator();
	   while(itr.hasNext()){
	    tmp=(Node) itr.next();  
	    tmp.get_sup_tuples(25, 1);//To proto k kai to deytero 
	    height=tmp.return_height();
	    node[height].add(tmp);
	   }
	   //out.write("id\tlv\tsupp\n");
	   for(int i=0;i<=maxheight[numOfAttr-3];i++){
		   Collections.sort(node[i],new Comparer());
			//Collections.sort(import_nodes[i],new Comparer());
		//System.out.println("Height: "+i+" Taksinomimena");
		//out.write("Height: "+i+" Taksinomimena\n");
		ListIterator list=node[i].listIterator();
			
		while(list.hasNext()){
		    temp=(Node )list.next();
		    //System.out.println("id\t"+temp.getid()+"\tlv\t"+temp.getlevels()+"\tsupp\t"+temp.return_supp());
		    out.write(temp.getid()+"\t"+temp.getlevels()+"\t"+i+"\t"+temp.return_supp()+"\n");
		}
		//System.out.println("==================================");
	   }
	   out.close();
   }
}
