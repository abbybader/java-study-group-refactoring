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

        // executor is inherited from ProjectDataReloader
        // Executor interface expects a Runnable, which is a Java thread-related interface
        executor.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                // load project details using the method from ProjectDataReloader
                loadProjectDetails();
            }
            // the executor needs to invoke this runnable immediately, and then every 15 seconds.
        }, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);

        //pretty similar...
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                loadLastUpdateTime();

            }
        }, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                loadLoginStatistics();

            }
        }, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
    }

}
