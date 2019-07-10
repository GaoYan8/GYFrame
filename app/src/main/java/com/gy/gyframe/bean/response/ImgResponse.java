package com.gy.gyframe.bean.response;

import com.gy.gyframe.bean.ImgEntity;
import com.gy.gyframe.bean.base.MessagesEntity;
import com.gy.gyframe.bean.base.PageInfoEntity;

import java.util.List;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/7/10
 * @Describe
 */
public class ImgResponse extends MessagesEntity {
    private PageInfoEntity<ImgEntity> result;

    public PageInfoEntity<ImgEntity> getResult() {
        return result;
    }

    public void setResult(PageInfoEntity<ImgEntity> result) {
        this.result = result;
    }
}
