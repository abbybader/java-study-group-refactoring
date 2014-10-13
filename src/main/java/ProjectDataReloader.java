
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * A class concerned with reloading a server-side cache of data, to avoid unnecessary 
 * and expensive calls outside the system (i.e. to persistence, to login server, etc).  
 * This is NOT good code, but it's realistic code... in that I found it in one of my 
 * systems.  We'll refactor it and make it better.
 * 
 * @author Abby B. Bullock
 * 
 */
public abstract class ProjectDataReloader {
    
    /**
     * A fancy Java interface that handles the scheduling of tasks that need to
     * happen periodically.
     */
    protected ScheduledExecutorService executor;
    
    /*
     * How often should the executor service reload our data?
     */
    protected static final int RELOAD_PERIOD = 15;
    protected static final TimeUnit RELOAD_PERIOD_UNIT = TimeUnit.SECONDS;
    
    protected final Project project;
    
    /*
     * Factory method to create a ProjectDataReloader of the appropriate "type"
     * for a Project.
     */
    public static ProjectDataReloader getReloaderForType(Project project) {
        
        ProjectType type = project.getType();
        if (type.equals(ProjectType.STATIC)) {
            return new StaticProjectDataReloader(project);
        } else if (type.equals(ProjectType.LIVE)) {
            return new LiveProjectDataReloader(project);
        }
        return null;
    }
    
    protected ProjectDataReloader(Project project) {
        this.project = project;
    }
    
    /**
     * Method to schedule the reloading of project data
     */
    protected abstract void scheduleDataLoading();
    
    public void start() {
        
        System.out.println("Starting project data reloading thread for project \"" + project.getName() + "\", type: "
            + project.getType());
        executor = Executors.newScheduledThreadPool(4);
        scheduleDataLoading();
        executor.scheduleAtFixedRate(new Runnable() {
            
            @Override
            public void run() {
                //periodically print our project
                project.prettyPrint();
            }
        }, 1, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
    }
    
    /**
     * Method stub for loading "project details". Updates the Project.
     */
    protected void loadProjectDetails() {
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

    /**
     * Method stub for loading "last update time". Updates the Project.
     */
    protected void loadLastUpdateTime() {
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

    /**
     * Method stub for loading "login statistics". Updates the Project.
     */
    protected void loadLoginStatistics() {
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
    
    public void stop() {
        
        System.out.println("Stopping project persistence reloading thread for project \"" + project.getName() + "\"...");
        
        executor.shutdown();
        
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            // we're shutting down anyway
        }
        
        executor.shutdownNow();
    }
    
    public static void main(String[] args) {
        ProjectDataReloader reloader1 = getReloaderForType(new Project("project1", ProjectType.STATIC));
        
        ProjectDataReloader reloader2 = getReloaderForType(new Project("project2", ProjectType.LIVE));
        
        reloader1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            
        }
        reloader2.start();
        
        try {
            Thread.sleep(180000);
        } catch (InterruptedException e) {
        }
        
        reloader1.stop(); reloader2.stop();
    }

}
