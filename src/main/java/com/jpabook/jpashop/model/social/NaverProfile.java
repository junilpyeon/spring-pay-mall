package com.jpabook.jpashop.model.social;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverProfile {
    private Long id;
    private Properties properties;

    @Getter
    @Setter
    @ToString
    private static class Properties {
        private String nickname;
        private String email;
        private String name;
    }
}
