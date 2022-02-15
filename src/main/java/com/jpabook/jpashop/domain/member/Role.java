package com.jpabook.jpashop.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

   GUSET("ROLE_GUEST", "�մ�"),
   USER("ROLE_USER", "�Ϲ� �����");

   private final String key;
   private final String title;
}
