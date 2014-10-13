import java.util.ArrayList;

/**
 * Reloader for a "live" project, where project data is expected to change
 * frequently.
 * 
 */
public class LiveProjectDataReloader extends ProjectDataReloader {

    protected LiveProjectDataReloader(Project project) {
        super(project);
        dataReloaders = new ArrayList<>();
        dataReloaders.add(new ProjectDetailsLoader(project));
        dataReloaders.add(new LastUpdateTimeLoader(project));
        dataReloaders.add(new LoginStatisticsLoader(project));
    }
}
