package com.omnicrola.panoptes.export.xls.wrappers;

/**
 * Created by omnic on 11/7/2015.
 */
public interface ICell {

    void setValue(String value);

    void setValue(float value);

    void clear();

    void setFormula(String formula);
}
