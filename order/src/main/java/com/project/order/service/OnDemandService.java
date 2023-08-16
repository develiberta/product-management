package com.project.order.service;

import com.project.lib.constant.CommonConstant;
import com.project.lib.dto.OnDemandResponseDto;
import com.project.lib.service.BaseService;
import com.project.lib.utils.HTTPUtils;
import com.project.order.config.OrderAppConfig;
import lombok.Data;
import lombok.ToString;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class OnDemandService extends BaseService {

    @Autowired
    OrderAppConfig orderAppConfig;

    @Data
    @ToString
    public class HttpJsonResponse implements HTTPUtils.OnHttpResponseListener {
        private Map<String, Object> response;
        private int httpStatus;

        /* */
        private void assign(HttpResponse response) throws Exception {
            this.httpStatus = response.getStatusLine().getStatusCode();

            if (this.httpStatus == HttpStatus.SC_OK) {
                /* */
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                try{
                    IOUtils.copy(response.getEntity().getContent(), out);
                } catch(IOException e){
                    throw new RuntimeException(e);
                }

                String content = new String(out.toByteArray());
                this.response = jacksonMapper.jsonStringToMap(content);
            } else {
                OnDemandResponseDto dto = new OnDemandResponseDto();

                this.response = jacksonMapper.objectToMap(dto);
            }
        }

        @Override
        public void onResponse(HttpResponse response) throws Exception {
            // TODO Auto-generated method stub
            assign(response);
        }
    }

    public OnDemandResponseDto demandToServerByPost(String urlSuffix, Object requestBody) throws Exception {

        OnDemandResponseDto response;

        try {
            String productServer = orderAppConfig.getProductServer() + urlSuffix;

            logger.info("Product 서버를 호출합니다. (POST)");
            logger.info(" - serverUrl={})", productServer);

            byte[] content = jacksonMapper.objectToJsonString(requestBody).getBytes(CommonConstant.UTF8);

            logger.info(" - content={}", content);

            HttpJsonResponse mapResponse = new HttpJsonResponse();

            HTTPUtils.post(productServer,
                    content,
                    CommonConstant.UTF8,
                    null,
                    "application/json",
                    null,
                    HTTPUtils.DEFAULT_TIMEOUT,
                    HTTPUtils.DEFAULT_SOCKTIMEOUT,
                    mapResponse
            );

            response = jacksonMapper.mapToClass(mapResponse.getResponse(), OnDemandResponseDto.class);

        } catch(Exception e) {
            logger.error(e.toString());
            throw e;
        }

        return response;
    }

    public OnDemandResponseDto demandToServerByGet(String urlSuffix, Map queryParam) throws Exception {

        OnDemandResponseDto response;

        try {
            String productServer = orderAppConfig.getProductServer() + urlSuffix;

            logger.info("Product 서버를 호출합니다. (GET)");
            logger.info(" - serverUrl={})", productServer);
            logger.info(" - queryParam={}", queryParam);

            HttpJsonResponse mapResponse = new HttpJsonResponse();

            HTTPUtils.get(productServer,
                    queryParam,
                    CommonConstant.UTF8,
                    null,
                    HTTPUtils.DEFAULT_TIMEOUT,
                    HTTPUtils.DEFAULT_SOCKTIMEOUT,
                    mapResponse
            );

            response = jacksonMapper.mapToClass(mapResponse.getResponse(), OnDemandResponseDto.class);

        } catch(Exception e) {
            logger.error(e.toString());
            throw e;
        }

        return response;
    }

    public OnDemandResponseDto demandToServerByPut(String urlSuffix, Map queryParam, Object requestBody) throws Exception {

        OnDemandResponseDto response;

        try {
            String productServer = orderAppConfig.getProductServer() + urlSuffix;

            logger.info("Product 서버를 호출합니다. (PUT)");
            logger.info(" - serverUrl={})", productServer);

            byte[] content = jacksonMapper.objectToJsonString(requestBody).getBytes(CommonConstant.UTF8);

            logger.info(" - queryParam={}", queryParam);
            logger.info(" - content={}", content);

            HttpJsonResponse mapResponse = new HttpJsonResponse();

            HTTPUtils.put(productServer,
                    content,
                    CommonConstant.UTF8,
                    queryParam,
                    "application/json",
                    null,
                    HTTPUtils.DEFAULT_TIMEOUT,
                    HTTPUtils.DEFAULT_SOCKTIMEOUT,
                    mapResponse
            );

            response = jacksonMapper.mapToClass(mapResponse.getResponse(), OnDemandResponseDto.class);

        } catch(Exception e) {
            logger.error(e.toString());
            throw e;
        }

        return response;
    }

}
