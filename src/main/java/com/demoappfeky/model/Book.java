package com.demoappfeky.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    private String tittle;
    private String author;
    private String publisher;
    private String publicationDate;
    private String language;
    private int numberOfPages;
    private String format;
    private int isbn;
    private double shippingWeight;
    private double listPrice;
    private double ourPrice;
    private boolean active = true;

    @Column(columnDefinition = "text")
    private String description;
    private int inStockNumber;

    @Column(length = 255 )
    private String logo;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    public String getLogoImagePath(){
        if (logo == null || id == null)
            return "/image/logo-default.png";
        return "/image/" + this.id + "/" + this.logo;
    }

}
