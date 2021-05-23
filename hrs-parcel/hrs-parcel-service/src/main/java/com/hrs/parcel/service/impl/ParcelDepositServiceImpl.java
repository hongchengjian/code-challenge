package com.hrs.parcel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrs.common.model.Pagination;
import com.hrs.parcel.domain.ParcelDeposit;
import com.hrs.parcel.enums.ParcelDepositStatusEnum;
import com.hrs.parcel.mapper.ParcelDepositMapper;
import com.hrs.parcel.model.request.ParcelDepositListCondition;
import com.hrs.parcel.model.vo.ParcelDepositListVO;
import com.hrs.parcel.service.ParcelDepositService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParcelDepositServiceImpl extends BaseServiceImpl<ParcelDepositMapper, ParcelDeposit> implements ParcelDepositService {
    @Override
    public Pagination<ParcelDepositListVO> queryParcelDepositList(ParcelDepositListCondition condition, Long pageNumber, Long pageSize) {
        IPage<ParcelDeposit> page = new Page<>(pageNumber, pageSize);
        QueryWrapper<ParcelDeposit> queryWrapper = new QueryWrapper<>();

        if (null != condition.getAdminId()) {
            queryWrapper.eq("admin_id", condition.getAdminId());
        }
        if (StringUtils.isNotEmpty(condition.getAdminMobile())) {
            queryWrapper.like("admin_mobile", condition.getAdminMobile());
        }
        if (StringUtils.isNotEmpty(condition.getAdminName())) {
            queryWrapper.like("admin_name", condition.getAdminName());
        }
        if (StringUtils.isNotEmpty(condition.getDepositorMobile())) {
            queryWrapper.like("depositor_mobile", condition.getDepositorMobile());
        }
        if (StringUtils.isNotEmpty(condition.getDepositorName())) {
            queryWrapper.like("depositor_name", condition.getDepositorName());
        }
        if (CollectionUtils.isNotEmpty(condition.getDepositStatus())) {
            queryWrapper.in("deposit_status", condition.getDepositStatus());
        }
        if (null != condition.getStartTime()) {
            queryWrapper.ge("start_time", condition.getStartTime());
        }
        if (null != condition.getEndTime()) {
            queryWrapper.le("end_time", condition.getEndTime());
        }
        queryWrapper.orderByDesc("update_time");

        IPage<ParcelDeposit> iPage = baseMapper.selectPage(page, queryWrapper);
        List<ParcelDepositListVO> results = null;
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            results = iPage.getRecords().stream().map(r -> {
                ParcelDepositListVO vo = ParcelDepositListVO.builder()
                        .adminId(r.getAdminId())
                        .adminMobile(r.getAdminMobile())
                        .adminName(r.getAdminName())
                        .cabinetNo(r.getCabinetNo())
                        .depositNo(r.getDepositNo())
                        .depositorMobile(r.getDepositorMobile())
                        .depositStatus(r.getDepositStatus())
                        .depositorName(r.getDepositorName())
                        .remark(r.getRemark())
                        .endTime(r.getEndTime())
                        .startTime(r.getStartTime())
                        .build();

                return vo;
            }).collect(Collectors.toList());
        }
        return Pagination.<ParcelDepositListVO>builder().pageNumber(pageNumber).pageSize(pageSize)
                .total(iPage.getTotal()).items(results == null ? new LinkedList<>() : results).build();

    }

    @Override
    public ParcelDeposit queryParcelDepositByDepositNo(String depositNo) {
        QueryWrapper<ParcelDeposit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deposit_no", depositNo);
        ParcelDeposit parcelDeposit = baseMapper.selectOne(queryWrapper);
        return parcelDeposit;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByLastStatus(ParcelDeposit parcelDeposit, ParcelDepositStatusEnum lastStatus) {
        UpdateWrapper depositWrapper = new UpdateWrapper();
        // 更新前上一个状态 防串状态
        depositWrapper.eq("deposit_status", lastStatus.getCode());
        return update(parcelDeposit, depositWrapper);
    }
}
