package girod.anthony.acerestaurant.Model;

public class ItemInfo {

    public String productName;
    public String price;
    public int imagePath;

//Mutators and accessors for food item information (name, price, associated image)
    public ItemInfo(String productName, String price, int imagePath) {
        this.productName = productName;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}
