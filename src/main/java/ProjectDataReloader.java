import java.util.ArrayList;
import java.util.List;
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
public class ProjectDataReloader {

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

    private final List<DataReloader> dataReloaders;

    public static ProjectDataReloader getReloaderForType(Project project) {

        List<DataReloader> reloaders = new ArrayList<>();
        ProjectType type = project.getType();
        if (type.equals(ProjectType.STATIC)) {
            reloaders.add(new LastUpdateTimeReloader(project, new PeriodicReloadPolicy(30000)));
        } else if (type.equals(ProjectType.LIVE)) {
            reloaders.add(new LoginStatusReloader(project, new PeriodicReloadPolicy(60000)));
            reloaders.add(new LastUpdateTimeReloader(project, new PeriodicReloadPolicy(30000)));
            reloaders.add(new ProjectDetailsReloader(project, new PeriodicReloadPolicy(15000000)));
        }
        return new ProjectDataReloader(project, reloaders);
    }

    protected ProjectDataReloader(Project project, List<DataReloader> dataReloaders) {
        this.project = project;
        this.dataReloaders = dataReloaders;
    }


    public void start() {
        // inline implementation of runnable for reloader thread
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Starting project data reloading thread for project \"" + project.getName()
                    + "\", type: " + project.getType());

                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
                for (DataReloader reloader : dataReloaders) {
                    executorService.scheduleAtFixedRate(reloader, 0, reloader.getReloadPolicy()
                        .getReloadDelayInMillis(), TimeUnit.MILLISECONDS);
                }

                while (!stopped) {

                    // check the termination flag
                    synchronized (ProjectDataReloader.this) {
                        if (stopped) {
                            executorService.shutdownNow();
                            break;
                        }
                    }

                    try {
                        // sleep for SLEEPING_PERIOD
                        Thread.sleep(SLEEPING_PERIOD);
                    } catch (InterruptedException ex) {
                        stopped=true;
                    }

                }

                System.out.println("Stopped project persistence reloading thread for project \"" + project.getName()
                    + "\"");

            }
        });

        thread.start();
    }

    public void stop() {

        System.out
            .println("Stopping project persistence reloading thread for project \"" + project.getName() + "\"...");

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

        reloader1.stop();
        reloader2.stop();
    }

}
