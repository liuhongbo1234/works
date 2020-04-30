package com.xiaomi.mi_deepclean.widget.formatters;

import com.xiaomi.mi_deepclean.widget.Formatter;

public class NoFormatter implements Formatter {
    @Override
    public String format(String prefix, String suffix, float value) {
        return prefix + value + suffix;
    }
}
