
public class PeriodicReloadPolicy {
    
    private final int reloadCycleLength;
    
    public PeriodicReloadPolicy(int reloadCycleLength) {
        this.reloadCycleLength = reloadCycleLength;
    }
    
    public boolean shouldReload(int reloadsCount) {
        return (reloadsCount % reloadCycleLength == 0);
    }

}
