package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

import com.api.config.LocalDateTimeDeserializer;
import com.api.config.LocalDateTimeSerializer;
import com.api.model.ApiReq;
import com.api.util.MapToFormUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinderTest {

    @Test
    public void objectMapper() throws JsonMappingException, JsonProcessingException {
        ApiReq apiReq = ApiReq.builder()
                .caseSeqno(123)
                .verifyTime(LocalDateTime.now())
                .subReq(ApiReq.SubReq.builder()
                        .amt("amt001")
                        .mac("mac001")
                        .build())
                .build();


        Map<String, Object> data;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
        data = objectMapper.readValue(objectMapper.writeValueAsString(apiReq),
                    new TypeReference<Map<String, Object>>() {});
        log.debug("res: mapData = {}", data);
        log.debug("res: dataMap = {}",  MapToFormUtil.mapToFormData(data,StandardCharsets.UTF_8));
    }

    @Test
    public void testObjectMapperToApiReqObject() {
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("caseSeqno", "123");
        propertyValues.add("subReq.amt", "amt001");
        propertyValues.add("subReq.mac", "mac001");
        propertyValues.add("verifyTime", "2023-03-21T15:30:45.123");//2023-12-10T12:51:53.294

        log.info("Form String: caseSeqno=123&subReq.amt=amt001&subReq.mac=mac001&verifyTime=2023-12-10T12:51:53.294");

        ApiReq apiReq = new ApiReq();
        DataBinder binder = new DataBinder(apiReq);
        binder.bind(propertyValues);

        log.info("ApiReq Object: {}", apiReq);

        assertEquals(123, apiReq.getCaseSeqno());
        assertEquals("amt001", apiReq.getSubReq().getAmt());
        assertEquals("mac001", apiReq.getSubReq().getMac());
    }

    @Test
    public void demo() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        Gson gson = gsonBuilder.create();

        // Gson gson = new Gson();

        ApiReq apiReq = ApiReq.builder()
                .caseSeqno(123)
                .verifyTime(LocalDateTime.now())
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
