package http.Handles;
/**
 * Class for contain handles objects
 */
public class Handles {

    private Health Health;

    public Handles() {
        this.Health = new Health();
    }

    public Health getHealth() { return Health; }
}