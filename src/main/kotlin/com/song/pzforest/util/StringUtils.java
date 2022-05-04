package com.song.pzforest.util;

import cn.hutool.core.text.StrFormatter;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StringUtils extends org.apache.commons.lang3.StringUtils{
    /** ���ַ��� */
    private static final String NULLSTR = "";

    /** �»��� */
    private static final char SEPARATOR = '_';

    /**
     * ��ȡ������Ϊ��ֵ
     *
     * @param value defaultValue Ҫ�жϵ�value
     * @return value ����ֵ
     */
    public static <T> T nvl(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }

    /**
     * * �ж�һ��Collection�Ƿ�Ϊ�գ� ����List��Set��Queue
     *
     * @param coll Ҫ�жϵ�Collection
     * @return true��Ϊ�� false���ǿ�
     */
    public static boolean isEmpty(Collection<?> coll)
    {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * �ж�һ��Collection�Ƿ�ǿգ�����List��Set��Queue
     *
     * @param coll Ҫ�жϵ�Collection
     * @return true���ǿ� false����
     */
    public static boolean isNotEmpty(Collection<?> coll)
    {
        return !isEmpty(coll);
    }

    /**
     * * �ж�һ�����������Ƿ�Ϊ��
     *
     * @param objects Ҫ�жϵĶ�������
     ** @return true��Ϊ�� false���ǿ�
     */
    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * �ж�һ�����������Ƿ�ǿ�
     *
     * @param objects Ҫ�жϵĶ�������
     * @return true���ǿ� false����
     */
    public static boolean isNotEmpty(Object[] objects)
    {
        return !isEmpty(objects);
    }

    /**
     * * �ж�һ��Map�Ƿ�Ϊ��
     *
     * @param map Ҫ�жϵ�Map
     * @return true��Ϊ�� false���ǿ�
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * �ж�һ��Map�Ƿ�Ϊ��
     *
     * @param map Ҫ�жϵ�Map
     * @return true���ǿ� false����
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
    }

    /**
     * * �ж�һ���ַ����Ƿ�Ϊ�մ�
     *
     * @param str String
     * @return true��Ϊ�� false���ǿ�
     */
    public static boolean isEmpty(String str)
    {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * �ж�һ���ַ����Ƿ�Ϊ�ǿմ�
     *
     * @param str String
     * @return true���ǿմ� false���մ�
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    /**
     * * �ж�һ�������Ƿ�Ϊ��
     *
     * @param object Object
     * @return true��Ϊ�� false���ǿ�
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * �ж�һ�������Ƿ�ǿ�
     *
     * @param object Object
     * @return true���ǿ� false����
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * * �ж�һ�������Ƿ����������ͣ�Java�����ͱ�����飩
     *
     * @param object ����
     * @return true�������� false����������
     */
    public static boolean isArray(Object object)
    {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * ȥ�ո�
     */
    public static String trim(String str)
    {
        return (str == null ? "" : str.trim());
    }

    /**
     * ��ȡ�ַ���
     *
     * @param str �ַ���
     * @param start ��ʼ
     * @return ���
     */
    public static String substring(final String str, int start)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = str.length() + start;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (start > str.length())
        {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * ��ȡ�ַ���
     *
     * @param str �ַ���
     * @param start ��ʼ
     * @param end ����
     * @return ���
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * �ж��Ƿ�Ϊ�գ����Ҳ��ǿհ��ַ�
     *
     * @param str Ҫ�жϵ�value
     * @return ���
     */
    public static boolean hasText(String str)
    {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str)
    {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * ��ʽ���ı�, {} ��ʾռλ��<br>
     * �˷���ֻ�Ǽ򵥽�ռλ�� {} ����˳���滻Ϊ����<br>
     * �������� {} ʹ�� \\ת�� { ���ɣ��������� {} ֮ǰ�� \ ʹ��˫ת��� \\\\ ����<br>
     * ����<br>
     * ͨ��ʹ�ã�format("this is {} for {}", "a", "b") -> this is a for b<br>
     * ת��{}�� format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * ת��\�� format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template �ı�ģ�壬���滻�Ĳ����� {} ��ʾ
     * @param params ����ֵ
     * @return ��ʽ������ı�
     */
    public static String format(String template, Object... params)
    {
        if (isEmpty(params) || isEmpty(template))
        {
            return template;
        }
        return StrFormatter.format(template, params);
    }


    /**
     * �շ�ת�»�������
     */
    public static String toUnderScoreCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // ǰ���ַ��Ƿ��д
        boolean preCharIsUpperCase = true;
        // ��ǰ�ַ��Ƿ��д
        boolean curreCharIsUpperCase = true;
        // ��һ�ַ��Ƿ��д
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * �Ƿ�����ַ���
     *
     * @param str ��֤�ַ���
     * @param strs �ַ�����
     * @return ��������true
     */
    public static boolean inStringIgnoreCase(String str, String... strs)
    {
        if (str != null && strs != null)
        {
            for (String s : strs)
            {
                if (str.equalsIgnoreCase(trim(s)))
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * �Ƿ�Ϊhttp(s)://��ͷ
     *
     * @param link ����
     * @return ���
     */
    public static boolean ishttp(String link)
    {
        return StringUtils.startsWithAny(link, "http://", "https://");
    }


    /**
     * ���»��ߴ�д��ʽ�������ַ���ת��Ϊ�շ�ʽ�����ת��ǰ���»��ߴ�д��ʽ�������ַ���Ϊ�գ��򷵻ؿ��ַ����� ���磺HELLO_WORLD->HelloWorld
     *
     * @param name ת��ǰ���»��ߴ�д��ʽ�������ַ���
     * @return ת������շ�ʽ�������ַ���
     */
    public static String convertToCamelCase(String name)
    {
        StringBuilder result = new StringBuilder();
        // ���ټ��
        if (name == null || name.isEmpty())
        {
            // û��Ҫת��
            return "";
        }
        else if (!name.contains("_"))
        {
            // �����»��ߣ���������ĸ��д
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // ���»��߽�ԭʼ�ַ����ָ�
        String[] camels = name.split("_");
        for (String camel : camels)
        {
            // ����ԭʼ�ַ����п�ͷ����β���»��߻�˫���»���
            if (camel.isEmpty())
            {
                continue;
            }
            // ����ĸ��д
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * �շ�ʽ������ ���磺user_name->userName
     */
    public static String toCamelCase(String s)
    {
        if (s == null)
        {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == SEPARATOR)
            {
                upperCase = true;
            }
            else if (upperCase)
            {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * ����ָ���ַ����Ƿ�ƥ��ָ���ַ����б��е�����һ���ַ���
     *
     * @param str ָ���ַ���
     * @param strs ��Ҫ�����ַ�������
     * @return �Ƿ�ƥ��
     */
    public static boolean matches(String str, List<String> strs)
    {
        if (isEmpty(str) || isEmpty(strs))
        {
            return false;
        }
        for (String pattern : strs)
        {
            if (isMatch(pattern, str))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * �ж�url�Ƿ����������:
     * ? ��ʾ�����ַ�;
     * * ��ʾһ��·���ڵ������ַ��������ɿ�㼶;
     * ** ��ʾ�����·��;
     *
     * @param pattern ƥ�����
     * @param url ��Ҫƥ���url
     * @return 123
     */
    public static boolean isMatch(String pattern, String url)
    {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj)
    {
        return (T) obj;
    }
}
