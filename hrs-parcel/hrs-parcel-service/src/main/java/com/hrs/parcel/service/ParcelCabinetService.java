package com.hrs.parcel.service;

import com.hrs.common.model.Pagination;
import com.hrs.parcel.domain.ParcelCabinet;
import com.hrs.parcel.enums.CabinetStatusEnum;
import com.hrs.parcel.model.request.ParcelCabinetListCondition;
import com.hrs.parcel.model.vo.ParcelCabinetListVO;

public interface ParcelCabinetService extends BaseService<ParcelCabinet> {
    Pagination<ParcelCabinetListVO> queryParcelCabinetList(ParcelCabinetListCondition condition, Long pageNumber, Long pageSize);

    ParcelCabinet queryParcelCabinetByCabinetNo(String cabinetNo);

    Boolean updateByLastStatus(ParcelCabinet parcelCabinet, CabinetStatusEnum lastStatus);
}
