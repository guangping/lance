/**
 * 版权声明： 版权所有 违者必究 2012
 * 日    期：12-6-21
 */
package com.rop.session;

import com.rop.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public  abstract class AbstractSession implements Session {

    private Map<String, Object> attributes = new HashMap<String, Object>();

    @Override
    public void setAttribute(String name, Object obj) {
        markChanged();
        attributes.put(name, obj);
    }

    @Override
    public Object getAttribute(String name) {
        markChanged();
        return attributes.get(name);
    }

    @Override
    public Map<String, Object> getAllAttributes() {
        Map<String, Object> tempAttributes = new HashMap<String, Object>(attributes.size());
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            if (!Constants.SESSION_CHANGED.equals(entry.getKey())) {
                tempAttributes.put(entry.getKey(),entry.getValue());
            }
        }
        return tempAttributes;
    }

    @Override
    public void removeAttribute(String name) {
        markChanged();
        attributes.remove(name);
    }

    @Override
    public boolean isChanged() {
        return attributes.containsKey(Constants.SESSION_CHANGED);
    }

    private void markChanged(){
        attributes.put(Constants.SESSION_CHANGED,Boolean.TRUE);
    }
}

