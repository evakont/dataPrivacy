package ptixiaki;

/**
 *
 * @author eva
 */
public class get {

    public static int end(){
        return GenerateGraph.end;
    }

    public static String level(int i){
        if(i == 0){
            return Suggestion.Level;
        }
        else if(i == 1){
            return Suggestion.Level1;
        }
        else if (i == 2){
            return Suggestion.Level2;
        }
        else {
            return Suggestion.Level3;
        }
    }

    public static void setlevel(String x , int i){
        if(i == 0){
            Suggestion.Level = x;
        }
        else if(i == 1){
            Suggestion.Level1 = x;
        }
        else if (i == 2){
            Suggestion.Level2 = x;
        }
        else {
            Suggestion.Level3 = x;
        }
    }
    public static String suppTupp(int i){
        if(i == 1){
            return Suggestion.suppTup1;
        }
        else if(i == 2){
            return Suggestion.suppTup2;
        }
        else {
            return Suggestion.suppTup3;
        }
    }
    public static int vId(){
        return Suggestion.idCnt;
    }

    public static String Nodes(int k){
        return GenerateGraph.StrName[k];

    }
    public static int sid(int i){
      if(i == 1){
            return Suggestion.MIN_ID;
        }
        else if(i == 2){
            return Suggestion.MIN_ID_R2;
        }
        else {
            return Suggestion.MIN_ID_R3;
        }
    }

}
