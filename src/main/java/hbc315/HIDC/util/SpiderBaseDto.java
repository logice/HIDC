package hbc315.HIDC.util;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by cheng on 16/6/27.
 */
public class SpiderBaseDto implements Serializable {

    private byte[] imageByte;

    private String imageCode;

    private String msgCode;

    private String mobile;

    private String pwd;

    private Map<String,String> cookieMap;

    private String imagePath;

    private String queryDate;

    private String queryStartDate;

    private String queryEndDate;

    private Integer currentPage =1;

    private Integer pageSize = 100000;


    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public Map<String, String> getCookieMap() {
        return cookieMap;
    }

    public void setCookieMap(Map<String, String> cookieMap) {
        this.cookieMap = cookieMap;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public void setQueryStartDate(String queryStartDate) {
        this.queryStartDate = queryStartDate;
    }


    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
