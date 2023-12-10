package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

import com.api.model.ApiReq;
import com.api.util.MapToFormUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinderTest {

    @Test
    public void demo() {
        Gson gson = new Gson();

        ApiReq apiReq = ApiReq.builder()
                .caseSeqno(123)
                .subReq(ApiReq.SubReq.builder()
                        .amt("amt001")
                        .mac("mac001")
                        .build())
                .build();
        String reqJson = gson.toJson(apiReq);
        log.debug("res: reqJson = {}", reqJson);
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> mapData = gson.fromJson(reqJson, mapType);
        log.debug("res: mapData = {}", mapData);
        log.debug("res: dataMap = {}",  MapToFormUtil.mapToFormData(mapData,StandardCharsets.UTF_8));
    }

    @Test
    public void testFormStringToApiReqObject() {
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("caseSeqno", "123");
        propertyValues.add("subReq.amt", "amt001");
        propertyValues.add("subReq.mac", "mac001");

        log.info("Form String: caseSeqno=123&subReq.amt=amt001&subReq.mac=mac001");

        ApiReq apiReq = new ApiReq();
        DataBinder binder = new DataBinder(apiReq);
        binder.bind(propertyValues);

        log.info("ApiReq Object: {}", apiReq);

        assertEquals(123, apiReq.getCaseSeqno());
        assertEquals("amt001", apiReq.getSubReq().getAmt());
        assertEquals("mac001", apiReq.getSubReq().getMac());
    }

}
