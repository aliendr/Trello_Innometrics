package com.example.trello.entries;

import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Action {

    @Id
    private String actionId;
    private String boardId;
    private String idMemberCreator;

    private String type;

    private String date;

    @Lob
    private String memberCreatorUsername;


    @Lob
    private String boardName;
    private String cardId;
    @Lob
    private String cardName;

    private String cardClosed;
    @Lob
    private String listName;
    private String listId;
    private String checkItemId;
    @Lob
    private String checkItemName;
    private String checkItemState;
    private String checkListId;
    @Lob
    private String checkListName;
    private String listAfterId;
    @Lob
    private String listAfterName;
    private String listBeforeId;
    @Lob
    private String listBeforeName;

    private String attachmentId;
    @Lob
    private String attachmentName;

    private String memberAddedDeletedId;
    @Lob
    private String memberAddedDeletedName;
    @Lob
    private String old;
    @Lob
    private String pluginId;
    @Lob
    private String pluginName;
    @Lob
    private String pluginUrl;



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

    public String getAttachmentId() {
        return attachmentId;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginUrl() {
        return pluginUrl;
    }

    public void setPluginUrl(String pluginUrl) {
        this.pluginUrl = pluginUrl;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getMemberAddedDeletedId() {
        return memberAddedDeletedId;
    }

    public void setMemberAddedDeletedId(String memberAddedDeletedId) {
        this.memberAddedDeletedId = memberAddedDeletedId;
    }

    public String getMemberAddedDeletedName() {
        return memberAddedDeletedName;
    }

    public void setMemberAddedDeletedName(String memberAddedDeletedName) {
        this.memberAddedDeletedName = memberAddedDeletedName;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }


    public String getCardClosed() {
        return cardClosed;
    }

    public void setCardClosed(String cardClosed) {
        this.cardClosed = cardClosed;
    }

    public String getListAfterId() {
        return listAfterId;
    }

    public void setListAfterId(String listAfterId) {
        this.listAfterId = listAfterId;
    }

    public String getListAfterName() {
        return listAfterName;
    }

    public void setListAfterName(String listAfterName) {
        this.listAfterName = listAfterName;
    }

    public String getListBeforeId() {
        return listBeforeId;
    }

    public void setListBeforeId(String listBeforeId) {
        this.listBeforeId = listBeforeId;
    }

    public String getListBeforeName() {
        return listBeforeName;
    }

    public void setListBeforeName(String listBeforeName) {
        this.listBeforeName = listBeforeName;
    }

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

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getCardId() {
        return cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(String checkItemId) {
        this.checkItemId = checkItemId;
    }

    public String getCheckItemName() {
        return checkItemName;
    }

    public void setCheckItemName(String checkItemName) {
        this.checkItemName = checkItemName;
    }

    public String getCheckItemState() {
        return checkItemState;
    }

    public void setCheckItemState(String checkItemState) {
        this.checkItemState = checkItemState;
    }

    public String getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(String checkListId) {
        this.checkListId = checkListId;
    }

    public String getCheckListName() {
        return checkListName;
    }

    public void setCheckListName(String checkListName) {
        this.checkListName = checkListName;
    }
}