
public class PeriodicReloadPolicy {
    
    private final long reloadDelayInMillis;
    
    public PeriodicReloadPolicy(long reloadDelayInMillis) {
        this.reloadDelayInMillis = reloadDelayInMillis;
    }
    
    public long getReloadDelayInMillis() {
        return reloadDelayInMillis;
    }

}
