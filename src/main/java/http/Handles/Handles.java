package http.Handles;

/**
 * Class for contain handles objects
 */
public class Handles {

    private Health Health;
    private QueryModel QueryModel;
    private GenerateTaskCode generateTaskCode;

    public Handles() {
        this.Health = new Health();
        this.QueryModel = new QueryModel();
        this.generateTaskCode = new GenerateTaskCode();
    }

    public Health getHealth() { return Health; }
    public QueryModel getQueryModel() { return QueryModel; }
    public GenerateTaskCode getGenerateTaskCode() { return generateTaskCode; }
}