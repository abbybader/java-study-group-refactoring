import java.util.Date;


public class ProjectDetailsLoader implements Runnable {

    private final Project project;
    
    public ProjectDetailsLoader(Project project) {
        this.project = project;
    }
    
    @Override
    public void run() {
        loadProjectDetails();        
    }
    
    /**
     * Method stub for loading "project details". Updates the Project.
     */
    private void loadProjectDetails() {
        System.out.println("Loading project details for project " + project.getName());
        System.out.println("(Talking to database and updating our project-related objects.)");
        //this could be a lot of lines of code and involve collaborators, helpers, etc
        //...
        //...
        //...
        //...
        //...
        //...
        //...
        //... Talk to database,
        //... Build domain objects,
        //... Update stuff
        //...
        //...
        //...
        //...
        //...
        //... Clear previously cached data
        //...
        //... Cache fresh data
        project.setProjectDetails("Project details created: " + new Date(System.currentTimeMillis()));
    }

}
