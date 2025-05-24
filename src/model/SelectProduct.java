package model;

public class SelectProduct {
    private String id, name, status, quantityType;

    public SelectProduct(String id, String name, String status, String quantityType) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.quantityType = quantityType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getQuantityType() {
        return quantityType;
    }

}
