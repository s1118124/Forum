/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Wong Sze Man
 */
public class Reply {
    private long id;
    private long topicID;
    private String userID;
    private String title;
    private String replyContent;
    private Map<String, Attachment> attachments = new LinkedHashMap<>();

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    public long getTopicID() {
        return topicID;
    }
    public void setTopicId(long topicID) {
        this.topicID = topicID;
    }
    
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Attachment getAttachment(String name) {
        return this.attachments.get(name);
    }
    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }
    public void addAttachment(Attachment attachment) {
        this.attachments.put(attachment.getName(), attachment);
    }
    public int getNumberOfAttachments() {
        return this.attachments.size();
    }
    public boolean hasAttachment(String name) {
        return this.attachments.containsKey(name);
    }
    public Attachment deleteAttachment(String name) {
        return this.attachments.remove(name);
    }
}
