package com.jay.hadoop.io;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by jay on 16/4/17.
 */
public class StringTextComparisonTest {
    @Test
    public void string() throws UnsupportedEncodingException {
        String s = "\u0041\u00DF\u6771\uD801\uDC00";
        assertThat(s.length(), is(5));
        assertThat(s.getBytes("UTF-8").length, is(10));

        assertThat(s.indexOf("\u0041"), is(0));

        assertThat(s.charAt(0), is('\u0041'));

        assertThat(s.codePointAt(0), is(0x0041));
    }

    @Test
    public void text() throws UnsupportedEncodingException {
        Text text = new Text("\u0041\u00DF\u6771\uD801\uDC00");
        assertThat(text.getLength(), is(10));

        assertThat(text.find("\u0041"), is(0));

        assertThat(text.charAt(0), is(0x0041));
    }
}
