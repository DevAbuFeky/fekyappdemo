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
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    private String branchName;
    private String publicationDate;
    private boolean active;
    @Column(columnDefinition = "text")
    private String description;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

//    @Transient
//    public String getLogoImagePath(){
//        if (logo == null || id == null)
//            return "/image/logo-default.png";
//        return "/image/" + this.id + "/" + this.logo;
//    }

}
