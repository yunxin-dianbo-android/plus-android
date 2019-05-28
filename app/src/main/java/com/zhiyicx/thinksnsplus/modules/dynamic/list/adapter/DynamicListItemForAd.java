package com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter;

import android.content.Context;

import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.Letter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import static com.zhiyicx.thinksnsplus.data.beans.DynamicListAdvert.DEFAULT_ADVERT_FROM_TAG;

/**
 * @author wulianshu
 * @describe
 * @date 2019/4/30
 * @contact email:450127106@qq.com
 */

public class DynamicListItemForAd extends DynamicListBaseItem {
    public DynamicListItemForAd(Context context) {
        super(context);
    }


    @Override
    public boolean isForViewType(DynamicDetailBeanV2 item, int position) {
        // TODO: 2019/4/30 之后修改
       return position == 2;
    }

    @Override
    public void convert(ViewHolder holder, DynamicDetailBeanV2 dynamicBean, DynamicDetailBeanV2 lastT, int position, int itemCounts) {
//        super.convert(holder, dynamicBean, lastT, position, itemCounts);

        LogUtils.d("------------wrods  = " + (System.currentTimeMillis() - start));

    }
}
