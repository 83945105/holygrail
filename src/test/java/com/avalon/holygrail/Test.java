package com.avalon.holygrail;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.file.util.FileUtil;
import com.avalon.holygrail.util.Export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by 白超 on 2018-2-5.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException, ExcelException, IOException {

        System.out.println(FileUtil.getRealPath("11"));
    }

}
