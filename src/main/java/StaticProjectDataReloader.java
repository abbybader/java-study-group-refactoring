/**
 * A reloader for projects that are no longer "live."  Project details and data
 * are not expected to change.
 *
 */
public class StaticProjectDataReloader extends ProjectDataReloader {

    protected StaticProjectDataReloader(Project project) {
        super(project);
    }

    /**
     * A "static" project's data doesn't change anymore, so we only need to refresh
     * "login statistics."  The executor will periodically (about once every 
     * RELOAD_PERIOD) execute the runnable defined below, which will invoke the login-
     * related loading method.
     */
    @Override
    protected void scheduleDataLoading() {

        Runnable loginStatisticsLoader = new LoginStatisticsLoader(project);
        
        executor.scheduleAtFixedRate(loginStatisticsLoader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
    }

}
