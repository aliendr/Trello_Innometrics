package com.example.trello.entries;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {


    private String boardId;
    private String token;
    private String key;
    private String name;
    @Id
    private String url;
    @ElementCollection
    @CollectionTable(name="listOfActions")
    private List<String> listOfActions;


    private Board(){};
    public Board(String url){
        this.url=url;
    }
    public Board(String name, String url, String boardId, String token, String key ){
        this.boardId=boardId;
        this.name=name;
        this.url = url;
        this.token= token;
        this.key=key;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<String> getListOfActions() {
        return listOfActions;
    }

    public void setListOfActions(List<String> listOfActions) {
        this.listOfActions = listOfActions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
