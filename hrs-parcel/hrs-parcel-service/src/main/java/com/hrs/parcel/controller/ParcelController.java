package com.hrs.parcel.controller;

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
import com.hrs.parcel.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 对内业务-包裹
 *
 * @author CJ
 * @since 1.0
 */
@RestController
public class ParcelController {

    @Autowired
    private ParcelService parcelService;

    /**
     * 分页查寄存柜：单个、全部、指定状态（单个、多个勾选）
     *
     * @param condition
     * @param pageNumber
     * @param pageSize
     * @return Pagination<ParcelCabinetListVO>
     */
    @RequestMapping("/internal/parcel/cabinet/list")
    public Pagination<ParcelCabinetListVO> queryParcelCabinetList(ParcelCabinetListCondition condition, @RequestParam(name = "pageNumber", defaultValue = "1") Long pageNumber, @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return parcelService.queryParcelCabinetList(condition, pageNumber, pageSize);
    }

    /**
     * 分页查存包裹记录：根据各种id、NO精确查、名字模糊、手机号模糊、时间区间、状态（勾一个还是几个）
     *
     * @param condition
     * @param pageNumber
     * @param pageSize
     * @return Pagination<ParcelDepositListVO>
     */
    @RequestMapping("/internal/parcel/deposit/list")
    public Pagination<ParcelDepositListVO> queryParcelDepositList(ParcelDepositListCondition condition, @RequestParam(name = "pageNumber", defaultValue = "1") Long pageNumber, @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return parcelService.queryParcelDepositList(condition, pageNumber, pageSize);
    }

    /**
     * 存
     *
     * @param request
     * @return ParcelDepositVO
     */
    @PutMapping("/internal/parcel/deposit")
    public InternalResponse<ParcelDepositVO> parcelDeposit(@Valid @RequestBody ParcelDepositRequest request) {
        ParcelDepositVO vo = parcelService.parcelDeposit(request);
        return InternalResponse.success(vo);
    }

    /**
     * 取
     *
     * @param request
     * @return ParcelPickUpVO
     */
    @PutMapping("/internal/parcel/pickup")
    public InternalResponse<ParcelPickUpVO> parcelPickUp(@Valid @RequestBody ParcelPickUpRequest request) {
        ParcelPickUpVO vo = parcelService.parcelPickUp(request);
        return InternalResponse.success(vo);
    }

    // TODO 丢失登记、清理寄存柜、赔偿、收费、极端情况后门等拓展

}
