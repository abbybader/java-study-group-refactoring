
/**
 * Reloader for a "live" project, where project data is expected to change
 * frequently.  Periodic reloads are necessary for time of last update, 
 * project details, and login statistics.
 * 
 */
public class LiveProjectDataReloader extends ProjectDataReloader {

    protected LiveProjectDataReloader(Project project) {
        super(project);
    }

    @Override
    protected void reloadProjectData() {
        // load details every other reload attempt
        if (reloadsCounter % 2 == 0) {
            loadProjectDetails();
        }
        
        //do this often
        loadLastUpdateTime();
        
        // don't need this very often..
        // load login statistics every five hundred reload attempts
        if (reloadsCounter % 500 == 0) {
            loadLoginStatistics();
        }
    }

}
