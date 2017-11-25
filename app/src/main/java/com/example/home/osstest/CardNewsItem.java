package com.example.home.osstest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Home on 2017-10-31.
 */

public class CardNewsItem implements Serializable{
    private String where;//어디서부터 가져왔는지에 대한 정보       //Info where it is from
    private ArrayList<String> cards;//카드 이미지들에 대한 경로가 저장되어있는 동적 배열  //Dynamic Array for Card Image Route
    private String headLine;
    CardNewsItem(String where, ArrayList<String> cards,String headLine){
        this.where = where;
        this.cards = cards;
        this.headLine = headLine;
    }

    public String getWhere(){
        return where;
    }
    public String getHeadline(){
        return headLine;
    }
    public ArrayList<String> getCards(){
        return cards;
    }
}
