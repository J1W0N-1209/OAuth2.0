package com.project.oauth2.domain.user.controller.dto;

import com.project.oauth2.domain.user.entity.Role;
import com.project.oauth2.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String , Object> attributes; // ex) { sub=1231344534523565757, name=홍길동, given_name=길동, family_name=홍, picture=https://xxx, email=xxx@gmail.com, email_verified=true, locale=ko}
    private String nameAttributeKey; // ex) { google="sub", kakao="id", naver="response" }
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes,
                           String nameAttributeKey,String name,
                           String email,String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,String userNameAttributeName,Map<String,Object> attributes) {
        return ofGoogle(userNameAttributeName,attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
