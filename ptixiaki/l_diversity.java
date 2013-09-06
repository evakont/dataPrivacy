package ptixiaki;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

/**
 *
 */

/**
 *@author aliki
 *@version Feb 16, 2010
 */
public class l_diversity {

    TreeMap<Integer, Node> treeMap;
    Connection conn = null;
    int numOfAttr;
    String TableName;
    l_diversity(Connection conn1,int numofAtt,String TName){
	conn=conn1;
	TableName=TName;
	numOfAttr=numofAtt;

    }
    public TreeMap<Integer, Node> creat_histogramm()throws SQLException, IOException{
	String[] nameattrib={"a","w","r","o","e","m"};
	//String[] nameattrib={"a","b","e","g"};   //IPUMS
	int []maxheight={9,11,15,18};	//Adult
	//int []maxheight={8,12};
	Statement statement =conn.createStatement();
        Statement statement_hist =conn.createStatement();
        Statement statement_in_hist =conn.createStatement();
	ResultSet rs = null;
	ResultSet rs1=null;
	TreeMap<Integer, Node> treeMap = new TreeMap<Integer,Node>(); //Topotheto ekei tous komvous gia na mporo na kano grhgoro search me to id tou kathe komvou
	//Vector<Node> vnodes=new Vector<Node>();
	//int []sum_supp_tup;		//Gia na metrhsoume to athroisma ton supp tuples se kathe ypsos
	//int []count_supp_tup;		//Gia na doume posoi komvoi einai se kathe level
	int []indxlevel;
	//int []min_per_level;
	indxlevel=new int[numOfAttr];
	Node lv;
	String levels="";
	int count2=0,id,m,count_hist=0;
        Writer output = null;
        File file = new File("apotelesmata_l_diversity.xls");
        output = new BufferedWriter(new FileWriter(file));

        //sum_supp_tup=new int[maxheight[numOfAttr-3]+1];
	//count_supp_tup=new int[maxheight[numOfAttr-3]+1];
	//min_per_level=new int [maxheight[numOfAttr-3]+1];

       // File fstream = new File("/home/aliki/Documents/Full_lattice_avg_supp_l_divers.txt");
	//Writer myoutput = new BufferedWriter(new FileWriter(fstream));

	//Erothsh gia na mathoume to lattice kai meta apo ayto tha kataskevasoume ola ta istogrammata

	String sensetive_attr="hours_per_week";
        //String sensetive_attr="occupation";		//Gia to Ipums
	String selectQuery="SELECT id";
	for(int i=0;i<numOfAttr;i++)
	    selectQuery+=", dim_"+i+",index_"+i;

	selectQuery+=" FROM "+TableName+Integer.toString(numOfAttr);
//	System.out.println("SELECT apo ton pinaka C3 \n");
//	System.out.println(selectQuery + "\n");
	/*for(int i2=0;i2<=maxheight[numOfAttr-3];i2++){
		sum_supp_tup[i2]=0;
		count_supp_tup[i2]=0;
		min_per_level[i2]=8000000;
	}*/

	rs=statement.executeQuery(selectQuery);

        String createQuery = "CREATE TABLE Hist ( id INTEGER  NOT NULL AUTO_INCREMENT, " +
                "name varchar(20), val Integer, pl Integer, PRIMARY KEY (id))";
        try{
	    statement_hist.executeUpdate(createQuery);
	    statement_hist.close();
	}catch (Exception E)
        {
            System.out.println("create"+E.getMessage());
        }
	while(rs.next()){
	    try{
	        Statement statement2 = conn.createStatement();
	    	String CreateQuery="CREATE VIEW ";
	    	CreateQuery+="test(";
	    	for(int i=0;i<numOfAttr;i++)
	    	    	CreateQuery+=nameattrib[i]+",";

	    	CreateQuery+="l,tuples) AS (SELECT ";
	    	for(int j=2;j<=(2*numOfAttr);j+=2)
	    	    CreateQuery+=rs.getString(j)+".level"+rs.getInt(j+1)+",";

	    	CreateQuery+=" count(distinct adult."+ sensetive_attr+"),count(*) FROM adult,";
		for(int j=2;j<=(2*numOfAttr);j+=2){
		    CreateQuery+=rs.getString(j);
		    if(j!=(2*numOfAttr))
			CreateQuery+=",";
		}


		CreateQuery+=" WHERE ";
		for(int i=2;i<=(2*numOfAttr);i+=2){
			CreateQuery+="adult."+rs.getString(i)+"="+rs.getString(i)+".level0 ";
		    if(i!=2*numOfAttr)
			CreateQuery+=" and ";
		}

		CreateQuery+=" GROUP BY ";
		for(int j=2;j<=(2*numOfAttr);j+=2){
	    	    CreateQuery+=rs.getString(j)+".level"+rs.getInt(j+1);
	    	    if(j!=(2*numOfAttr))
			CreateQuery+=",";

		}
		CreateQuery+=")";
//		System.out.println("Create thn View\n");
//		System.out.println(CreateQuery + "\n");

		statement2.executeUpdate(CreateQuery);

	     //dhmiourgia tou istorgmatos
		Statement statement3 =conn.createStatement();

		String SelectQuery1="SELECT l,count(*),sum(tuples) FROM test GROUP BY l";
		rs1=statement3.executeQuery(SelectQuery1);
		lv=new Node(numOfAttr);
		for(int j=2;j<=(2*numOfAttr);j+=2)
		    levels+=rs.getString(j)+rs.getInt(j+1);
//		System.out.println(levels+"\n");
		output.write(levels+"\n");
                String Name = levels.replaceAll("[^0-9]+","");
		id=rs.getInt(1);

		m=0;
		for(int j=2;j<=(2*numOfAttr);j+=2){
		    //out2.write(rs.getString(j)+rs.getInt(j+1)+"\t");
		    levels+=rs.getString(j)+rs.getInt(j+1);
		    indxlevel[m]=rs.getInt(j+1);	//Ayto einai apla an thelo na krathso se poio ypsos vriskome
		    m++;
		}

		lv.setid(id);
		lv.setlevels(levels);
		lv.setindexlevels(indxlevel);
		//lv.setindexlevels(indxlevel);
		while(rs1.next())		//Diavazei oles tis eggrafes tou pinaka
		{
		    //System.out.println("omades:"+rs1.getInt(1)+" plithos"+rs1.getInt(2));
		    lv.setgroups(rs1.getInt(1));
		    lv.setplithos(rs1.getInt(3));
		    if(count2<25){
			output.write(rs1.getInt(1)+"\t"+rs1.getInt(3)+"\n");
                        String InsertQuery = "INSERT INTO Hist (name, val, pl) VALUES ("+ Name +", " +rs1.getInt(1)+", "+rs1.getInt(2)+ ")";
                        try{
                            statement_in_hist.executeUpdate(InsertQuery);

                        }catch (Exception E)
                        {
                            System.out.println("insert into Hist " +E.getMessage());
                        }
                    }
		    count2++;

		}
		treeMap.put(id,lv);  //eisago ton komvo sto dentro
		count_hist+=2*lv.getsize_of_vector();
		/*for(int i2=0;i2<=maxheight[numOfAttr-3];i2++){
		    if(lv.return_height()==i2){
			sum_supp_tup[i2]+=lv.get_sup_tuples(9,2);		//Gia na metrhsoume to athroisma ton supp tuples se kathe ypsos perano 2 gia na einai l-diversity
			count_supp_tup[i2]++;
			if(lv.get_sup_tuples(9,2)<min_per_level[i2])
			    min_per_level[i2]=lv.get_sup_tuples(9,2);

		    }

		}*/

		String DeleteView="DROP VIEW test";
		statement3.executeUpdate(DeleteView);
		levels="";
		rs1.close();

		statement2.close();
		statement3.close();
		count2=0;


	    }catch(Exception E){
		System.out.println(E.getMessage());
	    }
	}
        statement_in_hist.close();
	rs.close();
	statement.close();
	output.close();
	/*myoutput.write("Ypsos \tsupp\t plhtos\tmin\n");
	for(int i2=0;i2<sum_supp_tup.length;i2++){
	    System.out.println(i2+"\t"+sum_supp_tup[i2]+" \t"+count_supp_tup[i2]+"\t"+min_per_level[i2]+"\n");
	    myoutput.write(i2+"\t"+sum_supp_tup[i2]+" \t"+count_supp_tup[i2]+"\t"+min_per_level[i2]+"\n");
	}*/


	//myoutput.close();

	return treeMap;
    }
}