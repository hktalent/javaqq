package com.blee.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @Title: Template.java
* @Package com.cloudbility.common.template
* @Description: 简易模板,支持传入字符串和从文件中读入templateText
* @author blee
* @date 2012-10-10 下午4:52:08
* @version V1.0
 */
public class Template {
    
    private static String NEW_LINE = System.getProperty("line.separator");

    private Map<String, String> variables;

    private String templateText;

    public Template(String templateText) {
        this.variables = new HashMap<String, String>();
        this.templateText = templateText;
    }
    
    public Template(File file) throws IOException {
        this.variables = new HashMap<String, String>();
        this.templateText = readFile(file);
    }

    public Template set(String name, String value) {
        this.variables.put(name, value);
        return this;
    }
    
    public Template reset() {
        this.variables.clear();
        return this;
    }

    public String evaluate() {
        TemplateParse p = new TemplateParse();
        List<Segment> segments = p.parseSegments(templateText);
        return concatenate(segments);
    }

    private String concatenate(List<Segment> segments) {
        StringBuffer result = new StringBuffer();
        for (Segment segment : segments) {
            result.append(segment.evaluate(variables));
        }
        return result.toString();
    }
    
    private String readFile(File file) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuilder fileStr = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                fileStr.append(line);
                fileStr.append(NEW_LINE);
            }
            return fileStr.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }
    
}
