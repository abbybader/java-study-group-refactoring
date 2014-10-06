
/**
 * Reloader for a "live" project, where project data is expected to change
 * frequently.  Periodic reloads are necessary for time of last update, 
 * project details, and login statistics.
 * 
 */
public class LiveProjectDataReloader extends ProjectDataReloader {
    
    private final LoginStatusReloader loginReloader;
    private final LastUpdateTimeReloader lastUpdateReloader;
    private final ProjectDetailsReloader projectDetailsReloader;

    protected LiveProjectDataReloader(Project project) {
        super(project);
        loginReloader = new LoginStatusReloader(project);
        lastUpdateReloader = new LastUpdateTimeReloader(project);
        projectDetailsReloader = new ProjectDetailsReloader(project);
    }

    @Override
    protected void reloadProjectData() {
        // load details every other reload attempt
        if (reloadsCounter % 2 == 0) {
            new Thread(projectDetailsReloader).start();
        }
        
        //do this often
        new Thread(lastUpdateReloader).start();
        
        // don't need this very often..
        // load login statistics every five hundred reload attempts
        if (reloadsCounter % 500 == 0) {
            new Thread(loginReloader).start();
        }
    }

}
