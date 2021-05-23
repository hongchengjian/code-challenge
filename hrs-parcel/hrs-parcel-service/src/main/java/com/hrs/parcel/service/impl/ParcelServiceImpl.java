package com.hrs.parcel.service.impl;

import com.hrs.common.exception.CustomException;
import com.hrs.common.model.Pagination;
import com.hrs.parcel.domain.ParcelCabinet;
import com.hrs.parcel.domain.ParcelDeposit;
import com.hrs.parcel.domain.ParcelPickup;
import com.hrs.parcel.enums.CabinetStatusEnum;
import com.hrs.parcel.enums.ParcelDepositStatusEnum;
import com.hrs.parcel.enums.ParcelResultEnum;
import com.hrs.parcel.model.request.ParcelCabinetListCondition;
import com.hrs.parcel.model.request.ParcelDepositRequest;
import com.hrs.parcel.model.request.ParcelDepositListCondition;
import com.hrs.parcel.model.request.ParcelPickUpRequest;
import com.hrs.parcel.model.vo.ParcelCabinetListVO;
import com.hrs.parcel.model.vo.ParcelDepositListVO;
import com.hrs.parcel.model.vo.ParcelDepositVO;
import com.hrs.parcel.model.vo.ParcelPickUpVO;
import com.hrs.parcel.service.ParcelCabinetService;
import com.hrs.parcel.service.ParcelDepositService;
import com.hrs.parcel.service.ParcelPickupService;
import com.hrs.parcel.service.ParcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class ParcelServiceImpl implements ParcelService {
    @Autowired
    private ParcelCabinetService parcelCabinetService;
    @Autowired
    private ParcelDepositService parcelDepositService;
    @Autowired
    private ParcelPickupService parcelPickupService;

    @Override
    public Pagination<ParcelCabinetListVO> queryParcelCabinetList(ParcelCabinetListCondition condition, Long pageNumber, Long pageSize) {
        // TODO 验证查询条件数据值正确性
        return parcelCabinetService.queryParcelCabinetList(condition, pageNumber, pageSize);
    }

    @Override
    public Pagination<ParcelDepositListVO> queryParcelDepositList(ParcelDepositListCondition condition, Long pageNumber, Long pageSize) {
        // TODO 验证查询条件数据值正确性
        return parcelDepositService.queryParcelDepositList(condition, pageNumber, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParcelDepositVO parcelDeposit(ParcelDepositRequest request) {
        ParcelDepositVO vo = new ParcelDepositVO();
        String cabinetNo = request.getCabinetNo();

        // 根据寄存柜查单个
        ParcelCabinet parcelCabinet = parcelCabinetService.queryParcelCabinetByCabinetNo(cabinetNo);
        if (null == parcelCabinet) {
            throw new CustomException(ParcelResultEnum.CABINET_IS_EMPTY);
        }
        if (null != parcelCabinet.getCabinetStatus() && parcelCabinet.getCabinetStatus() != CabinetStatusEnum.AVAILABLE.getCode()) {
            throw new CustomException(ParcelResultEnum.CABINET_UNAVAILABLE);
        }

        // 插入单个
        String depositNo = "D" + UUID.randomUUID().toString();
        ParcelDeposit parcelDeposit = ParcelDeposit.builder()
                .depositNo(depositNo)
                .cabinetNo(cabinetNo)
                .adminId(request.getAdminId())
                .adminMobile(request.getAdminMobile())
                .adminName(request.getAdminName())
                .depositorMobile(request.getDepositorMobile())
                .depositorName(request.getDepositorName())
                .remark(request.getRemark())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
        parcelDepositService.save(parcelDeposit);

        // 更新寄存柜状态
        parcelCabinet.setCabinetStatus(CabinetStatusEnum.OCCUPIED.getCode());
        parcelCabinetService.updateByLastStatus(parcelCabinet, CabinetStatusEnum.AVAILABLE);

        // TODO 返回赋值
        vo.setDepositNo(depositNo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParcelPickUpVO parcelPickUp(ParcelPickUpRequest request) {
        ParcelPickUpVO vo = new ParcelPickUpVO();
        // 校验寄存柜状态
        String cabinetNo = request.getCabinetNo();
        ParcelCabinet parcelCabinet = parcelCabinetService.queryParcelCabinetByCabinetNo(cabinetNo);
        if (null == parcelCabinet) {
            throw new CustomException(ParcelResultEnum.CABINET_IS_EMPTY);
        }
        if (null != parcelCabinet.getCabinetStatus() && parcelCabinet.getCabinetStatus() == CabinetStatusEnum.UNAVAILABLE.getCode()) {
            throw new CustomException(ParcelResultEnum.CABINET_UNAVAILABLE);
        }

        // 查存包裹存记录
        String depositNo = request.getDepositNo();
        ParcelDeposit parcelDeposit = parcelDepositService.queryParcelDepositByDepositNo(depositNo);
        if (null == parcelDeposit) {
            throw new CustomException(ParcelResultEnum.PARCEL_DEPOSIT_IS_EMPTY);
        }

        // 已取件或逾期取件后 不可多次取
        if (null != parcelDeposit.getDepositStatus() && (ParcelDepositStatusEnum.PICKED_UP.getCode() == parcelDeposit.getDepositStatus()
                || ParcelDepositStatusEnum.OVERDUE_PICKED_UP.getCode() == parcelDeposit.getDepositStatus())) {
            throw new CustomException(ParcelResultEnum.PARCEL_DEPOSIT_PICKED_UP);
        }

        // 寄存柜状态是空闲
        if (null != parcelCabinet.getCabinetStatus() && parcelCabinet.getCabinetStatus() == CabinetStatusEnum.AVAILABLE.getCode()) {
            throw new CustomException(ParcelResultEnum.PARCEL_LOST);
        }

        // 正常取件 寄存中 -》已取件
        if (null != parcelDeposit.getDepositStatus() && ParcelDepositStatusEnum.OCCUPIED.getCode() == parcelDeposit.getDepositStatus() &&
                null != parcelCabinet.getCabinetStatus() && parcelCabinet.getCabinetStatus() == CabinetStatusEnum.OCCUPIED.getCode()) {
            parcelDeposit.setDepositStatus(ParcelDepositStatusEnum.PICKED_UP.getCode());
            parcelDepositService.updateByLastStatus(parcelDeposit, ParcelDepositStatusEnum.OCCUPIED);

            // 释放存储柜
            parcelCabinet.setCabinetStatus(CabinetStatusEnum.AVAILABLE.getCode());
            parcelCabinetService.updateByLastStatus(parcelCabinet, CabinetStatusEnum.OCCUPIED);
        }

        // 逾期取件 已逾期 -》逾期取件
        if (null != parcelDeposit.getDepositStatus() && ParcelDepositStatusEnum.OVERDUE.getCode() == parcelDeposit.getDepositStatus() &&
                null != parcelCabinet.getCabinetStatus() && parcelCabinet.getCabinetStatus() == CabinetStatusEnum.OVERDUE.getCode()) {
            parcelDeposit.setDepositStatus(ParcelDepositStatusEnum.OVERDUE_PICKED_UP.getCode());
            parcelDepositService.updateByLastStatus(parcelDeposit, ParcelDepositStatusEnum.OVERDUE);

            // 释放存储柜
            parcelCabinet.setCabinetStatus(CabinetStatusEnum.AVAILABLE.getCode());
            parcelCabinetService.updateByLastStatus(parcelCabinet, CabinetStatusEnum.OVERDUE);
        }


        // 创建取记录
        String pickupNo = "P" + UUID.randomUUID().toString();
        ParcelPickup parcelPickup = ParcelPickup.builder()
                .pickupNo(pickupNo)
                .pickupType(request.getPickupType())
                .depositNo(request.getDepositNo())
                .adminId(request.getAdminId())
                .adminMobile(request.getAdminMobile())
                .adminName(request.getAdminName())
                .recipientMobile(request.getRecipientMobile())
                .recipientName(request.getRecipientName())
                .build();
        parcelPickupService.save(parcelPickup);

        // TODO 返回赋值
        vo.setPickupNo(pickupNo);
        return vo;
    }
}
