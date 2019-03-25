package pub.avalon.holygrail.response.beans;

import com.fasterxml.jackson.core.TreeNode;

/**
 * @author 白超
 * @date 2019/3/25
 */
public class JacksonResultDetail extends AbstractJsonResultDetail {

    private TreeNode treeNode;

    public JacksonResultDetail(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    @Override
    public ResultInfo getResultInfo() {
        if (this.resultInfo != null) {
            return this.resultInfo;
        }
        this.resultInfo = new JacksonResultInfo(treeNode.get("resultInfo"));
        return this.resultInfo;
    }
}
