package model;

public class SelectSupplier {
    private String id, name, mobile, email, companyId, status;

    public SelectSupplier(String id, String name, String mobile, String email, String companyId, String status) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.companyId = companyId;
        this.status = status;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getStatus() {
        return status;
    }
}
