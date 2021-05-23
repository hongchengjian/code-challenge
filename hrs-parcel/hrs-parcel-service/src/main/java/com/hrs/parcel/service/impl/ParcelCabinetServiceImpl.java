package com.hrs.parcel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrs.common.model.Pagination;
import com.hrs.parcel.domain.ParcelCabinet;
import com.hrs.parcel.enums.CabinetStatusEnum;
import com.hrs.parcel.mapper.ParcelCabinetMapper;
import com.hrs.parcel.model.request.ParcelCabinetListCondition;
import com.hrs.parcel.model.vo.ParcelCabinetListVO;
import com.hrs.parcel.service.ParcelCabinetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParcelCabinetServiceImpl extends BaseServiceImpl<ParcelCabinetMapper, ParcelCabinet> implements ParcelCabinetService {
    @Override
    public Pagination<ParcelCabinetListVO> queryParcelCabinetList(ParcelCabinetListCondition condition, Long pageNumber, Long pageSize) {
        IPage<ParcelCabinet> page = new Page<>(pageNumber, pageSize);
        QueryWrapper<ParcelCabinet> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(condition.getCabinetNo())) {
            queryWrapper.eq("cabinet_no", condition.getCabinetNo());
        }

        if (CollectionUtils.isNotEmpty(condition.getCabinetStatus())) {
            queryWrapper.in("cabinet_status", condition.getCabinetStatus());
        }

        IPage<ParcelCabinet> iPage = baseMapper.selectPage(page, queryWrapper);
        List<ParcelCabinetListVO> results = null;
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            results = iPage.getRecords().stream().map(r -> {

                ParcelCabinetListVO vo = ParcelCabinetListVO.builder()
                        .cabinetNo(r.getCabinetNo())
                        .cabinetStatus(r.getCabinetStatus())
                        .build();
                return vo;
            }).collect(Collectors.toList());
        }
        return Pagination.<ParcelCabinetListVO>builder().pageNumber(pageNumber).pageSize(pageSize)
                .total(iPage.getTotal()).items(results == null ? new LinkedList<>() : results).build();
    }

    @Override
    public ParcelCabinet queryParcelCabinetByCabinetNo(String cabinetNo) {
        QueryWrapper<ParcelCabinet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cabinet_no", cabinetNo);
        ParcelCabinet parcelCabinet = baseMapper.selectOne(queryWrapper);
        return parcelCabinet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByLastStatus(ParcelCabinet parcelCabinet, CabinetStatusEnum lastStatus) {
        UpdateWrapper cabinetWrapper = new UpdateWrapper();
        // 更新前上一个状态 防串状态
        cabinetWrapper.eq("cabinet_status", lastStatus.getCode());
        cabinetWrapper.eq("cabinet_no", parcelCabinet.getCabinetNo());
        return update(parcelCabinet, cabinetWrapper);
    }
}
