package com.hrs.parcel.service;

import com.hrs.common.exception.CustomException;
import com.hrs.parcel.domain.ParcelCabinet;
import com.hrs.parcel.domain.ParcelDeposit;
import com.hrs.parcel.enums.CabinetStatusEnum;
import com.hrs.parcel.enums.ParcelResultEnum;
import com.hrs.parcel.model.request.ParcelDepositRequest;
import com.hrs.parcel.base.BaseTest;
import com.hrs.parcel.model.vo.ParcelDepositVO;
import com.hrs.parcel.service.impl.ParcelServiceImpl;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParcelServiceTest extends BaseTest {
    @InjectMocks
    private ParcelServiceImpl injectMockParcelServiceImpl;
    @Mock
    private ParcelService mockParcelService;
    @Mock
    private ParcelCabinetService mockParcelCabinetService;
    @Mock
    private ParcelDepositService mockParcelDepositService;

    @Test
    public void parcelDeposit() {
        ParcelDepositRequest request = mockParcelDepositRequest();
        String cabinetNo = "NO0001";
        // mock一个非空闲状态的寄存柜
        when(mockParcelCabinetService.queryParcelCabinetByCabinetNo(cabinetNo)).thenReturn(mockParcelCabinet());
        // 验证非空闲状态的寄存柜不能存
        try {
            injectMockParcelServiceImpl.parcelDeposit(request);
        } catch (CustomException e) {
            assertNotNull(e);
            assertEquals(e.getResultJson().getMessage(), ParcelResultEnum.CABINET_UNAVAILABLE.getDesc());
        }

        ParcelCabinet parcelCabinet = mockParcelCabinetService.queryParcelCabinetByCabinetNo(cabinetNo);
        parcelCabinet.setCabinetStatus(CabinetStatusEnum.AVAILABLE.getCode());
        // 存
        ParcelDepositVO vo = injectMockParcelServiceImpl.parcelDeposit(request);

        ArgumentCaptor<ParcelDeposit> argumentCaptor = ArgumentCaptor.forClass(ParcelDeposit.class);
        // 验证存是否被调用1次
        verify(mockParcelDepositService, times(1)).save(argumentCaptor.capture());
        // 验证抓取的入参
        ParcelDeposit parcelDeposit = argumentCaptor.getValue();
        assertNotNull(parcelDeposit);
        // 验证存记录编号是否以D开头
        String depositNo = parcelDeposit.getDepositNo();
        assertTrue(depositNo.startsWith("D"));

        // 验证最后返回vo
        assertNotNull(vo);
        assertTrue(vo.getDepositNo().equals(depositNo));

        // 验证同一个并发请求重复存相同的柜子应该抛异常
        try {
            injectMockParcelServiceImpl.parcelDeposit(request);
        } catch (CustomException e) {
            assertNotNull(e);
            assertEquals(e.getResultJson().getMessage(), ParcelResultEnum.CABINET_UNAVAILABLE.getDesc());
        }
    }

    @Test
    public void parcelPickUp() {
    }

    @Test
    public void queryParcelCabinetList() {

    }

    @Test
    public void queryParcelDepositList() {
    }

    private ParcelCabinet mockParcelCabinet() {
        ParcelCabinet parcelCabinet = new ParcelCabinet();
        parcelCabinet.setCabinetStatus(CabinetStatusEnum.OCCUPIED.getCode());
        parcelCabinet.setCabinetNo("NO0001");
        return parcelCabinet;
    }

    private ParcelDepositRequest mockParcelDepositRequest() {
        ParcelDepositRequest request = ParcelDepositRequest.builder()
                .cabinetNo("NO0001")
                .adminName("receptionist")
                .adminId(1L)
                .adminMobile("13761575193")
                .depositorName("hcj")
                .depositorMobile("15161575193")
                .build();
        return request;
    }

}