package com.jpabook.jpashop.domain.member;

import lombok.RequiredArgsConstructor;

/**
 * �Ҽ� �α��� ���� enum
 */
@RequiredArgsConstructor
public enum Social {
    KAKAO("īī��"),
    NAVER("���̹�"),
    GOOGLE("����"),
    FACEBOOK("���̽���"),
    GITHUB("�����");

    private final String title;
}
