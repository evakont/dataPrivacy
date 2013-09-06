package ptixiaki;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.Iterator;
import javax.swing.JOptionPane;
/**
 *@author aliki
 *@version Nov 18, 2009
 */
public class Suggestion {
    
    static private int numOfAtt;
    static private Integer[] h;
    static private int maxsupp;
    static private int k;
    static private Connection conn;
    static private TreeMap<Integer, Node> Treem;
    public static int min_id;			//To elaxisto id tou komvou kathos katevainoume
    public static int MIN_ID;
    public static int MIN_ID_R3 = 0;
    public static int MIN_ID_R2 = 0;
    public static int k_R3 = 0;
    public static String Level1 = new String();
    public static String Level3 = new String();
    public static String Level2 = new String();
    public static boolean relax = false;
    static private int min_in_subtree;		//Oi elaxistes suppresed eggrafes kathos katevainoume
    static private int min_height;		//kai to elaxiso ypsos kathos katevainoume
    static Vector<desc_anc_nodes> List;
    static private int CurrentMinHeight;
    static private int CurrentMinId;
    static private int num_of_visited_nodes1;	//Krataei tous visited komvous otan eimaste ok kai katevainoume
    static private int num_of_visited_nodes_sugg_1_3;	//Krataei tous visited komvous kai suggestion 1 kai 3
    static private int num_of_visited_nodes_sugg_2;	//Krataei  tous visited komvous gia suggestion 2
    static private int flag; //Giana ksexorizoume se poia periptosh eimaste suggestion h katevasma
    static public BufferedWriter out1;
    static private int priv_constrain;
    public static int choice = 0;
    public static int ids[] = new int[10000];
    public static int idCnt = 0;
    public static String Level= new String();
    public static String suppTup = new String();
    public static String suppTup1 = new String();
    public static String suppTup2 = new String();
    public static String suppTup3 = new String();
    public Suggestion(Connection con,int num,TreeMap<Integer, Node> komvoi,int priv_const){
	
	numOfAtt=num;
	conn=con;
	Treem=komvoi;
	priv_constrain=priv_const;
    }
    static public void check_the_parametres()throws SQLException,IOException{
	
	
//	int i;
//
//	String tablename="C";
//	String tablename_edge="E";
//	//System.out.println("Mphka sthn check the parametres");
//	String[] Dim;
//	String s = new String();
//
//	Statement statement =conn.createStatement();
//	Dim=new String[numOfAtt];
//	h=new Integer[numOfAtt];
//	String SelectQuery="Select ";
//	Dim=new String[numOfAtt];
//	for(int j=0;j<numOfAtt;j++){
//    	    SelectQuery+="dim_"+j;
//    	    if(j!=numOfAtt-1)
//    		SelectQuery+=",";
//
//	}
//
//	SelectQuery+=" From "+tablename+numOfAtt;
//	try{
//	    ResultSet rs= statement.executeQuery(SelectQuery);
//	    System.out.println(SelectQuery);
//	    if(rs.first()){
//		    for(i=1;i<=numOfAtt;i++)
//			Dim[i-1]=rs.getString(i);
//
//		}
//	    rs.close();    //neeew
//	}catch(Exception E)
//        {
//            System.out.println(E.getMessage());
//	}
//
//	statement.close();			//evala parapano
	//InputStreamReader reader = new InputStreamReader(System.in);
	//BufferedReader input = new BufferedReader(reader);
	//DINOYME TIS PARAMETROYS ME TO XERI
	/*while(choice!=1){
	    
	    //Diavazoume apo thn proth eggrafh ti levels exoume kai ta apothikevoume ston 
	    for(i=0;i<numOfAtt;i++){
		System.out.println("For the attribute "+Dim[i]+" give the level you want:");
		s=input.readLine();
		h[i]=Integer.parseInt(s);
	    
	    }
	    System.out.println("Give me the k you want:");
	    s=input.readLine();
	    k=Integer.parseInt(s);
	    System.out.println("Give me the max suppressed tuples you allow:");
	    s=input.readLine();
	    maxsupp=Integer.parseInt(s);
	    check_if_its_ok();
	    System.out.println("Thes na stamathseis????");
	    System.out.println("Dose to 1, allios opoiodhpote allo arithmo gia na synexiseis !!");
	    s=input.readLine();
	    choice=Integer.parseInt(s);
	}*/
        /************ tis evas **********/
	param();
        /************ tis alikis **********/
	//read_parameters_from_file();
	
	
    }
    /************* tis evas ************/
    static public void param(){
        try{
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader input = new BufferedReader(reader);
            //DINOYME TIS PARAMETROYS ME TO XERI
            //Anoigoume buffer gia grapsimo
            FileWriter fstream = new FileWriter("out.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            FileWriter fstream1 = new FileWriter("lysh.txt");
            out1= new BufferedWriter(fstream1);
            h=new Integer[numOfAtt];
            for(int i = 0; i < numOfAtt; i++){
                h[i] = gui.h[i];
            }
            k = gui.k;
            maxsupp = gui.maxsupp;
            num_of_visited_nodes1=0;
	    num_of_visited_nodes_sugg_1_3=0;
	    num_of_visited_nodes_sugg_2=0;
	    long start = System.currentTimeMillis();
	    long end = System.currentTimeMillis();
	    long online_time=end-start;
            check_if_its_ok();         
            out.close();
            out1.close();
        }catch(Exception E)
        {
            System.out.println("try-chatch param " + E.getMessage());
	}
    }

    /*
     *************** den tin xrisimopoiw egw *******
     **** tis parametrous tis dinei o xristis sto gui***
     */

    static public void read_parameters_from_file() throws SQLException,IOException{
	
	int i;
	String line;
	String[] parametr;
	//Anoigoume buffer gia diavasma
	FileReader reader=new FileReader("arxeio_gia_diavasma.txt");
	BufferedReader input = new BufferedReader(reader);
	//Anoigoume buffer gia grapsimo
	FileWriter fstream = new FileWriter("out.txt");
	BufferedWriter out = new BufferedWriter(fstream);
	
	FileWriter fstream1 = new FileWriter("lysh.txt", true);
	out1= new BufferedWriter(fstream1);
	out.write("\txronos: \t"+"# of vis nodes1 \t"+" # of visited nodes sugges 1_3 \t"+"# of visited nodes sugges 2 \n");
	
	while((line=input.readLine())!=null){
	    
	    //Diavazoume apo thn proth eggrafh ti levels exoume kai ta apothikevoume ston 
	    //System.out.println(line);
	   
	    parametr=line.split(" ");

	    
	    k=Integer.parseInt(parametr[0]);
	    maxsupp=Integer.parseInt(parametr[1]);
	    
	    for(i=2;i<parametr.length;i++){
		h[i-2]=Integer.parseInt(parametr[i]);
	    
	    }
	    out.write("Parametroi:"+k+", "+maxsupp+", ");
	    out1.write("Parametroi:"+k+", "+maxsupp+", ");
	    for(int l=0;l<h.length;l++){
		out.write(" "+h[l]);
		out1.write(" "+h[l]);
	    }
	    out.write("\n");
	    out1.write("\n");
	    num_of_visited_nodes1=0;
	    num_of_visited_nodes_sugg_1_3=0;
	    num_of_visited_nodes_sugg_2=0;
	    initialize_attrib_visited_false();
	    long start = System.currentTimeMillis();
	    check_if_its_ok();
	    long end = System.currentTimeMillis();
	    long online_time=end-start;
	    
	    out.write("\t"+online_time+"\t"+num_of_visited_nodes1+"\t"+num_of_visited_nodes_sugg_1_3+"\t"+num_of_visited_nodes_sugg_2+"\n");
	    out1.write("\n");
	}
	out.close();
	out1.close();
	reader.close();
	input.close();
    }
    static public void check_if_its_ok() throws SQLException,IOException{
	
	String tablename="C";
	int id=0,supp_tuples,relax3_k,height,supp,id_relax1_3;
	Vector<Integer> chld;
	Integer[] level;
	Statement statement =conn.createStatement();
	String SelectQuery="Select id From "+tablename+numOfAtt+" Where ";
	for(int i=0;i<numOfAtt;i++){
	    SelectQuery+="index_"+i+"="+h[i];
	    if(i!=numOfAtt-1)
		SelectQuery+=" and ";
	    	    
	}
	ResultSet rs= statement.executeQuery(SelectQuery);
	if(rs.first()){
	    id=rs.getInt(1);	    
	}	
	statement.close(); 
	rs.close();
	
	flag=0;
	supp_tuples=suppressed_tuples(id,k);
	height=return_heigth(id);
	//System.out.println("To ypsos aytou to komvou einai: "+height);
	if(supp_tuples<=maxsupp){
            relax = false;
	   // System.out.println("Eimaste ok mporoume na katevoume pio kato");
	    flag=1;
	    num_of_visited_nodes1++;
	    System.out.println(id + " node");
            ids[idCnt] = id;
            idCnt++;
	    chld=getchild(id);
	    //min_id=id;
	   // min_in_subtree=maxsupp;
	    min_id=id;
            MIN_ID = min_id;
	    min_in_subtree=supp_tuples;
	    min_height=height;
	    List=new Vector <desc_anc_nodes>();
	    
	    move_to_subtree(chld);
	    find_min_in_list();
	    level=return_level(min_id);
	    out1.write("Move down find, id: "+min_id+" supp tuples:"+min_in_subtree+" level:");
            suppTup = Integer.toString(min_in_subtree);
            MIN_ID = min_id;
            Level = "";
	    for(int i=0;i<numOfAtt;i++){
		 out1.write(level[i]+" ");
                 Level += level[i]+" ";
            }
	    out1.write("\n");
	   System.out.println("\n"+"To id pou vrhkame katevainontas einai:"+min_id+" kai oi supp eggrafes:"+min_in_subtree+"\n");
	}
	else{
	    num_of_visited_nodes_sugg_1_3++;
	    num_of_visited_nodes_sugg_2++;
	   //System.out.println("******************Prepei na kanoume relaxation************************");
           relax = true;
           id_relax1_3=check_for_children_suggest_1_and_3(id);

	    suggestion_relaxation1(id_relax1_3);
	    
	    //System.out.println("RELAXATION 2");
	    suggestion_relaxation2(id);
//            JOptionPane.showMessageDialog(null, "RELAXATION 2 \n To deytero relaxation dinei ton komvo: "+CurrentMinId+ " me "+suppressed_tuples(CurrentMinId,k)+" eggrafes");
            MIN_ID_R2 = CurrentMinId;
            System.out.println("To deytero relaxation dinei ton komvo: "+CurrentMinId+ " me "+suppressed_tuples(CurrentMinId,k)+" eggrafes"+"\n");
	    level=return_level(CurrentMinId);
	    out1.write("Relaxation2 id:"+CurrentMinId+" supp tuples:"+suppressed_tuples(CurrentMinId,k)+" level:");
            suppTup2 = Integer.toString(suppressed_tuples(CurrentMinId,k));
	    for(int i=0;i<numOfAtt;i++){
                Level = "";
                out1.write(level[i]+" ");
                Level2 += level[i]+" ";
            }
	    out1.write("\n");
	    
	    //System.out.println("RELAXATION 3");

	    relax3_k=suggestion_relaxation3(id_relax1_3);
	    if(relax3_k>1){
//                JOptionPane.showMessageDialog(null, "RELAXATION 3 \nTo trito relaxation edose san k :"+relax3_k+" gia ton komvo me id "+id_relax1_3);
                System.out.println("To trito relaxation edose san k :"+relax3_k+" gia ton komvo me id "+id_relax1_3+"\n");
		MIN_ID_R3 = id_relax1_3;
                Level3 = GenerateGraph.Solution(2);
                k_R3 = relax3_k;
                suppTup3 = Integer.toString(suppressed_tuples(id_relax1_3,relax3_k));
		out1.write("Relaxation3 id:"+id_relax1_3+" k:"+relax3_k+" Supp_tupp:"+suppressed_tuples(id_relax1_3,relax3_k)+"\n");
	    }
	    else{
                JOptionPane.showMessageDialog(null, "Den mporeis na petyxeis to Relaxation 3 poy thes gia tis parametrous poy edoses");
		System.out.println("Den mporeis na petyxeis to Relaxation 3 poy thes gia tis parametrous poy edoses");
		out1.write("Relaxation3 Den mporeis na petyxei gia aytes tis parametrous"+"\n");
	    }    
	 }	 
	
    }
    static public int suppressed_tuples(int id,int k_in)
    {
	Node test;
	int supp_tuples=0,grp;
	Boolean Find=true;
	Vector<Integer> groups;
	Vector<Integer> plithos;
	test=Treem.get(id);
	groups=test.getgroups();
	plithos=test.getplithos();
	ListIterator iter = groups.listIterator();
	ListIterator iter2=plithos.listIterator();
	while (Find) {
	    grp=(Integer) iter.next();
	    if(grp<k_in)
	    {
		//System.out.println("Ierarxia--group--:  "+grp);
		//Diorthosh an exo l-diversity ypologizetai allios xoris na pollaplasiazo epi to group
		if(priv_constrain==1)
		    supp_tuples+=grp*((Integer)iter2.next()); //Otan exoume k anonymity
		else
		    supp_tuples+=(Integer) iter2.next();	//Otan exoume l-diveristy
		
	    }
	    else
		Find=false;	
	}
        suppTup = Integer.toString(supp_tuples);
	return(supp_tuples);
    }
    public static Vector<Integer> getchild(int id) throws SQLException,IOException{
	
	String tablename_edge="E";
	Statement statement =conn.createStatement();
	String SelectQuery="Select start From "+tablename_edge+numOfAtt+" e Where e.end="+id;
	Vector<Integer> childs=new Vector<Integer>();
	ResultSet rs= statement.executeQuery(SelectQuery);
	while(rs.next()){
	    if(flag==1){
		num_of_visited_nodes1++;
                ids[idCnt] = id;
                idCnt++;
            }
	   childs.add(rs.getInt(1));
	    
	}
	statement.close(); 
	rs.close(); 
	return childs;
	
    }
    public static void initialize_attrib_visited_false()throws SQLException,IOException{
	
	Node test;
	//Na arxikopoihso se olous tous komvous to pedio visited false
	Collection c = Treem.values();
	
	Iterator itr = c.iterator();
	while(itr.hasNext()){
	    test=(Node) itr.next();
	    test.set_false_visit();
	}
	    
    }
    public static void set_true(int id){
	Node test;
	test=Treem.get(id);
	test.set_true_visit();
    }
    public static boolean get_visited(int id) throws SQLException,IOException{
	Node test;
	test=Treem.get(id);
	return test.ret_visited();
    }


    public static void move_to_subtree(Vector<Integer> child) throws SQLException,IOException{
	
	ListIterator iter = child.listIterator();
	int supp_tuples,id,height;
	
	desc_anc_nodes nd;
	while(iter.hasNext()){
	    
	    id=(Integer) iter.next();
	    supp_tuples=suppressed_tuples(id,k);
	    height=return_heigth(id);
	    if(supp_tuples<=maxsupp){
		if(get_visited(id)==false){
		    nd= new desc_anc_nodes(id,height,supp_tuples);
		    //List.add(nd);
		    //System.out.println("Eimaste ok mporoume na katevoume pio kato");
		    if(height<min_height){	   
			min_height=height;
			List.clear();
			List.add(nd);
	
		    }
		    else if(height==min_height)
			List.add(nd);
		    set_true(id);		//allages gia na mhn psaxnei tous idious komvous
		    move_to_subtree(getchild(id));	
		}
		//System.out.println("oi Suppressed tuples einai "+supp_tuples+" me id:"+id+" kai height:"+height);
	    }    
	}
    }
    
    public static void find_min_in_list(){
	
	int id,supp;
	desc_anc_nodes nd;
	ListIterator iter1 = List.listIterator();
	if(iter1.hasNext()){
	    nd= (desc_anc_nodes) iter1.next();
	    id=nd.get_id();
	    supp=nd.get_supp_tuples();
	    min_id=id;
            MIN_ID = min_id;
	    min_in_subtree=supp;
	    num_of_visited_nodes1++;
            ids[idCnt] = id;
            idCnt++;
	   // System.out.println("Sto teleytaio height oi komvoi einai id:"+id+" supp:"+supp); 
	}
	while(iter1.hasNext()){
	    nd= (desc_anc_nodes) iter1.next();
	    id=nd.get_id();
	    supp=nd.get_supp_tuples();
	    //System.out.println("Sto teleytaio height oi komvoi einai id:"+id+" supp:"+supp);   
	    num_of_visited_nodes1++;
            ids[idCnt] = id;
            idCnt++;
	    //System.out.println("Desc tou M max me id:"+id+", supp tuples:"+supp);
	    if(supp<min_in_subtree){
		min_id=id;
                MIN_ID = min_id;
		min_in_subtree=supp;
	    }
	}
	//System.out.println("Oi vistited komvoi einai sylonika :"+num_of_visited_nodes1);
    }
    public static int check_for_children_suggest_1_and_3(int id)throws SQLException,IOException{
	
	int min_child=id,supp_tup,max_supp;
	max_supp=suppressed_tuples(id,k);
	Vector<Integer> childs;
	childs=getchild(id);
	ListIterator iter1 = childs.listIterator();
	while(iter1.hasNext()){
	    num_of_visited_nodes_sugg_1_3++;
	    id=(Integer) iter1.next();
	    supp_tup=suppressed_tuples(id,k);
	    if(supp_tup<=max_supp){
		max_supp=supp_tup;
		min_child=id;
	    }  
	}
	return min_child;
    }
    public static void suggestion_relaxation1(int id) throws SQLException,IOException{
	
	Integer[] level;
	//System.out.println("RELAXATION 1");
	int supp_tuples;
	
	supp_tuples=suppressed_tuples(id,k);
	//System.out.println("Poy exei exei to elaxisto plhthos suppressed eggrafon: "+supp_tuples );
	//System.out.println("O komvos aytos exei id: "+id +"\n");
	level=return_level(id);
	MIN_ID = id;
	out1.write("relaxation1: id:"+id+" supp tuples:"+ supp_tuples+" level:");
	for(int i=0;i<numOfAtt;i++){
            out1.write(level[i]+" ");
            Level1 += level[i]+" ";
        }
        suppTup1 = Integer.toString(supp_tuples);
        System.out.println("relaxation1: id:"+id+" supp tuples:"+ supp_tuples+" level:" + Level1);
//        JOptionPane.showMessageDialog(null, "RELAXATION 1 \nTo proto relaxation edose ton komvo me id "+id + " supp tuples: "+ supp_tuples+ "kai level " + Level1);
	out1.write("\n");	
    }
    public static int suggestion_relaxation3(int id){
	
	//System.out.println("S'ayto to relaxation kratame stathero to h kai to max_supp alla oxi to k");

	int k_mesa=k,supp_tuples;
	supp_tuples=suppressed_tuples(id,k_mesa);
	while(supp_tuples>maxsupp && k_mesa>0){ 
	    k_mesa--;
	    supp_tuples=suppressed_tuples(id,k_mesa);
	   // System.out.println("****** "+supp_tuples+" ******");
	}
	// System.out.println("****to k "+k_mesa+" ******");
	return k_mesa;
	
    }
    public static void suggestion_relaxation2(int id) throws SQLException,IOException{
	
	int low, high=0;
	int[] h1={5,4,3,3,5,4};		//SOSTO
	//int[] h1={5,2,5,4};	//Gia to IPUMS
	low=return_heigth(id);
	
	for(int i=0;i<numOfAtt;i++)
	    high+=h1[i]-1;
	//System.out.println("To low toy dentrou einai:"+low);
	//System.out.println("To high toy dentrou einai:"+high);    
	binary_search(low,high);
	
    }
    public static Vector<Integer> getparents(int id) throws SQLException,IOException{
	
	String tablename_edge="E";
	Statement statement =conn.createStatement();
	String SelectQuery="Select end From "+tablename_edge+numOfAtt+" e Where e.start="+id;
	Vector<Integer> parents=new Vector<Integer>();
	ResultSet rs= statement.executeQuery(SelectQuery);
	while(rs.next()){
	   parents.add(rs.getInt(1));
	    
	}
	rs.close();
	statement.close();
	return parents;

    }
    public static int return_heigth(int id) throws SQLException,IOException{
	
	int height=0;
	String tablename="C";
	Statement statement =conn.createStatement();
	String SelectQuery="Select " ;
	for(int i=0;i<numOfAtt;i++){
	    SelectQuery+="index_"+i;
	    if(i!=(numOfAtt-1))
		SelectQuery+=",";
	}
	SelectQuery+=" From "+tablename+numOfAtt+" e Where e.id="+id;
	//System.out.println(SelectQuery);
	ResultSet rs= statement.executeQuery(SelectQuery);
	if(rs.next()){
	   for(int j=1;j<=numOfAtt;j++)
	       height+=rs.getInt(j);
	    
	}
	rs.close();
	statement.close();
	return height;
		
    }
    public static Integer[] return_level(int id) throws IOException,SQLException{
	
	Integer[] level;
	String tablename="C";
	Statement statement =conn.createStatement();
	String SelectQuery="Select " ;
	for(int i=0;i<numOfAtt;i++){
	    SelectQuery+="index_"+i;
	    if(i!=(numOfAtt-1))
		SelectQuery+=",";
	}
	level=new Integer[numOfAtt];
	SelectQuery+=" From "+tablename+numOfAtt+" e Where e.id="+id;
	ResultSet rs= statement.executeQuery(SelectQuery);
	if(rs.next()){
	   for(int j=1;j<=numOfAtt;j++)
	       level[j-1]=rs.getInt(j);
	    
	}
	rs.close();
	statement.close();
	return level;
	
    }
    public static Vector<desc_anc_nodes> bring_nodes_in_height(int height) throws SQLException,IOException{
	
	int supp,id;
	String tablename="C";
	desc_anc_nodes nd;
	Statement statement =conn.createStatement();
	String SelectQuery="Select id From " +tablename +numOfAtt+" where (";
	for(int i=0;i<numOfAtt;i++){
	    SelectQuery+="index_"+i;
	    if(i!=(numOfAtt-1))
		SelectQuery+=" + ";
	}
	SelectQuery+=")="+height;
	Vector<desc_anc_nodes> nodes_in_height=new Vector<desc_anc_nodes>();
	ResultSet rs= statement.executeQuery(SelectQuery);
	while(rs.next()){  
	    id=rs.getInt(1);   
	    supp=suppressed_tuples(id,k);
	    nd=new desc_anc_nodes(id,height,supp);
	    //System.out.println("To id poy exei to sygkekrimeno ypsos einai "+nd.get_id());
	    nodes_in_height.add(nd);
	}
	statement.close(); 
	rs.close(); 
	
	return(nodes_in_height);
    }
    public static void binary_search(int low, int high)throws SQLException,IOException{
	
	int current;
	Boolean up;
	CurrentMinHeight=high;
	//System.out.println("ksekinise to binary search gia ta ypsos low:"+low+" gia to high: "+high);	
	while(low<=high){
	    
	    current=(low+high)/2;
	    //System.out.println("To current ypsos einai"+current);
	   
	    up=check_height(current);
	    if(up){
		low=current+1;
	    }else{
		CurrentMinHeight=current;
		//System.out.println(CurrentMinHeight);
		high=current-1;   //update the current height
	    }   
	}
	find_min_in_CurrentMinHeight();
	
    }
    public static boolean check_height(int current)throws SQLException,IOException{
	
	//System.out.println("Mphka sthn check current");
	int id,supp_tuples;
	desc_anc_nodes nd;
	Vector<desc_anc_nodes> list;
	list=bring_nodes_in_height(current);
	ListIterator iter = list.listIterator();
	int pl_komvon_ana_ypsos=0;
	while(iter.hasNext()){
	    num_of_visited_nodes_sugg_2++;
	    nd=(desc_anc_nodes) iter.next();
	    id=nd.get_id();
	    //System.out.println(" ******Sthn check To id **** "+id);
	    supp_tuples=nd.get_supp_tuples();
	    //System.out.println("Id: "+id+" supp:"+supp_tuples);
	    
	    pl_komvon_ana_ypsos++;
	    if(supp_tuples<=maxsupp){
		//System.out.println("Vrhka pio noris kai vghka, elegxontas:"+pl_komvon_ana_ypsos);		
		return false;
	    }
	}
	//System.out.println("Eksetasa olo to ypsos kai den vrhka:"+pl_komvon_ana_ypsos); 
	return true;
    }
    public static void find_min_in_CurrentMinHeight()throws SQLException,IOException{
	
	//System.out.println("%%%%%%%%%%%%%%% Mphka sthn find_min_CurrentMinHeight%%%%%%%%%%%%%%%%%%%");
	
	int id,supp_tuples,CurrentMinSupp,count=0;
	desc_anc_nodes nd;
	Vector<desc_anc_nodes> list;
	list=bring_nodes_in_height(CurrentMinHeight);
	//System.out.println("To ypsos pou stamthsa einai:"+CurrentMinHeight);
	ListIterator iter1 = list.listIterator();
	nd=(desc_anc_nodes) iter1.next();
	CurrentMinId=nd.get_id();
        System.out.println(CurrentMinId + " wtf");
	CurrentMinSupp=nd.get_supp_tuples();
	num_of_visited_nodes_sugg_2++;
	//System.out.println("To ypospsifio elaxistoo apo to binary search "+CurrentMinId +" supp:"+CurrentMinSupp);
	while(iter1.hasNext())
	{
	    count++;
	    num_of_visited_nodes_sugg_2++;
	    nd=(desc_anc_nodes) iter1.next();
	    id=nd.get_id();
	    supp_tuples=nd.get_supp_tuples();
	   // System.out.println("To ypopsifio elaxisto apo to binary search "+id +" supp:"+supp_tuples);
	    if(supp_tuples<CurrentMinSupp){
		CurrentMinId=id;
		CurrentMinSupp=supp_tuples;
	    }    
	}
	
	//System.out.println("So to min ypsos epsaksa :"+count);       
    }  
}
