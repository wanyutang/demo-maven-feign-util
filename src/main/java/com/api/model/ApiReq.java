package com.api.model;


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

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubReq {
        private String amt;
        private String mac;
    }
    
}
