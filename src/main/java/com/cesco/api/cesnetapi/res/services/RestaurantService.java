package com.cesco.api.cesnetapi.res.services;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.cesco.api.cesnetapi.res.mappers.RestaurantPoseMapper;
import com.cesco.api.cesnetapi.res.models.DailySales;
import com.cesco.api.cesnetapi.res.models.DailySalesList;
import com.cesco.api.cesnetapi.res.models.FranchieseList;
import com.cesco.api.cesnetapi.res.models.Franchises;
import com.cesco.api.cesnetapi.res.models.ItemList;
import com.cesco.api.cesnetapi.res.models.ItemListList;
import com.cesco.api.cesnetapi.res.models.PrepaidCardSale;
import com.cesco.api.cesnetapi.res.models.PrepaidCardSaleDtl;
import com.cesco.api.cesnetapi.res.models.PrepaidCardSaleList;
import com.cesco.api.cesnetapi.res.models.PrepaidCardSaleListDtlList;
import com.cesco.api.cesnetapi.res.models.RestaurantParam;
import com.cesco.api.cesnetapi.res.models.SalesDtl;
import com.cesco.api.cesnetapi.res.models.SalesDtlList;
import com.cesco.api.cesnetapi.res.models.SalesReceipt;
import com.cesco.api.cesnetapi.res.models.SalesReceiptDtlCardDe;
import com.cesco.api.cesnetapi.res.models.SalesReceiptDtlCashDe;
import com.cesco.api.cesnetapi.res.models.SalesReceiptDtlCorpDe;
import com.cesco.api.cesnetapi.res.models.SalesReceiptDtlDe;
import com.cesco.api.cesnetapi.res.models.SalesReceiptDtlList;
import com.cesco.api.cesnetapi.res.models.SalesReceiptDtlMappDe;
import com.cesco.api.cesnetapi.res.models.SalesReceiptDtlPrepaidCard;
import com.cesco.api.cesnetapi.res.models.SalesReceiptList;

import org.hamcrest.core.IsNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestaurantService {
    
    @Autowired
    private RestaurantPoseMapper mapper;
    private org.springframework.http.HttpHeaders getHttpHeaders(String headerParam) {

        // HttpHeaders headers = new HttpHeaders();
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        // headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));  
        headers.setContentType(new org.springframework.http.MediaType("application","json",Charset.forName("UTF-8")));    
        return headers;
    }

    private RestTemplate getRestTemplate() {
  
        //RestTemplate timeout 설정 추가 (세스코 API에 잘못된 파라미터나 값 전달되는경우 무한 리로딩 현상 발생하므로 어플리케이션에서 직접 커넥션 관련 타임아웃 추가함)
          HttpComponentsClientHttpRequestFactory factory 
                = new HttpComponentsClientHttpRequestFactory();
          
          factory.setConnectTimeout(8 * 1000 * 10); //80초
          factory.setReadTimeout(6* 1000 * 20); //2분 //1200초
          
          return new RestTemplate(factory);
    }

    public Boolean setDailySalesList() throws SQLException
    {   
        List<DailySales> dailySales;
        Boolean result = false;
        DailySalesList dailySalesList = new DailySalesList();
        RestaurantParam param = new RestaurantParam();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());
        param.setS_code("3");
        param.setHd_code("H0X");
        param.setSp_code("000001");
        // param.setSale_date(yesterday);            //데이터 가져올 결제일자를 어제로 고정 (스케쥴러 배포 시 주석해제)
        param.setSale_date("20240831");   // 데이터 가져올 결제일자 (스케쥴러 배포 시 주석)
        param.setOther_sp_fg("0");
        HttpEntity<Object> req = new HttpEntity<Object>(param, getHttpHeaders(null));
                                        
        ResponseEntity<DailySalesList> res = getRestTemplate().exchange(
              "https://poson.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
              ,HttpMethod.POST
              ,req
              ,DailySalesList.class);

        // 결과저장
        if (res.getBody().getRet_code().equals("0000")){

            dailySalesList.setData(res.getBody().getData());
            log.debug(dailySalesList.toString());
            Integer count = 0;
    
            dailySales =dailySalesList.getData();
    
            count = dailySales.size();
            result = setDailySales(dailySales, count);
        }
        return result;
    }

    public Boolean setDailySales(List<DailySales> dailySales, Integer count) throws SQLException
    {
        
        log.debug(dailySales.toString());
        Boolean result = false;
        for(Integer i = 0; i < count; i++ )
        {
            String hdCode					=dailySales.get(i).getHd_code();
            String spCode					=dailySales.get(i).getSp_code();
            String spName					=dailySales.get(i).getSp_name();
            String bizNo					=dailySales.get(i).getBiz_no();
            String erpCode					=dailySales.get(i).getErp_code();
            String salesDate				=dailySales.get(i).getSale_date();
            String getTotalAmt              =dailySales.get(i).getTotal_amt();
            if(getTotalAmt.isEmpty()){
                getTotalAmt = "0";
            }
            int totalAmt					=Integer.parseInt(getTotalAmt);
            String getSale_amt              =dailySales.get(i).getSale_amt();
            if(getSale_amt.isEmpty()){
                getSale_amt = "0";
            }
            int saleAmt					    =Integer.parseInt(getSale_amt);
            String getNet_amt              =dailySales.get(i).getNet_amt();
            if(getNet_amt.isEmpty()){
                getNet_amt = "0";
            }
            int netAmt					    =Integer.parseInt(getNet_amt);
            String getTotal_dc_amt              =dailySales.get(i).getTotal_dc_amt();
            if(getTotal_dc_amt.isEmpty()){
                getTotal_dc_amt = "0";
            }
            int totalDcAmt                  =Integer.parseInt(getTotal_dc_amt);
            String getVat_amt              =dailySales.get(i).getVat_amt();
            if(getVat_amt.isEmpty()){
                getVat_amt = "0";
            }
            int vatAmt					    =Integer.parseInt(dailySales.get(i).getVat_amt());
            String getBill_qty              =dailySales.get(i).getBill_qty();
            if(getBill_qty.isEmpty()){
                getBill_qty = "0";
            }
            int billQty					    =Integer.parseInt(getBill_qty);
            String getNormal_qty              =dailySales.get(i).getNormal_qty();
            if(getNormal_qty.isEmpty()){
                getNormal_qty = "0";
            }
            int normalQty				    =Integer.parseInt(getNormal_qty);
            String getNormal_amt              =dailySales.get(i).getNormal_amt();
            if(getNormal_amt.isEmpty()){
                getNormal_amt = "0";
            }
            int normalAmt				    =Integer.parseInt(getNormal_amt);
            String getReturn_qty              =dailySales.get(i).getReturn_qty();
            if(getReturn_qty.isEmpty()){
                getReturn_qty = "0";
            }
            int returnQty				    =Integer.parseInt(getReturn_qty);
            String getReturn_amt              =dailySales.get(i).getReturn_amt();
            if(getReturn_amt.isEmpty()){
                getReturn_amt = "0";
            }
            int returnAmt				    =Integer.parseInt(getReturn_amt);
            String getService_amt              =dailySales.get(i).getService_amt();
            if(getService_amt.isEmpty()){
                getService_amt = "0";
            }
            int serviceAmt				    =Integer.parseInt(getService_amt);
            String getCash_qty              =dailySales.get(i).getCash_qty();
            if(getCash_qty.isEmpty()){
                getCash_qty = "0";
            }
            int cashQty					    =Integer.parseInt(getCash_qty);
            String getCash_amt              =dailySales.get(i).getCash_amt();
            if(getCash_amt.isEmpty()){
                getCash_amt = "0";
            }
            int cashAmt					    =Integer.parseInt(getCash_amt);
            String getCard_qty              =dailySales.get(i).getCard_qty();
            if(getTotalAmt.isEmpty()){
                getCard_qty = "0";
            }
            int cardQty					    =Integer.parseInt(getCard_qty);
            String getCard_amt              =dailySales.get(i).getCard_amt();
            if(getCard_amt.isEmpty()){
                getCard_amt = "0";
            }
            int cardAmt					    =Integer.parseInt(getCard_amt);
            String getTick_qty              =dailySales.get(i).getTick_qty();
            if(getTick_qty.isEmpty()){
                getTick_qty = "0";
            }
            int tickQty					    =Integer.parseInt(getTick_qty);
            String getTick_amt              =dailySales.get(i).getTick_amt();
            if(getTick_amt.isEmpty()){
                getTick_amt = "0";
            }
            int tickAmt					    =Integer.parseInt(getTick_amt);
            String getPoint_qty              =dailySales.get(i).getPoint_qty();
            if(getPoint_qty.isEmpty()){
                getPoint_qty = "0";
            }
            int pointQty					=Integer.parseInt(getPoint_qty);
            String getPoint_amt              =dailySales.get(i).getPoint_amt();
            if(getPoint_amt.isEmpty()){
                getPoint_amt = "0";
            }
            int pointAmt					=Integer.parseInt(getPoint_amt);
            String getGift_qty              =dailySales.get(i).getGift_qty();
            if(getGift_qty.isEmpty()){
                getGift_qty = "0";
            }
            int giftQty					    =Integer.parseInt(getGift_qty);
            String getGift_amt              =dailySales.get(i).getGift_amt();
            if(getGift_amt.isEmpty()){
                getGift_amt = "0";
            }
            int giftAmt					    =Integer.parseInt(getGift_amt);
            String getPrepaid_card_qty              =dailySales.get(i).getPrepaid_card_qty();
            if(getPrepaid_card_qty.isEmpty()){
                getPrepaid_card_qty = "0";
            }
            int prepaidCardQty			    =Integer.parseInt(getPrepaid_card_qty);
            String getPrepaid_card_amt              =dailySales.get(i).getPrepaid_card_amt();
            if(getPrepaid_card_amt.isEmpty()){
                getPrepaid_card_amt = "0";
            }
            int prepaidCardAmt			    =Integer.parseInt(getPrepaid_card_amt);
            String getOcb_qty              =dailySales.get(i).getOcb_qty();
            if(getOcb_qty.isEmpty()){
                getOcb_qty = "0";
            }
            int ocbQty					    =Integer.parseInt(getOcb_qty);
            String getOcb_amt              =dailySales.get(i).getOcb_amt();
            if(getOcb_amt.isEmpty()){
                getOcb_amt = "0";
            }
            int ocbAmt					    =Integer.parseInt(getOcb_amt);
            String getCorp_dc_qty              =dailySales.get(i).getCorp_dc_qty();
            if(getCorp_dc_qty.isEmpty()){
                getCorp_dc_qty = "0";
            }
            int corpDcQty				    =Integer.parseInt(getCorp_dc_qty);
            String getCorp_dc_amt              =dailySales.get(i).getCorp_dc_amt();
            if(getCorp_dc_amt.isEmpty()){
                getCorp_dc_amt = "0";
            }
            int corpDcAmt				    =Integer.parseInt(getCorp_dc_amt);
            String getNormal_dc_qty              =dailySales.get(i).getNormal_dc_qty();
            if(getNormal_dc_qty.isEmpty()){
                getNormal_dc_qty = "0";
            }
            int normalDcQty				    =Integer.parseInt(getNormal_dc_qty);
            String getNormal_dc_ant              =dailySales.get(i).getNormal_dc_ant();
            if(getNormal_dc_ant.isEmpty()){
                getNormal_dc_ant = "0";
            }
            int normalDcAmt				    =Integer.parseInt(getNormal_dc_ant);
            String getService_dc_qty              =dailySales.get(i).getService_dc_qty();
            if(getService_dc_qty.isEmpty()){
                getService_dc_qty = "0";
            }
            int serviceDcQty				=Integer.parseInt(getService_dc_qty);
            String getService_dc_amt              =dailySales.get(i).getService_dc_amt();
            if(getService_dc_amt.isEmpty()){
                getService_dc_amt = "0";
            }
            int serviceDcAmt				=Integer.parseInt(getService_dc_amt);
            String getCoupon_dc_qty              =dailySales.get(i).getCoupon_dc_qty();
            if(getCoupon_dc_qty.isEmpty()){
                getCoupon_dc_qty = "0";
            }
            int couponDcQty				    =Integer.parseInt(getCoupon_dc_qty);
            String getCoupon_dc_amt              =dailySales.get(i).getCoupon_dc_amt();
            if(getCoupon_dc_amt.isEmpty()){
                getCoupon_dc_amt = "0";
            }
            int couponDcAmt				    =Integer.parseInt(getCoupon_dc_amt);
            String getCust_dc_qty              =dailySales.get(i).getCust_dc_qty();
            if(getCust_dc_qty.isEmpty()){
                getCust_dc_qty = "0";
            }
            int custDcQty				    =Integer.parseInt(getCust_dc_qty);
            String getCust_dc_amt              =dailySales.get(i).getCust_dc_amt();
            if(getCust_dc_amt.isEmpty()){
                getCust_dc_amt = "0";
            }
            int custDcAmt				    =Integer.parseInt(getCust_dc_amt);
            String getCust_accum_point              =dailySales.get(i).getCust_accum_point();
            if(getCust_accum_point.isEmpty()){
                getCust_accum_point = "0";
            }
            int custAccumPoint			    =Integer.parseInt(getCust_accum_point);
            String getCust_use_point              =dailySales.get(i).getCust_use_point();
            if(getCust_use_point.isEmpty()){
                getCust_use_point = "0";
            }
            int custUsePoint				=Integer.parseInt(getCust_use_point);
            String getCust_cnt              =dailySales.get(i).getCust_cnt();
            if(getCust_cnt.isEmpty()){
                getCust_cnt = "0";
            }
            int custCnt					    =Integer.parseInt(getCust_cnt);
            String getDu_dc_qty              =dailySales.get(i).getDu_dc_qty();
            if(getDu_dc_qty.isEmpty()){
                getDu_dc_qty = "0";
            }
            int duDcQty					    =Integer.parseInt(getDu_dc_qty);
            String getDu_dc_amt              =dailySales.get(i).getDu_dc_amt();
            if(getDu_dc_amt.isEmpty()){
                getDu_dc_amt = "0";
            }
            int duDcAmt					    =Integer.parseInt(getDu_dc_amt);
            String getExchange_qty              =dailySales.get(i).getExchange_qty();
            if(getExchange_qty.isEmpty()){
                getExchange_qty = "0";
            }
            int exchangeQty				    =Integer.parseInt(getExchange_qty);
            String getExchange_amt              =dailySales.get(i).getExchange_amt();
            if(getExchange_amt.isEmpty()){
                getExchange_amt = "0";
            }
            int exchangeAmt				    =Integer.parseInt(getExchange_amt);
            String getCo_qty              =dailySales.get(i).getCo_qty();
            if(getCo_qty.isEmpty()){
                getCo_qty = "0";
            }
            int coQty					    =Integer.parseInt(getCo_qty);
            String getCo_amt              =dailySales.get(i).getCo_amt();
            if(getCo_amt.isEmpty()){
                getCo_amt = "0";
            }
            int coAmt					    =Integer.parseInt(getCo_amt);
            String getEmoney_qty              =dailySales.get(i).getEmoney_qty();
            if(getEmoney_qty.isEmpty()){
                getEmoney_qty = "0";
            }
            int emoneyQty				    =Integer.parseInt(getEmoney_qty);
            String getEmoney_amt              =dailySales.get(i).getEmoney_amt();
            if(getEmoney_amt.isEmpty()){
                getEmoney_amt = "0";
            }
            int emoneyAmt				    =Integer.parseInt(getEmoney_amt);
            String getEnuri_qty              =dailySales.get(i).getEnuri_qty();
            if(getEnuri_qty.isEmpty()){
                getEnuri_qty = "0";
            }
            int enuriQty					=Integer.parseInt(getEnuri_qty);
            String getEnuri_amt              =dailySales.get(i).getEnuri_amt();
            if(getEnuri_amt.isEmpty()){
                getEnuri_amt = "0";
            }
            int enuriAmt					=Integer.parseInt(getEnuri_amt);
            String getCash_cancel_qty              =dailySales.get(i).getCash_cancel_qty();
            if(getCash_cancel_qty.isEmpty()){
                getCash_cancel_qty = "0";
            }
            int cashCancelQty			    =Integer.parseInt(getCash_cancel_qty);
            String getCash_cancel_amt              =dailySales.get(i).getCash_cancel_amt();
            if(getCash_cancel_amt.isEmpty()){
                getCash_cancel_amt = "0";
            }
            int cashCancelAmt			    =Integer.parseInt(getCash_cancel_amt);
            String getCard_cancel_qty              =dailySales.get(i).getCard_cancel_qty();
            if(getCard_cancel_qty.isEmpty()){
                getCard_cancel_qty = "0";
            }
            int cardCancelQty			    =Integer.parseInt(getCard_cancel_qty);
            String getCard_cancel_amt              =dailySales.get(i).getCard_cancel_amt();
            if(getCard_cancel_amt.isEmpty()){
                getCard_cancel_amt = "0";
            }
            int cardCancelAmt			    =Integer.parseInt(getCard_cancel_amt);
            String getTick_cancel_qty              =dailySales.get(i).getTick_cancel_qty();
            if(getTick_cancel_qty.isEmpty()){
                getTick_cancel_qty = "0";
            }
            int tickCancelQty			    =Integer.parseInt(getTick_cancel_qty);
            String getTick_cancel_amt              =dailySales.get(i).getTick_cancel_amt();
            if(getTick_cancel_amt.isEmpty()){
                getTick_cancel_amt = "0";
            }
            int tickCancelAmt			    =Integer.parseInt(getTick_cancel_amt);
            String getPoint_cancel_qty              =dailySales.get(i).getPoint_cancel_qty();
            if(getPoint_cancel_qty.isEmpty()){
                getPoint_cancel_qty = "0";
            }
            int pointCancelQty			    =Integer.parseInt(getPoint_cancel_qty);
            String getPoint_cancel_amt              =dailySales.get(i).getPoint_cancel_amt();
            if(getPoint_cancel_amt.isEmpty()){
                getPoint_cancel_amt = "0";
            }
            int pointCancelAmt			    =Integer.parseInt(getPoint_cancel_amt);
            String getGift_cancel_qty              =dailySales.get(i).getGift_cancel_qty();
            if(getGift_cancel_qty.isEmpty()){
                getGift_cancel_qty = "0";
            }
            int giftCancelQty			    =Integer.parseInt(getGift_cancel_qty);
            String getGift_cancel_amt              =dailySales.get(i).getGift_cancel_amt();
            if(getGift_cancel_amt.isEmpty()){
                getGift_cancel_amt = "0";
            }
            int giftCancelAmt			    =Integer.parseInt(getGift_cancel_amt);
            String getPrepaid_cancel_qty              =dailySales.get(i).getPrepaid_cancel_qty();
            if(getPrepaid_cancel_qty.isEmpty()){
                getPrepaid_cancel_qty = "0";
            }
            int prepaidCancelQty			=Integer.parseInt(getPrepaid_cancel_qty);
            String getPrepaid_cancel_amt              =dailySales.get(i).getPrepaid_cancel_amt();
            if(getPrepaid_cancel_amt.isEmpty()){
                getPrepaid_cancel_amt = "0";
            }
            int prepaidCancelAmt			=Integer.parseInt(getPrepaid_cancel_amt);
            String getOcb_cancel_qty              =dailySales.get(i).getOcb_cancel_qty();
            if(getOcb_cancel_qty.isEmpty()){
                getOcb_cancel_qty = "0";
            }
            int ocbCancelQty				=Integer.parseInt(getOcb_cancel_qty);
            String getOcb_cancel_amt              =dailySales.get(i).getOcb_cancel_amt();
            if(getOcb_cancel_amt.isEmpty()){
                getOcb_cancel_amt = "0";
            }
            int ocbCancelAmt				=Integer.parseInt(getOcb_cancel_amt);
            String getExchange_cancel_qty              =dailySales.get(i).getExchange_cancel_qty();
            if(getExchange_cancel_qty.isEmpty()){
                getExchange_cancel_qty = "0";
            }
            int exchangeCancelQty		    =Integer.parseInt(getExchange_cancel_qty);
            String getExchange_cancel_amt              =dailySales.get(i).getExchange_cancel_amt();
            if(getExchange_cancel_amt.isEmpty()){
                getExchange_cancel_amt = "0";
            }
            int exchangeCancelAmt		    =Integer.parseInt(getExchange_cancel_amt);
            String getCo_cancel_qty              =dailySales.get(i).getCo_cancel_qty();
            if(getCo_cancel_qty.isEmpty()){
                getCo_cancel_qty = "0";
            }
            int coCancelQty				    =Integer.parseInt(getCo_cancel_qty);
            String getCo_cancel_amt              =dailySales.get(i).getCo_cancel_amt();
            if(getCo_cancel_amt.isEmpty()){
                getCo_cancel_amt = "0";
            }
            int coCancelAmt				    =Integer.parseInt(getCo_cancel_amt);
            String getEmoney_cancel_qty              =dailySales.get(i).getEmoney_cancel_qty();
            if(getEmoney_cancel_qty.isEmpty()){
                getEmoney_cancel_qty = "0";
            }
            int emoneyCancelQty			    =Integer.parseInt(getEmoney_cancel_qty);
            String getEmoney_cancel_amt              =dailySales.get(i).getEmoney_cancel_amt();
            if(getEmoney_cancel_amt.isEmpty()){
                getEmoney_cancel_amt = "0";
            }
            int emoneyCancelAmt			    =Integer.parseInt(getEmoney_cancel_amt);
            String getPromotion_dc_qty              =dailySales.get(i).getPromotion_dc_qty();
            if(getPromotion_dc_qty.isEmpty()){
                getPromotion_dc_qty = "0";
            }
            int promotionDcQty			    =Integer.parseInt(getPromotion_dc_qty);
            String getPromotion_dc_amt              =dailySales.get(i).getPromotion_dc_amt();
            if(getPromotion_dc_amt.isEmpty()){
                getPromotion_dc_amt = "0";
            }
            int promotionDcAmt			    =Integer.parseInt(getPromotion_dc_amt);
    
            result = mapper.setDailySales(hdCode, spCode, spName, bizNo, erpCode, salesDate, totalAmt, saleAmt
                                        , netAmt, totalDcAmt, vatAmt, billQty, normalQty, normalAmt, returnQty
                                        , returnAmt, serviceAmt, cashQty, cashAmt, cardQty, cardAmt, tickQty
                                        , tickAmt, pointQty, pointAmt, giftQty, giftAmt, prepaidCardQty, prepaidCardAmt
                                        , ocbQty, ocbAmt, corpDcQty, corpDcAmt, normalDcQty, normalDcAmt, serviceDcQty
                                        , serviceDcAmt, couponDcQty, couponDcAmt, custDcQty, custDcAmt, custAccumPoint
                                        , custUsePoint, custCnt, duDcQty, duDcAmt, exchangeQty, exchangeAmt, coQty, coAmt
                                        , emoneyQty, emoneyAmt, enuriQty, enuriAmt, cashCancelQty, cashCancelAmt, cardCancelQty
                                        , cardCancelAmt, tickCancelQty, tickCancelAmt, pointCancelQty, pointCancelAmt, giftCancelQty
                                        , giftCancelAmt, prepaidCancelQty, prepaidCancelAmt, ocbCancelQty, ocbCancelAmt, exchangeCancelQty
                                        , exchangeCancelAmt, coCancelQty, coCancelAmt, emoneyCancelQty, emoneyCancelAmt, promotionDcQty, promotionDcAmt
                                        );
        }
        return result;
    }

    public Boolean setFranchisesList() throws SQLException
    {
        Boolean result = false;
        List<Franchises> franchises;
        FranchieseList franchieseList = new FranchieseList();
        RestaurantParam param = new RestaurantParam();
        param.setS_code("4");
        param.setHd_code("H0X");
        param.setSp_code("000001");
        param.setSale_date("");
        param.setOther_sp_fg("0");
        HttpEntity<Object> req = new HttpEntity<Object>(param, getHttpHeaders(null));
                                        
        ResponseEntity<FranchieseList> res = getRestTemplate().exchange(
              "https://poson.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
              ,HttpMethod.POST
              ,req
              ,FranchieseList.class);

        // 결과저장
        if (res.getBody().getRet_code().equals("0000")){

            franchieseList.setData(res.getBody().getData());
            log.debug(franchieseList.toString());
            Integer count = 0;
    
            franchises = franchieseList.getData();
    
            count = franchises.size();
            result = setFranchises(franchises, count);
        }
        return result;
    }

    public boolean setFranchises(List<Franchises> franchises, Integer count) throws SQLException
    {
        log.debug(franchises.toString());
        Boolean result = false;	

        for(int i = 0; i < count; i++)
        {
            String hdCode			=franchises.get(i).getHd_code();
            String spCode			=franchises.get(i).getSp_code();
            String spName			=franchises.get(i).getSp_name();
            String bizNo			=franchises.get(i).getBiz_no();
            String openFlag		    =franchises.get(i).getOpen_flag();
            String erpSpcode		=franchises.get(i).getErp_sp_code();
            String masterName		=franchises.get(i).getMaster_name();
            String telNo			=franchises.get(i).getTel_no();
            String spType			=franchises.get(i).getSp_type();
            String areaCode		    =franchises.get(i).getArea_code();
            String salesClassCode	=franchises.get(i).getSale_class_code();
            String saleClassName	=franchises.get(i).getSale_class_name();
            String adress1			=franchises.get(i).getAddress1();
            String adress2			=franchises.get(i).getAddress2();
            String brandCode		=franchises.get(i).getBrand_code();
            String brandName		=franchises.get(i).getBrand_name();
    
            result = mapper.setFranchises(hdCode, spCode, spName, bizNo, openFlag, erpSpcode
                                        , masterName, telNo, spType, areaCode, salesClassCode
                                        , saleClassName, adress1, adress2, brandCode, brandName
                                        );
        }
        return result;
    }

    public Boolean setSalesDtlList() throws SQLException
    {
        Boolean result = false;
        List<SalesDtl> salesDtl;
        SalesDtlList salesDtlList = new SalesDtlList();
        RestaurantParam param = new RestaurantParam();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());
        param.setS_code("5");
        param.setHd_code("H0X");
        param.setSp_code("000001");
        // param.setSale_date(yesterday);           //데이터 가져올 결제일자를 어제로 고정 (스케쥴러 배포 시 주석해제)
        param.setSale_date("20240831");   // 데이터 가져올 결제일자 (스케쥴러 배포 시 주석)
        param.setOther_sp_fg("0");
        HttpEntity<Object> req = new HttpEntity<Object>(param, getHttpHeaders(null));
                                        
        ResponseEntity<SalesDtlList> res = getRestTemplate().exchange(
              "https://poson.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
              ,HttpMethod.POST
              ,req
              ,SalesDtlList.class);

        // 결과저장
        if (res.getBody().getRet_code().equals("0000")){

            salesDtlList.setData(res.getBody().getData());
            log.debug(salesDtlList.toString());
            Integer count = 0;
    
            salesDtl =salesDtlList.getData();
    
            count = salesDtl.size();
            result = setSalesDtl(salesDtl, count);
        }
        return result;
    }

    public boolean setSalesDtl(List<SalesDtl> salesDtl, Integer count) throws SQLException
    {
        log.debug(salesDtl.toString());
        boolean result = false;

        for(int i = 0; i < count; i++)
        {
            String hdCode     =salesDtl.get(i).getHd_code();
            String spCode	  =salesDtl.get(i).getSp_code();
            String spName	  =salesDtl.get(i).getSp_name();
            String bizNo	  =salesDtl.get(i).getBiz_no();
            String erpCode    =salesDtl.get(i).getErp_code();
            String saleDate	  =salesDtl.get(i).getSale_date();
            String timeSlot	  =salesDtl.get(i).getTime_slot();
            String saleDay	  =salesDtl.get(i).getSale_day();
            String getBill_qty =salesDtl.get(i).getBill_qty();
            if (getBill_qty.isEmpty()){
                getBill_qty = "0";
            }
            int billQty		  =Integer.parseInt(getBill_qty);
            String getTotal_amt =salesDtl.get(i).getTotal_amt();
            if (getTotal_amt.isEmpty()){
                getTotal_amt = "0";
            }
            int totalAmt	  =Integer.parseInt(getTotal_amt);
            String getSale_amt =salesDtl.get(i).getSale_amt();
            if (getSale_amt.isEmpty()){
                getSale_amt = "0";
            }
            int saleAmt		  =Integer.parseInt(getSale_amt);
            String getNet_amt =salesDtl.get(i).getNet_amt();
            if (getNet_amt.isEmpty()){
                getNet_amt = "0";
            }
            int netAmt		  =Integer.parseInt(getNet_amt);	
            String getBill_amt =salesDtl.get(i).getBill_amt();
            if (getBill_amt.isEmpty()){
                getBill_amt = "0";
            }
            int billAmt		  =Integer.parseInt(getBill_amt);
    
            result = mapper.setSalesDtl(hdCode, spCode, spName, bizNo, erpCode, saleDate
                                        , timeSlot, saleDay, billQty, totalAmt, saleAmt
                                        , netAmt, billAmt
                                        );
        }
        return result;
    }

    public Boolean setItemListList() throws SQLException
    {
        Boolean result = false;
        List<ItemList> itemList;
        ItemListList itemListList= new ItemListList();
        RestaurantParam param = new RestaurantParam();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());
        param.setS_code("9");
        param.setHd_code("H0X");
        param.setSp_code("000001");
        // param.setSale_date(yesterday);           //데이터 가져올 결제일자를 어제로 고정 (스케쥴러 배포 시 주석해제)
        param.setSale_date("20240831");   // 데이터 가져올 결제일자 (스케쥴러 배포 시 주석)
        param.setOther_sp_fg("0");
        HttpEntity<Object> req = new HttpEntity<Object>(param, getHttpHeaders(null));
                                        
        ResponseEntity<ItemListList> res = getRestTemplate().exchange(
              "https://poson.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
              ,HttpMethod.POST
              ,req
              ,ItemListList.class);

        // 결과저장
        if (res.getBody().getRet_code().equals("0000")){

            itemListList.setData(res.getBody().getData());
            log.debug(itemListList.toString());
            Integer count = 0;
    
            itemList = itemListList.getData();
    
            count = itemList.size();
            result = setItemlist(itemList, count);
        }
        return result;
    }


    public boolean setItemlist(List<ItemList> itemList, Integer count) throws SQLException
    {
        log.debug(itemList.toString());
        Boolean result = false;
        
        for(int i = 0; i < count; i++)
        {
            String hdCode				=itemList.get(i).getHd_code();
            String spCode				=itemList.get(i).getSp_code();
            String itemCode			    =itemList.get(i).getItem_code();
            String itemName			    =itemList.get(i).getItem_name();
            String shortName			=itemList.get(i).getShort_name();
            String emglichName			=itemList.get(i).getEnglish_name();
            String qtyName				=itemList.get(i).getQty_name();
            String largeScale			=itemList.get(i).getLarge_scale();
            String largeScaleNm		    =itemList.get(i).getLarge_scale_nm();
            String mediumScale			=itemList.get(i).getMedium_scale();
            String mediumScaleNm		=itemList.get(i).getMedium_scale_nm();
            String smallScale			=itemList.get(i).getSmall_scale();
            String smallScaleNm		    =itemList.get(i).getSmall_scale_nm();
            String getItemCost          =itemList.get(i).getItem_cost();
            if(getItemCost.isEmpty()){
                getItemCost = "0";
            }
            int	itemCost				=Integer.parseInt(getItemCost);
            String getItemPriceString   =itemList.get(i).getItem_price();
            if(getItemPriceString.isEmpty()){
                getItemPriceString = "0";
            }
            int	itemPrice				=Integer.parseInt(getItemPriceString);
            String getVatRate           =itemList.get(i).getVat_rate();
            if(getVatRate.isEmpty()){
                getVatRate = "0";
            }
            int	vatRate				    =Integer.parseInt(itemList.get(i).getVat_rate());
            String vatRateFg			=itemList.get(i).getVat_rate_fg();
            String useFlag				=itemList.get(i).getUse_flag();
            String taxFlag				=itemList.get(i).getTax_flag();
            String taxFlagNm			=itemList.get(i).getTax_flag_nm();
            String priceFlag			=itemList.get(i).getPrice_flag();
            String priceFlagNm			=itemList.get(i).getPrice_flag_nm();
            String submenuType			=itemList.get(i).getSub_menu_type();
            String serviceFlag			=itemList.get(i).getService_flag();
            String itemType			    =itemList.get(i).getItem_type();
            String itemTypeNm			=itemList.get(i).getItem_type_nm();
            String itemImgUrl			=itemList.get(i).getItem_img_url();
            String itemDescription		=itemList.get(i).getItem_description();
            String itemDescriptionEng	=itemList.get(i).getItem_description_eng();
            String saleStartTime		=itemList.get(i).getSale_start_time();
            String saleEndTime			=itemList.get(i).getSale_end_time();
            String getDailySaleQty      =itemList.get(i).getDaily_sale_qty();
            if(getDailySaleQty.isEmpty()){
                getDailySaleQty = "0";
            }
            int	dailySaleQty			=Integer.parseInt(getDailySaleQty);
            String kioskSubMenuType	    =itemList.get(i).getKiosk_sub_menu_type();
            String kioskDisplayIcon	    =itemList.get(i).getKiosk_display_icon();
            String saleDays			    =itemList.get(i).getSlae_days();
            String soldOut				=itemList.get(i).getSold_out();
            String orderGroupFg		    =itemList.get(i).getOrder_group_fg();
    
            result = mapper.setItemlist(hdCode, spCode, itemCode, itemName
                                        , shortName, emglichName, qtyName, largeScale
                                        , largeScaleNm, mediumScale, mediumScaleNm, smallScale
                                        , smallScaleNm, itemCost, itemPrice, vatRate, vatRateFg
                                        , useFlag, taxFlag, taxFlagNm, priceFlag, priceFlagNm
                                        , submenuType, serviceFlag, itemType, itemTypeNm, itemImgUrl
                                        , itemDescription, itemDescriptionEng, saleStartTime, saleEndTime
                                        , dailySaleQty, kioskSubMenuType, kioskDisplayIcon, saleDays, soldOut, orderGroupFg
                                        );
        }
        return result;
    }

    public Boolean setSalesreceiptList() throws SQLException
    {
        Boolean result = false;
        List<SalesReceipt> salesReceipt;
        SalesReceiptList salesReceiptList= new SalesReceiptList();
        RestaurantParam param = new RestaurantParam();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());
        param.setS_code("6");
        param.setHd_code("H0X");
        param.setSp_code("000001");
        // param.setSale_date(yesterday);           //데이터 가져올 결제일자를 어제로 고정 (스케쥴러 배포 시 주석해제)
        param.setSale_date("20240831");   // 데이터 가져올 결제일자 (스케쥴러 배포 시 주석)
        param.setOther_sp_fg("0");
        HttpEntity<Object> req = new HttpEntity<Object>(param, getHttpHeaders(null));
                                        
        ResponseEntity<SalesReceiptList> res = getRestTemplate().exchange(
              "https://poson.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
              ,HttpMethod.POST
              ,req
              ,SalesReceiptList.class);

        // 결과저장
        if (res.getBody().getRet_code().equals("0000")){

            salesReceiptList.setData(res.getBody().getData());
            log.debug(salesReceiptList.toString());
            Integer count = 0;
    
            salesReceipt = salesReceiptList.getData();
    
            count = salesReceipt.size();
            result = setSalesreceipt(salesReceipt, count);
        }
        return result;
    }

    public boolean setSalesreceipt(List<SalesReceipt> salesReceipt, Integer count) throws SQLException
    {
        log.debug(salesReceipt.toString());
        Boolean result = false;
        
        for(int i = 0; i < count; i++)
        {

            String hdCode			=salesReceipt.get(i).getHd_code();
            String spCode			=salesReceipt.get(i).getSp_code();
            String spName			=salesReceipt.get(i).getSp_name();
            String bizNo			=salesReceipt.get(i).getBiz_no();
            String erpCode			=salesReceipt.get(i).getErp_code();
            String saleDate		    =salesReceipt.get(i).getSale_date();
            String posNo			=salesReceipt.get(i).getPos_no();
            String billNo			=salesReceipt.get(i).getBill_no();
            String saleFlag		    =salesReceipt.get(i).getSale_flag();
            String cancelFlag		=salesReceipt.get(i).getCancel_flag();
            String orderDatetime	=salesReceipt.get(i).getOrder_datetime();
            String serveDatetime	=salesReceipt.get(i).getServe_datetime();
            String orgSaleNo		=salesReceipt.get(i).getOrg_sale_no();
            String getTotalAmt      =salesReceipt.get(i).getTotal_amt();
            if(getTotalAmt.isEmpty()){
                getTotalAmt = "0";
            }
            int totalAmt			=Integer.parseInt(getTotalAmt);
            String getSaleAmt      =salesReceipt.get(i).getSale_amt();
            if(getSaleAmt.isEmpty()){
                getSaleAmt = "0";
            }
            int saleAmt			    =Integer.parseInt(getSaleAmt);
            String getNetAmt      =salesReceipt.get(i).getNet_amt();
            if(getNetAmt.isEmpty()){
                getNetAmt = "0";
            }
            int netAmt				=Integer.parseInt(getNetAmt);
            String getTotalDcAmt      =salesReceipt.get(i).getTotal_dc_amt();
            if(getTotalDcAmt.isEmpty()){
                getTotalDcAmt = "0";
            }
            int totalDcAmt			=Integer.parseInt(getTotalDcAmt);
            String geVatAmt      =salesReceipt.get(i).getVat_amt();
            if(geVatAmt.isEmpty()){
                geVatAmt = "0";
            }
            int vatAmt				=Integer.parseInt(geVatAmt);
            String getServiceAmt      =salesReceipt.get(i).getService_amt();
            if(getServiceAmt.isEmpty()){
                getServiceAmt = "0";
            }
            int serviceAmt			=Integer.parseInt(getServiceAmt);
            String getCashAmt      =salesReceipt.get(i).getCash_amt();
            if(getCashAmt.isEmpty()){
                getCashAmt = "0";
            }
            int cashAmt			    =Integer.parseInt(getCashAmt);
            String getCardAmt      =salesReceipt.get(i).getCard_amt();
            if(getCardAmt.isEmpty()){
                getCardAmt = "0";
            }
            int cardAmt			    =Integer.parseInt(getCardAmt);
            String getTickAmt      =salesReceipt.get(i).getTick_amt();
            if(getTickAmt.isEmpty()){
                getTickAmt = "0";
            }
            int tickAmt			    =Integer.parseInt(getTickAmt);
            String getPointAmt      =salesReceipt.get(i).getPoint_amt();
            if(getPointAmt.isEmpty()){
                getPointAmt = "0";
            }
            int pointAmt			=Integer.parseInt(getPointAmt);
            String getGiftAmt      =salesReceipt.get(i).getGift_amt();
            if(getGiftAmt.isEmpty()){
                getGiftAmt = "0";
            }
            int giftAmt			    =Integer.parseInt(getGiftAmt);
            String getPrepaidCardAmt      =salesReceipt.get(i).getPrepaid_card_amt();
            if(getPrepaidCardAmt.isEmpty()){
                getPrepaidCardAmt = "0";
            }
            int prepaidCardAmt		=Integer.parseInt(getPrepaidCardAmt);
            String getOcbAmt      =salesReceipt.get(i).getOcb_amt();
            if(getOcbAmt.isEmpty()){
                getOcbAmt = "0";
            }
            int ocbAmt				=Integer.parseInt(getOcbAmt);
            String getCorpDcAmt      =salesReceipt.get(i).getCorp_dc_amt();
            if(getCorpDcAmt.isEmpty()){
                getCorpDcAmt = "0";
            }
            int corpDcAmt			=Integer.parseInt(getCorpDcAmt);
            String getNormalDcAmt      =salesReceipt.get(i).getNormal_dc_ant();
            if(getNormalDcAmt.isEmpty()){
                getNormalDcAmt = "0";
            }
            int normalDcAmt		    =Integer.parseInt(getNormalDcAmt);
            String getServiceDcAmt      =salesReceipt.get(i).getService_dc_amt();
            if(getServiceDcAmt.isEmpty()){
                getServiceDcAmt = "0";
            }
            int serviceDcAmt		=Integer.parseInt(getServiceDcAmt);
            String getCouponDcAmt      =salesReceipt.get(i).getCoupon_dc_amt();
            if(getCouponDcAmt.isEmpty()){
                getCouponDcAmt = "0";
            }
            int couponDcAmt		    =Integer.parseInt(salesReceipt.get(i).getCoupon_dc_amt());
            String getCustDcAmt      =salesReceipt.get(i).getCust_dc_amt();
            if(getCustDcAmt.isEmpty()){
                getCustDcAmt = "0";
            }
            int custDcAmt			=Integer.parseInt(getCustDcAmt);
            String getCustAccumPoint      =salesReceipt.get(i).getCust_accum_point();
            if(getCustAccumPoint.isEmpty()){
                getCustAccumPoint = "0";
            }
            int custAccumPoint		=Integer.parseInt(getCustAccumPoint);
            String getCustUsePoint      =salesReceipt.get(i).getCust_use_point();
            if(getCustUsePoint.isEmpty()){
                getCustUsePoint = "0";
            }
            int custUsePoint		=Integer.parseInt(getCustUsePoint);
            String getCustNM      =salesReceipt.get(i).getCust_cnt();
            if(getCustNM.isEmpty()){
                getCustNM = "0";
            }
            int custCnt			    =Integer.parseInt(getCustNM);
            String getExchangeAmt      =salesReceipt.get(i).getExchange_amt();
            if(getExchangeAmt.isEmpty()){
                getExchangeAmt = "0";
            }
            int exchangeAmt		    =Integer.parseInt(salesReceipt.get(i).getExchange_amt());
            String getCoAmt      =salesReceipt.get(i).getCo_amt();
            if(getCoAmt.isEmpty()){
                getCoAmt = "0";
            }
            int coAmt				=Integer.parseInt(getCoAmt);
            String getEmoneyAmt      =salesReceipt.get(i).getEmoney_amt();
            if(getEmoneyAmt.isEmpty()){
                getEmoneyAmt = "0";
            }
            int emoneyAmt			=Integer.parseInt(getEmoneyAmt);
            String getEnuriAmt      =salesReceipt.get(i).getEnuri_amt();
            if(getEnuriAmt.isEmpty()){
                getEnuriAmt = "0";
            }
            int enuriAmt			=Integer.parseInt(getEnuriAmt);
            String getPromotionDcAmt      =salesReceipt.get(i).getPromotion_dc_amt();
            if(getPromotionDcAmt.isEmpty()){
                getPromotionDcAmt = "0";
            }
            int promotionDcAmt		=Integer.parseInt(getPromotionDcAmt);
            String custNo		    =salesReceipt.get(i).getCust_no();
            String custCardNo		=salesReceipt.get(i).getCust_card_no();
            String saleSpCode		=salesReceipt.get(i).getSale_sp_code();
    
            result = mapper.setSalesreceipt(hdCode, spCode, spName, bizNo, erpCode
                                            , saleDate, posNo, billNo, saleFlag, cancelFlag
                                            , orderDatetime, serveDatetime, orgSaleNo, totalAmt
                                            , saleAmt, netAmt, totalDcAmt, vatAmt, serviceAmt, cashAmt
                                            , cardAmt, tickAmt, pointAmt, giftAmt, prepaidCardAmt, ocbAmt
                                            , corpDcAmt, normalDcAmt, serviceDcAmt, couponDcAmt, custDcAmt
                                            , custAccumPoint, custUsePoint, custCnt, exchangeAmt, coAmt, emoneyAmt
                                            , enuriAmt, promotionDcAmt, custNo, custCardNo, saleSpCode
                                            );
        }
        return result;
    }
    
    // public boolean setSalesreceiptDtl(SalesReceipt salesReceipt) throws SQLException
    // {
    //     log.debug(salesReceipt.toString());
 
    //     String hdCode			=salesReceipt.getHdCode();
    //     String spCode			=salesReceipt.getSpCode();
    //     String spName			=salesReceipt.getSpName();
    //     String bizNo			=salesReceipt.getBizNo();
    //     String erpCode			=salesReceipt.getErpCode();
    //     String saleDate		    =salesReceipt.getSaleDate();
    //     String posNo			=salesReceipt.getPosNo();
    //     String billNo			=salesReceipt.getBillNo();
    //     String saleFlag		    =salesReceipt.getSaleFlag();
    //     String cancelFlag		=salesReceipt.getCancelFlag();
    //     String orderDatetime	=salesReceipt.getOrderDatetime();
    //     String serveDatetime	=salesReceipt.getServeDatetime();
    //     String orgSaleNo		=salesReceipt.getOrgSaleNo();
    //     int totalAmt			=salesReceipt.getTotalAmt();
    //     int saleAmt			    =salesReceipt.getSaleAmt();
    //     int netAmt				=salesReceipt.getNetAmt();
    //     int totalDcAmt			=salesReceipt.getTotalDcAmt();
    //     int vatAmt				=salesReceipt.getVatAmt();
    //     int serviceAmt			=salesReceipt.getServiceAmt();
    //     int cashAmt			    =salesReceipt.getCashAmt();
    //     int cardAmt			    =salesReceipt.getCardAmt();
    //     int tickAmt			    =salesReceipt.getTickAmt();
    //     int pointAmt			=salesReceipt.getPointAmt();
    //     int giftAmt			    =salesReceipt.getGiftAmt();
    //     int prepaidCardAmt		=salesReceipt.getPrepaidCardAmt();
    //     int ocbAmt				=salesReceipt.getOcbAmt();
    //     int corpDcAmt			=salesReceipt.getCorpDcAmt();
    //     int normalDcAmt		    =salesReceipt.getNormalDcAmt();
    //     int serviceDcAmt		=salesReceipt.getServiceDcAmt();
    //     int couponDcAmt		    =salesReceipt.getCouponDcAmt();
    //     int custDcAmt			=salesReceipt.getCustDcAmt();
    //     int custAccumPoint		=salesReceipt.getCustAccumPoint();
    //     int custUsePoint		=salesReceipt.getCustUsePoint();
    //     int custCnt			    =salesReceipt.getCustCnt();
    //     int exchangeAmt		    =salesReceipt.getExchangeAmt();
    //     int coAmt				=salesReceipt.getCoAmt();
    //     int emoneyAmt			=salesReceipt.getEmoneyAmt();
    //     int enuriAmt			=salesReceipt.getEnuriAmt();
    //     int promotionDcAmt		=salesReceipt.getPromotionDcAmt();
    //     Boolean result = false;

    //     result = mapper.setSalesreceipt(hdCode, spCode, spName, bizNo, erpCode
    //                                     , saleDate, posNo, billNo, saleFlag, cancelFlag
    //                                     , orderDatetime, serveDatetime, orgSaleNo, totalAmt
    //                                     , saleAmt, netAmt, totalDcAmt, vatAmt, serviceAmt, cashAmt
    //                                     , cardAmt, tickAmt, pointAmt, giftAmt, prepaidCardAmt, ocbAmt
    //                                     , corpDcAmt, normalDcAmt, serviceDcAmt, couponDcAmt, custDcAmt
    //                                     , custAccumPoint, custUsePoint, custCnt, exchangeAmt, coAmt, emoneyAmt
    //                                     , enuriAmt, promotionDcAmt
    //                                     );
    //     return result;
    // }

    public boolean setSalesreceiptDtlList() throws SQLException
    {
        Boolean result = false;
        List<SalesReceiptDtlDe> receiptDtl;
        List<SalesReceiptDtlMappDe> receiptDtlMapp;
        List<SalesReceiptDtlCashDe> receiptDtlCash;
        List<SalesReceiptDtlCardDe> receiptDtlCard;
        List<SalesReceiptDtlCorpDe> receiptDtlCorp;
        List<SalesReceiptDtlPrepaidCard> receiptDtlPrepaidCard;
        SalesReceiptDtlList salesReceiptDtlList = new SalesReceiptDtlList();
        RestaurantParam param = new RestaurantParam();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());
        // param.setS_code("8");
        // param.setHd_code("H0X");
        // param.setSp_code("000001");
        // param.setSale_date(yesterday);
        // param.setOther_sp_fg(null);
        param.setS_code("8");
        param.setHd_code("H0X");
        param.setSp_code("000001");
        // param.setSale_date(yesterday);           //데이터 가져올 결제일자를 어제로 고정 (스케쥴러 배포 시 주석해제)
        param.setSale_date("20240831");   // 데이터 가져올 결제일자 (스케쥴러 배포 시 주석)
        param.setOther_sp_fg("0");
        HttpEntity<Object> req = new HttpEntity<Object>(param, getHttpHeaders(null));
                                        
        ResponseEntity<SalesReceiptDtlList> res = getRestTemplate().exchange(
              "https://poson.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
            //   "http://devposon.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
              ,HttpMethod.POST
              ,req
              ,SalesReceiptDtlList.class);

        // 결과저장
        if (res.getBody().getRet_code().equals("0000")){

            salesReceiptDtlList.setSale_header(res.getBody().getSale_header());
            salesReceiptDtlList.setSale_detail(res.getBody().getSale_detail());
            salesReceiptDtlList.setCash_slip(res.getBody().getCash_slip());
            salesReceiptDtlList.setCard_slip(res.getBody().getCard_slip());
            salesReceiptDtlList.setHeader_cnt(res.getBody().getHeader_cnt());
            salesReceiptDtlList.setDetail_cnt(res.getBody().getDetail_cnt());
            salesReceiptDtlList.setCard_cnt(res.getBody().getCard_cnt());
            salesReceiptDtlList.setCash_cnt(res.getBody().getCash_cnt());
            salesReceiptDtlList.setCorp_slip(res.getBody().getCorp_slip());
            salesReceiptDtlList.setPrepaid_slip(res.getBody().getPrepaid_slip());
            log.debug(salesReceiptDtlList.toString());
            receiptDtl = salesReceiptDtlList.getSale_header();
            receiptDtlMapp = salesReceiptDtlList.getSale_detail();
            receiptDtlCash = salesReceiptDtlList.getCash_slip();
            receiptDtlCard = salesReceiptDtlList.getCard_slip();
            receiptDtlCorp = salesReceiptDtlList.getCorp_slip();
            receiptDtlPrepaidCard = salesReceiptDtlList.getPrepaid_slip();
            int headerCnt = salesReceiptDtlList.getHeader_cnt();
            int detailCnt = salesReceiptDtlList.getDetail_cnt();
            int cardCnt = salesReceiptDtlList.getCard_cnt();
            int cashCnt = salesReceiptDtlList.getCash_cnt();
            Integer count = 0;
    
            count = receiptDtl.size();
            result = setSalesreceiptDtl(receiptDtl, count, headerCnt, detailCnt, cardCnt, cashCnt);
            count = receiptDtlMapp.size();
            result = setSalesreceiptDtlMapp(receiptDtlMapp, count);
            count = receiptDtlCash.size();
            result = setSalesreceiptDtlCash(receiptDtlCash, count);
            count = receiptDtlCard.size();
            result = setSalesreceiptDtlCard(receiptDtlCard, count);
            count = receiptDtlCorp.size();
            result = setSalesreceiptDtlCorp(receiptDtlCorp, count);
            count = receiptDtlPrepaidCard.size();
            result = setSalesreceiptDtlPrepaidCard(receiptDtlPrepaidCard, count);
        }

        return result;
    }

    public Boolean setSalesreceiptDtl (List<SalesReceiptDtlDe> salesReceiptDtlDes, Integer count, Integer headerCnt, Integer detailCnt, Integer cardCnt, Integer cashCnt) throws SQLException 
    {
        log.debug(salesReceiptDtlDes.toString());
        Boolean result = false;
        for(int i = 0; i < count; i++)
        {
            int header_cnnt			=headerCnt;
            int detail_cnt			=detailCnt;
            int card_cnt			=cardCnt;
            int cash_cnt			=cashCnt;
            String hdCode			=salesReceiptDtlDes.get(i).getHd_code();
            String spCode			=salesReceiptDtlDes.get(i).getSp_code();
            String spName			=salesReceiptDtlDes.get(i).getSp_name();
            String bizno			=salesReceiptDtlDes.get(i).getBiz_no();
            String erpCode			=salesReceiptDtlDes.get(i).getErp_code();
            String saleDate			=salesReceiptDtlDes.get(i).getSale_date();
            String posNo			=salesReceiptDtlDes.get(i).getPos_no();
            String billNo			=salesReceiptDtlDes.get(i).getBill_no();
            String saleFlag			=salesReceiptDtlDes.get(i).getSale_flag();
            String cancelFlag		=salesReceiptDtlDes.get(i).getCancel_flag();
            String orderDatetime	=salesReceiptDtlDes.get(i).getOrder_datetime();
            String serveDatetime	=salesReceiptDtlDes.get(i).getServe_datetime();
            String orgSaleNo		=salesReceiptDtlDes.get(i).getOrg_sale_no();
            String getTotalAmt      =salesReceiptDtlDes.get(i).getTotal_amt();
            if(getTotalAmt.isEmpty()){
                getTotalAmt = "0";
            }
            int totalAmt			=Integer.parseInt(getTotalAmt);
            String getSaleAmt      =salesReceiptDtlDes.get(i).getSale_amt();
            if(getSaleAmt.isEmpty()){
                getSaleAmt = "0";
            }
            int saleAmt				=Integer.parseInt(getSaleAmt);
            String getNetAmt      =salesReceiptDtlDes.get(i).getNet_amt();
            if(getNetAmt.isEmpty()){
                getNetAmt = "0";
            }
            int netAmt				=Integer.parseInt(getNetAmt);
            String getTotalDcAmt      =salesReceiptDtlDes.get(i).getTotal_dc_amt();
            if(getTotalDcAmt.isEmpty()){
                getTotalDcAmt = "0";
            }
            int totalDcAmt			=Integer.parseInt(getTotalDcAmt);
            String getVatAmt      =salesReceiptDtlDes.get(i).getVat_amt();
            if(getVatAmt.isEmpty()){
                getVatAmt = "0";
            }
            int vatAmt				=Integer.parseInt(getVatAmt);
            String getServiceAmt      =salesReceiptDtlDes.get(i).getService_amt();
            if(getServiceAmt.isEmpty()){
                getServiceAmt = "0";
            }
            int serviceAmt			=Integer.parseInt(getServiceAmt);
            String getCashAmt      =salesReceiptDtlDes.get(i).getCash_amt();
            if(getCashAmt.isEmpty()){
                getCashAmt = "0";
            }
            int cashAmt				=Integer.parseInt(getCashAmt);
            String getCardAmt      =salesReceiptDtlDes.get(i).getCard_amt();
            if(getCardAmt.isEmpty()){
                getCardAmt = "0";
            }
            int cardAmt				=Integer.parseInt(getCardAmt);
            String getTickAmt      =salesReceiptDtlDes.get(i).getTick_amt();
            if(getTickAmt.isEmpty()){
                getTickAmt = "0";
            }
            int tickAmt				=Integer.parseInt(getTickAmt);
            String getPointAmt      =salesReceiptDtlDes.get(i).getPoint_amt();
            if(getPointAmt.isEmpty()){
                getPointAmt = "0";
            }
            int pointAmt			=Integer.parseInt(salesReceiptDtlDes.get(i).getPoint_amt());
            String getGiftAmt      =salesReceiptDtlDes.get(i).getGift_amt();
            if(getGiftAmt.isEmpty()){
                getGiftAmt = "0";
            }
            int giftAmt				=Integer.parseInt(getGiftAmt);
            String getPrepaidCardAmt      =salesReceiptDtlDes.get(i).getPrepaid_card_amt();
            if(getPrepaidCardAmt.isEmpty()){
                getPrepaidCardAmt = "0";
            }
            int prepaidCardAmt		=Integer.parseInt(getPrepaidCardAmt);
            String getOcbAmt      =salesReceiptDtlDes.get(i).getOcb_amt();
            if(getOcbAmt.isEmpty()){
                getOcbAmt = "0";
            }
            int ocbAmt				=Integer.parseInt(salesReceiptDtlDes.get(i).getOcb_amt());
            String getCorpDcAmt      =salesReceiptDtlDes.get(i).getCorp_dc_amt();
            if(getCorpDcAmt.isEmpty()){
                getCorpDcAmt = "0";
            }
            int corpDcAmt			=Integer.parseInt(getCorpDcAmt);
            String getNormalDcAmt      =salesReceiptDtlDes.get(i).getNormal_dc_amt();
            if(getNormalDcAmt.isEmpty()){
                getNormalDcAmt = "0";
            }
            int normalDcAmt			=Integer.parseInt(getNormalDcAmt);
            String getServiceDcAmt      =salesReceiptDtlDes.get(i).getService_dc_amt();
            if(getServiceDcAmt.isEmpty()){
                getServiceDcAmt = "0";
            }
            int serviceDcAmt		=Integer.parseInt(getServiceDcAmt);
            String getCouponDcAmt      =salesReceiptDtlDes.get(i).getCoupon_dc_amt();
            if(getCouponDcAmt.isEmpty()){
                getCouponDcAmt = "0";
            }
            int couponDcAmt			=Integer.parseInt(getCouponDcAmt);
            String getCustDcAmt      =salesReceiptDtlDes.get(i).getCust_dc_amt();
            if(getCustDcAmt.isEmpty()){
                getCustDcAmt = "0";
            }
            int custDcAmt			=Integer.parseInt(getCustDcAmt);
            String getCustAccumPoint      =salesReceiptDtlDes.get(i).getCust_accum_point();
            if(getCustAccumPoint.isEmpty()){
                getCustAccumPoint = "0";
            }
            int custAccumPoint		=Integer.parseInt(getCustAccumPoint);
            String getCustUsePoint      =salesReceiptDtlDes.get(i).getCust_use_point();
            if(getCustUsePoint.isEmpty()){
                getCustUsePoint = "0";
            }
            int custUsePoint		=Integer.parseInt(getCustUsePoint);
            String getCustCnt      =salesReceiptDtlDes.get(i).getCust_cnt();
            if(getCustCnt.isEmpty()){
                getCustCnt = "0";
            }
            int custCnt				=Integer.parseInt(getCustCnt);
            String getExchangeAmt      =salesReceiptDtlDes.get(i).getExchange_amt();
            if(getExchangeAmt.isEmpty()){
                getExchangeAmt = "0";
            }
            int exchargeAmt			=Integer.parseInt(getExchangeAmt);
            String getCoAmt      =salesReceiptDtlDes.get(i).getCo_amt();
            if(getCoAmt.isEmpty()){
                getCoAmt = "0";
            }
            int coAmt				=Integer.parseInt(getCoAmt);
            String getEmoneyAmt      =salesReceiptDtlDes.get(i).getEmoney_amt();
            if(getEmoneyAmt.isEmpty()){
                getEmoneyAmt = "0";
            }
            int emoneyAmt			=Integer.parseInt(getEmoneyAmt);
            String getEnuriAmt      =salesReceiptDtlDes.get(i).getEnuri_amt();
            if(getEnuriAmt.isEmpty()){
                getEnuriAmt = "0";
            }
            int enuriAmt			=Integer.parseInt(getEnuriAmt);
            String getPromotionDcAmt      =salesReceiptDtlDes.get(i).getPromotion_dc_amt();
            if(getPromotionDcAmt.isEmpty()){
                getPromotionDcAmt = "0";
            }
            int promotionDcAmt		=Integer.parseInt(salesReceiptDtlDes.get(i).getPromotion_dc_amt());
            String getReceiptAmt      =salesReceiptDtlDes.get(i).getReceipt_amt();
            if(getReceiptAmt.isEmpty()){
                getReceiptAmt = "0";
            }
            int receiptAmt			=Integer.parseInt(getReceiptAmt);
            String getChangeAmt      =salesReceiptDtlDes.get(i).getChange_amt();
            if(getChangeAmt.isEmpty()){
                getChangeAmt = "0";
            }
            int changeAmt			=Integer.parseInt(getChangeAmt);
            String custNo			=salesReceiptDtlDes.get(i).getCust_no();
            String custCardNo		=salesReceiptDtlDes.get(i).getCust_card_no();
            String saleSpCode       =salesReceiptDtlDes.get(i).getSale_sp_code();
            
            result = mapper.setSalesreceiptDtl(header_cnnt, detail_cnt, cash_cnt, card_cnt, hdCode, spCode
                                                , spName, bizno, erpCode, saleDate, posNo, billNo, saleFlag
                                                , cancelFlag, orderDatetime, serveDatetime, orgSaleNo, totalAmt
                                                , saleAmt, netAmt, totalDcAmt, vatAmt, serviceAmt, cashAmt, cardAmt
                                                , tickAmt, pointAmt, giftAmt, prepaidCardAmt, ocbAmt, corpDcAmt
                                                , normalDcAmt, serviceDcAmt, couponDcAmt, custDcAmt, custAccumPoint
                                                , custUsePoint, custCnt, exchargeAmt, coAmt, emoneyAmt, enuriAmt
                                                , promotionDcAmt, receiptAmt, changeAmt, custNo, custCardNo, saleSpCode
                                                );
        }
        return result;
    }

    public Boolean setSalesreceiptDtlMapp(List<SalesReceiptDtlMappDe> salesReceiptDtlMappDes, Integer count) throws SQLException
    {
        log.debug(salesReceiptDtlMappDes.toString());
        Boolean result = false;
        for(int i = 0; i < count; i++)
        {
            String hdCode			=salesReceiptDtlMappDes.get(i).getHd_code();
            String spCdoe			=salesReceiptDtlMappDes.get(i).getSp_code();
            String saleDate			=salesReceiptDtlMappDes.get(i).getSale_date();
            String posno			=salesReceiptDtlMappDes.get(i).getPos_no();
            String billNo			=salesReceiptDtlMappDes.get(i).getBill_no();
            String detailno			=salesReceiptDtlMappDes.get(i).getDetail_no();
            String itemCode			=salesReceiptDtlMappDes.get(i).getItem_code();
            String itemName			=salesReceiptDtlMappDes.get(i).getItem_name();
            String qty				=salesReceiptDtlMappDes.get(i).getQty();
            String getTotal         =salesReceiptDtlMappDes.get(i).getTotal_amt();
            if (getTotal.isEmpty()){
                getTotal = "0";
            }
            int totalAmt			=Integer.parseInt(getTotal);
            String getSaleAmt         =salesReceiptDtlMappDes.get(i).getSale_amt();
            if (getSaleAmt.isEmpty()){
                getSaleAmt = "0";
            }
            int saleAmt				=Integer.parseInt(getSaleAmt);
            String getNetAmt         =salesReceiptDtlMappDes.get(i).getNet_amt();
            if (getNetAmt.isEmpty()){
                getNetAmt = "0";
            }
            int netAmt				=Integer.parseInt(getNetAmt);
            String getTotalDcAmt         =salesReceiptDtlMappDes.get(i).getTotal_dc_amt();
            if (getTotalDcAmt.isEmpty()){
                getTotalDcAmt = "0";
            }
            int totalDcAmt			=Integer.parseInt(getTotalDcAmt);
            String getVatAmt         =salesReceiptDtlMappDes.get(i).getVat_amt();
            if (getVatAmt.isEmpty()){
                getVatAmt = "0";
            }
            int vatAmt				=Integer.parseInt(getVatAmt);
            String getPointAmt         =salesReceiptDtlMappDes.get(i).getPoint_amt();
            if (getPointAmt.isEmpty()){
                getPointAmt = "0";
            }
            int pointAmt			=Integer.parseInt(getPointAmt);
            String getCorpDcAmt         =salesReceiptDtlMappDes.get(i).getCorp_dc_amt();
            if (getCorpDcAmt.isEmpty()){
                getCorpDcAmt = "0";
            }
            int corpDcAmt			=Integer.parseInt(getCorpDcAmt);
            String getNormalDcAmt         =salesReceiptDtlMappDes.get(i).getNormal_dc_amt();
            if (getNormalDcAmt.isEmpty()){
                getNormalDcAmt = "0";
            }
            int normalDcAmt			=Integer.parseInt(getNormalDcAmt);
            String getServiceDcAmt         =salesReceiptDtlMappDes.get(i).getService_dc_amt();
            if (getServiceDcAmt.isEmpty()){
                getServiceDcAmt = "0";
            }
            int serviceDcAmt		=Integer.parseInt(getServiceDcAmt);
            String getCouponDcAmt         =salesReceiptDtlMappDes.get(i).getCoupon_dc_amt();
            if (getCouponDcAmt.isEmpty()){
                getCouponDcAmt = "0";
            }
            int couponDcAmt			=Integer.parseInt(getCouponDcAmt);
            String getCustDcAmt         =salesReceiptDtlMappDes.get(i).getCust_dc_amt();
            if (getCustDcAmt.isEmpty()){
                getCustDcAmt = "0";
            }
            int custDcAmt			=Integer.parseInt(getCustDcAmt);
            String getPromotionDcAmt         =salesReceiptDtlMappDes.get(i).getPromotion_dc_amt();
            if (getPromotionDcAmt.isEmpty()){
                getPromotionDcAmt = "0";
            }
            int promotionDcAmt		=Integer.parseInt(getPromotionDcAmt);
            String getCustAccumPoint         =salesReceiptDtlMappDes.get(i).getCust_accum_point();
            if (getCustAccumPoint.isEmpty()){
                getCustAccumPoint = "0";
            }
            int custAccumPoint		=Integer.parseInt(getCustAccumPoint);
            String getCustUsePoint         =salesReceiptDtlMappDes.get(i).getCust_use_point();
            if (getCustUsePoint.isEmpty()){
                getCustUsePoint = "0";
            }
            int custUsePoint		=Integer.parseInt(getCustUsePoint);
            String saleFlag			=salesReceiptDtlMappDes.get(i).getSale_flag();
            String orderDatetime	=salesReceiptDtlMappDes.get(i).getOrder_datetime();
            String serveDatetime	=salesReceiptDtlMappDes.get(i).getServe_datetime();
            String getItemPrice         =salesReceiptDtlMappDes.get(i).getItem_price();
            if (getItemPrice.isEmpty()){
                getItemPrice = "0";
            }
            int itemPrice			=Integer.parseInt(getItemPrice);
            String subMenuType		=salesReceiptDtlMappDes.get(i).getSub_menu_type();
            String subMenuFlag		=salesReceiptDtlMappDes.get(i).getSub_menu_flag();
            String parentDetailNo	=salesReceiptDtlMappDes.get(i).getParent_detail_no();
            String getSubMenuCount         =salesReceiptDtlMappDes.get(i).getSub_menu_count();
            if (getSubMenuCount.isEmpty()){
                getSubMenuCount = "0";
            }
            int subMenuCount		=Integer.parseInt(getSubMenuCount);
            String getCashAmt         =salesReceiptDtlMappDes.get(i).getCash_amt();
            if (getCashAmt.isEmpty()){
                getCashAmt = "0";
            }
            int cashAmt				=Integer.parseInt(getCashAmt);
            String getCardAmt         =salesReceiptDtlMappDes.get(i).getCard_amt();
            if (getCardAmt.isEmpty()){
                getCardAmt = "0";
            }
            int cardAmt				=Integer.parseInt(getCardAmt);
            String getTickAmt         =salesReceiptDtlMappDes.get(i).getTick_amt();
            if (getTickAmt.isEmpty()){
                getTickAmt = "0";
            }
            int tickAmt				=Integer.parseInt(getTickAmt);
            String getGiftAmt         =salesReceiptDtlMappDes.get(i).getGift_amt();
            if (getGiftAmt.isEmpty()){
                getGiftAmt = "0";
            }
            int giftAmt				=Integer.parseInt(getGiftAmt);
            String getPrepaidCardAmt         =salesReceiptDtlMappDes.get(i).getPrepaid_card_amt();
            if (getPrepaidCardAmt.isEmpty()){
                getPrepaidCardAmt = "0";
            }
            int prepaidCardAmt		=Integer.parseInt(getPrepaidCardAmt);
            String getOcbAmt         =salesReceiptDtlMappDes.get(i).getOcb_amt();
            if (getOcbAmt.isEmpty()){
                getOcbAmt = "0";
            }
            int ocbAmt				=Integer.parseInt(getOcbAmt);
            String getCoAmt         =salesReceiptDtlMappDes.get(i).getCo_amt();
            if (getCoAmt.isEmpty()){
                getCoAmt = "0";
            }
            int coAmt				=Integer.parseInt(getCoAmt);
            String getExchange         =salesReceiptDtlMappDes.get(i).getExchange_amt();
            if (getExchange.isEmpty()){
                getExchange = "0";
            }
            int exchangeAmt			=Integer.parseInt(getExchange);
            String getEmoneyAmt         =salesReceiptDtlMappDes.get(i).getEmoney_amt();
            if (getEmoneyAmt.isEmpty()){
                getEmoneyAmt = "0";
            }
            int emoneyAmt			=Integer.parseInt(getEmoneyAmt);
            String getEnuriAmt         =salesReceiptDtlMappDes.get(i).getEnuri_amt();
            if (getEnuriAmt.isEmpty()){
                getEnuriAmt = "0";
            }
            int enuriAmt			=Integer.parseInt(getEnuriAmt);
            String getChargeAmt         =salesReceiptDtlMappDes.get(i).getCharge_amt();
            if (getChargeAmt.isEmpty()){
                getChargeAmt = "0";
            }
            int chargeAmt			=Integer.parseInt(getChargeAmt);
            String itemTaxFlag		=salesReceiptDtlMappDes.get(i).getItem_tax_flag();
            String erpCode		    =salesReceiptDtlMappDes.get(i).getErp_code();
            String erpItemCode		=salesReceiptDtlMappDes.get(i).getItem_code();
            String saleSpCode		=salesReceiptDtlMappDes.get(i).getSale_sp_code();
            String barCode		    =salesReceiptDtlMappDes.get(i).getBarcode();
            String spName		    =salesReceiptDtlMappDes.get(i).getSp_name();
            String bizNo		    =salesReceiptDtlMappDes.get(i).getBiz_no();
            
            result = mapper.setSalesreceiptDtlMapp(hdCode, spCdoe, saleDate, posno, billNo
                                                    , detailno, itemCode, itemName, qty, totalAmt
                                                    , saleAmt, netAmt, totalDcAmt, vatAmt, pointAmt, corpDcAmt
                                                    , normalDcAmt, serviceDcAmt, couponDcAmt, custDcAmt, promotionDcAmt, custAccumPoint
                                                    , custUsePoint, saleFlag, orderDatetime, serveDatetime, itemPrice
                                                    , subMenuType, subMenuFlag, parentDetailNo, subMenuCount, cashAmt
                                                    , cardAmt, tickAmt, giftAmt, prepaidCardAmt, ocbAmt, coAmt, exchangeAmt
                                                    , emoneyAmt, enuriAmt, chargeAmt, itemTaxFlag, erpCode, erpItemCode, saleSpCode, barCode
                                                    , spName, bizNo
                                                    );
        }
        return result;
    }

    public Boolean setSalesreceiptDtlCash(List<SalesReceiptDtlCashDe> salesReceiptDtlCashDes, Integer count) throws SQLException
    {
        log.debug(salesReceiptDtlCashDes.toString());
        Boolean result = false;
        for(int i = 0; i < count; i++)
        {
            String hdCode			=salesReceiptDtlCashDes.get(i).getHd_code();
            String spCode			=salesReceiptDtlCashDes.get(i).getSp_code();
            String bizNo			=salesReceiptDtlCashDes.get(i).getBiz_no();
            String erpCode			=salesReceiptDtlCashDes.get(i).getErp_code();
            String saleDate			=salesReceiptDtlCashDes.get(i).getSale_date();
            String posNo			=salesReceiptDtlCashDes.get(i).getPos_no();
            String billNo			=salesReceiptDtlCashDes.get(i).getBill_no();
            String getCashSlipNo         =salesReceiptDtlCashDes.get(i).getCash_slip_no();
            if (getCashSlipNo.isEmpty()){
                getCashSlipNo = "0";
            }
            int cashSlipNo			=Integer.parseInt(getCashSlipNo);
            String identityNo		=salesReceiptDtlCashDes.get(i).getIdentity_no();
            String identityType		=salesReceiptDtlCashDes.get(i).getIdentity_type();
            String tradeFlag		=salesReceiptDtlCashDes.get(i).getTrade_flag();
            String tradeFlagNm		=salesReceiptDtlCashDes.get(i).getTrade_flag_nm();
            String spName		    =salesReceiptDtlCashDes.get(i).getSp_name();
            String saleSpCode		=salesReceiptDtlCashDes.get(i).getSale_sp_code();
            String getApprAmt         =salesReceiptDtlCashDes.get(i).getAppr_amt();
            if (getApprAmt.isEmpty()){
                getApprAmt = "0";
            }
            int apprAmt			    =Integer.parseInt(getApprAmt);
            String apprFlag			=salesReceiptDtlCashDes.get(i).getAppr_flag();
            String apprNo			=salesReceiptDtlCashDes.get(i).getAppr_no();
            String apprDatetime		=salesReceiptDtlCashDes.get(i).getAppr_datetime();
            String getVatAmt         =salesReceiptDtlCashDes.get(i).getVat_amt();
            if (getVatAmt.isEmpty()){
                getVatAmt = "0";
            }
            int vatAmt				=Integer.parseInt(getVatAmt);
            String getServiceAmt         =salesReceiptDtlCashDes.get(i).getService_amt();
            if (getServiceAmt.isEmpty()){
                getServiceAmt = "0";
            }
            int serviceAmt			=Integer.parseInt(getServiceAmt);
            String saleFlag			=salesReceiptDtlCashDes.get(i).getSale_flag();

            result = mapper.setSalesreceiptDtlCash(hdCode, spCode, bizNo, erpCode, saleDate, posNo, billNo, cashSlipNo
                                                    , identityNo, identityType, tradeFlag, tradeFlagNm, spName, saleSpCode, apprAmt, apprFlag
                                                    , apprNo, apprDatetime, vatAmt, serviceAmt, saleFlag
                                                    );
        }
        return result;
    }

    public Boolean setSalesreceiptDtlCard(List<SalesReceiptDtlCardDe> salesReceiptDtlCardDes, Integer count) throws SQLException
    {
        log.debug(salesReceiptDtlCardDes.toString());
        Boolean result = false;
        for(int i = 0; i < count; i++)
        {
            String hdCode			=salesReceiptDtlCardDes.get(i).getHd_code();
            String spCode			=salesReceiptDtlCardDes.get(i).getSp_code();
            String saleDate			=salesReceiptDtlCardDes.get(i).getSale_date();
            String posNo			=salesReceiptDtlCardDes.get(i).getPos_no();
            String billNo			=salesReceiptDtlCardDes.get(i).getBill_no();
            String getCardSlip      =salesReceiptDtlCardDes.get(i).getCard_slip_no();
            if(getCardSlip.isEmpty()){
                getCardSlip = "0";
            }
            int cardSlipNo			=Integer.parseInt(getCardSlip);
            String cardNo			=salesReceiptDtlCardDes.get(i).getCard_no();
            String getApprAmt      =salesReceiptDtlCardDes.get(i).getAppr_amt();
            if(getApprAmt.isEmpty()){
                getApprAmt = "0";
            }
            int apprAmt				=Integer.parseInt(getApprAmt);
            String installment		=salesReceiptDtlCardDes.get(i).getInstallment();
            String apprNo			=salesReceiptDtlCardDes.get(i).getAppr_no();
            String apprDatetime		=salesReceiptDtlCardDes.get(i).getAppr_datetime();
            String apprFlag			=salesReceiptDtlCardDes.get(i).getAppr_flag();
            String issuerCode		=salesReceiptDtlCardDes.get(i).getIssuer_code();
            String issuerName		=salesReceiptDtlCardDes.get(i).getIssuer_name();
            String acquirerCode		=salesReceiptDtlCardDes.get(i).getAcquirer_code();
            String acquirerName		=salesReceiptDtlCardDes.get(i).getAcquirer_name();
            String getCorpDcAmt      =salesReceiptDtlCardDes.get(i).getCorp_dc_amt();
            if(getCorpDcAmt.isEmpty()){
                getCorpDcAmt = "0";
            }
            int corpDcAmt			=Integer.parseInt(getCorpDcAmt);
            String getVatAmt      =salesReceiptDtlCardDes.get(i).getVat_amt();
            if(getVatAmt.isEmpty()){
                getVatAmt = "0";
            }
            int vatAmt				=Integer.parseInt(getVatAmt);
            String getServiceAmt      =salesReceiptDtlCardDes.get(i).getService_amt();
            if(getServiceAmt.isEmpty()){
                getServiceAmt = "0";
            }
            int serviceAmt			=Integer.parseInt(salesReceiptDtlCardDes.get(i).getService_amt());
            String saleFlag			=salesReceiptDtlCardDes.get(i).getSale_flag();
            String erpCode          =salesReceiptDtlCardDes.get(i).getErp_code();
            String spName           =salesReceiptDtlCardDes.get(i).getSp_name();
            String salesSpCode      =salesReceiptDtlCardDes.get(i).getSale_sp_code();
            String bizNo            =salesReceiptDtlCardDes.get(i).getBiz_no();

            result = mapper.setSalesreceptdtlCard(hdCode, spCode, saleDate, posNo, billNo
                                                    , cardSlipNo, cardNo, apprAmt, installment
                                                    , apprNo, apprDatetime, apprFlag, issuerCode, issuerName
                                                    , acquirerCode, acquirerName, corpDcAmt, vatAmt, serviceAmt, saleFlag, erpCode, spName, salesSpCode, bizNo
                                                    );
        }
        return result;
    }

    public Boolean setSalesreceiptDtlCorp(List<SalesReceiptDtlCorpDe> salesReceiptDtlCorpDe, Integer count) throws SQLException
    {
        log.debug(salesReceiptDtlCorpDe.toString());
        Boolean result = false;
        for(int i = 0; i < count; i++)
        {
            String sp_code			=salesReceiptDtlCorpDe.get(i).getSp_code();
            String pos_no			=salesReceiptDtlCorpDe.get(i).getPos_no();
            String erp_code			=salesReceiptDtlCorpDe.get(i).getErp_code();
            String sale_flag		=salesReceiptDtlCorpDe.get(i).getSale_flag();
            String wcc				=salesReceiptDtlCorpDe.get(i).getWcc();
            String valid_term       =salesReceiptDtlCorpDe.get(i).getValid_term();
            String proc_flag		=salesReceiptDtlCorpDe.get(i).getProc_flag();
            String card_data		=salesReceiptDtlCorpDe.get(i).getCard_data();
            String org_appr_date	=salesReceiptDtlCorpDe.get(i).getOrg_appr_date();
            String getAllot_rate    =salesReceiptDtlCorpDe.get(i).getAllot_rate();
            if(getAllot_rate.isEmpty())
            {
                getAllot_rate = "0";
            }
            int allot_rate			=Integer.parseInt(getAllot_rate);
            String appr_no			=salesReceiptDtlCorpDe.get(i).getAppr_no();
            String payment_flag		=salesReceiptDtlCorpDe.get(i).getPayment_flag();
            String getRemain_point    =salesReceiptDtlCorpDe.get(i).getRemain_point();
            if(getRemain_point.isEmpty())
            {
                getRemain_point = "0";
            }
            int remain_point		=Integer.parseInt(getRemain_point);
            String corp_code		=salesReceiptDtlCorpDe.get(i).getCorp_code();
            String sale_date		=salesReceiptDtlCorpDe.get(i).getSale_date();
            String allot_flag		=salesReceiptDtlCorpDe.get(i).getAllot_flag();
            String getCorp_dc_amt    =salesReceiptDtlCorpDe.get(i).getCorp_dc_amt();
            if(getCorp_dc_amt.isEmpty())
            {
                getCorp_dc_amt = "0";
            }
            int corp_dc_amt		=Integer.parseInt(getCorp_dc_amt);
            String corp_name		=salesReceiptDtlCorpDe.get(i).getCorp_name();
            String appr_flag		=salesReceiptDtlCorpDe.get(i).getAppr_flag();
            String msg				=salesReceiptDtlCorpDe.get(i).getMsg();
            String hd_code			=salesReceiptDtlCorpDe.get(i).getHd_code();
            String getUsable_point    =salesReceiptDtlCorpDe.get(i).getUsable_point();
            if(getUsable_point.isEmpty())
            {
                getUsable_point = "0";
            }
            int usable_point		=Integer.parseInt(getUsable_point);
            String sp_name			=salesReceiptDtlCorpDe.get(i).getSp_name();
            String card_no			=salesReceiptDtlCorpDe.get(i).getCard_no();
            String corp_slip_no		=salesReceiptDtlCorpDe.get(i).getCorp_slip_no();
            String appr_datetime	=salesReceiptDtlCorpDe.get(i).getAppr_datetime();
            String getUse_point    =salesReceiptDtlCorpDe.get(i).getUse_point();
            if(getUse_point.isEmpty())
            {
                getUsable_point = "0";
            }
            int use_point		=Integer.parseInt(getUse_point);
            String sale_sp_code		=salesReceiptDtlCorpDe.get(i).getSale_sp_code();
            String biz_no			=salesReceiptDtlCorpDe.get(i).getBiz_no();
            String getSale_point    =salesReceiptDtlCorpDe.get(i).getSale_point();
            if(getSale_point.isEmpty())
            {
                getSale_point = "0";
            }
            int sale_point		=Integer.parseInt(getSale_point);
            String card_len			=salesReceiptDtlCorpDe.get(i).getCard_len();
            String bill_no			=salesReceiptDtlCorpDe.get(i).getBill_no();

            result = mapper.setSalereceiptdtlCorp(sp_code, pos_no, erp_code, sale_flag, wcc, valid_term, proc_flag, card_data,
                                                 org_appr_date, allot_rate, appr_no, payment_flag, remain_point, 
                                                 corp_code, sale_date, allot_flag, corp_dc_amt, corp_name, appr_flag, msg, hd_code, 
                                                 usable_point, sp_name, card_no, corp_slip_no, appr_datetime, use_point, sale_sp_code, biz_no, 
                                                 sale_point, card_len, bill_no);
        }
        return result;
    }

    public Boolean setSalesreceiptDtlPrepaidCard(List<SalesReceiptDtlPrepaidCard> salesReceiptDtlPrepaidCard, Integer count) throws SQLException
    {
        log.debug(salesReceiptDtlPrepaidCard.toString());
        Boolean result = false;
        for(int i = 0; i < count; i++)
        {
            String sp_code			=salesReceiptDtlPrepaidCard.get(i).getSp_code();
            String pos_no			=salesReceiptDtlPrepaidCard.get(i).getPos_no();
            String erp_code			=salesReceiptDtlPrepaidCard.get(i).getErp_code();
            String sale_flag		=salesReceiptDtlPrepaidCard.get(i).getSale_flag();
            String getAppr_amt		=salesReceiptDtlPrepaidCard.get(i).getAppr_amt();
            if(getAppr_amt.isEmpty())
            {
                getAppr_amt = "0";
            }
            int appr_amt		    =Integer.parseInt(getAppr_amt);
            String prepaid_code     =salesReceiptDtlPrepaidCard.get(i).getPrepaid_code();
            String wcc				=salesReceiptDtlPrepaidCard.get(i).getWcc();
            String proc_flag		=salesReceiptDtlPrepaidCard.get(i).getProc_flag();
            String card_data		=salesReceiptDtlPrepaidCard.get(i).getCard_data();
            String org_appr_date	=salesReceiptDtlPrepaidCard.get(i).getOrg_appr_date();
            String appr_no			=salesReceiptDtlPrepaidCard.get(i).getAppr_no();
            String payment_flag		=salesReceiptDtlPrepaidCard.get(i).getPayment_flag();
            String sale_date		=salesReceiptDtlPrepaidCard.get(i).getSale_date();
            String appr_flag		=salesReceiptDtlPrepaidCard.get(i).getAppr_flag();
            String msg				=salesReceiptDtlPrepaidCard.get(i).getMsg();
            String prepaid_slip_no  =salesReceiptDtlPrepaidCard.get(i).getPrepaid_slip_no();
            String hd_code			=salesReceiptDtlPrepaidCard.get(i).getHd_code();
            String sp_name			=salesReceiptDtlPrepaidCard.get(i).getSp_name();
            String card_no			=salesReceiptDtlPrepaidCard.get(i).getCard_no();
            String prepaid_name     =salesReceiptDtlPrepaidCard.get(i).getPrepaid_name();
            String appr_datetime	=salesReceiptDtlPrepaidCard.get(i).getAppr_datetime();
            String sale_sp_code		=salesReceiptDtlPrepaidCard.get(i).getSale_sp_code();
            String biz_no			=salesReceiptDtlPrepaidCard.get(i).getBiz_no();
            String card_len			=salesReceiptDtlPrepaidCard.get(i).getCard_len();
            String bill_no			=salesReceiptDtlPrepaidCard.get(i).getBill_no();

            result = mapper.setSalesreceptdtlPrepaidCard(sp_code, pos_no, erp_code, sale_flag, appr_amt, prepaid_code, 
                                                         wcc, proc_flag, org_appr_date, card_data, appr_no, payment_flag, 
                                                         sale_date, appr_flag, msg, prepaid_slip_no, hd_code, sp_name, card_no, 
                                                         prepaid_name, appr_datetime, sale_sp_code, biz_no, card_len, bill_no);
        }
        return result;
    }

    public Boolean setPrepaidCardSale() throws SQLException
    {
        Boolean result = false;
        List<PrepaidCardSale> prepaidCardSale;
        PrepaidCardSaleList prepaidCardSaleList= new PrepaidCardSaleList();
        RestaurantParam param = new RestaurantParam();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());
        param.setS_code("7");
        param.setHd_code("H0X");
        param.setSp_code("000001");
        // param.setSale_date(yesterday);           //데이터 가져올 결제일자를 어제로 고정 (스케쥴러 배포 시 주석해제)
        param.setSale_date("20240831");   // 데이터 가져올 결제일자 (스케쥴러 배포 시 주석)
        param.setOther_sp_fg("0");
        HttpEntity<Object> req = new HttpEntity<Object>(param, getHttpHeaders(null));
                                        
        ResponseEntity<PrepaidCardSaleList> res = getRestTemplate().exchange(
              "https://poson.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
            //   "https://devposon.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
              ,HttpMethod.POST
              ,req
              ,PrepaidCardSaleList.class);

        // 결과저장
        if (res.getBody().getRet_code().equals("0000")){

            prepaidCardSaleList.setData(res.getBody().getData());
            log.debug(prepaidCardSaleList.toString());
            Integer count = 0;
    
            prepaidCardSale = prepaidCardSaleList.getData();
    
            count = prepaidCardSale.size();
            result = setPrepaidCardSale(prepaidCardSale, count);
        }
        return result;
    }

    public Boolean setPrepaidCardSale(List<PrepaidCardSale> prepaidCardSale, Integer count) throws SQLException
    {
        log.debug(prepaidCardSale.toString());
        Boolean result = false;
        for(int i = 0; i < count; i++)
        {
            String hd_code                  = prepaidCardSale.get(i).getHd_code();
            String sp_code                  = prepaidCardSale.get(i).getSp_code();
            String sp_name                  = prepaidCardSale.get(i).getSp_name();
            String biz_no                  = prepaidCardSale.get(i).getBiz_no();
            String erp_code                  = prepaidCardSale.get(i).getErp_code();
            String sale_date                  = prepaidCardSale.get(i).getSale_date();
            String getPrepaid_sale_cnt		=prepaidCardSale.get(i).getPrepaid_sale_cnt();
            if(getPrepaid_sale_cnt.isEmpty())
            {
                getPrepaid_sale_cnt = "0";
            }
            int prepaid_sale_cnt		    =Integer.parseInt(getPrepaid_sale_cnt);
            String getPrepaid_sale_amt		=prepaidCardSale.get(i).getPrepaid_sale_amt();
            if(getPrepaid_sale_amt.isEmpty())
            {
                getPrepaid_sale_amt = "0";
            }
            int prepaid_sale_amt		    =Integer.parseInt(getPrepaid_sale_amt);
            String getPrepaid_sale_normal_cnt		=prepaidCardSale.get(i).getPrepaid_sale_normal_cnt();
            if(getPrepaid_sale_normal_cnt.isEmpty())
            {
                getPrepaid_sale_normal_cnt = "0";
            }
            int prepaid_sale_normal_cnt		    =Integer.parseInt(getPrepaid_sale_normal_cnt);
            String getPrepaid_sale_normal_amt		=prepaidCardSale.get(i).getPrepaid_sale_normal_amt();
            if(getPrepaid_sale_normal_amt.isEmpty())
            {
                getPrepaid_sale_normal_amt = "0";
            }
            int prepaid_sale_normal_amt		    =Integer.parseInt(getPrepaid_sale_normal_amt);
            String getPrepaid_sale_cancel_cnt		=prepaidCardSale.get(i).getPrepaid_sale_cancel_cnt();
            if(getPrepaid_sale_cancel_cnt.isEmpty())
            {
                getPrepaid_sale_cancel_cnt = "0";
            }
            int prepaid_sale_cancel_cnt		    =Integer.parseInt(getPrepaid_sale_cancel_cnt);
            String getPrepaid_sale_cancel_amt		=prepaidCardSale.get(i).getPrepaid_sale_cancel_amt();
            if(getPrepaid_sale_cancel_amt.isEmpty())
            {
                getPrepaid_sale_cancel_amt = "0";
            }
            int prepaid_sale_cancel_amt		    =Integer.parseInt(getPrepaid_sale_cancel_amt);
            String getPrepaid_charge_cnt		=prepaidCardSale.get(i).getPrepaid_charge_cnt();
            if(getPrepaid_charge_cnt.isEmpty())
            {
                getPrepaid_charge_cnt = "0";
            }
            int prepaid_charge_cnt		    =Integer.parseInt(getPrepaid_charge_cnt);
            String getPrepaid_charge_amt		=prepaidCardSale.get(i).getPrepaid_charge_amt();
            if(getPrepaid_charge_amt.isEmpty())
            {
                getPrepaid_charge_amt = "0";
            }
            int prepaid_charge_amt		    =Integer.parseInt(getPrepaid_charge_amt);
            String getPrepaid_charge_normal_cnt		=prepaidCardSale.get(i).getPrepaid_charge_normal_cnt();
            if(getPrepaid_sale_cnt.isEmpty())
            {
                getPrepaid_charge_normal_cnt = "0";
            }
            int prepaid_charge_normal_cnt		    =Integer.parseInt(getPrepaid_charge_normal_cnt);
            String getPrepaid_charge_normal_amt		=prepaidCardSale.get(i).getPrepaid_charge_normal_amt();
            if(getPrepaid_charge_normal_amt.isEmpty())
            {
                getPrepaid_charge_normal_amt = "0";
            }
            int prepaid_charge_normal_amt		    =Integer.parseInt(getPrepaid_charge_normal_amt);
            String getPrepaid_charge_cancel_cnt		=prepaidCardSale.get(i).getPrepaid_charge_cancel_cnt();
            if(getPrepaid_charge_cancel_cnt.isEmpty())
            {
                getPrepaid_charge_cancel_cnt = "0";
            }
            int prepaid_charge_cancel_cnt		    =Integer.parseInt(getPrepaid_charge_cancel_cnt);
            String getPrepaid_charge_cancel_amt		=prepaidCardSale.get(i).getPrepaid_charge_cancel_amt();
            if(getPrepaid_charge_cancel_amt.isEmpty())
            {
                getPrepaid_charge_cancel_amt = "0";
            }
            int prepaid_charge_cancel_amt		    =Integer.parseInt(getPrepaid_charge_cancel_amt);
        
        
            result = mapper.setPrepaidCardSale(hd_code, sp_code, sp_name, biz_no, erp_code, sale_date, prepaid_sale_cnt
                                                , prepaid_sale_amt, prepaid_sale_normal_cnt, prepaid_sale_normal_amt, prepaid_sale_cancel_cnt
                                                , prepaid_sale_cancel_amt, prepaid_charge_cnt, prepaid_charge_amt, prepaid_charge_normal_cnt
                                                , prepaid_charge_normal_amt, prepaid_charge_cancel_cnt, prepaid_charge_cancel_amt);
        }
        return result;
    }

    public Boolean setPrepaidCardSaleDtl() throws SQLException
    {
        Boolean result = false;
        List<PrepaidCardSaleDtl> prepaidCardSaleDtl;
        PrepaidCardSaleListDtlList prepaidCardSaleListDtlList= new PrepaidCardSaleListDtlList();
        RestaurantParam param = new RestaurantParam();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());
        param.setS_code("7");
        param.setHd_code("H0X");
        param.setSp_code("000001");
        // param.setSale_date(yesterday);           //데이터 가져올 결제일자를 어제로 고정 (스케쥴러 배포 시 주석해제)
        param.setSale_date("20240831");   // 데이터 가져올 결제일자 (스케쥴러 배포 시 주석)
        param.setOther_sp_fg("0");
        HttpEntity<Object> req = new HttpEntity<Object>(param, getHttpHeaders(null));
                                        
        ResponseEntity<PrepaidCardSaleListDtlList> res = getRestTemplate().exchange(
              "https://poson.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
            //   "http://devposon.easypos.net/servlet/EasyPosJsonChannelSVL?cmd=TlxSyncEasyposSaleCMD"
              ,HttpMethod.POST
              ,req
              ,PrepaidCardSaleListDtlList.class);

        // 결과저장
        if (res.getBody().getRet_code().equals("0000")){

            prepaidCardSaleListDtlList.setSaleDetailData(res.getBody().getSaleDetailData());
            log.debug(prepaidCardSaleListDtlList.toString());
            Integer count = 0;
    
            prepaidCardSaleDtl = prepaidCardSaleListDtlList.getSaleDetailData();
    
            count = prepaidCardSaleDtl.size();
            result = setPrepaidCardSaleDtl(prepaidCardSaleDtl, count);
        }
        return result;
    }

    public Boolean setPrepaidCardSaleDtl(List<PrepaidCardSaleDtl> prepaidCardSaleDtl, Integer count) throws SQLException
    {
        log.debug(prepaidCardSaleDtl.toString());
        Boolean result = false;
        for(int i = 0; i < count; i++)
        {
            String sale_date			=prepaidCardSaleDtl.get(i).getSale_date();
            String pos_no			    =prepaidCardSaleDtl.get(i).getPos_no();
            String seq			        =prepaidCardSaleDtl.get(i).getSeq();
            String prepaid_name			=prepaidCardSaleDtl.get(i).getPrepaid_name();
            String card_no			    =prepaidCardSaleDtl.get(i).getCard_no();
            String getQty               =prepaidCardSaleDtl.get(i).getQty();
            if(getQty.isEmpty()){
                getQty = "0";
            }
            int qty			            =Integer.parseInt(getQty);
            String getSale_amt			=prepaidCardSaleDtl.get(i).getSale_amt();
            if(getSale_amt.isEmpty()){
                getSale_amt = "0";
            }
            int sale_amt			    =Integer.parseInt(getSale_amt);
            String sale_flag		    =prepaidCardSaleDtl.get(i).getSale_flag();
            String payment_flag			=prepaidCardSaleDtl.get(i).getPayment_flag();
            String appr_no		        =prepaidCardSaleDtl.get(i).getAppr_no();
            String appr_card_no		    =prepaidCardSaleDtl.get(i).getAppr_card_no();

            result = mapper.setPrepaidCardSaleDtl(sale_date, pos_no, seq, appr_card_no, prepaid_name, card_no, qty, sale_amt, sale_flag, payment_flag, appr_no);
        }
        return result;
    }
}
