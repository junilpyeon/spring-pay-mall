package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.item.Top;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Brand {

    @Id @GeneratedValue
    @Column(name = "brand_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "brand_item",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "brand_item",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "top_id"))
    private List<Top> tops = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Brand parent;

    @OneToMany(mappedBy = "parent")
    private List<Brand> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Brand child) {
        this.child.add(child);
        child.setParent(this);
    }

}
