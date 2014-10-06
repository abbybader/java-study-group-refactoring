import java.util.Date;


public class LoginStatusReloader extends DataReloader {

    protected LoginStatusReloader(Project project) {
        super(project);
    }

    @Override
    protected void loadData() {
        System.out.println("Loading login statistics for project " + project.getName());
        System.out.println("(Talking to our login server via http request)");
        // This might involve other collaborators/helpers to make the http request and 
        // handle the response.
        //...
        //...
        //...
        //...
        //...
        //... Talk to login server
        //...
        //...
        //...
        //... Clear previously cached data
        //...
        //... Cache fresh data
        project.setLoginStatistics("Login statistics looked up: " + new Date(System.currentTimeMillis()));
    }

}
