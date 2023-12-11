package com.api.model;


import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiReq {

    private int caseSeqno;
    private SubReq subReq;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime verifyTime;
    private String[] arr;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubReq {
        private String amt;
        private String mac;
    }
    
}