package com.example.trello.entries;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    private String id;
    private String username;

    @ElementCollection
    @CollectionTable(name="listOfBoards")
    private List<String> boards;

    private Member(){}
    public Member(String id, String username, List<String> boards){
        this.id = id;
        this.username = username;
        this.boards = boards;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBoards(List<String> boards) {
        this.boards = boards;
    }

    public String getId(){
        return this.id;
    }

    public List<String> getBoards(){
        return this.boards;
    }

    public String getUsername(){
        return this.username;
    }
}
