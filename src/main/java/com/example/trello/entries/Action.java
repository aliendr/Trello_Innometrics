package com.example.trello.entries;

import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Action {

    @Id
    private String actionId;
    private String boardId;
    private String idMemberCreator;
    private String type;
    private String date;
    private String memberCreatorUsername;
    //private String data;




    private Action(){};
    public Action(String id, String boardId, String idMemberCreator, String type, String date, String memberCreatorUsername) {
        this.actionId = id;
        this.boardId = boardId;
        this.idMemberCreator = idMemberCreator;
        this.type = type;
        this.date = date;
        this.memberCreatorUsername = memberCreatorUsername;
        //this.data = data;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

//    public void setData(String data) {
//        this.data = data;
//    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public void setIdMemberCreator(String idMemberCreator) {
        this.idMemberCreator = idMemberCreator;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActionId(){
        return this.actionId;
    }

    public String getIdMemberCreator(){
        return this.idMemberCreator;
    }

    public String getType(){
        return this.type;
    }

    public String getDate(){
        return this.date;
    }

//     public String getData(){
//        return data;
//    }


    @Override
    public String toString() {
        return "Action{" +
                "actionId='" + actionId + '\'' +
                ", boardId='" + boardId + '\'' +
                ", idMemberCreator='" + idMemberCreator + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", memberCreatorUsername='" + memberCreatorUsername + '\'' +
                '}';
    }

    public String getMemberCreatorUsername() {
        return memberCreatorUsername;
    }

    public void setMemberCreatorUsername(String memberCreatorUsername) {
        this.memberCreatorUsername = memberCreatorUsername;
    }
}