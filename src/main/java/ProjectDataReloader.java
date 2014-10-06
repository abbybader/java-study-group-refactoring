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

    private final Project project;

    private ScheduledExecutorService executorService;

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

        System.out.println("Starting project data reloading thread for project \"" + project.getName() + "\", type: "
            + project.getType());
        executorService = Executors.newScheduledThreadPool(4);
        for (DataReloader reloader : dataReloaders) {
            executorService.scheduleAtFixedRate(reloader, 0, reloader.getReloadPolicy().getReloadDelayInMillis(),
                TimeUnit.MILLISECONDS);
        }
    }

    public void stop() {
        System.out
            .println("Stopping project persistence reloading thread for project \"" + project.getName() + "\"...");
        executorService.shutdownNow();
    }

    public static void main(String[] args) {
        ProjectDataReloader reloader1 = getReloaderForType(new Project("project1", ProjectType.STATIC));

        ProjectDataReloader reloader2 = getReloaderForType(new Project("project2", ProjectType.LIVE));

        reloader1.start();
        try {
            Thread.sleep(1000);
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
