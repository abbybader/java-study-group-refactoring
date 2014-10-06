
import java.util.Date;


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
     * Reload period is 30 seconds
     */
    private static final long RELOAD_PERIOD = 30000;
    
    /**
     * Sleep by small portions of 1 second
     */
    private static final long SLEEPING_PERIOD = 1000;

    
    private boolean stopped = false;

    /**
     * The per-project reloading thread
     */
    private Thread thread;
    
    protected final Project project;
    
    /**
     * Counter for how many times the reloadProjectData() method has been called,
     * used for reloading data types more or less often
     */
    protected int reloadsCounter = 0;
    
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
     * The reload method, called once per reload period;
     */
    protected abstract void reloadProjectData();
    
    public void start() {
        // inline implementation of runnable for reloader thread
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Starting project data reloading thread for project \"" + project.getName() + "\", type: "
                    + project.getType());

                while (!stopped) {

                    // remember the start time
                    long s = System.currentTimeMillis();

                    try {

                        // call a project-type-specific reloading procedure that reloads some of the project data from
                        // persistence
                        System.out.println("Starting reloading for project " + project.getName());
                        reloadProjectData();
                        System.out.println("Done reloading for project " + project.getName());
                        project.prettyPrint();
                        System.out.println();

                        // check the termination flag
                        synchronized (ProjectDataReloader.this) {
                            if (stopped) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Could not load project data for ptoject " + project.getName() + " : "
                            + e.getMessage());
                    }

                    // calculate the time taken for the reload
                    long timeUsedForLastReload = System.currentTimeMillis() - s;

                    // sleep until next fetch
                    if (timeUsedForLastReload < RELOAD_PERIOD) {
                        long timeLeftToSleep = RELOAD_PERIOD - timeUsedForLastReload;

                        while (timeLeftToSleep > 0) {

                            // check the termination flag
                            synchronized (ProjectDataReloader.this) {
                                if (stopped) {
                                    break;
                                }
                            }

                            try {
                                // sleep for SLEEPING_PERIOD
                                Thread.sleep(SLEEPING_PERIOD);
                            } catch (InterruptedException ex) {
                                continue;
                            }

                            // dec the timeLeft
                            timeLeftToSleep -= SLEEPING_PERIOD;
                        }
                    }
                    reloadsCounter++;
                }

                System.out.println("Stopped project persistence reloading thread for project \"" + project.getName() + "\"");
            }
        });

        thread.start();
    }
    
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
        
        stopped = true;
    }
    
    public static void main(String[] args) {
        ProjectDataReloader reloader1 = getReloaderForType(new Project("project1", ProjectType.STATIC));
        
        ProjectDataReloader reloader2 = getReloaderForType(new Project("project2", ProjectType.LIVE));
        
        reloader1.start();
        try {
            Thread.sleep(SLEEPING_PERIOD);
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
