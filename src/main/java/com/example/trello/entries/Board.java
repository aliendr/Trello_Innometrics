package com.example.trello.entries;

import javax.persistence.*;
import java.util.List;

@Entity
public class Board {

    //@UniqueConstraint()
    @Id
    private String boardId;
    private String token;
    private String key;
    private String name;
    private String boardUrl;

    private boolean webhook;


    private Board(){};
    public Board(String boardUrl){
        this.boardUrl = boardUrl;
    }
    public Board(String name, String boardUrl, String boardId, String token, String key ){
        this.boardId=boardId;
        this.name=name;
        this.boardUrl = boardUrl;
        this.token= token;
        this.key=key;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoardUrl() {
        return boardUrl;
    }

    public void setBoardUrl(String boardUrl) {
        this.boardUrl = boardUrl;
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

    public boolean getWebhook() {
        return webhook;
    }

    public void setWebhook(boolean webhook) {
        this.webhook = webhook;
    }
}
