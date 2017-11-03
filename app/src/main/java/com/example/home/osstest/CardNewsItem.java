package com.example.home.osstest;

import java.util.ArrayList;

/**
 * Created by Home on 2017-10-31.
 */

public class CardNewsItem {
    private String where;//어디서부터 가져왔는지에 대한 정보
    private ArrayList<String> cards;//카드 이미지들에 대한 경로가 저장되어있는 동적 배열

    CardNewsItem(String where, ArrayList<String> cards){
        this.where = where;
        this.cards = cards;
    }

    public String getWhere(){
        return where;
    }

    public ArrayList<String> getCards(){
        return cards;
    }

    public int size() { return cards.size(); }
}
