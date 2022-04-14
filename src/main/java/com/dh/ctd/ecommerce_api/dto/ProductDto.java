package com.dh.ctd.ecommerce_api.dto;

public class ProductDto {

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

    private Integer category_id;

    public ProductDto() {
    }
    public ProductDto(String title, Double price, String description, String image, Integer minimumAge, Integer maximumPlayersNumber, Integer minimumPlayersNumber, String language, Integer playingTime, Integer category_id) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.minimumAge = minimumAge;
        this.maximumPlayersNumber = maximumPlayersNumber;
        this.minimumPlayersNumber = minimumPlayersNumber;
        this.language = language;
        this.playingTime = playingTime;
        this.category_id = category_id;
    }
    public ProductDto(Integer id, String title, Double price, String description, String image, Integer minimumAge, Integer maximumPlayersNumber, Integer minimumPlayersNumber, String language, Integer playingTime, Integer category_id) {
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
        this.category_id = category_id;
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
    public Integer getCategory_id() {
        return category_id;
    }
    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
    public Integer getMinimumAge() {
        return minimumAge;
    }
    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }
    public Integer getMaximumPlayersNumber() {
        return maximumPlayersNumber;
    }
    public void setMaximumPlayersNumber(Integer maximumPlayersNumber) {
        this.maximumPlayersNumber = maximumPlayersNumber;
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
}
