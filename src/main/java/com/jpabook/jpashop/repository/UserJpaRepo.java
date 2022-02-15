package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    Optional<User> findByUid(String email);

    Optional<User> findByUidAndProvider(String uid, String provider);

    @Query("SELECT m FROM User m WHERE (:uid is null or m.uid = :uid) and (:gender is null or m.gender = :gender) and (:clothes_size is null or m.clothes_size = :clothes_size)")
	User findByIdAndList(String uid, String gender, String clothes_size);
    
    @Query("SELECT m FROM User m WHERE (:id is null or m.id = :id) and (:uid is null or m.uid = :uid)")
	User findByIdAndInfo(String id, String uid);

}

