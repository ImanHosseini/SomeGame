import org.jbox2d.dynamics.Body;

/**
 * Created by ImanH on 3/24/2019.
 * Seyed Iman Hosseini Zavaraki
 * Github @ https://github.com/ImanHosseini
 * Wordpress @ https://imanhosseini.wordpress.com/
 */
public class GBody {
    Body body;
    int TAG;
    GBody(Body body,int TAG){
        this.body=body;
        this.TAG = TAG;
    }
}
