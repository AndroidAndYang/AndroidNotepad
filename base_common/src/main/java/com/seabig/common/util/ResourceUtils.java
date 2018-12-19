package com.seabig.common.util;

import android.content.Context;

/**
 * author： YJZ
 * date:  2018/12/18
 * des:
 */

public class ResourceUtils {

    /**
     * 通过图片名称获取图片资源 id
     *
     * @param imageName 图片名称
     * @return 图片资源 id
     */
    public static int getImageResIdByName(Context context, String imageName, String fileName) {
        return context.getResources().getIdentifier(imageName, fileName
                , AppUtils.getPackageName(context));
    }
}
