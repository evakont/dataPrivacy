
package ptixiaki;

import java.util.ListIterator;
import java.util.Vector;

/**
 * Kratame ta apotelesmta gia apo kathe komvo tou lattice gia na ftiaksoume to istogramma
 */

/**
 *@author aliki
 *@version Oct 21, 2009
 */
public class Node {
    private String Namelevel;
    private int[] levelsindex;
    private Vector<Integer> groups;
    private Vector<Integer> plithos;
    private int id;
    private boolean visited;
    private int suppresion;
    public Node(int Attr){
	
	//Namelevel=new String();
	levelsindex=new int[Attr];
	groups=new Vector<Integer>();
	plithos=new Vector<Integer>();
	visited=false;
    }
    
    public void setid(int id1){
	id=id1;
    }
    public void setlevels(String levels){
	
	    Namelevel=levels;
    }
    public void setindexlevels(int[] index){
	for(int i=0;i<index.length;i++)
	    levelsindex[i]=index[i];
    }
    public void setgroups(int grp)
    {
	groups.add(grp);
    }
    public void setplithos(int pl)
    {
	plithos.add(pl);
    }
    public void set_false_visit(){
	visited=false;
    }
    public void set_true_visit(){
	visited=true;
    }
    public String getlevels(){
	return Namelevel;
    }
    public int[] getindexlevel(){
	return levelsindex;
    }
    public boolean ret_visited(){
	return visited;
    }
    public Vector<Integer> getgroups(){
	return groups;
    }
    public Vector<Integer> getplithos(){
	return plithos;
    }
    public int getsize_of_vector(){
	return plithos.size();
    }
    public int getid(){
	return id;
    }
    public int return_supp(){
    	return suppresion;
    }
    public int return_height(){
	int sum=0;
	for(int i=0;i<levelsindex.length;i++)
	    sum+=levelsindex[i];
	
	return sum;
    }
    public int get_sup_tuples(int k,int priv_constr){
	int supp_tuples=0,grp;
	Boolean Find=true;
	ListIterator iter = groups.listIterator();
	ListIterator iter2=plithos.listIterator();
	while (Find) {
	    grp=(Integer) iter.next();
	    if(grp<k)
	    {
		//System.out.println("Ierarxia--group--:  "+grp);
		if(priv_constr==1)
		    supp_tuples+=grp*((Integer)iter2.next());
		else
		    supp_tuples+=((Integer)iter2.next());
	    }
	    else
		Find=false;	
	}
	this.suppresion=supp_tuples;
	return(supp_tuples);
    }
    public void print_levels(){
	
	System.out.print("Ta levels einai:");
	System.out.print(Namelevel);
	System.out.println();
    }
}
