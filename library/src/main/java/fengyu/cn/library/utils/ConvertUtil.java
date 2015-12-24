package fengyu.cn.library.utils;

/**
 * Created by fys on 2015/11/25.
 */
public class ConvertUtil {


    /**
     * 安全类型转换函数 object to int
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static final int convertToInt(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 安全类型转换函数 object to long
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static final long convertToLong(Object value, long defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Long.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).longValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 安全类型转换函数 object to double
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static final double convertToDouble(Object value, double defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Double.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).doubleValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 安全类型转换函数 object to string
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static final String convertToString(Object value, String defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return String.valueOf(value.toString());
        } catch (Exception e) {

            return defaultValue;

        }
    }

}
