package com.dh.ctd.ecommerce_api.model;

import javax.persistence.*;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String image;

    private Integer minimumAge;
    private Integer maximumPlayersNumber;
    private Integer minimumPlayersNumber;
    private String language;
    private Integer playingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
    }
    public Product(String title, Double price, String description, String image, Integer minimumAge, Integer maximumPlayersNumber, Integer minimumPlayersNumber, String language, Integer playingTime, Category category) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.minimumAge = minimumAge;
        this.maximumPlayersNumber = maximumPlayersNumber;
        this.minimumPlayersNumber = minimumPlayersNumber;
        this.language = language;
        this.playingTime = playingTime;
        this.category = category;
    }
    public Product(Integer id, String title, Double price, String description, String image, Integer minimumAge, Integer maximumPlayersNumber, Integer minimumPlayersNumber, String language, Integer playingTime, Category category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.minimumAge = minimumAge;
        this.maximumPlayersNumber = maximumPlayersNumber;
        this.minimumPlayersNumber = minimumPlayersNumber;
        this.language = language;
        this.playingTime = playingTime;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Integer getMinimumAge() {
        return minimumAge;
    }
    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }
    public Integer getMinimumPlayersNumber() {
        return minimumPlayersNumber;
    }
    public void setMinimumPlayersNumber(Integer minimumPlayersNumber) {
        this.minimumPlayersNumber = minimumPlayersNumber;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public Integer getPlayingTime() {
        return playingTime;
    }
    public void setPlayingTime(Integer playingTime) {
        this.playingTime = playingTime;
    }
    public Integer getMaximumPlayersNumber() {
        return maximumPlayersNumber;
    }
    public void setMaximumPlayersNumber(Integer maximumPlayersNumber) {
        this.maximumPlayersNumber = maximumPlayersNumber;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", category=" + category +
                '}';
    }
}
