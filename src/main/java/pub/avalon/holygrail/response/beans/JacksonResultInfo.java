package pub.avalon.holygrail.response.beans;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 白超
 * @date 2018/6/22
 */
public class JacksonResultInfo extends AbstractJsonResultInfo {

    private TreeNode treeNode;

    public JacksonResultInfo(TreeNode treeNode) {
        if (treeNode == null) {
            throw new RuntimeException("JacksonResultInfo Constructor arg treeNode is null.");
        }
        this.treeNode = treeNode;
    }

    @Override
    public ResultCode getResultCode() {
        if (this.resultCode != null) {
            return this.resultCode;
        }
        String value = ((TextNode) this.treeNode.get("resultCode")).textValue();
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.name().equals(value)) {
                this.resultCode = resultCode;
                break;
            }
        }
        return this.resultCode;
    }

    @Override
    public boolean isSuccess() {
        if (this.success != null) {
            return this.success;
        }
        this.success = ((BooleanNode) this.treeNode.get("success")).booleanValue();
        return this.success;
    }

    @Override
    public boolean isFail() {
        if (this.fail != null) {
            return this.fail;
        }
        this.fail = ((BooleanNode) this.treeNode.get("fail")).booleanValue();
        return this.fail;
    }

    @Override
    public boolean isError() {
        if (this.error != null) {
            return this.error;
        }
        this.error = ((BooleanNode) this.treeNode.get("error")).booleanValue();
        return this.error;
    }

    @Override
    public int getType() {
        if (this.type != null) {
            return this.type;
        }
        this.type = ((IntNode) this.treeNode.get("type")).intValue();
        return this.type;
    }

    @Override
    public String getMessage() {
        if (this.message != null) {
            return this.message;
        }
        this.message = ((TextNode) this.treeNode.get("message")).textValue();
        return this.message;
    }

    @Override
    public int getMessageCode() {
        if (this.messageCode != null) {
            return this.messageCode;
        }
        this.messageCode = ((IntNode) this.treeNode.get("messageCode")).intValue();
        return this.messageCode;
    }

    @Override
    public String getExceptionMessage() {
        if (this.exceptionMessage != null) {
            return this.exceptionMessage;
        }
        TreeNode treeNode = this.treeNode.get("exceptionMessage");
        if (treeNode instanceof TextNode) {
            this.exceptionMessage = ((TextNode) treeNode).textValue();
            return this.exceptionMessage;
        }
        return this.exceptionMessage;
    }

    @Override
    public Collection<ResultDetail> getResultDetails() {
        TreeNode treeNode = this.treeNode.get("resultDetails");
        if (treeNode instanceof ArrayNode) {
            Collection<ResultDetail> resultDetails = new ArrayList<>();
            for (JsonNode jsonNode : ((ArrayNode) treeNode)) {
                resultDetails.add(new JsonResultDetail(jsonNode.toString()));
            }
            return resultDetails;
        }
        return this.resultDetails;
    }

}
