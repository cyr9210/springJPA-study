package me.bong.springjpa06.post;

import lombok.Value;

@Value
public class CommentSummary_Class {
    private String comment;

    private int up;

    private int down;

    public CommentSummary_Class(String comment, int up, int down) {
        this.comment = comment;
        this.up = up;
        this.down = down;
    }

    public String getVotes(){
        return up + " " + down;
    }

}
