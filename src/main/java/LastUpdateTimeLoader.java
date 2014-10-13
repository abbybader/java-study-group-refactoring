import java.util.Date;


public class LastUpdateTimeLoader implements Runnable {
    
    private final Project project;
    
    public LastUpdateTimeLoader(Project project) {
        this.project = project;
    }

    @Override
    public void run() {
        loadLastUpdateTime();
    }

    /**
     * Method stub for loading "last update time". Updates the Project.
     */
    private void loadLastUpdateTime() {
        System.out.println("Loading last update time for project " + project.getName());
        System.out.println("(Checking the database to see when the data was last refreshed)");
        // this might also be a lot of lines of code
        //...
        //...
        //...
        //...
        //...
        //...
        //... Look at database
        //...
        //...
        //...
        //...
        //... Clear previously cached data
        //...
        //... Cache fresh data
        project.setLastUpdateTime("Project update time calculated: " + new Date(System.currentTimeMillis()));
    }    

}
