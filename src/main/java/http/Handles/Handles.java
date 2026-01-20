package http.Handles;

/**
 * Class for contain handles objects
 */
public class Handles {

    private Health Health;
    private QueryModel QueryModel;

    public Handles() {
        this.Health = new Health();
        this.QueryModel = new QueryModel();
    }

    public Health getHealth() { return Health; }
    public QueryModel getQueryModel() { return QueryModel; }
}