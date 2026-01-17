package http.Handles;
/**
 * Class for contain handles objects
 */
public class Handles {

    private Health Health;
    private AskModel askModel;

    public Handles() {
        this.Health = new Health();
        this.askModel = new AskModel();
    }

    public Health getHealth() { return Health; }
    public AskModel getAskModel() { return askModel; }
}