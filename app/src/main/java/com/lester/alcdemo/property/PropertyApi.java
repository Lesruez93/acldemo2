package com.lester.alcdemo.property;


public class PropertyApi {

  private String item;
  private String price;
  private String quantity;
  private String imageUrl;
  private String storeId;
  private String desc;
  private String image;




  public PropertyApi(String item, String price, String desc, String imageUrl, String storeId, String image) {
    this.storeId = storeId;
    this.desc =storeId;
    this.imageUrl = imageUrl;
    this.price = price;
    this.price = desc;
    this.item = item;

    this.image = image;
  }

  public PropertyApi() {

  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}