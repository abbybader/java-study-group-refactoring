
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
        loginReloader = new LoginStatusReloader(project, new PeriodicReloadPolicy(2));
        lastUpdateReloader = new LastUpdateTimeReloader(project, new PeriodicReloadPolicy(1));
        projectDetailsReloader = new ProjectDetailsReloader(project, new PeriodicReloadPolicy(500));
    }

    @Override
    protected void reloadProjectData() {
        if (projectDetailsReloader.getReloadPolicy().shouldReload(reloadsCounter)) {
            new Thread(projectDetailsReloader).start();
        }
        
        if (lastUpdateReloader.getReloadPolicy().shouldReload(reloadsCounter)) {
            new Thread(lastUpdateReloader).start();
        }
        
        if (loginReloader.getReloadPolicy().shouldReload(reloadsCounter)) {
            new Thread(loginReloader).start();
        }
    }

}
