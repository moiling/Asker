package com.moinut.asker.model.bean;

import com.moinut.asker.utils.TimeUtils;

import java.io.Serializable;

public class Question implements Serializable {
    private int id;
    private int contentId;
    private String title;
    private String date;
    private String recent;
    private String type;
    private int answerCount;
    private int bestAnswerId;
    private int starCount;
    private String authorName;
    private String content;
    private boolean stared;

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getBestAnswerId() {
        return bestAnswerId;
    }

    public void setBestAnswerId(int bestAnswerId) {
        this.bestAnswerId = bestAnswerId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecent() {
        return recent;
    }

    public long getRecentTimeStamp() {
        return TimeUtils.strToDate(recent).getTime();
    }

    public String getRecentFormat() {
        return TimeUtils.convertTimeToFormat(TimeUtils.strToDate(recent));
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Question {" +
                "\nanswerCount = " + answerCount +
                ",\n id = " + id +
                ",\n contentId = " + contentId +
                ",\n title = '" + title + '\'' +
                ",\n date = '" + date + '\'' +
                ",\n recent = '" + recent + '\'' +
                ",\n type = '" + type + '\'' +
                ",\n bestAnswerId = " + bestAnswerId +
                ",\n starCount = " + starCount +
                ",\n authorName = '" + authorName + '\'' +
                ",\n content = '" + content + '\'' +
                "\n}";
    }
}
