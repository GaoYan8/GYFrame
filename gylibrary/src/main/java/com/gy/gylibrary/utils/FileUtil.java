/*
 * Copyright (C) 2012 www.amsoft.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gy.gylibrary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    /**
     * 默认APP根目录.
     */
    private static String downloadRootDir = null;

    /**
     * 默认下载图片文件目录.
     */
    private static String imageDownloadDir = null;

    /**
     * 默认下载文件目录.
     */
    private static String fileDownloadDir = null;

    /**
     * 默认缓存目录.
     */
    private static String cacheDownloadDir = null;

    /**
     * 默认下载数据库文件的目录.
     */
    private static String dbDownloadDir = null;


    /**
     * 图片拍照
     */
    private static String imageDcimDir = null;

    /**
     * 剩余空间大于200M才使用SD缓存.
     */
    private static int freeSdSpaceNeededToCache = 200 * 1024 * 1024;

    /**
     * 默认下载文件地址.
     */
    //private static String DOWNLOAD_ROOT_DIR = "download";

    /**
     * 默认下载图片文件地址.
     */
    private static String DOWNLOAD_IMAGE_DIR = "images";


    /**
     * 默认下载文件地址.
     */
    private static String DOWNLOAD_FILE_DIR = "files";

    /**
     * 图片拍照
     */
    private static String IMAGE_DCIM_DIR = "dcim";

    /**
     * APP缓存目录.
     */
    private static String CACHE_DIR = "cache";

    /**
     * DB目录.
     */
    private static String DB_DIR = "db";


    /**
     * 描述：初始化存储目录.
     *
     * @param context the context
     */
    public static void initFileDir(Context context) {

        PackageInfo info = null;
        try {
            String packageName = context.getPackageName();
            info = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 默认下载文件根目录.
        //String downloadRootPath = File.separator + DOWNLOAD_ROOT_DIR + File.separator + info.packageName + File.separator;
        String downloadRootPath = File.separator + info.packageName + File.separator;

        // 默认下载图片文件目录.
        String imageDownloadPath = downloadRootPath + DOWNLOAD_IMAGE_DIR
                + File.separator;

        // 默认下载文件目录.
        String fileDownloadPath = downloadRootPath + DOWNLOAD_FILE_DIR
                + File.separator;

        //默认拍照图片目录
        String fileImageDcimDir = downloadRootPath + IMAGE_DCIM_DIR
                + File.separator;

        // 默认缓存目录.
        String cacheDownloadPath = downloadRootPath + CACHE_DIR
                + File.separator;

        // 默认DB目录.
        String dbDownloadPath = downloadRootPath + DB_DIR + File.separator;

        try {
            if (!isCanUseSD()) {
                return;
            } else {

                File root = Environment.getExternalStorageDirectory();
                File downloadDir = new File(root.getAbsolutePath()
                        + downloadRootPath);
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs();
                }
                downloadRootDir = downloadDir.getPath();

                File cacheDownloadDirFile = new File(root.getAbsolutePath()
                        + cacheDownloadPath);
                if (!cacheDownloadDirFile.exists()) {
                    cacheDownloadDirFile.mkdirs();
                }
                cacheDownloadDir = cacheDownloadDirFile.getPath();

                File imageDownloadDirFile = new File(root.getAbsolutePath()
                        + imageDownloadPath);
                if (!imageDownloadDirFile.exists()) {
                    imageDownloadDirFile.mkdirs();
                }
                imageDownloadDir = imageDownloadDirFile.getPath();

                File fileDownloadDirFile = new File(root.getAbsolutePath()
                        + fileDownloadPath);
                if (!fileDownloadDirFile.exists()) {
                    fileDownloadDirFile.mkdirs();
                }
                fileDownloadDir = fileDownloadDirFile.getPath();

                File dbDownloadDirFile = new File(root.getAbsolutePath()
                        + dbDownloadPath);
                if (!dbDownloadDirFile.exists()) {
                    dbDownloadDirFile.mkdirs();
                }
                dbDownloadDir = dbDownloadDirFile.getPath();


                File dbImageDcimDir_ = new File(root.getAbsolutePath() + fileImageDcimDir);
                if (!dbImageDcimDir_.exists()) {
                    dbImageDcimDir_.mkdirs();
                }
                imageDcimDir = dbImageDcimDir_.getPath();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述：通过文件的本地地址从SD卡读取图片.
     *
     * @param file          the file
     * @param type          图片的处理类型（剪切或者缩放到指定大小） 如果设置为原图，则后边参数无效，得到原图
     * @param desiredWidth  新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getBitmapFromSD(File file, int type, int desiredWidth,
                                         int desiredHeight) {
        Bitmap bitmap = null;
        try {
            // SD卡是否存在
            if (!isCanUseSD()) {
                return null;
            }

            // 文件是否存在
            if (!file.exists()) {
                return null;
            }

            // 文件存在
            if (type == ImageUtil.CUTIMG) {
                bitmap = ImageUtil.getCutBitmap(file, desiredWidth,
                        desiredHeight);
            } else if (type == ImageUtil.SCALEIMG) {
                bitmap = ImageUtil.getScaleBitmap(file, desiredWidth,
                        desiredHeight);
            } else {
                bitmap = ImageUtil.getBitmap(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 描述：通过文件的本地地址从SD卡读取图片.
     *
     * @param file the file
     * @return Bitmap 图片
     */
    public static Bitmap getBitmapFromSD(File file) {
        Bitmap bitmap = null;
        try {
            // SD卡是否存在
            if (!isCanUseSD()) {
                return null;
            }
            // 文件是否存在
            if (!file.exists()) {
                return null;
            }
            // 文件存在
            bitmap = ImageUtil.getBitmap(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存bitmap道SD卡
     *
     * @param mBitmap
     * @param fileName
     */
    public static String saveBitmapFromSD(Bitmap mBitmap, String fileName) {
        File f = new File(fileName);
        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return f.getAbsolutePath();
    }

    /**
     * 描述：将图片的byte[]写入本地文件.
     *
     * @param imgByte       图片的byte[]形势
     * @param fileName      文件名称，需要包含后缀，如.jpg
     * @param type          图片的处理类型（剪切或者缩放到指定大小）
     * @param desiredWidth  新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getBitmapFromByte(byte[] imgByte, String fileName,
                                           int type, int desiredWidth, int desiredHeight) {
        FileOutputStream fos = null;
        DataInputStream dis = null;
        ByteArrayInputStream bis = null;
        Bitmap bitmap = null;
        File file = null;
        try {
            if (imgByte != null) {

                file = new File(imageDownloadDir + fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                int readLength = 0;
                bis = new ByteArrayInputStream(imgByte);
                dis = new DataInputStream(bis);
                byte[] buffer = new byte[1024];

                while ((readLength = dis.read(buffer)) != -1) {
                    fos.write(buffer, 0, readLength);
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                }
                fos.flush();

                bitmap = getBitmapFromSD(file, type, desiredWidth,
                        desiredHeight);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (Exception e) {
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
        }
        return bitmap;
    }

    /**
     * 描述：根据URL从互连网获取图片.
     *
     * @param url           要下载文件的网络地址
     * @param type          图片的处理类型（剪切或者缩放到指定大小）
     * @param desiredWidth  新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getBitmapFromURL(String url, int type, int desiredWidth, int desiredHeight) {
        Bitmap bit = null;
        try {
            bit = ImageUtil.getBitmap(url, type, desiredWidth, desiredHeight);
        } catch (Exception e) {
            LogUtils.d("下载图片异常：" + e.getMessage());
        }
        return bit;
    }

    /**
     * 描述：获取src中的图片资源.
     *
     * @param src 图片的src路径，如（“image/arrow.png”）
     * @return Bitmap 图片
     */
    public static Bitmap getBitmapFromSrc(String src) {
        Bitmap bit = null;
        try {
            bit = BitmapFactory.decodeStream(FileUtil.class
                    .getResourceAsStream(src));
        } catch (Exception e) {
            LogUtils.d("获取图片异常：" + e.getMessage());
        }
        return bit;
    }

    /**
     * 描述：获取Asset中的图片资源.
     *
     * @param context  the context
     * @param fileName the file name
     * @return Bitmap 图片
     */
    public static Bitmap getBitmapFromAsset(Context context, String fileName) {
        Bitmap bit = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);
            bit = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            LogUtils.d("获取图片异常：" + e.getMessage());
        }
        return bit;
    }

    /**
     * 描述：获取Asset中的图片资源.
     *
     * @param context  the context
     * @param fileName the file name
     * @return Drawable 图片
     */
    public static Drawable getDrawableFromAsset(Context context, String fileName) {
        Drawable drawable = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);
            drawable = Drawable.createFromStream(is, null);
        } catch (Exception e) {
            LogUtils.d("获取图片异常：" + e.getMessage());
        }
        return drawable;
    }


    /**
     * 描述：获取网络文件的大小.
     *
     * @param Url 图片的网络路径
     * @return int 网络文件的大小
     */
    public static int getContentLengthFromUrl(String Url) {
        int mContentLength = 0;
        try {
            URL url = new URL(Url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) url
                    .openConnection();
            mHttpURLConnection.setConnectTimeout(5 * 1000);
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection
                    .setRequestProperty(
                            "Accept",
                            "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            mHttpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
            mHttpURLConnection.setRequestProperty("Referer", Url);
            mHttpURLConnection.setRequestProperty("Charset", "UTF-8");
            mHttpURLConnection
                    .setRequestProperty(
                            "User-Agent",
                            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            mHttpURLConnection.connect();
            if (mHttpURLConnection.getResponseCode() == 200) {
                // 根据响应获取文件大小
                mContentLength = mHttpURLConnection.getContentLength();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("获取长度异常：" + e.getMessage());
        }
        return mContentLength;
    }

    /**
     * 获取文件名，通过网络获取.
     *
     * @param url 文件地址
     * @return 文件名
     */
    public static String getRealFileNameFromUrl(String url) {
        String name = null;
        try {
            if (url == null || url.trim().length() == 0) {
                return name;
            }

            URL mUrl = new URL(url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mUrl
                    .openConnection();
            mHttpURLConnection.setConnectTimeout(5 * 1000);
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection
                    .setRequestProperty(
                            "Accept",
                            "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            mHttpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
            mHttpURLConnection.setRequestProperty("Referer", url);
            mHttpURLConnection.setRequestProperty("Charset", "UTF-8");
            mHttpURLConnection.setRequestProperty("User-Agent", "");
            mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            mHttpURLConnection.connect();
            if (mHttpURLConnection.getResponseCode() == 200) {
                for (int i = 0; ; i++) {
                    String mine = mHttpURLConnection.getHeaderField(i);
                    if (mine == null) {
                        break;
                    }
                    if ("content-disposition".equals(mHttpURLConnection
                            .getHeaderFieldKey(i).toLowerCase())) {
                        Matcher m = Pattern.compile(".*filename=(.*)").matcher(
                                mine.toLowerCase());
                        if (m.find())
                            return m.group(1).replace("\"", "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("网络上获取文件名失败");
        }
        return name;
    }



    /**
     * 描述：从sd卡中的文件读取到byte[].
     *
     * @param path sd卡中文件路径
     * @return byte[]
     */
    public static byte[] getByteArrayFromSD(String path) {
        byte[] bytes = null;
        ByteArrayOutputStream out = null;
        try {
            File file = new File(path);
            // SD卡是否存在
            if (!isCanUseSD()) {
                return null;
            }
            // 文件是否存在
            if (!file.exists()) {
                return null;
            }

            long fileSize = file.length();
            if (fileSize > Integer.MAX_VALUE) {
                return null;
            }

            FileInputStream in = new FileInputStream(path);
            out = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
            in.close();
            bytes = out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return bytes;
    }

    /**
     * 描述：将byte数组写入文件.
     *
     * @param path    the path
     * @param content the content
     * @param create  the create
     */
    public static void writeByteArrayToSD(String path, byte[] content, boolean create) {

        FileOutputStream fos = null;
        try {
            File file = new File(path);
            // SD卡是否存在
            if (!isCanUseSD()) {
                return;
            }
            // 文件是否存在
            if (!file.exists()) {
                if (create) {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                        file.createNewFile();
                    }
                } else {
                    return;
                }
            }
            fos = new FileOutputStream(path);
            fos.write(content);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 计算sdcard上的剩余空间.
     *
     * @return the int
     */
    public static int freeSpaceOnSD() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
                .getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
                .getBlockSize()) / 1024 * 1024;
        return (int) sdFreeMB;
    }

    /**
     * 根据文件的最后修改时间进行排序.
     */
    public static class FileLastModifSort implements Comparator<File> {

        /*
         * (non-Javadoc)
         *
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 删除所有缓存文件.
     *
     * @return true, if successful
     */
    public static boolean clearDownloadFile() {
        try {
            File fileDirectory = new File(downloadRootDir);
            deleteFile(fileDirectory);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除文件.
     *
     * @return true, if successful
     */
    public static boolean deleteFile(File file) {

        try {
            if (!isCanUseSD()) {
                return false;
            }
            if (file == null) {
                return true;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            } else {
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 描述：读取Assets目录的文件内容.
     *
     * @param context  the context
     * @param name     the name
     * @param encoding the encoding
     * @return the string
     */
    public static String readAssetsByName(Context context, String name,
                                          String encoding) {
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getAssets().open(name));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                buffer.append(line);
            }
            text = new String(buffer.toString().getBytes(), encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    /**
     * 描述：读取Raw目录的文件内容.
     *
     * @param context  the context
     * @param id       the id
     * @param encoding the encoding
     * @return the string
     */
    public static String readRawByName(Context context, int id, String encoding) {
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources()
                    .openRawResource(id));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                buffer.append(line);
            }
            text = new String(buffer.toString().getBytes(), encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    /**
     * Gets the download root dir.
     *
     * @param context the context
     * @return the download root dir
     */
    public static String getDownloadRootDir(Context context) {
        if (downloadRootDir == null) {
            initFileDir(context);
        }
        return downloadRootDir;
    }

    /**
     * Gets the image download dir.
     *
     * @param context the context
     * @return the image download dir
     */
    public static String getImageDownloadDir(Context context) {
        if (downloadRootDir == null) {
            initFileDir(context);
        }
        return imageDownloadDir;
    }

    /**
     * Gets the file download dir.
     *
     * @param context the context
     * @return the file download dir
     */
    public static String getFileDownloadDir(Context context) {
        if (downloadRootDir == null) {
            initFileDir(context);
        }
        return fileDownloadDir;
    }

    /**
     * Gets the cache download dir.
     *
     * @param context the context
     * @return the cache download dir
     */
    public static String getCacheDownloadDir(Context context) {
        if (downloadRootDir == null) {
            initFileDir(context);
        }
        return cacheDownloadDir;
    }

    /**
     * Gets the db download dir.
     *
     * @param context the context
     * @return the db download dir
     */
    public static String getDbDownloadDir(Context context) {
        if (downloadRootDir == null) {
            initFileDir(context);
        }
        return dbDownloadDir;
    }

    /**
     * 得到拍照图片路径
     *
     * @param context
     * @return
     */
    public static String getImageDcimDir(Context context) {
        if (downloadRootDir == null) {
            initFileDir(context);
        }
        return imageDcimDir;
    }

    /**
     * Gets the free sd space needed to cache.
     *
     * @return the free sd space needed to cache
     */
    public static int getFreeSdSpaceNeededToCache() {
        return freeSdSpaceNeededToCache;
    }


    ///////////////////////复制文件//////////////////////////////

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean isok = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            } else {
                isok = false;
            }
        } catch (Exception e) {
            // System.out.println("复制单个文件操作出错");
            // e.printStackTrace();
            isok = false;
        }
        return isok;

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        boolean isok = true;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            isok = false;
        }
        return isok;
    }

    /**
     * 获取指定文件的大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        if(null != files) {
            for (File file : files) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += getDirSize(file); // 递归调用继续统计
                }
            }
        }
        return dirSize;

    }

    /**
     * 转换文件大小
     *
     * @param fileSize
     * @return
     */
    public static String formatFielSize(long fileSize) {

        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        /**
         * 判断第一个字符串是不是“.”，如果是在前面加“0”
         */
        if (".".equals(fileSizeString.subSequence(0, 1))) {
            fileSizeString = "0" + fileSizeString;
        }
        return fileSizeString;

    }


    /**
     * 获取程序外部的缓存目录
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName()
                + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath()
                + cacheDir);
    }

//================================= 文件类型===================================//

    /**
     * 文件类型
     */
    public interface FileType {
        int UNKNOW = -1;
        int PDF = 0;
        int WORD = 1;
        int EXCEL = 2;
        int TXT = 3;
        int JPG = 4;
        int ZIP = 5;
        int RAR = 6;
        int PNG = 7;
        int PPT = 8;
    }

    /**
     * 验证文件是否为EXCEL文件
     *
     * @param filename
     * @return
     */
    public static boolean isExcel(String filename) {
        String strPattern = "[.]((xls)|(xlsx))$";
        Pattern p = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(filename);
        Log.d("m.matches()", String.valueOf(m.matches()));
        return m.matches();
    }

    /**
     * 验证文件是否为PDF文件
     *
     * @param filename
     * @return
     */
    public static boolean isPDF(String filename) {
        String strPattern = "[.](pdf)$";
        Pattern p = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(filename);
        Log.d("m.matches()", String.valueOf(m.matches()));
        return m.matches();
    }

    /**
     * 验证文件是否为TXT文件
     *
     * @param filename
     * @return
     */
    public static boolean isTXT(String filename) {
        String strPattern = "[.](txt)$";
        Pattern p = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(filename);
        Log.d("m.matches()", String.valueOf(m.matches()));
        return m.matches();
    }

    /**
     * 验证文件是否为图片
     *
     * @param filename 文件名
     * @return
     */
    public static boolean isPic(String filename) {
        String strPattern = "[.]((jpg)|(png)|(jpeg))$";
        Pattern p = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(filename);
        Log.d("m.matches()", String.valueOf(m.matches()));
        return m.matches();
    }

    /**
     * 验证文件是否为WORD文件
     *
     * @param filename
     * @return
     */
    public static boolean isWord(String filename) {
        String strPattern = "[.]((doc)|(docx))$";
        Pattern p = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(filename);
        Log.d("m.matches()", String.valueOf(m.matches()));
        return m.matches();
    }

    /**
     * 获取文件类型
     *
     * @param filename
     * @return
     */
    public static int getFileType(String filename) {
        if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
            return FileType.EXCEL;
        } else if (filename.endsWith(".pdf")) {
            return FileType.PDF;
        } else if (filename.endsWith(".doc") || filename.endsWith(".docx")) {
            return FileType.WORD;
        } else if (filename.endsWith(".txt")) {
            return FileType.TXT;
        } else if (filename.endsWith(".png")) {
            return FileType.PNG;
        } else if (filename.endsWith(".zip")) {
            return FileType.ZIP;
        } else if (filename.endsWith(".rar")) {
            return FileType.RAR;
        } else if (filename.endsWith(".jpg")) {
            return FileType.JPG;
        } else if (filename.endsWith(".ppt")) {
            return FileType.PPT;
        } else {
            return FileType.UNKNOW;
        }
    }

    /**
     * 根据uri获取文件  path
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealUriFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
