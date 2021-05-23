package com.hrs.parcel.service;

import com.hrs.common.model.Pagination;
import com.hrs.parcel.domain.ParcelDeposit;
import com.hrs.parcel.enums.ParcelDepositStatusEnum;
import com.hrs.parcel.model.request.ParcelDepositListCondition;
import com.hrs.parcel.model.vo.ParcelDepositListVO;

public interface ParcelDepositService extends BaseService<ParcelDeposit> {
    Pagination<ParcelDepositListVO> queryParcelDepositList(ParcelDepositListCondition condition, Long pageNumber, Long pageSize);

    ParcelDeposit queryParcelDepositByDepositNo(String parcelNo);

    Boolean updateByLastStatus(ParcelDeposit parcelDeposit, ParcelDepositStatusEnum lastStatus);
}
