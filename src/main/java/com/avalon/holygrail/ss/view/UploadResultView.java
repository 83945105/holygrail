package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 上传结果视图
 */
public class UploadResultView extends MessageView {

    protected String realFileName;

    protected String fileSuffix;

    protected String saveFileName;

    protected String saveFileFullName;

    protected String fullSavePath;

    protected String filePath;

    public UploadResultView(ResultInfo resultInfo) {
        super(resultInfo);
    }

    public UploadResultView(ResultInfo resultInfo, String realFileName, String fileSuffix, String saveFileName, String saveFileFullName, String fullSavePath, String filePath) {
        super(resultInfo);
        this.realFileName = realFileName;
        this.fileSuffix = fileSuffix;
        this.saveFileName = saveFileName;
        this.saveFileFullName = saveFileFullName;
        this.fullSavePath = fullSavePath;
        this.filePath = filePath;
    }

    public String getRealFileName() {
        return realFileName;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String getSaveFileFullName() {
        return saveFileFullName;
    }

    public void setSaveFileFullName(String saveFileFullName) {
        this.saveFileFullName = saveFileFullName;
    }

    public String getFullSavePath() {
        return fullSavePath;
    }

    public void setFullSavePath(String fullSavePath) {
        this.fullSavePath = fullSavePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
