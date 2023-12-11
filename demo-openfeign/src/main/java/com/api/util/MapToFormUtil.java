package com.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import lombok.SneakyThrows;

public class MapToFormUtil {

    private static final String QUERY_DELIMITER = "&";
    private static final String EQUAL_SIGN = "=";
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static String mapToFormData(Map<String, Object> map, Charset charset) {
        StringJoiner sj = new StringJoiner(String.valueOf(QUERY_DELIMITER));
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof List<?>) {
                String listFormData = listMapToFormData(entry.getKey(), convertToListOfMaps(entry.getValue()), charset);
                sj.add(listFormData);
            } else if (entry.getValue() instanceof Map<?, ?>) {
                String subMapFormData = subMapToFormData(entry, charset);
                sj.add(subMapFormData);
            } else {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                sj.add(encode(key, charset) + EQUAL_SIGN + value);
            }
        }
        return sj.toString();
    }

    public static String subMapToFormData(Map.Entry<String, Object> entry, Charset charset) {
        StringJoiner sj = new StringJoiner(String.valueOf(QUERY_DELIMITER));
        for (Map.Entry<String, Object> subEntry : convertToMapOfObject(entry.getValue()).entrySet()) {
            String key = entry.getKey() + "." + subEntry.getKey();
            String value = subEntry.getValue().toString();
            sj.add(encode(key, charset) + EQUAL_SIGN + encode(value, charset));
        }
        return sj.toString();
    }

    public static String entryToFormData(Map.Entry<String, Object> entry, Charset charset) {
        String key = entry.getKey();
        Object value = entry.getValue();

        if (value instanceof Collection<?>) {
            return listMapToFormData(key, convertToListOfMaps(value), charset);
        } else {
            return encode(key, charset) + EQUAL_SIGN + encode(value.toString(), charset);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertToMapOfObject(Object obj) {
        if (obj instanceof Map<?, ?>) {
            return (Map<String, Object>) obj;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> convertToListOfMaps(Object obj) {
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            List<Map<String, Object>> maps = new ArrayList<>();
            for (Object element : list) {
                if (element instanceof Map<?, ?>) {
                    maps.add((Map<String, Object>) element);
                } else {
                    return null;
                }
            }
            return maps;
        }
        return null;
    }

    public static String listMapToFormData(String prefix, List<Map<String, Object>> list, Charset charset) {
        StringJoiner sj = new StringJoiner(QUERY_DELIMITER);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = String.format("%s[%d].%s", prefix, i, entry.getKey());
                if (entry.getValue() instanceof Map<?, ?>) {
                    String subMapFormData = subMapToFormData(entry, charset);
                    sj.add(subMapFormData);
                } else {
                    String value = entry.getValue().toString();
                    sj.add(encode(key, charset) + EQUAL_SIGN + value);
                }
            }
        }
        return sj.toString();
    }

    @SneakyThrows
    public static String encode(Object string, Charset charset) {
        return URLEncoder.encode(string.toString(), charset.name());
    }

}