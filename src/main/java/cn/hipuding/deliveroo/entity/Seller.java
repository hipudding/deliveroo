package cn.hipuding.deliveroo.entity;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "seller")
@Table(appliesTo = "seller")
public class Seller {
    @Id
    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "describe")
    private String describe;

    @Column(name = "image")
    private String image;

    @Column(name = "password")
    private String password;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
