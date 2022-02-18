package com.jpabook.jpashop.domain.chatting;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public class ChatRoom {

    @Id
    @GeneratedValue
    @Column(name = "chatroom_id")
    private Long id;
    private int pr_id;
    private String sellerId;
    private String buyerId;
    private String fileName;
    private Timestamp createdDate;
    private String sellerName;
    private String buyerName;
    //not in DB
    private String content;
    private String sendTime;
    private String senderName;
    private String pr_title;

    public ChatRoom(Long id, int pr_id, String sellerId, String buyerId, String fileName,
                    Timestamp createdDate, String sellerName, String buyerName) {
        super();
        this.id = id;
        this.pr_id = pr_id;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.fileName = fileName;
        this.createdDate = createdDate;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
    }

    public ChatRoom() {
        // TODO Auto-generated constructor stub
    }

    public ChatRoom(String content, String senderName, String sendTime) {
        this.content = content;
        this.senderName = senderName;
        this.sendTime = sendTime;
    }

}

