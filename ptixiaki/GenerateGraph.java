package ptixiaki;


import java.sql.*;
import java.util.*;

/**
 *@author aliki
 *@version Oct 16, 2009
 */
public class GenerateGraph {

    static Connection con;
    private String TableNameNode="C";
    private String TableNameEdge="E";
    private String CopyNodeTable="S";
    static String[] Dim={"age","work_class","race","occupation", "education","marital_status"};  //Gia Adult
    //static String[] Dim={"age","gender","birthplace","education"}; //Gia Ipums
    static Vector[] Index;
    private int sizeOfAttr; //To noumero ton pinakon poy ftiaxno C0,C1...kl
    static int numofAttr; //To plhthos ton attribute pou tha xrhsimopoihsoume
    public static int end = Integer.parseInt(gui.s);
    public static String N[] = new String[100000];
    public static String StrName[] = new String[end];
    public static String FatherName[] = new String[end];
    public GenerateGraph(Connection conn,int size,int numofAtt){

	con=conn;
	sizeOfAttr=size;
	numofAttr=numofAtt;
    }
    public void NodeGeneration()throws SQLException{

	int i;
	//System.out.println("MPhka sthn NOde Generate");
	Statement statement =con.createStatement();
	Statement statement2=con.createStatement();
	String InsertQuery="INSERT INTO "+TableNameNode+Integer.toString(sizeOfAttr)+"(";
	for(i=0;i<sizeOfAttr;i++){
	    InsertQuery += " dim_" + i+"," ;
	    InsertQuery += " index_" + i+",";
	}

	InsertQuery+=" parent1, parent2) SELECT ";
	for(i=0;i<sizeOfAttr-1;i++){
	    InsertQuery += " p.dim_" + i+"," ;
	    InsertQuery += " p.index_" + i+",";
	}
	InsertQuery+=" q.dim_" + (sizeOfAttr-2)+",q.index_"+(sizeOfAttr-2)+",p.id,q.id" ;
	InsertQuery+=" FROM "+CopyNodeTable+Integer.toString(sizeOfAttr-1)+" p,"+CopyNodeTable+Integer.toString(sizeOfAttr-1)+" q";
	InsertQuery+=" WHERE";
	for(i=0;i<(sizeOfAttr-2);i++){
	    InsertQuery+=" p.dim_"+i+"=q.dim_"+i+" AND p.index_"+i+"=q.index_"+i+" AND";
	}
	InsertQuery+=" p.dim_"+(sizeOfAttr-2)+"<q.dim_"+(sizeOfAttr-2);
	//System.out.println(InsertQuery + "\n");
        ////gui.line++; gui.output_area.insert(  InsertQuery + "\n",gui.line); gui.line++;
	try{
	    statement.executeUpdate(InsertQuery);
	    statement.close();
	}catch(Exception E)
        {
            System.out.println(E.getMessage());
        }
	//ftiaxno index gia ton pinaka ton komvon gia na mporeso na psaxno grhgora to id symfona me to level
	if(sizeOfAttr==numofAttr){
	    //System.out.println("Mphka na ftiakso indexes");
            //gui.line++; gui.output_area.insert("  Mphka na ftiakso indexes",gui.line); gui.line++;
	    String create_indx="CREATE INDEX C_allDims_BT_indx ON "+TableNameNode+Integer.toString(numofAttr)+" (";
	    for(i=0;i<numofAttr;i++){
		create_indx+="index_"+i;
		if(i!=numofAttr-1)
		    create_indx+=",";
	    }

	    create_indx+=")";
	    try{
		    statement2.executeUpdate(create_indx);
		    statement2.close();
	    }catch(Exception E)
	      {
	            System.out.println(E.getMessage());
	     }
	}
	//System.out.println("Vghka sthn NOde Generate");
    }
    public void EndgeGeneration()throws SQLException{

	//System.out.println("Mphkaaaa sthn Edges Generate");

	Statement statement =con.createStatement();
	Statement statement2=con.createStatement();
	Statement statement3=con.createStatement();
	Statement statement4=con.createStatement();
	Statement statement5=con.createStatement();
	Statement statement6=con.createStatement();

	Statement statement11=con.createStatement();
	Statement statement12=con.createStatement();
	String create_indx11="CREATE INDEX indx_"+TableNameNode+Integer.toString(sizeOfAttr)+"_1 ON " +TableNameNode+Integer.toString(sizeOfAttr)+"(parent1)";
	statement11.executeUpdate(create_indx11);
	statement11.close();

	String create_indx12="CREATE INDEX indx_"+TableNameNode+Integer.toString(sizeOfAttr)+"_2 ON " +TableNameNode+Integer.toString(sizeOfAttr)+"(parent2)";
	statement12.executeUpdate(create_indx12);
	statement12.close();

	String CreateQuery="CREATE VIEW CandidateEdges(start,end) AS (SELECT ";
	CreateQuery+="distinct p.id, q.id FROM "+TableNameNode+Integer.toString(sizeOfAttr)+" p,"+TableNameNode+Integer.toString(sizeOfAttr)+" q,";
	CreateQuery+=TableNameEdge+Integer.toString(sizeOfAttr-1)+" e,"+TableNameEdge+Integer.toString(sizeOfAttr-1)+" f";
	CreateQuery+=" WHERE (e.start=p.parent1 AND e.end=q.parent1 AND f.start=p.parent2 AND f.end=q.parent2))";
	CreateQuery+=" UNION (SELECT distinct p.id, q.id FROM "+ TableNameNode+Integer.toString(sizeOfAttr)+" p,"+TableNameNode+Integer.toString(sizeOfAttr);
	CreateQuery+=" q,"+TableNameEdge+Integer.toString(sizeOfAttr-1) +" e WHERE (e.start=p.parent1 AND e.end=q.parent1 AND p.parent2=q.parent2))";
	CreateQuery+=" UNION (SELECT distinct p.id, q.id FROM " + TableNameNode+Integer.toString(sizeOfAttr)+" p,"+TableNameNode+Integer.toString(sizeOfAttr);
	CreateQuery+=" q,"+TableNameEdge+Integer.toString(sizeOfAttr-1) +" e WHERE (e.start=p.parent2 AND e.end=q.parent2 AND p.parent1=q.parent1))";
	//System.out.println(CreateQuery + "\n");

	try{
	    statement2.executeUpdate(CreateQuery);
	    statement2.close();
	}catch(Exception E){
	    System.out.println(E.getMessage());
	}

	//Deyteros tropos prota ftiaxno to help meta to index kai meta to insert, kai pali argei to insert
	//System.out.println("Ftiaxno to help");
	String CreateQuery2="CREATE TABLE help (start INTEGER,end INTEGER)";

	statement4.executeUpdate(CreateQuery2);
	statement4.close();
	//String CreateQuery2="CREATE TABLE help SELECT start,end FROM CandidateEdges";
	//System.out.println("TO index argei");
	String create_indx="CREATE INDEX help_start_end ON help(start,end)";
	//System.out.println("TO index argei");
	statement5.executeUpdate(create_indx);
	statement5.close();


	//System.out.println("============Insert============");
	String insert_in_help="INSERT INTO help SELECT start,end FROM CandidateEdges";
	statement6.execute(insert_in_help);
	statement6.close();
	//Test tha dokimaso na ektelese tohn parakato query grgora

	//System.out.println("H deyterh evthsh einai h argh??");
	String InsertQuery="INSERT INTO "+TableNameEdge+Integer.toString(sizeOfAttr)+"(start,end)";
	InsertQuery+=" SELECT D.start, D.end FROM help D";
	InsertQuery+=" WHERE (D.start, D.end) NOT IN (SELECT p.start,q.end FROM help p,help q";
	InsertQuery+=" WHERE p.end=q.start)";

	//System.out.println(InsertQuery + "\n");
	try{
	    statement.executeUpdate(InsertQuery);
	    statement.close();
	}catch(Exception E){
	    System.out.println(E.getMessage());
	}
	String DeleteView="DROP VIEW CandidateEdges";
	String DeleteView1="DROP TABLE help";
	//String DeleteIndex="DROP INDEX help_start_end ON help;";
	//Statement statement6=con.createStatement();
	try{
	   // statement6.execute(DeleteIndex);
	    //statement6.close();
	    statement3.executeUpdate(DeleteView);
	    statement3.executeUpdate(DeleteView1);
	    statement3.close();


	}catch(Exception E){
	    System.out.println(E.getMessage());
	}
	//System.out.println("VVghka  sthn NOde Generate");
    }

    /**
     * metraei to plithos twn kombwn
     */
    public static int nodes() throws SQLException{
        int Nodes = 0;

        Statement statement = con.createStatement();
        String SelectQuery = "SELECT COUNT(*) " +"FROM C"+end+" ;";
        try{
            ResultSet result = statement.executeQuery(SelectQuery);
            while(result.next()){
                Nodes = result.getInt(1);
            }
         }catch (Exception E){
            System.out.println(E.getMessage());
        }
        return Nodes;
    }

    /**
     * epistrefei ta onomata twn episkeumenwn kombwn
     */
    public static String[] VisitedNodes(){
        int cnt = Suggestion.idCnt;
        String Vnodes[] = new String[cnt];
        try{
            for(int j = 0; j < cnt; j++){
                Vnodes[j] = Visited(Suggestion.ids[j]);
            }
        }catch (Exception E){
            System.out.println("visited "+E.getMessage());
        }
        return(Vnodes);
    }

    /**
     * pernei to id enos kombou (poy exei episkeutei)
     * kai epistefei to onoma tou
     */

    public static String Visited(int id) throws SQLException{

        String nodeId = new String();
        Statement statement = con.createStatement();

        String SelectQuery = "SELECT";
        for(int i  = 0; i < end-1; i++){
            SelectQuery += " index_"+i+",";
        }
        SelectQuery += "index_"+(end-1)+" FROM C"+end+" WHERE id="+id+";";
        try{
            ResultSet result = statement.executeQuery(SelectQuery);
            while(result.next()){
                for(int i = 1; i < end + 1; i++){
                    nodeId += result.getString(i);
                }
            }
        }catch (Exception E){
            System.out.println("visited "+E.getMessage());
        }
        return(nodeId);
    }

    /**
     * epistrefei ta onomata ton kombwn
     */
    public static String[] nodeNames() throws SQLException{
        int nodes = nodes();
        String Names = new String();
        String names[] = new String[nodes];
        int cnt = 0;

        Statement statement = con.createStatement();
        String SelectQuery = "SELECT";
        for(int i  = 0; i < end-1; i++){
            SelectQuery += " index_"+i+",";
        }
        SelectQuery += "index_"+(end-1)+" FROM C"+end+" ;";
        try{
            ResultSet result = statement.executeQuery(SelectQuery);
            while(result.next()){
                for(int i = 1; i < end + 1; i++){
                    Names += result.getString(i);
                }
                names[cnt] = Names;
                N[cnt] = Names;
                Names = "";
                cnt++;
            }
        }catch (Exception E){
            System.out.println(E.getMessage());
        }
        return names;
    }

    /**
     * pernei to onoma enos kombou kai briskei tis akmes tou
     */
    public static void getTheEdges(String Nodename) throws SQLException{
        char [] chars;

        int [] ints = new int[end];
        int id = 0, cnt = 0, CNT = 0;
        int children[] = new int[end];
        int fathers[] = new int[end];
        int Names[][] = new int[end][end];
        int fNames[][] = new int[end][end];
        for(int j = 0; j < end; j++){
            StrName[j] = "";
            FatherName[j] = "";
        }
        chars = Nodename.toCharArray();
        for(int i = 0; i < end; i++){
            ints[i] = Character.getNumericValue(chars[i]);
        }

        Statement statement = con.createStatement();
        Statement statement1 = con.createStatement();
        Statement statement2 = con.createStatement();
        Statement statement4 = con.createStatement();
        Statement statement5 = con.createStatement();

        //pernw to id tou node
        String SelectQuery = "SELECT id " +"FROM C"+end+" WHERE (";
        for(int i  = 0; i < end-1; i++){
            SelectQuery += " index_"+i+"="+ints[i]+" AND ";
        }
        SelectQuery += "index_"+(end-1)+"="+ints[end-1] +");";

        try{
            ResultSet result = statement.executeQuery(SelectQuery);
            while(result.next()){
                id = result.getInt(1);
            }

            //pernw ta id twn nodes pou sindeontai me ton node pou pira prin
            String SelectQuery1 = "SELECT end FROM E"+end+" WHERE start="+id+";";

            ResultSet result1 = statement1.executeQuery(SelectQuery1);
            while(result1.next()){
                children[cnt] = result1.getInt(1);
                cnt++;
            }
            //brisko gia ton epilegmeno kombo apo poious dixnetai
            String SelectQuery4 = "SELECT start FROM E"+end+" WHERE end="+id+";";
            ResultSet result4 = statement4.executeQuery(SelectQuery4);
            while(result4.next()){
                fathers[CNT] = result4.getInt(1);
                CNT++;
            }
            //pernw ta onomata twn nodes pou pira to id
            for(int j = 0; j < end; j++){
                String SelectQuery2 = "SELECT";
                for(int i = 0; i < end-1; i++){
                    SelectQuery2 += " index_"+i+",";
                }
                SelectQuery2 +=  " index_"+(end-1)+" FROM C"+end+" WHERE id="+children[j]+";";
                ResultSet result2 = statement2.executeQuery(SelectQuery2);
                while(result2.next()){
                    for(int k = 0; k < end; k++){
                        Names[j][k] = result2.getInt(k+1);
                    }
                }
            }
            //perno ta onomata ton pateradon
            for(int j = 0; j < end; j++){
                String SelectQuery5 = "SELECT";
                for(int i = 0; i < end-1; i++){
                    SelectQuery5 += " index_"+i+",";
                }
                SelectQuery5 +=  " index_"+(end-1)+" FROM C"+end+" WHERE id="+fathers[j]+";";
                ResultSet result5 = statement5.executeQuery(SelectQuery5);
                while(result5.next()){
                    for(int k = 0; k < end; k++){
                        fNames[j][k] = result5.getInt(k+1);
                    }
                }
            }
            for(int j = 0; j < end; j++){
                for(int i = 0; i < end; i++){
                    StrName[j] += Names[j][i];
                }
            }

            for(int j = 0; j < end; j++){
                for(int i = 0; i < end; i++){
                    FatherName[j] += fNames[j][i];
                }
            }
            statement.close();
            statement1.close();
            statement2.close();
            statement4.close();
            statement5.close();
        }catch (Exception E){
            System.out.println(E.getMessage());
        }
    }

    /**
     * briskei pio einai to onoma tou kombou einai i lisi apo to id tou
     */
    public static String Solution(int R)throws SQLException{
        String solution =  new String();
        int id;
        int node[] = new int[end];
        Statement statement = con.createStatement();
            if(R == 0){
                id = Suggestion.MIN_ID;
            }
            else if(R == 1){
                id = Suggestion.MIN_ID_R2;
            }
            else{
                id = Suggestion.MIN_ID_R3;
            }
            try{
                String SelectQuery = "SELECT";
                for(int i = 0; i < end-1; i++){
                    SelectQuery += " index_"+i+",";
                }
                SelectQuery +=  " index_"+(end-1)+" FROM C"+end+" WHERE id="+id+";";
                ResultSet result = statement.executeQuery(SelectQuery);
                while(result.next()){
                    for(int k = 0; k < end; k++){
                        node[k] = result.getInt(k+1);
                    }
                }
                for(int j = 0; j < end; j++){
                        solution += node[j];
                }

            }catch (Exception E){
                System.out.println(E.getMessage());
            }
        return solution;
    }


    public void CreateNodeTable()throws SQLException{
	//System.out.println("MPhka sthn Creat Node Table");

	Statement statement =con.createStatement();

	String CreateQuery="CREATE TABLE "+TableNameNode+Integer.toString(sizeOfAttr)+"( id INTEGER  NOT NULL AUTO_INCREMENT";

	for (int i = 0; i < sizeOfAttr; i++) {
	    CreateQuery += ", dim_" + i + " varchar(20)";
	    CreateQuery += ", index_" + i + " Integer";
	}
	CreateQuery+=",parent1 Integer, parent2 Integer";
	CreateQuery +=", PRIMARY KEY (id))";
	//System.out.println(CreateQuery + "\n");
	try{
	    statement.executeUpdate(CreateQuery);
	    statement.close();
	}catch (Exception E)
        {
            System.out.println(E.getMessage());
        }
	//System.out.println("Vghkasthn Creat Node Table");

    }
    public void CreateEdgeTable()throws SQLException{

	//System.out.println("MPhka sthn Creat Edge Table");
	Statement statement =con.createStatement();

	String CreateQuery="CREATE TABLE "+TableNameEdge+Integer.toString(sizeOfAttr)+"( start INTEGER, end INTEGER, PRIMARY KEY(start, end))";
	statement.executeUpdate(CreateQuery);
	//System.out.println(CreateQuery + "\n");
	statement.close();
	//System.out.println("Vghka sthn Creat Edge Table");

    }
    static void createDimensions(){

	int[] h={5,4,3,3,5,4};	//TO megisto plhthos apo levels gia ola ta quasie poy xrhsimopoioume
	//int[] h={5,2,4,5};

	Index=new Vector[numofAttr];

	for(int i=0;i<numofAttr;i++){
	    Index[i]=new Vector <Integer>();
	    for(int j=0;j<h[i];j++)
		Index[i].add(j);
	}

    }
    static void InitializeTables()throws SQLException{

	//System.out.println("MPhka sthn Initialize");
	int j=0;
	Statement statement =con.createStatement();
	createDimensions();
	//Arxikopoihsh tou pinaka C1
	String insertQuery="INSERT INTO C1 (dim_0,index_0) VALUES ";
	for(int i=0;i<numofAttr;i++){


	    ListIterator iter = Index[i].listIterator();
	    j=0;
	    while (iter.hasNext()) {
		insertQuery+="( '"+Dim[i]+"',"+iter.next()+")";
		j++;
		if(j!=Index[i].size())
		    insertQuery+=",";
	    }
	    if(i!=numofAttr-1)
		insertQuery+=",";

	}
        //System.out.println("the" + insertQuery + "\n");
	try{
	    statement.executeUpdate(insertQuery);
	    //System.out.println("the" + insertQuery + "\n");
	    statement.close();
	}catch (Exception E)
        {
            System.out.println("evai" +E.getMessage());
        }
	//Arxikopoihsh tou pinaka E0
	Statement statement2 =con.createStatement();
	//createDimensions(h);
	String insertQuery2="INSERT INTO E1 (start,end) SELECT p.id,q.id FROM C1 p, C1 q";
	insertQuery2+=" WHERE (p.dim_0=q.dim_0) AND (q.index_0-p.index_0=1)";
	try{
	    statement2.executeUpdate(insertQuery2);
	    //System.out.println(insertQuery2 + "\n");
	    statement2.close();
	}catch (Exception E)
        {
            System.out.println(E.getMessage());
        }


    }
}
