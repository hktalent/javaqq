package com.blee.template;

import java.util.Map;

public interface Segment {

    public String evaluate(Map<String, String> variables);

}
