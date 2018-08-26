package pub.avalon.holygrail.resource.bean;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * @author 白超
 * @date 2018-4-4
 */
public class FileResult {

    /**
     * 文件真实名称
     */
    private String fileRealName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件保存名称
     */
    private String fileSaveName;

    /**
     * 文件相对保存路径
     */
    private String fileRelativeSavePath;

    /**
     * 文件绝对保存路径
     */
    private String fileAbsoluteSavePath;

    public String getFileRealName() {
        return fileRealName;
    }

    public void setFileRealName(String fileRealName) {
        this.fileRealName = fileRealName;
    }

    public String getFileSuffix() {
        return fileSuffix == null ? FilenameUtils.getExtension(this.getFileRealName()) : fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }

    public String getFileRelativeSavePath() {
        return fileRelativeSavePath;
    }

    public void setFileRelativeSavePath(String fileRelativeSavePath) {
        this.fileRelativeSavePath = fileRelativeSavePath;
    }

    public String getFileAbsoluteSavePath() {
        return fileAbsoluteSavePath;
    }

    public void setFileAbsoluteSavePath(String fileAbsoluteSavePath) {
        this.fileAbsoluteSavePath = fileAbsoluteSavePath;
    }

    /**
     * 获取文件保存全名
     */
    public String getFileSaveFullName() {
        return this.getFileSaveName() + FilenameUtils.EXTENSION_SEPARATOR + this.getFileSuffix();
    }

    /**
     * 获取文件保存全路径
     */
    public String getFileSaveFullPath() {
        return this.getFileAbsoluteSavePath() + File.separator + this.getFileSaveFullName();
    }
}
