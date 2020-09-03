package User;

public class JobPost {
    private String position;
    private String postDate;
    private String companyName;
    private String requirement;

    public JobPost(String postDate, String position, String companyName){
        this.postDate=postDate;
        this.position=position;
        this.companyName=companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

}
