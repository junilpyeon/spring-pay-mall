package com.jpabook.jpashop.service;


import com.jpabook.jpashop.domain.member.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.jpabook.jpashop.repository.UserJpaRepo;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserJpaRepo UserJpaRepo;

   
   public User getUserList(String uid, String gender, String clothes_size) {
	   return UserJpaRepo.findByIdAndList(uid, gender, clothes_size);
   }

   public User getUserInfo(String id, String uid) {
	   if(uid!=null) {
		   id = null;
	   }
	   return UserJpaRepo.findByIdAndInfo(id, uid);
   }


}
