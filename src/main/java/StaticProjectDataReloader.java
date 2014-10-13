import java.util.ArrayList;
import java.util.List;

/**
 * A reloader for projects that are no longer "live."  Project details and data
 * are not expected to change.
 *
 */
public class StaticProjectDataReloader extends ProjectDataReloader {
    
    protected List<DataReloader> dataReloaders;

    protected StaticProjectDataReloader(Project project) {
        super(project);
        dataReloaders = new ArrayList<>();
        dataReloaders.add(new LoginStatisticsLoader(project));
    }

 
    @Override
    protected void scheduleDataLoading() {
        for (DataReloader reloader : dataReloaders) {
            executor.scheduleAtFixedRate(reloader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
        }
    }

}
