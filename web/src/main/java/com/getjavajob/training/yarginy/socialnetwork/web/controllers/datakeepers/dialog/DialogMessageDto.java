package com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.dialog;

public class DialogMessageDto {
    private String id;
    private String text;
    private String image;
    private String authorId;
    private String authorName;
    private String authorSurname;
    private String stringPosted;
    private String dialogId;

    public DialogMessageDto() {
    }

    public DialogMessageDto(String id, String text, String image, String authorId, String stringPosted) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.authorId = authorId;
        this.stringPosted = stringPosted;
    }

    public DialogMessageDto(String id, String text, String image, String authorId, String authorName,
                            String authorSurname, String stringPosted) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.stringPosted = stringPosted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getStringPosted() {
        return stringPosted;
    }

    public void setStringPosted(String stringPosted) {
        this.stringPosted = stringPosted;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }
}
