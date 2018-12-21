import static spark.Spark.get;
import static spark.Spark.port;

public class Application {
    public static void main(String[] args) {
        /* INITIAL SETUP */
        port(8080);
        get("/", (request, response) -> "Hello world!");
    }
}
