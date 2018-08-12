package com.avalon.holygrail.file.bean;

/**
 * 复制结果
 *
 * @author 白超
 * @date 2018-4-4
 */
public class CopyResult extends FileResult {

    /**
     * 源路径
     */
    private String sourcePath;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}
