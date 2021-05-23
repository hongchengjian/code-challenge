package com.hrs.admin.controller;

import com.hrs.admin.biz.ParcelBizService;
import com.hrs.common.api.ResultCode;
import com.hrs.common.api.ResultJson;
import com.hrs.common.exception.CustomException;
import com.hrs.common.model.Pagination;
import com.hrs.common.util.JsonUtil;
import com.hrs.parcel.model.request.ParcelCabinetListCondition;
import com.hrs.parcel.model.request.ParcelDepositListCondition;
import com.hrs.parcel.model.request.ParcelDepositRequest;
import com.hrs.parcel.model.request.ParcelPickUpRequest;
import com.hrs.parcel.model.vo.ParcelCabinetListVO;
import com.hrs.parcel.model.vo.ParcelDepositVO;
import com.hrs.parcel.model.vo.ParcelPickUpVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 包裹相关
 *
 * @author CJ
 * @since 1.0
 */
@RestController
@Api(tags = "包裹相关API")
@Slf4j
public class ParcelController extends BaseController {

    @Autowired
    private ParcelBizService parcelBizService;

    /**
     * 查寄存柜
     *
     * @param condition
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查寄存柜", notes = "分页查寄存柜：单个、全部、指定状态（单个、多个勾选)")
    @GetMapping("/parcel/cabinet")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cabinetNo", value = "寄存柜编号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cabinetStatus", value = "寄存柜状态0-空闲、1-占用、2-逾期、3-不可用（多状态勾选）", required = false, dataType = "Integer", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "页码", required = false, dataType = "Long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "条数", required = false, dataType = "Long", paramType = "query", defaultValue = "10")
    })
    public ResultJson<Pagination<ParcelCabinetListVO>> parcelCabinetList(ParcelCabinetListCondition condition, @RequestParam(name = "pageNumber", defaultValue = "1") Long pageNumber,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        log.info("queryParcelCabinetList start,condition={},pageNumber={},pageSize={}", JsonUtil.toString(condition), pageNumber, pageSize);
        Pagination<ParcelCabinetListVO> result = parcelBizService.queryParcelCabinetList(condition, pageNumber, pageSize);
        log.info("queryParcelCabinetList end,result={}", JsonUtil.toString(result));
        return ResultJson.success(result);
    }

    /**
     * 查包裹存储记录
     */
    @ApiOperation(value = "查包裹存储记录", notes = "分页查存包裹记录：根据各种id、NO精确查、名字模糊、手机号模糊、时间区间、状态（勾一个还是几个）")
    @GetMapping("/parcel/deposit/list")
    // TODO Swagger入参
    public ResultJson parcelDepsositList(ParcelDepositListCondition request, @RequestParam(name = "pageNumber", defaultValue = "1") Long pageNumber,
                                         @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return ResultJson.success(parcelBizService.queryParcelDepositList(request, pageNumber, pageSize));
    }

    /**
     * 存包裹
     *
     * @param request
     * @return ResultJson<ParcelDepositVO>
     */
    @ApiOperation(value = "存包裹", notes = "创建包裹存储记录并占用寄存柜")
    @PutMapping("/parcel/deposit")
    // TODO Swagger入参
    public ResultJson<ParcelDepositVO> parcelDeposit(@Valid @RequestBody ParcelDepositRequest request) {
        ParcelDepositVO vo = null;
        try {
            vo = parcelBizService.parcelDeposit(request);
        } catch (CustomException e) {
            return e.getResultJson();
        } catch (Exception e) {
            return ResultJson.failure(ResultCode.SYSTEM_ERROR.getCode(), e.getMessage());
        }
        return ResultJson.success(vo);
    }

    /**
     * 取包裹
     *
     * @param request
     * @return ResultJson<ParcelPickUpVO>
     */
    @ApiOperation(value = "取包裹", notes = "取包裹并释放寄存柜且创建取包裹记录")
    @PutMapping("/parcel/pickup")
    // TODO Swagger入参
    public ResultJson<ParcelPickUpVO> parcelPickUp(@Valid @RequestBody ParcelPickUpRequest request) {
        ParcelPickUpVO vo = null;
        try {
            vo = parcelBizService.parcelPickUp(request);
        } catch (CustomException e) {
            return e.getResultJson();
        } catch (Exception e) {
            return ResultJson.failure(ResultCode.SYSTEM_ERROR.getCode(), e.getMessage());
        }
        return ResultJson.success(vo);
    }

}
