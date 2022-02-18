package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.chatting.ChatList;
import com.jpabook.jpashop.domain.chatting.ChatRoom;
import com.jpabook.jpashop.domain.item.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ChatroomRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ChatroomRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public void save(ChatRoom chatRoom) {
        if(chatRoom.getId() == null) {
            em.persist(chatRoom);
        } else {
            em.merge(chatRoom);
        }
    }


    public ChatRoom findOne(Long id) {
        return em.find(ChatRoom.class, id);
    }

    public List<ChatList> findByEmail(String email) {
        return em.createQuery("select c from ChatRoom c" +
                        "",ChatList.class)
                .getResultList();
    }
}
