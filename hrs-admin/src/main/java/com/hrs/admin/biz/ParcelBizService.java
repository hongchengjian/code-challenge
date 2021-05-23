package com.hrs.admin.biz;

import com.hrs.common.exception.CustomException;
import com.hrs.common.model.InternalResponse;
import com.hrs.parcel.model.request.ParcelDepositListCondition;
import com.hrs.common.model.Pagination;
import com.hrs.parcel.client.ParcelFeignClient;
import com.hrs.parcel.model.request.ParcelDepositRequest;
import com.hrs.parcel.model.request.ParcelPickUpRequest;
import com.hrs.parcel.model.request.ParcelCabinetListCondition;
import com.hrs.parcel.model.vo.ParcelCabinetListVO;
import com.hrs.parcel.model.vo.ParcelDepositListVO;
import com.hrs.parcel.model.vo.ParcelDepositVO;
import com.hrs.parcel.model.vo.ParcelPickUpVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Feign Client-》内部服务 业务处理
 *
 * @author CJ
 * @since 1.0
 */
@Slf4j
@Service
public class ParcelBizService {

    @Resource
    private ParcelFeignClient parcelFeignClient;

    public Pagination<ParcelCabinetListVO> queryParcelCabinetList(ParcelCabinetListCondition condition, Long pageNumber, Long pageSize) {
        return parcelFeignClient.queryParcelCabinetList(condition, pageNumber, pageSize);
    }

    public Pagination<ParcelDepositListVO> queryParcelDepositList(ParcelDepositListCondition request, Long pageNumber, Long pageSize) {
        return parcelFeignClient.queryParcelDepositList(request, pageNumber, pageSize);
    }

    public ParcelDepositVO parcelDeposit(ParcelDepositRequest record) {
        InternalResponse<ParcelDepositVO> response = parcelFeignClient.parcelDeposit(record);
        if (!response.isSuccess()) {
            throw new CustomException(response.getCode(), response.getMessage());
        }
        return response.getResult();
    }

    public ParcelPickUpVO parcelPickUp(ParcelPickUpRequest request) {
        InternalResponse<ParcelPickUpVO> response = parcelFeignClient.parcelPickUp(request);
        if (!response.isSuccess()) {
            throw new CustomException(response.getCode(), response.getMessage());
        }
        return response.getResult();
    }

}
