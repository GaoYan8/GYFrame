package com.gy.gyframe.bean;


import java.util.Date;


/**
 * <p>
 * 图片表
 * </p>
 *
 * @author wangzhong
 * @since 2019-05-24
 */
public class ImgEntity {


    /**
     * 主键ID
     */
    private String id;

    /**
     * 图片标题
     */
    private String imgTitle;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 图片类型（01 首页banner; 02 企业需求; 03 榜单banner; 04 平台体系; 05 组织机构;）
     */
    private String imgType;

    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 数量
     */
    private String numbers;

    /**
     * 启用状态（0 未启用 1 启用），默认未启用
     */
    private String enable;

    /**
     * 图片排序
     */
    private String sort;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建用户
     */
    private String createUser;


    /**
     * 解决前台修改图文问题
     */
    private String host;
    /**
     * 拼接主机名路径
     */
    private String showImgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getShowImgUrl() {
        return showImgUrl;
    }

    public void setShowImgUrl(String showImgUrl) {
        this.showImgUrl = showImgUrl;
    }
}
