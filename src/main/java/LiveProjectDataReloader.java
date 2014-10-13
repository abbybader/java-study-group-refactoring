import java.util.ArrayList;
import java.util.List;

/**
 * Reloader for a "live" project, where project data is expected to change
 * frequently.
 * 
 */
public class LiveProjectDataReloader extends ProjectDataReloader {
    
    private List<DataReloader> dataReloaders;

    protected LiveProjectDataReloader(Project project) {
        super(project);
        dataReloaders = new ArrayList<>();
        dataReloaders.add(new ProjectDetailsLoader(project));
        dataReloaders.add(new LastUpdateTimeLoader(project));
        dataReloaders.add(new LoginStatisticsLoader(project));
    }

    @Override
    protected void scheduleDataLoading() {

        for (DataReloader reloader : dataReloaders ) { 
            executor.scheduleAtFixedRate(reloader, 0, RELOAD_PERIOD, RELOAD_PERIOD_UNIT);
        }
    }

}
