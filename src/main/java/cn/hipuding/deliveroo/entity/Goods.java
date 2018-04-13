package cn.hipuding.deliveroo.entity;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "goods")
@Table(appliesTo = "goods")
public class Goods {
    @Id
    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "price")
    private Double price;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "info")
    private String describe;

    @Column(name = "image")
    private String image;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
