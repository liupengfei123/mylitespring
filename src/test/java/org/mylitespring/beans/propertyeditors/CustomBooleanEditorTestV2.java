package org.mylitespring.beans.propertyeditors;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomBooleanEditorTestV2 {
    @Test
    public void testConvertStringToBoolean() {
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        assertTrue((Boolean) editor.getValue());
        editor.setAsText("false");
        assertFalse((Boolean) editor.getValue());

        editor.setAsText("on");
        assertTrue((Boolean) editor.getValue());
        editor.setAsText("off");
        assertFalse((Boolean) editor.getValue());


        editor.setAsText("yes");
        assertTrue((Boolean) editor.getValue());
        editor.setAsText("no");
        assertFalse((Boolean) editor.getValue());

        try {
            editor.setAsText("aabbcc");
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

}