package pub.avalon.holygrail.resource.bean;

/**
 * 复制结果
 *
 * @author 白超
 * @date 2018-4-4
 */
public class CopyResult extends UploadFile {

    /**
     * 源路径
     */
    private String sourcePath;

    public CopyResult(String fileAbsoluteSavePath, String fileSaveName, String fileSuffix) {
        super(fileAbsoluteSavePath, fileSaveName, fileSuffix);
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}
