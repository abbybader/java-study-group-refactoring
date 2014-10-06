
public abstract class DataReloader implements Runnable {
    
    protected final Project project;
    private final PeriodicReloadPolicy reloadPolicy;
    
    protected DataReloader(Project project, PeriodicReloadPolicy reloadPolicy) {
        this.project = project;
        this.reloadPolicy = reloadPolicy;
    }
    
    protected abstract void loadData();
    
    @Override
    public void run() {
        loadData();
    }
    
    public PeriodicReloadPolicy getReloadPolicy() {
        return reloadPolicy;
    }

}
