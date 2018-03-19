package com.avalon.holygrail.file.util;

import com.avalon.holygrail.file.bean.DownloadRecord;
import com.avalon.holygrail.file.exception.DownLoadException;
import com.avalon.holygrail.file.exception.FileException;
import com.avalon.holygrail.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.sql.Timestamp;

/**
 * 文件工具类
 * Created by 白超 on 2018/1/22.
 */
public class FileUtil {

    /**
     * 项目路径
     */
    public static final String PROJECT_PATH = getProjectPath();

    public static String getProjectPath() {
        String projectPath = FileUtil.class.getClassLoader().getResource("").getPath();
        try {
            projectPath = URLDecoder.decode(projectPath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int end = projectPath.length() - "WEB-INF/classes/".length();
        return projectPath.substring(1, end);
    }

    /**
     * 获取资源真实路径
     * @param path 资源相对项目路径
     * @return 资源全路径
     */
    public static String getRealPath(String path) {
        return PROJECT_PATH + File.separator + path;
    }

    /**
     * 下载
     * @param fileName 文件名称
     * @param suffix 文件后缀
     * @param inputStream 文件流
     * @param request 请求
     * @param response 响应
     * @throws Exception
     */
    public static DownloadRecord download(String fileName, String suffix, String filePath, InputStream inputStream,
                                HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, DownLoadException {
        //声明本次下载状态的记录对象
        DownloadRecord downloadRecord = new DownloadRecord(fileName, filePath, request);
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1") + "." + suffix);
        //用于记录以完成的下载的数据量，单位是byte
        long downloadedLength = 0l;
        OutputStream os = null;
        try {
            //激活下载操作
            os = response.getOutputStream();
            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }

        } catch (Exception e) {
            downloadRecord.setStatus(DownloadRecord.STATUS_ERROR);
            throw new DownLoadException(e, downloadRecord);
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        downloadRecord.setStatus(DownloadRecord.STATUS_SUCCESS);
        downloadRecord.setEndTime(new Timestamp(System.currentTimeMillis()));
        downloadRecord.setLength(downloadedLength);
        return downloadRecord;
    }

    /**
     * 下载
     * @param fileName 文件名称
     * @param suffix 文件后缀
     * @param filePath 文件路径
     * @param request 请求
     * @param response 响应
     * @throws Exception
     */
    public static DownloadRecord download(String fileName, String suffix, String filePath,
                                HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, DownLoadException, FileNotFoundException {
        return download(fileName, suffix, filePath, new FileInputStream(filePath), request, response);
    }

    /**
     * 将base64保存为图片
     *
     * @param base64
     * @param savePath     文件保存路径(不包含项目路径,不包含文件名和后缀名,尾部不用加/)
     * @param saveFileName 保存文件名(不包含后缀名)
     * @param suffix       文件后缀
     */
    public static void base64ToImage(String base64, String savePath, String saveFileName, String suffix) throws Exception {
        if (StringUtil.isEmpty(base64)) {
            throw new FileException("图片base64数据为空");
        }
        base64 = base64.replace("data:image/" + suffix + ";base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(base64);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        savePath = getRealPath(savePath);
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String saveFile = new StringBuilder().append(savePath).append(File.separator).append(saveFileName).append(FilenameUtils.EXTENSION_SEPARATOR).append(suffix).toString();
        OutputStream out = null;
        try {
            out = new FileOutputStream(saveFile);
            out.write(b);
            out.flush();
        } catch (IOException e) {
            throw new FileException(e);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
