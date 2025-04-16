package nandes.pt;

public class Greeting {

    private final Long id;
    private final String context;

    public Greeting(Long id, String context) {
        this.id = id;
        this.context = context;
    }

    public Long getId() {
        return id;
    }

    public String getContext() {
        return context;
    }
}
