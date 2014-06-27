/**
 * 日期：12-2-10
 */
package com.rop;

import com.rop.utils.RopUtils;

/**
 * 支持的响应的格式类型
 */
public enum MessageFormat {

    xml, json, stream;

    public static MessageFormat getFormat(String value) {
        if (!RopUtils.hasText(value)) {
            return xml;
        } else {
            try {
                return MessageFormat.valueOf(value.toLowerCase());
            } catch (IllegalArgumentException e) {
                return xml;
            }
        }
    }

    public static boolean isValidFormat(String value) {
        if (!RopUtils.hasText(value)) {
            return true;
        }else{
            try {
                MessageFormat.valueOf(value.toLowerCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }

}
