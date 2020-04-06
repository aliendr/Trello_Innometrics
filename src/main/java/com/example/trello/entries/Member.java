package com.example.trello.entries;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    private String token;
    private String key;

    @ElementCollection
    @CollectionTable(name="listOfBoards")
    private List<String> boards;

    private Member(){}
    public Member(String token, String key, List<String> boards){
        this.token = token;
        this.key = key;
        this.boards = boards;
    }

    public void setBoards(List<String> boards) {
        this.boards = boards;
    }

    public List<String> getBoards(){
        return this.boards;
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
