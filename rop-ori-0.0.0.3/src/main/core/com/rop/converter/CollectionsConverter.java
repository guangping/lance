/**
 * 版权声明： 版权所有 违者必究 2012
 * 日    期：12-6-8
 */
package com.rop.converter;

import com.rop.request.RopConverter;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: stamen
 * Date: 13-10-25
 * Time: 上午11:54
 * To change this template use File | Settings | File Templates.
 */
public class CollectionsConverter implements RopConverter<String, Collection> {

    @Override
    public String unconvert(Collection target) {
        return null;
    }

    @Override
    public Class<String> getSourceClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Class<Collection> getTargetClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection convert(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
