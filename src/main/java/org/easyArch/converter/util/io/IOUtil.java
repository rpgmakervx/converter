package org.easyArch.converter.util.io;/**
 * Description : 
 * Created by YangZH on 16-10-28
 *  上午10:03
 */

import org.easyArch.converter.util.codec.EncryptUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

/**
 * Description :
 * Created by code4j on 16-10-28
 * 上午10:03
 */

public class IOUtil {

    private static final int DEFAULT_BUFFER_SIZE = 2 << 12;

    public static void closeIO(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeChl(Channel ch){
        try {
            ch.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    public static byte[] toByteArray(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(is, baos);
        return baos.toByteArray();
    }

    public static char[] toCharArray(Reader reader) {
        CharArrayWriter caw = new CharArrayWriter();
        copy(reader, caw);
        return caw.toCharArray();
    }

    public static String toString(InputStream is) {
        StringWriter sw = new StringWriter();
        copy(is, sw);
        return sw.toString();
    }

    public static String toString(Reader reader) {
        StringWriter sw = new StringWriter();
        copy(reader, sw);
        return sw.toString();
    }

    private static long bufferedCopy(InputStream is, OutputStream os) {
        byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
        int len = 0;
        long count = 0;
        try {
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                count += len;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return count;
    }

    private static long bufferedCopy(Reader reader, Writer writer) {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        int len = 0;
        long count = 0;
        try {
            while ((len = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, len);
                count += len;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return count;
    }

    /**
     * 拷贝小于4G的流
     *
     * @param is
     * @param os
     * @return
     */
    public static long copy(InputStream is, OutputStream os) {
        return bufferedCopy(is, os);
    }

    public static long copy(Reader reader, Writer writer) {
        return bufferedCopy(reader, writer);
    }

    public static long copy(InputStream is, Writer writer) {
        InputStreamReader isr = new InputStreamReader(is);
        return copy(isr, writer);
    }

    public static long copy(Reader reader, OutputStream os) {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        return copy(reader, osw);
    }

    public static long nioCopy(InputStream is, OutputStream os) {
        ReadableByteChannel readChannel = Channels.newChannel(is);
        WritableByteChannel writeChannel = Channels.newChannel(os);
        ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE << 2);
        int len = 0;
        int count = 0;
        try {
            while ((len = readChannel.read(buffer)) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    writeChannel.write(buffer);
                }
                buffer.clear();
                count += len;
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean equals(InputStream is1, InputStream is2) {
        String hashStr1 = EncryptUtil.sha1(toByteArray(is1));
        String hashStr2 = EncryptUtil.sha1(toByteArray(is2));
        return hashStr1.equals(hashStr2);
    }

    public static boolean equals(Reader reader1, Reader reader2) {
        String hashStr1 = EncryptUtil.sha1(getBytes(toCharArray(reader1)));
        String hashStr2 = EncryptUtil.sha1(getBytes(toCharArray(reader2)));
        return hashStr1.equals(hashStr2);
    }

    public static long transferTo(FileInputStream from,OutputStream to){
        FileChannel fChannel = from.getChannel();
        WritableByteChannel tChannel = Channels.newChannel(to);
        return transferTo(fChannel,tChannel);
    }

    public static long transferTo(FileChannel from,WritableByteChannel to){
        long count = 0;
        try {
            count = from.transferTo(0, from.size(), to);
        } catch (IOException e) {
            e.printStackTrace();
            count = -1;
        }finally {
            closeChl(from);
            closeChl(to);
        }
        return count;
    }
    public static long transferFrom(InputStream from,FileInputStream to){
        return 0;
    }
    public static long transferFrom(ReadableByteChannel from,FileChannel to,long length){
        long count = 0;
        try {
            count = to.transferFrom(to,0,length);
        } catch (IOException e) {
            e.printStackTrace();
            count = -1;
        }finally {
            closeChl(from);
            closeChl(to);
        }
        return count;
    }

}
