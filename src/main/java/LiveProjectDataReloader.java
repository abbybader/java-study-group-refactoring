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

        // Define the tasks
        DataReloader projectDetailLoader = new ProjectDetailsLoader(project);
        DataReloader lastUpdateTimeLoader = new LastUpdateTimeLoader(project);
        DataReloader loginStatisticsLoader = new LoginStatisticsLoader(project);
        
        // Now schedule the tasks
        // executor is inherited from ProjectDataReloader        
        executor.scheduleAtFixedRate(projectDetailLoader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
        executor.scheduleAtFixedRate(lastUpdateTimeLoader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
        executor.scheduleAtFixedRate(loginStatisticsLoader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
    }

}
