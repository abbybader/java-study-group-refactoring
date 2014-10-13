import java.util.ArrayList;

/**
 * A reloader for projects that are no longer "live."  Project details and data
 * are not expected to change.
 *
 */
public class StaticProjectDataReloader extends ProjectDataReloader {

    protected StaticProjectDataReloader(Project project) {
        super(project);
        dataReloaders = new ArrayList<>();
        dataReloaders.add(new LoginStatisticsLoader(project));
    }

}
