
public abstract class DataReloader implements Runnable {
    
    protected final Project project;
    
    protected DataReloader(Project project) {
        this.project = project;
    }
    
    protected abstract void loadData();
    
    @Override
    public void run() {
        loadData();
    }

}
