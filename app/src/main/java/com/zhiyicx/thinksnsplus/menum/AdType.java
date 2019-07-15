package com.zhiyicx.thinksnsplus.menum;

public enum AdType {
//1	App 启动广告
//14	App 推荐-精选
//15	App 发现-圈子
//16	App 我的-观看历史
//17	App 搜索页

    GUIDE(1),RECOMMEND_SELECT(14),FIND_CIRCLE(15),MINE_VIDEO_HISTORY(16),SEARCH(17);
    private int adType;
    private AdType(int adType){
         this.adType = adType;
    }
    public int getValue(){
        return adType;
    }
}
