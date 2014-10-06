
/**
 * A reloader for projects that are no longer "live."  Project details and data
 * are not expected to change, so only login statistics are required to be kept
 * fresh.
 *
 */
public class StaticProjectDataReloader extends ProjectDataReloader {
    
    private final LoginStatusReloader loginReloader;

    protected StaticProjectDataReloader(Project project) {
        super(project);
        loginReloader = new LoginStatusReloader(project, new PeriodicReloadPolicy(1));
    }

    @Override
    protected void reloadProjectData() {
        
        if (loginReloader.getReloadPolicy().shouldReload(reloadsCounter)) {
            new Thread( loginReloader).start();
        }
        
    }

}
