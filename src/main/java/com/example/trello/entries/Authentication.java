package com.example.trello.entries;



import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authentication {
    @Id
    private String memberId;
    private String token;
    private String key;

    Authentication(){}

    public Authentication(String token, String key){
        this.key=key;
        this.token=token;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
