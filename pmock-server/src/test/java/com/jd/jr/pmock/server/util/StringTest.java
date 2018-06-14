package com.jd.jr.pmock.server.util;

import org.junit.Test;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-13
 * Time: 上午11:42
 */
public class StringTest {
    @Test
    public void wrap(){
        String text = "clouds;monokai;twilight;xcode;clouds_midnight;vibrant_ink;tomorrow_night_eighties;tomorrow_night_bright;tomorrow_night_blue;tomorrow_night;tomorrow;textmate;terminal;sqlserver;solarized_light;solarized_dark;solarized_dark;pastel_on_dark;mono_industrial;merbivore_soft;merbivore;kuroir;kr_theme;katzenmilch;iplastic;idle_fingers;gruvbox;gob;github;eclipse;dreamweaver;dracula;dawn;crimson_editor;cobalt;chrome;chaos;ambiance";
        String[] textArray = text.split(";");
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(" [");
        for(String mode:textArray){
            stringBuilder.append(" { text: '"+mode+"', value: '"+mode+"' },");
        }

        stringBuilder.append(" ]");
        System.out.println("------------");
        System.out.println(stringBuilder.toString());
    }
}
