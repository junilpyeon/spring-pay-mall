package com.jpabook.jpashop.service;

import com.jpabook.jpashop.model.response.CommonResult;
import com.jpabook.jpashop.model.response.ListResult;
import com.jpabook.jpashop.model.response.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    // enum���� api ��û ����� ���� code, message�� �����մϴ�.
    public enum CommonResponse {
        SUCCESS(0, "�����Ͽ����ϴ�.");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
    // ���ϰ� ����� ó���ϴ� �޼ҵ�
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    // ���߰� ����� ó���ϴ� �޼ҵ�
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    // ���� ����� ó���ϴ� �޼ҵ�
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }
    // ���� ����� ó���ϴ� �޼ҵ�
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    // ��� �𵨿� api ��û ���� �����͸� �������ִ� �޼ҵ�
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}
