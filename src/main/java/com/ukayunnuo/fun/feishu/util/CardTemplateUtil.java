package com.ukayunnuo.fun.feishu.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 飞书卡片模板工具类
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@Slf4j
public class CardTemplateUtil {

    private static final String TEMPLATE_FOLDER = "feishu-robot-card-template";

    private static final String TEMPLATE_FILE_END = "Template.json";
    private static final Map<String, JSONObject> CARD_TEMPLATE_MAP = new HashMap<>();

    static {
        init();
    }

    /**
     * 初始化加载模板文件
     */
    private static void init() {
        try {
            File[] files = new ClassPathResource(TEMPLATE_FOLDER).getFile().listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (Boolean.FALSE.equals(file.getName().endsWith(TEMPLATE_FILE_END))) {
                    continue;
                }
                CARD_TEMPLATE_MAP.put(FileUtil.getPrefix(file), JSONObject.parseObject(FileUtil.readUtf8String(file)));
            }
        } catch (Exception e) {
            log.error("CardTemplateUtil init error! case:{}", e.getMessage(), e);
        }
    }

    /**
     * 设置map模板内容
     *
     * @param templateName 模板名称
     * @param value        值
     */
    public static void put(String templateName, JSONObject value) {
        CARD_TEMPLATE_MAP.put(templateName, value);
    }

    /**
     * 获取map模板内容
     *
     * @param templateName 模板名称
     * @return {@link JSONObject}
     */
    public static JSONObject get(String templateName) {
        if (MapUtil.isEmpty(CARD_TEMPLATE_MAP)) {
            return JSONObject.of();
        }
        return CARD_TEMPLATE_MAP.get(templateName);
    }

    /**
     * 获取map模板内容
     *
     * @param templateName 模板名称
     * @param clazz        clazz
     * @return {@link T}
     */
    public static <T> T get(String templateName, Class<T> clazz) {
        JSONObject jsonObject = get(templateName);
        if (Objects.isNull(jsonObject)) {
            return ReflectUtil.newInstance(clazz);
        }
        return JSONObject.parseObject(jsonObject.toJSONString(), clazz);
    }


    /**
     * 获取map模板内容
     *
     * @param templateName 模板名称
     * @param clazz        clazz
     * @param param        替换参数
     * @return {@link T}
     */
    public static <T, R> T get(String templateName, Class<T> clazz, R param) {
        T t = get(templateName, clazz);

        if (Objects.isNull(param)) {
            return t;
        }
        String jsonString = JSONObject.toJSONString(t);

        if (StrUtil.isBlank(jsonString)){
            return t;
        }

        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(param));
        for (String key : jsonObject.keySet()) {
            String strKey = StrUtil.format("${{}}", key);
            jsonString = StrUtil.replace(jsonString, strKey, jsonObject.getString(key));
        }

        return JSONObject.parseObject(jsonString, clazz);
    }

}
