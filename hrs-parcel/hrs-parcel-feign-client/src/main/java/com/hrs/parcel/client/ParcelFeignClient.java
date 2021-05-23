package com.hrs.parcel.client;

import com.hrs.common.model.InternalResponse;
import com.hrs.common.model.Pagination;
import com.hrs.parcel.model.request.ParcelCabinetListCondition;
import com.hrs.parcel.model.request.ParcelDepositRequest;
import com.hrs.parcel.model.request.ParcelDepositListCondition;
import com.hrs.parcel.model.request.ParcelPickUpRequest;
import com.hrs.parcel.model.vo.ParcelCabinetListVO;
import com.hrs.parcel.model.vo.ParcelDepositListVO;
import com.hrs.parcel.model.vo.ParcelDepositVO;
import com.hrs.parcel.model.vo.ParcelPickUpVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "hrs-parcel-service")
public interface ParcelFeignClient {

    /**
     * 查存储柜
     *
     * @param condition  查询条件
     * @param pageNumber 分页码
     * @param pageSize   分页大小
     * @return Pagination<ParcelCabinetListVO>
     */
    @RequestMapping(value = "/internal/parcel/cabinet/list")
    Pagination<ParcelCabinetListVO> queryParcelCabinetList(ParcelCabinetListCondition condition, @RequestParam(name = "pageNumber", defaultValue = "1") Long pageNumber, @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize);

    /**
     * 查包裹存储记录
     *
     * @param condition  查询条件
     * @param pageNumber 分页码
     * @param pageSize   分页大小
     * @return Pagination<ParcelDepositListVO>
     */
    @RequestMapping(value = "/internal/parcel/deposit/list")
    Pagination<ParcelDepositListVO> queryParcelDepositList(ParcelDepositListCondition condition, @RequestParam(name = "pageNumber") Long pageNumber, @RequestParam(name = "pageSize") Long pageSize);

    /**
     * 存包裹
     *
     * @param request
     * @return ParcelDepositVO
     */
    @RequestMapping(value = "/internal/parcel/deposit", method = RequestMethod.PUT)
    InternalResponse<ParcelDepositVO> parcelDeposit(ParcelDepositRequest request);

    /**
     * 取包裹
     *
     * @param request
     * @return ParcelPickUpVO
     */
    @RequestMapping(value = "/internal/parcel/pickup", method = RequestMethod.PUT)
    InternalResponse<ParcelPickUpVO> parcelPickUp(ParcelPickUpRequest request);

}
