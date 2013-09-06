package ptixiaki;
/**
 * 
 */

/**
 *@author aliki
 *@version Nov 30, 2009
 */
public class desc_anc_nodes {

    private int id;
    private int height;
    private int supp_tuples;
    
    public desc_anc_nodes(int id1,int height1, int supp_tuples1){
	
	id=id1;
	height=height1;
	supp_tuples=supp_tuples1;
	
    }
    public int get_id(){
	return id;
    }
    public int get_height(){
	return height;
    }
    public int get_supp_tuples(){
	return supp_tuples;
    }
    
}
