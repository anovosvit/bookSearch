package com.github.anovosvit.searchbook.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Book POJO (used GSON) and entity for room
@Entity(tableName = "book_table")
public class VolumeInfo {

    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false)
    public long id;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @Ignore
    @SerializedName("authors")
    @Expose
    private List<String> authors = null;

    @ColumnInfo(name = "author")
    @Expose(serialize = false)
    private String author;

    @ColumnInfo(name = "publisher")
    @SerializedName("publisher")
    @Expose
    private String publisher;

    @ColumnInfo(name = "publishedDate")
    @SerializedName("publishedDate")
    @Expose
    private String publishedDate;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @ColumnInfo(name = "category")
    @Expose(serialize = false)
    private String category;

    @Ignore
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;

    @ColumnInfo(name = "pageCount")
    @SerializedName("pageCount")
    @Expose
    private int pageCount;

    @ColumnInfo(name = "printType")
    @SerializedName("printType")
    @Expose
    private String printType;

    @Ignore
    @SerializedName("imageLinks")
    @Expose
    private ImageLinks imageLinks;

    @ColumnInfo(name = "imageLinks")
    @Expose(serialize = false)
    private String imageLink;

    @ColumnInfo(name = "averageRating")
    @SerializedName("averageRating")
    @Expose
    private float averageRating;

    @ColumnInfo(name = "previewLink")
    @SerializedName("previewLink")
    @Expose
    private String previewLink;

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getPrintType() {
        return printType;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public List<String> getCategories() {
        return categories;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public String getAuthorsString() {
        String result = String.valueOf(getAuthors());
        return result.substring(1, result.length() - 1);
    }

    public String getCategoriesString() {
        String result = String.valueOf(getCategories());
        return result.substring(1, result.length() - 1);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getAuthor() {
        String result;
        if (authors != null) {
            return getAuthorsString();
        } else {
            result = this.author;
        }
        return result;
    }

    public String getCategory() {
        if (categories != null) {
            return getCategoriesString();
        }
        return category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getImageLink() {
        String result;
        if (getImageLinks() != null) {
            result = getImageLinks().getSmallThumbnail().replace("http://", "https://");
        } else {
            result = this.imageLink;
        }
        return result;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

}