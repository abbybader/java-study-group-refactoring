
public class Project {
    
    private final String name;
    private final ProjectType type;
    private String projectDetails = "neverUpdated";
    private String lastUpdateTime = "neverUpdated";
    private String loginStatistics = "neverUpdated";
    
    public Project(String name, ProjectType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return  name;
    }
    
    public ProjectType getType() {
        return type;
    }

    public String getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String string) {
        this.lastUpdateTime = string;
    }

    public String getLoginStatistics() {
        return loginStatistics;
    }

    public void setLoginStatistics(String loginStatistics) {
        this.loginStatistics = loginStatistics;
    }

    public void prettyPrint() {
        System.out.println("Project: " + getName() + "; type: " + getType());
        System.out.println(getProjectDetails());
        System.out.println(getLastUpdateTime());
        System.out.println(getLoginStatistics());
        
        
    }
    
    

}
