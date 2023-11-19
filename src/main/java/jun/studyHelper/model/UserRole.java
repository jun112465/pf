package jun.studyHelper.model;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String label;

    private UserRole(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
