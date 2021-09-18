package fiesc.licenta.gsess;

public class Products {
    Long productId;
    String productName, ProductStore, ProductDescription, UnitsOnOrder,productPictureUrl;
    Float discount;

    public Products(Long productId, String productName, String productStore, String productDescription, String unitsOnOrder, String productPictureUrl, Float discount) {
        this.productId = productId;
        this.productName = productName;
        ProductStore = productStore;
        ProductDescription = productDescription;
        UnitsOnOrder = unitsOnOrder;
        this.productPictureUrl = productPictureUrl;
        this.discount = discount;
    }

    public Products() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductStore() {
        return ProductStore;
    }

    public void setProductStore(String productStore) {
        ProductStore = productStore;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getUnitsOnOrder() {
        return UnitsOnOrder;
    }

    public void setUnitsOnOrder(String unitsOnOrder) {
        UnitsOnOrder = unitsOnOrder;
    }

    public String getProductPictureUrl() {
        return productPictureUrl;
    }

    public void setProductPictureUrl(String productPictureUrl) {
        this.productPictureUrl = productPictureUrl;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }
}
