package com.hrs.parcel.service;

import com.hrs.common.model.Pagination;
import com.hrs.parcel.model.request.ParcelCabinetListCondition;
import com.hrs.parcel.model.request.ParcelDepositRequest;
import com.hrs.parcel.model.request.ParcelDepositListCondition;
import com.hrs.parcel.model.request.ParcelPickUpRequest;
import com.hrs.parcel.model.vo.ParcelCabinetListVO;
import com.hrs.parcel.model.vo.ParcelDepositListVO;
import com.hrs.parcel.model.vo.ParcelDepositVO;
import com.hrs.parcel.model.vo.ParcelPickUpVO;

public interface ParcelService {

    Pagination<ParcelCabinetListVO> queryParcelCabinetList(ParcelCabinetListCondition condition, Long pageNumber, Long pageSize);

    Pagination<ParcelDepositListVO> queryParcelDepositList(ParcelDepositListCondition condition, Long pageNumber, Long pageSize);

    ParcelDepositVO parcelDeposit(ParcelDepositRequest request);

    ParcelPickUpVO parcelPickUp(ParcelPickUpRequest request);
}
