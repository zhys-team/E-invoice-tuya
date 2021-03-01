/**
 * 
 */
package top.anets.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 序列化,反序列化工具
 * 
 * @project iweixin-pay
 * @fileName SerializeUtil.java
 * @Description
 * @author light-zhang
 * @date 2018年5月31日上午9:51:25
 * @version 1.0.0
 */
public class SerializeUtil {



    /**
     * 统一编码，字符串获取byte数组</br>
     * 编码格式：UTF-8
     */
    public static byte[] String2Byte(String s)
    {
        if (s == null)
        {
            return null;
        }
        
        try
        {
            return s.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 统一编码，byte数组转字符串</br>
     * 编码格式：UTF-8
     */
    public static String Byte2String(byte[] bytes)
    {
        if (bytes == null)
        {
            return null;
        }
        
        try
        {
            return new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 序列化
     * 
     * @param object
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static byte[] serializeObj(Object object)
    {
        if (object == null)
        {
            return null;
        }
        
        ByteArrayOutputStream byteOutStream = null;
        
        ObjectOutputStream outputStream = null;
        
        try
        {
            byteOutStream = new ByteArrayOutputStream();
            
            outputStream = new ObjectOutputStream(byteOutStream);
            
            outputStream.writeObject(object);
            
            byte[] bytes = byteOutStream.toByteArray();
            
            return bytes;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            
            return null;
        }
        finally
        {
            if (null != outputStream)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            
            if (null != byteOutStream)
            {
                try
                {
                    byteOutStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
    
    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Object unserializeObj(byte[] bytes)
    {
        ByteArrayInputStream byteInputStream = null;
        
        ObjectInputStream inputStream = null;
        
        try
        {
            byteInputStream = new ByteArrayInputStream(bytes);
            
            inputStream = new ObjectInputStream(byteInputStream);
            
            return inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (null != byteInputStream)
            {
                try
                {
                    byteInputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            
            if (null != inputStream)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
    
    /**
     * key链表转byte二维数组
     */
    public static byte[][] serializeListString(List<String> list)
    {
        if (list == null)
        {
            return null;
        }
        
        byte[][] byteArray = new byte[list.size()][];
        
        for (int i = 0; i < list.size(); i++)
        {
            byte[] bytefield = SerializeUtil.String2Byte(list.get(i));
            
            byteArray[i] = bytefield;
        }
        
        return byteArray;
    }
    
    /**
     * key链表转byte二维数组
     */
    public static byte[][] serializeList(List<?> list)
    {
        if (list == null)
        {
            return null;
        }
        
        byte[][] byteArray = new byte[list.size()][];
        
        for (int i = 0; i < list.size(); i++)
        {
            byte[] bytefield = SerializeUtil.serializeObj(list.get(i));
            
            byteArray[i] = bytefield;
        }
        
        return byteArray;
    }
    
    /**
     * 将List<byte[]>反序列化为list<T> 集合
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> unserializeList(List<byte[]> bytes)
    {
        if (bytes == null)
        {
            return null;
        }
        
        List<T> list = new ArrayList<T>();
        
        for (byte[] ele : bytes)
        {
            T t = (T)SerializeUtil.unserializeObj(ele);
            
            list.add(t);
        }
        
        return list;
    }
    
    /**
     * Map<String,T>转 Map<byte[], byte[]> </br>
     * 其中第一个byte[]为String的字节码，第二个byte[]为T的序列化字节码
     */
    public static <T> Map<byte[], byte[]> serializeMap(Map<String, T> map)
    {
        if (map == null)
        {
            return null;
        }
        
        Map<byte[], byte[]> jedisMap = new HashMap<byte[], byte[]>();
        
        for (Map.Entry<String, T> entry : map.entrySet())
        {
            jedisMap.put(SerializeUtil.String2Byte(entry.getKey()), SerializeUtil.serializeObj(entry.getValue()));
        }
        
        return jedisMap;
    }
    
    /**
     * Map<String,T>转 Map<byte[], byte[]> </br>
     * 其中第一个byte[]为String的字节码，第二个byte[]为T的序列化字节码
     */
    public static Map<byte[], Double> serializeDoubleMap(Map<?, Double> map)
    {
        if (map == null)
        {
            return null;
        }
        
        Map<byte[], Double> jedisMap = new HashMap<byte[], Double>();
        
        for (Map.Entry<?, Double> entry : map.entrySet())
        {
            jedisMap.put(SerializeUtil.serializeObj(entry.getKey()), entry.getValue());
        }
        
        return jedisMap;
    }
    
    /**
     * 将字节map反序列化为Map
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> unserializeMap(Map<byte[], byte[]> map)
    {
        if (map == null)
        {
            return null;
        }
        
        Map<String, T> result = new HashMap<String, T>();
        
        for (Map.Entry<byte[], byte[]> entry : map.entrySet())
        {
            result.put(SerializeUtil.Byte2String(entry.getKey()), (T)SerializeUtil.unserializeObj(entry.getValue()));
        }
        
        return result;
    }
    
    /**
     * 将List<byte[]>反序列化为Set<T> 集合
     */
    public static Set<String> byteSet2String(Set<byte[]> bytes)
    {
        if (bytes == null)
        {
            return null;
        }
        
        Set<String> set = new HashSet<String>();
        
        for (byte[] ele : bytes)
        {
            String t = SerializeUtil.Byte2String(ele);
            
            set.add(t);
        }
        
        return set;
    }
    
    /**
     * 将List<byte[]>反序列化为Set<T> 集合
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> unserializeSet(Set<byte[]> bytes)
    {
        if (bytes == null)
        {
            return null;
        }
        
        Set<T> set = new HashSet<T>();
        
        for (byte[] ele : bytes)
        {
            T t = (T)SerializeUtil.unserializeObj(ele);
            
            set.add(t);
        }
        
        return set;
    }
    
    /**
     * 将List<byte[]>反序列化为Set<T> 集合
     */
    @SuppressWarnings("unchecked")
    public static <T> LinkedHashSet<T> unserializeSet2LinkedHashSet(Set<byte[]> bytes)
    {
        if (bytes == null)
        {
            return null;
        }
        
        LinkedHashSet<T> set = new LinkedHashSet<T>();
        
        for (byte[] ele : bytes)
        {
            T t = (T)SerializeUtil.unserializeObj(ele);
            
            set.add(t);
        }
        
        return set;
    }
    
    /**
     * 关闭io流对象
     * 
     * @param closeable
     */
    public static void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * * 反序列化byte[]为object或者String。</br>
     * * 注意：如果是Object序列化后的byte[],仅适用jdk自带的序列化</br>
     * * 使用本方法能避免直接反序列化，通过异常判断是String还是Object（只有巧合的走异常流程）。
     */
    public static Object unSerializeObjOrString(byte[] bytes)
    {
        // 不足6位，直接认为是字符串，,经测试单个字符序列化后的byte[]也有8位
        if (bytes.length < 6)
        {
            return SerializeUtil.Byte2String(bytes);
        }
        
        String protocol = Integer.toHexString(bytes[0] & 0x000000ff) + Integer.toHexString(bytes[1] & 0x000000ff);
        
        // 如果是jdk序列化后的
        if ("ACED".equals(protocol.toUpperCase()))
        {
            Object obj = SerializeUtil.unserializeObj(bytes);
            
            if (obj != null)
            {
                return obj;
            }
            
            // 如果是巧合，则返回的是null
            else
            {
                return SerializeUtil.Byte2String(bytes);
            }
        }
        
        // 如果是字符串的byte[]字节形式
        return SerializeUtil.Byte2String(bytes);
    }

}