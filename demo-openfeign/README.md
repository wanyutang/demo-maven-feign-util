# Demo Maven Feign Util

## 簡介
基於 Spring Boot 和 Maven Lab，專注於展示如何使用 `spring-cloud-starter-openfeign` 來實現微服務間的通信。

將演示如何設置和使用 Feign 客戶端來簡化 HTTP API 的呼叫。

## 開始前的準備
- Java 8 或以上版本
- Maven 3.6 或以上版本
- 基本的 Spring Boot 知識

## 安裝與啟動
Clone 專案後，使用 Maven 命令來啟動：
```bash
mvn spring-boot:run
```

## 架構解析
 aven 架構，包含以下主要部分：
- `pom.xml`：定義了專案依賴和插件。
- `src/main/java`：包含應用程序的源代碼。
- `src/main/resources`：包含應用程序的配置文件。

## 功能模塊
- **Feign 客戶端實現**：演示如何使用 Feign 進行服務間的呼叫。
- **錯誤處理**：包含基本的錯誤處理和異常捕獲機制。
- **日誌記錄**：展示如何在應用中實現日誌記錄。

## 參考來源
- [Spring Cloud OpenFeign 整合指南](https://gist.github.com/wanyutang/2747c64766d08a609837246559f3111b)