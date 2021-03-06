package com.zhiyicx.thinksnsplus.data.source.local.db;

import android.content.Context;

import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.data.beans.BackgroundRequestTaskBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.ChannelInfoBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.ChannelSubscripBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.CommentedBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.DaoMaster;
import com.zhiyicx.thinksnsplus.data.beans.DigedBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.DynamicBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.FlushMessagesDao;
import com.zhiyicx.thinksnsplus.data.beans.FollowFansBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.InfoCommentListBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.InfoListBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.MusicAlbumDetailsBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.MusicCommentListBeanDao;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * @author LiuChao
 * @describe 数据库升级
 * @date 2017/2/18
 * @contact email:450127106@qq.com
 */

public class UpDBHelper extends DaoMaster.OpenHelper {

    public UpDBHelper(Context context, String name) {
        super(context, name);
    }

    // 注意选择GreenDao参数的onUpgrade方法
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        LogUtils.i("greenDAO",
                "Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");
        // 每次升级，将需要更新的表进行更新，第二个参数为要升级的Dao文件.
        MigrationHelper.getInstance().migrate(db, UserInfoBeanDao.class);
        MigrationHelper.getInstance().migrate(db, DynamicBeanDao.class);
        MigrationHelper.getInstance().migrate(db, BackgroundRequestTaskBeanDao.class);
        MigrationHelper.getInstance().migrate(db, FollowFansBeanDao.class);
        MigrationHelper.getInstance().migrate(db, InfoListBeanDao.class);
        MigrationHelper.getInstance().migrate(db, InfoCommentListBeanDao.class);
        MigrationHelper.getInstance().migrate(db, MusicCommentListBeanDao.class);
        MigrationHelper.getInstance().migrate(db, ChannelSubscripBeanDao.class);
        MigrationHelper.getInstance().migrate(db, ChannelInfoBeanDao.class);
        MigrationHelper.getInstance().migrate(db, FlushMessagesDao.class);
        MigrationHelper.getInstance().migrate(db, DigedBeanDao.class);
        MigrationHelper.getInstance().migrate(db, CommentedBeanDao.class);
        MigrationHelper.getInstance().migrate(db, MusicCommentListBeanDao.class);
        MigrationHelper.getInstance().migrate(db, MusicAlbumDetailsBeanDao.class);
    }
}
