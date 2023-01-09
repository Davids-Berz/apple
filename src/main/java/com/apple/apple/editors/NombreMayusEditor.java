package com.apple.apple.editors;

import java.beans.PropertyEditorSupport;


public class NombreMayusEditor extends PropertyEditorSupport{
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        setValue(text.toUpperCase());
    }
}
