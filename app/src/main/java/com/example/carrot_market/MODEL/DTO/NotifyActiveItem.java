package com.example.carrot_market.MODEL.DTO;

public class NotifyActiveItem {


//    $favorite_obj['division_type']='coment';
//    $favorite_obj['key']=$row['num'];
//    $favorite_obj['product_key']=$row['product_key'];
//    $favorite_obj['id']=$row['coment_id'];
//    $favorite_obj['image_path']=$row['product_image_path'];
//    $favorite_obj['description']=$row['title'];


    boolean delete_check=true;

    String division_type;
    String key;
    String product_key;
    String id;
    String image_path;
    String description;

    public boolean isDelete_check() {
        return delete_check;
    }

    public void setDelete_check(boolean delete_check) {
        this.delete_check = delete_check;
    }


    public String getDivision_type() {
        return division_type;
    }

    public void setDivision_type(String division_type) {
        this.division_type = division_type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    String time_stamp;


}
