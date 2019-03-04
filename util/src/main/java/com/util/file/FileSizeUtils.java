package com.util.file;


import com.util.LogUtils;
import com.util.constants.MemoryConstants;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class FileSizeUtils {

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型
     * @return double值的大小
     */

    public static Double getFileOrFilesSize(String filePath,@MemoryConstants.Unit int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                if (file.exists()) {
                    blockSize = getFileSize(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("获取失败!");
        }
        return formatFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param file 文件
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(File file) {

        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("获取失败!");
        }

        return formatFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     */
    private static Long getFileSize(File file) throws Exception {
        long size = 0;
        if (null != file && file.exists()) {
            FileInputStream fis;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            LogUtils.e("文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param file file
     * @throws Exception
     */
    private static Long getFileSizes(File file) throws Exception {
        long size = 0;
        File[] flist = file.listFiles();
        if (null != flist) {
            for (File f : flist) {
                if (f.isDirectory()) {
                    size += getFileSizes(f);
                } else {
                    size += getFileSizes(f);
                }
                size += getFileSize(f);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileSize 文件大小
     */
    private static String formatFileSize(Long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        String wrongSize = "0B";
        if (fileSize == 0L) {
            return wrongSize;
        }

        if (fileSize < MemoryConstants.KB) {
            fileSizeString = df.format(fileSize.doubleValue()) + "B";
        } else if (fileSize < MemoryConstants.MB) {
            fileSizeString = df.format(fileSize.doubleValue() / MemoryConstants.KB) + "KB";
        } else if (fileSize < MemoryConstants.GB) {
            fileSizeString = df.format(fileSize.doubleValue() / MemoryConstants.MB) + "MB";
        } else {
            fileSizeString = df.format(fileSize.doubleValue() / MemoryConstants.GB) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileSize 文件大小
     * @param sizeType 大小类型
     */
    private static Double formatFileSize(Long fileSize,@MemoryConstants.Unit int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0.0;
        switch (sizeType) {
            case MemoryConstants.BYTE:
                fileSizeLong = Double.valueOf(df.format(fileSize.doubleValue()));
                break;
            case MemoryConstants.KB:
                fileSizeLong = Double.valueOf(df.format(fileSize.doubleValue() / MemoryConstants.KB));
                break;
            case MemoryConstants.MB:
                fileSizeLong = Double.valueOf(df.format(fileSize.doubleValue() / MemoryConstants.MB));
                break;
            case MemoryConstants.GB:
                fileSizeLong = Double.valueOf(df.format(fileSize.doubleValue() / MemoryConstants.GB));
                break;
        }
        return fileSizeLong;
    }

}
