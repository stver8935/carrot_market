package com.example.carrot_market.CONTROLLER.METHOD;

import com.example.carrot_market.MODEL.DTO.DealReviewLeaveItem;

import java.util.ArrayList;

public class MannerListSetting {

    private ArrayList<DealReviewLeaveItem> arrayList;


    public MannerListSetting(ArrayList<DealReviewLeaveItem> arrayList) {
        this.arrayList=arrayList;
        this.arrayList.clear();
    }

    public ArrayList<DealReviewLeaveItem> insert_bad_manner_list(){

        arrayList.add(new DealReviewLeaveItem("거래 시간과 장소를 정한 후 연락이 안돼요."));
        arrayList.add(new DealReviewLeaveItem("약속 장소에 나타나지 않았어요"));
        arrayList.add(new DealReviewLeaveItem("거래 시간과 장소를 정한 후 거래 직전 취소했어요"));
        arrayList.add(new DealReviewLeaveItem("무조건 택배거래만 하려고 해요."));
        arrayList.add(new DealReviewLeaveItem("상품 가치없는 물건을 팔아요"));
        arrayList.add(new DealReviewLeaveItem("차에서 내리지도 않고 창문만 열고 거래하려고 해요."));
        arrayList.add(new DealReviewLeaveItem("약속시간을 안 지켜요."));
        arrayList.add(new DealReviewLeaveItem("구매 가격보다 비싼 가격으로 판매해요."));
        arrayList.add(new DealReviewLeaveItem("상품 상태가 설명과 달라요"));
        arrayList.add(new DealReviewLeaveItem("무리하게 가격을 깎아요."));
        arrayList.add(new DealReviewLeaveItem("불친절해요."));
        arrayList.add(new DealReviewLeaveItem("예약만 해놓고 거래시간을 명확하게 알려주지 않아요"));
        arrayList.add(new DealReviewLeaveItem("상품 설명에 중요한 정보가 누락 됐어요"));
        arrayList.add(new DealReviewLeaveItem("너무 늦은 시간이나 새벽에 연락해요."));
        arrayList.add(new DealReviewLeaveItem("질문해도 답이 없어요."));

        return arrayList;
    };

    public ArrayList<DealReviewLeaveItem> insert_good_manner_list(){

        arrayList.add(new DealReviewLeaveItem("무료로 나눠주셨어요."));
        arrayList.add(new DealReviewLeaveItem("상품상태가 설명한 것과 같아요."));
        arrayList.add(new DealReviewLeaveItem("상품설명이 자세해요."));
        arrayList.add(new DealReviewLeaveItem("좋은 상품을 저렴하게 판매해요."));
        arrayList.add(new DealReviewLeaveItem("약속시간을 잘 지켜요."));
        arrayList.add(new DealReviewLeaveItem("응답이 빨라요."));
        arrayList.add(new DealReviewLeaveItem("친절하고 매너가 좋아요."));


        return arrayList;
    };




}
