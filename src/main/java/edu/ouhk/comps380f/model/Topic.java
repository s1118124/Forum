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
public class Topic {
    private long id;
    private String userID;
    private String title;
    private String textContent;
    private Map<String, Attachment> attachments = new LinkedHashMap<>();

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    public String getTextContent() {
        return textContent;
    }
    public void setTextContent(String textContent) {
        this.textContent = textContent;
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
