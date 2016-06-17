package com.moinut.asker.model.bean;

import com.moinut.asker.utils.TimeUtils;

public class Answer {
    private int id;
    private int contentId;
    private String date;
    private int questionId;
    private int likeNumber;
    private int dislikeNumber;
    private String authorName;
    private String authorType;
    private String content;

    public String getAuthorType() {
        return authorType;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getDate() {
        return date;
    }

    public long getDateTimeStamp() {
        return TimeUtils.strToDate(date).getTime();
    }

    public String getDateFormat() {
        return TimeUtils.convertTimeToFormat(TimeUtils.strToDate(date));
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDislikeNumber() {
        return dislikeNumber;
    }

    public void setDislikeNumber(int dislikeNumber) {
        this.dislikeNumber = dislikeNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "authorName='" + authorName + '\'' +
                ", id=" + id +
                ", contentId=" + contentId +
                ", date='" + date + '\'' +
                ", questionId=" + questionId +
                ", likeNumber=" + likeNumber +
                ", dislikeNumber=" + dislikeNumber +
                ", content='" + content + '\'' +
                '}';
    }
}
