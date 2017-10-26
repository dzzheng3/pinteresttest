package interview.legacy.pinterest.legacyinterview;



public class SharedTransitionHelper {

    public static int depth = 0;
// TODO remove this
    public static String getSharedTransitionName(){
        return "sharedTransition"+ String.valueOf(depth);
    }
}
