package com.blee.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author atlas
 * @date 2012-10-9
 */
public class IOUtils {
    public static void copy(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = is.read(buffer)) != -1) {
            os.write(buffer, 0, read);
        }
    }
    

    public static void copy(File from, File to) throws IOException {
        if (from.equals(to))
            return;
        if (!to.exists()) {
            to.createNewFile();
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(from);
            os = new FileOutputStream(to);
            copy(is, os);
        } catch (IOException e) {
            throw e;
        } finally {
            closeQuietly(is);
            closeQuietly(os);
        }
    }

    public static void closeQuietly(Closeable c) {
        try {
            if (c != null)
                c.close();
        } catch (IOException e) {
        }
    }

    public static String getStreamContent(InputStream is, String charset)
            throws IOException {
        return getStreamContent(is, charset, -1);
    }

    public static String getStreamContent(InputStream is, String charset,
            int buffsize) throws IOException {
        if (charset == null) {
            charset = "UTF-8";
        }
        if (buffsize <= 0) {
            buffsize = 10240;
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream(buffsize);
            copy(is, os);
            return os.toString(charset);
        } catch (IOException e) {
            throw e;
        }
    }

    public static String getStreamContent(InputStream is) throws IOException {
        return getStreamContent(is, null, -1);
    }

    public static String getStreamContent(InputStream is, int buffsize)
            throws IOException {
        return getStreamContent(is, null, buffsize);
    }
}
