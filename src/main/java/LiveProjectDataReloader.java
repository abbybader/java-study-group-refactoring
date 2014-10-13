/**
 * Reloader for a "live" project, where project data is expected to change
 * frequently.
 * 
 */
public class LiveProjectDataReloader extends ProjectDataReloader {

    protected LiveProjectDataReloader(Project project) {
        super(project);
    }

    /**
     * A "live" project needs to periodically reload "project details," "last update
     * time", and "login statistics."  The ScheduledExecutorService "executor" will
     * periodically (about once every RELOAD_PERIOD) execute the runnables defined
     * below, which will invoke those loading methods.
     */
    @Override
    protected void scheduleDataLoading() {

        // Executor interface expects a Runnable, which is a Java thread-related interface
        Runnable projectDetailLoader = new Runnable() {

            @Override
            public void run() {
                // load project details using the method from ProjectDataReloader
                loadProjectDetails();
            }
        };
        
        Runnable lastUpdateTimeLoader = new Runnable() {
            @Override
            public void run() {
                loadLastUpdateTime();

            }
        };

        Runnable loginStatisticsLoader = new Runnable() {
            @Override
            public void run() {
                loadLoginStatistics();

            }
        };
        // executor is inherited from ProjectDataReloader
        // the executor needs to invoke this runnable immediately, and then every 15 seconds.
        executor.scheduleAtFixedRate(projectDetailLoader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);

        // pretty similar...
        
        executor.scheduleAtFixedRate(lastUpdateTimeLoader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
        executor.scheduleAtFixedRate(loginStatisticsLoader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
    }

}
