package com.cesco.api.cesnetapi.res.mappers;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RestaurantPoseMapper {
    
    Boolean setDailySales(  
            @Param("HD_CODE")					String hdCode
        ,	@Param("SP_CODE")					String spCode
        ,	@Param("SP_NAME")					String spName
        ,	@Param("BIZ_NO")					String bizNo
        ,	@Param("ERP_CODE")					String erpCode
        ,	@Param("SALE_DATE")					String salesDate
        ,	@Param("TOTAL_AMT")					int totalAmt
        ,	@Param("SALE_AMT")					int saleAmt
        ,	@Param("NET_AMT")					int netAmt
        ,	@Param("TOTAL_DC_AMT")				int totalDcAmt
        ,	@Param("VAT_AMT")					int vatAmt
        ,	@Param("BILL_QTY")					int billQty
        ,	@Param("NORMAL_QTY")				int normalQty
        ,	@Param("NORMAL_AMT")				int normalAmt
        ,	@Param("RETURN_QTY")				int returnQty
        ,	@Param("RETURN_AMT")				int returnAmt
        ,	@Param("SERVICE_AMT")				int serviceAmt
        ,	@Param("CASH_QTY")					int cashQty
        ,	@Param("CASH_AMT")					int cashAmt
        ,	@Param("CARD_QTY")					int cardQty
        ,	@Param("CARD_AMT")					int cardAmt
        ,	@Param("TICK_QTY")					int tickQty
        ,	@Param("TICK_AMT")					int tickAmt
        ,	@Param("POINT_QTY")					int pointQty
        ,	@Param("POINT_AMT")					int pointAmt
        ,	@Param("GIFT_QTY")					int giftQty
        ,	@Param("GIFT_AMT")					int giftAmt
        ,	@Param("PREPAID_CARD_QTY")			int prepaidCardQty
        ,	@Param("PREPAID_CARD_AMT")			int prepaidCardAmt
        ,	@Param("OCB_QTY")					int ocbQty
        ,	@Param("OCB_AMT")					int ocbAmt
        ,	@Param("CORP_DC_QTY")				int corpDcQty
        ,	@Param("CORP_DC_AMT")				int corpDcAmt
        ,	@Param("NORMAIL_DC_QTY")			int normalDcQty
        ,	@Param("NORMAIL_DC_AMT")			int normalDcAmt
        ,	@Param("SERVICE_DC_QTY")			int serviceDcQty
        ,	@Param("SERVICE_DC_AMT")			int serviceDcAmt
        ,	@Param("COUPON_DC_QTY")				int couponDcQty
        ,	@Param("COUPON_DC_AMT")				int couponDcAmt
        ,	@Param("CUST_DC_QTY")				int custDcQty
        ,	@Param("CUST_DC_AMT")				int custDcAmt
        ,	@Param("CUST_ACCUM_POINT")			int custAccumPoint
        ,	@Param("CUST_USE_POINT")			int custUsePoint
        ,	@Param("CUST_CNT")					int custCnt
        ,	@Param("DU_DC_QTY")					int duDcQty
        ,	@Param("DU_DC_AMT")					int duDcAmt
        ,	@Param("EXCHANGE_QTY")				int exchangeQty
        ,	@Param("EXCHANGE_AMT")				int exchangeAmt
        ,	@Param("CO_QTY")					int coQty
        ,	@Param("CO_AMT")					int coAmt
        ,	@Param("EMONEY_QTY")				int emoneyQty
        ,	@Param("EMONEY_AMT")				int emoneyAmt
        ,	@Param("ENURI_QTY")					int enuriQty
        ,	@Param("ENURI_AMT")					int enuriAmt
        ,	@Param("CASH_CANCEL_QTY")			int cashCancelQty
        ,	@Param("CASH_CANCEL_AMT")			int cashCancelAmt
        ,	@Param("CARD_CANCEL_QTY")			int cardCancelQty
        ,	@Param("CARD_CANCEL_AMT")			int cardCancelAmt
        ,	@Param("TICK_CANCEL_QTY")			int tickCancelQty
        ,	@Param("TICK_CANCEL_AMT")			int tickCancelAmt
        ,	@Param("POINT_CANCEL_QTY")			int pointCancelQty
        ,	@Param("POINT_CANCEL_AMT")			int pointCancelAmt
        ,	@Param("GIFT_CANCEL_QTY")			int giftCancelQty
        ,	@Param("GIFT_CANCEL_AMT")			int giftCancelAmt
        ,	@Param("PREPAID_CANCEL_QTY")		int prepaidCancelQty
        ,	@Param("PREPAID_CANCEL_AMT")		int prepaidCancelAmt
        ,	@Param("OCB_CANCEL_QTY")			int ocbCancelQty
        ,	@Param("OCB_CANCEL_AMT")			int ocbCancelAmt
        ,	@Param("EXCHANGE_CANCEL_QTY")		int exchangeCancelQty
        ,	@Param("EXCHANGE_CANCEL_AMT")		int exchangeCancelAmt
        ,	@Param("CO_CANCEL_QTY")				int coCancelQty
        ,	@Param("CO_CANCEL_AMT")				int coCancelAmt
        ,	@Param("EMONEY_CANCEL_QTY")			int emoneyCancelQty
        ,	@Param("EMONEY_CANCEL_AMT")			int emoneyCancelAmt
        ,	@Param("PROMOTION_DC_QTY")			int promotionDcQty
        ,	@Param("PROMOTION_DC_AMT")			int promotionDcAmt  
    ) throws SQLException;

    Boolean setFranchises(
            @Param("HD_CODE")					String hdCode
        ,	@Param("SP_CODE")					String spCode
        ,	@Param("SP_NAME")					String spName
        ,	@Param("BIZ_NO")					String bizNo
        ,	@Param("OPEN_FLAG")					String openFlag
        ,	@Param("ERP_SP_CODE")				String erpSpcode
        ,	@Param("MASTER_NAME")				String masterName
        ,	@Param("TEL_NO")					String telNo
        ,	@Param("SP_TYPE")					String spType
        ,	@Param("AREA_CODE")					String areaCode
        ,	@Param("SALE_CLASS_CODE")			String salesClassCode
        ,	@Param("SALE_CLASS_NAME")			String saleClassName
        ,	@Param("ADDRESS1")					String adress1
        ,	@Param("ADDRESS2")					String adress2
        ,	@Param("BRAND_CODE")				String brandCode
        ,	@Param("BRAND_NAME")				String brandName
    ) throws SQLException;

    Boolean setSalesDtl(
            @Param("HD_CODE")					String hdCode
        ,	@Param("SP_CODE")					String spCode
        ,	@Param("SP_NAME")					String spName
        ,	@Param("BIZ_NO")					String bizNo
        ,	@Param("ERP_CODE")					String erpCode
        ,	@Param("SALE_DATE")					String saleDate
        ,	@Param("TIME_SLOT")				    String timeSlot
        ,	@Param("SALE_DAY")				    String saleDay
        ,	@Param("BILL_QTY")					int billQty
        ,	@Param("TOTAL_AMT")					int totalAmt
        ,	@Param("SALE_AMT")					int saleAmt
        ,	@Param("NET_AMT")			        int netAmt
        ,	@Param("BILL_AMT")			        int billAmt
    ) throws SQLException;

    Boolean setItemlist(
            @Param("HD_CODE")				    String hdCode
        ,	@Param("SP_CODE")				    String spCode
        ,	@Param("ITEM_CODE")				    String itemCode
        ,	@Param("ITEM_NAME")				    String itemName
        ,	@Param("SHORT_NAME")			    String shortName
        ,	@Param("EMGLICH_NAME")			    String emglichName
        ,	@Param("QTY_NAME")				    String qtyName
        ,	@Param("LARGE_SCALE")			    String largeScale
        ,	@Param("LARGE_SCALE_NM")		    String largeScaleNm
        ,	@Param("MEDIUM_SCALE")			    String mediumScale
        ,	@Param("MEDIUM_SCALE_MN")		    String mediumScaleNm
        ,	@Param("SMALL_SCALE")			    String smallScale
        ,	@Param("SMALL_SCALE_NM")		    String smallScaleNm
        ,	@Param("ITEM_COST")				    int	itemCost
        ,	@Param("ITEM_PRICE")			    int	itemPrice
        ,	@Param("VAT_RATE")				    int	vatRate
        ,	@Param("VAT_RATE_FG")			    String vatRateFg
        ,	@Param("USE_FLAG")				    String useFlag
        ,	@Param("TAX_FLAG")				    String taxFlag
        ,	@Param("TAX_FLAG_NM")			    String taxFlagNm
        ,	@Param("PRICE_FLAG")			    String priceFlag
        ,	@Param("PRICE_FLAG_NM")			    String priceFlagNm
        ,	@Param("SUB_MENU_TYPE")			    String submenuType
        ,	@Param("SERVICE_FLAG")			    String serviceFlag
        ,	@Param("ITEM_TYPE")				    String itemType
        ,	@Param("ITEM_TYPE_NM")			    String itemTypeNm
        ,	@Param("ITEM_IMG_URL")			    String itemImgUrl
        ,	@Param("ITEM_DESCRIPTION")		    String itemDescription
        ,	@Param("ITEM_DESCRIPTION_ENG")	    String itemDescriptionEng
        ,	@Param("SALE_START_TIME")		    String saleStartTime
        ,	@Param("SALE_END_TIME")			    String saleEndTime
        ,	@Param("DAILY_SALE_QTY")		    int	dailySaleQty
        ,	@Param("KIOSK_SUB_MENU_TYPE")	    String kioskSubMenuType
        ,	@Param("KIOSK_DISPLAY_ICON")	    String kioskDisplayIcon
        ,	@Param("SALE_DAYS")				    String saleDays
        ,	@Param("SOLD_OUT")				    String soldOut
        ,	@Param("ORDER_GROUP_FG")		    String orderGroupFg
    ) throws SQLException;

    Boolean setSalesreceipt(
            @Param("HD_CODE")			String hdCode
        ,	@Param("SP_CODE")			String spCode
        ,	@Param("SP_NAME")			String spName
        ,	@Param("BIZ_NO")			String bizNo
        ,	@Param("ERP_CODE")			String erpCode
        ,	@Param("SALE_DATE")			String saleDate
        ,	@Param("POS_NO")			String posNo
        ,	@Param("BILL_NO")			String billNo
        ,	@Param("SALE_FLAG")			String saleFlag
        ,	@Param("CANCEL_FLAG")		String cancelFlag
        ,	@Param("ORDER_DATETIME")	String orderDatetime
        ,	@Param("SERVE_DATETIME")	    String serveDatetime
        ,	@Param("ORG_SALE_NO")		String orgSaleNo
        ,	@Param("TOTAL_AMT")			int totalAmt
        ,	@Param("SALE_AMT")			int saleAmt
        ,	@Param("NET_AMT")			int netAmt
        ,	@Param("TOTAL_DC_AMT")		int totalDcAmt
        ,	@Param("VAT_AMT")			int vatAmt
        ,	@Param("SERVICE_AMT")		int serviceAmt
        ,	@Param("CASH_AMT")			int cashAmt
        ,	@Param("CARD_AMT")			int cardAmt
        ,	@Param("TICK_AMT")			int tickAmt
        ,	@Param("POINT_AMT")			int pointAmt
        ,	@Param("GIFT_AMT")			int giftAmt
        ,	@Param("PREPAID_CARD_AMT")	int prepaidCardAmt
        ,	@Param("OCB_AMT")			int ocbAmt
        ,	@Param("CORP_DC_AMT")		int corpDcAmt
        ,	@Param("NORMAIL_DC_AMT")	int normalDcAmt
        ,	@Param("SERVICE_DC_AMT")	int serviceDcAmt
        ,	@Param("COUPON_DC_AMT")		int couponDcAmt
        ,	@Param("CUST_DC_AMT")		int custDcAmt
        ,	@Param("CUST_ACCUM_POINT")	int custAccumPoint
        ,	@Param("CUST_USE_POINT")	int custUsePoint
        ,	@Param("CUST_CNT")			int custCnt
        ,	@Param("EXCHANGE_AMT")		int exchangeAmt
        ,	@Param("CO_AMT")			int coAmt
        ,	@Param("EMONEY_AMT")		int emoneyAmt
        ,	@Param("ENURI_AMT")			int enuriAmt
        ,	@Param("PROMOTION_DC_AMT")	int promotionDcAmt
        ,	@Param("CUST_NO")	        String custNo
        ,	@Param("CUST_CARD_NO")	    String custCardNo
        ,	@Param("SALE_SP_CODE")	    String saleSpCode
    ) throws SQLException;

    Boolean setSalesreceiptDtl(
            @Param("HEADER_CNT")			int headerCnt		
        ,	@Param("DETAIL_CNT")			int detailCnt
        ,	@Param("CASH_CNT")				int cash_cnt
        ,	@Param("CARD_CNT")				int cardCnt
        ,	@Param("HD_CODE")				String hdCode
        ,	@Param("SP_CODE")				String spCode
        ,	@Param("SP_NAME")				String spName
        ,	@Param("BIZ_NO")				String bizno
        ,	@Param("ERP_CODE")				String erpCode
        ,	@Param("SALE_DATE")				String saleDate
        ,	@Param("POS_NO")				String posNo
        ,	@Param("BILL_NO")				String billNo
        ,	@Param("SALE_FLAG")				String saleFlag
        ,	@Param("CANCEL_FLAG")			String cancelFlag
        ,	@Param("ORDER_DATETIME")		String orderDatetime
        ,	@Param("SERVE_DATETIME")		String serveDatetime
        ,	@Param("ORG_SALE_NO")			String orgSaleNo
        ,	@Param("TOTAL_AMT")				int totalAmt
        ,	@Param("SALE_AMT")				int saleAmt
        ,	@Param("NET_AMT")				int netAmt
        ,	@Param("TOTAL_DC_AMT")			int totalDcAmt
        ,	@Param("VAT_AMT")				int vatAmt
        ,	@Param("SERVICE_AMT")			int serviceAmt
        ,	@Param("CASH_AMT")				int cashAmt
        ,	@Param("CARD_AMT")				int cardAmt
        ,	@Param("TICK_AMT")				int tickAmt
        ,	@Param("POINT_AMT")				int pointAmt
        ,	@Param("GIFT_AMT")				int giftAmt
        ,	@Param("PREPAID_CARD_AMT")		int prepaidCardAmt
        ,	@Param("OCB_AMT")				int ocbAmt
        ,	@Param("CORP_DC_AMT")			int corpDcAmt
        ,	@Param("NORMAIL_DC_AMT")		int normalDcAmt
        ,	@Param("SERVICE_DC_AMT")		int serviceDcAmt
        ,	@Param("COUPON_DC_AMT")			int couponDcAmt
        ,	@Param("CUST_DC_AMT")			int custDcAmt
        ,	@Param("CUST_ACCUM_POINT")		int custAccumPoint
        ,	@Param("CUST_USE_POINT")		int custUsePoint
        ,	@Param("CUST_CNT")				int custCnt
        ,	@Param("EXCHANGE_AMT")			int exchargeAmt
        ,	@Param("CO_AMT")				int coAmt
        ,	@Param("EMONEY_AMT")			int emoneyAmt
        ,	@Param("ENURI_AMT")				int enuriAmt
        ,	@Param("PROMOTION_DC_AMT")		int promotionDcAmt
        ,	@Param("RECIEPT_AMT")			int receiptAmt
        ,	@Param("CHANGE_AMT")			int changeAmt
        ,	@Param("CUST_NO")				String custNo
        ,	@Param("CUST_CARD_NO")			String custCardNo
        ,   @Param("SALE_SP_CODE")          String saleSpCode
    ) throws SQLException;

    Boolean setSalesreceiptDtlMapp(
        @Param("HD_CODE")				    String hdCode
    ,    @Param("SP_CODE")				    String spCdoe
    ,    @Param("SALE_DATE")				String saleDate
    ,    @Param("POS_NO")				    String posno
    ,    @Param("BILL_NO")				    String billNo
    ,    @Param("DETAIL_NO")				String detailno
    ,    @Param("ITEM_CODE")				String itemCode
    ,    @Param("ITEM_NAME")				String itemName
    ,    @Param("QTY")					    String qty
    ,    @Param("TOTAL_AMT")				int totalAmt
    ,    @Param("SALE_AMT")				    int saleAmt
    ,    @Param("NET_AMT")				    int netAmt
    ,    @Param("TOTAL_DC_AMT")			    int totalDcAmt
    ,    @Param("VAT_AMT")				    int vatAmt
    ,    @Param("POINT_AMT")				int pointAmt
    ,    @Param("CORP_DC_AMT")			    int corpDcAmt
    ,    @Param("NORMAIL_DC_AMT")		    int normalDcAmt
    ,    @Param("SERVICE_DC_AMT")		    int serviceDcAmt
    ,    @Param("COUPON_DC_AMT")			int couponDcAmt
    ,    @Param("CUST_DC_AMT")			    int custDcAmt
    ,    @Param("PROMOTION_DC_AMT")		    int promotionDcAmt
    ,    @Param("CUST_ACCUM_POINT")		    int custAccumPoint
    ,    @Param("CUST_USE_POINT")		    int custUsePoint
    ,    @Param("SALE_FLAG")				String saleFlag
    ,    @Param("ORDER_DATETIME")		    String orderDatetime
    ,    @Param("SERVE_DATETIME")			String serveDatetime
    ,    @Param("ITEM_PRICE")			    int itemPrice
    ,    @Param("SUB_MENU_TYPE")			String subMenuType
    ,    @Param("SUB_MENU_FLAG")			String subMenuFlag
    ,    @Param("PARENT_DETAIL_NO")		    String parentDetailNo
    ,    @Param("SUB_MENU_COUNT")		    int subMenuCount
    ,    @Param("CASH_AMT")				    int cashAmt
    ,    @Param("CARD_AMT")				    int cardAmt
    ,    @Param("TICK_AMT")				    int tickAmt
    ,    @Param("GIFT_AMT")				    int giftAmt
    ,    @Param("PREPAID_CARD_AMT")		    int prepaidCardAmt
    ,    @Param("OCB_AMT")				    int ocbAmt
    ,    @Param("CO_AMT")				    int coAmt
    ,    @Param("EXCHANGE_AMT")			    int exchangeAmt
    ,    @Param("EMONEY_AMT")			    int emoneyAmt
    ,    @Param("ENURI_AMT")				int enuriAmt
    ,    @Param("CHARGE_AMT")			    int chargeAmt
    ,    @Param("ITEM_TAX_FLAG")			String itemTaxFlag
    ,    @Param("ERP_CODE")			        String erpCode
    ,    @Param("ERP_ITEM_CODE")			String erpItemCode
    ,    @Param("SALE_SP_CODE")			    String saleSpCode
    ,    @Param("BARCODE")			        String barcode
    ,    @Param("SP_NAME")			        String spName
    ,    @Param("BIZ_NO")			        String bizNo
    
    ) throws SQLException;

    Boolean setSalesreceiptDtlCash(
        @Param("HD_CODE")			String hdCode
    ,	@Param("SP_CODE")			String spCode
    ,   @Param("BIZ_NO")            String bizNo
    ,   @Param("ERP_CODE")          String erpCode
    ,	@Param("SALE_DATE")			String saleDate
    ,	@Param("POS_NO")			String posNo
    ,	@Param("BILL_NO")			String billNo
    ,	@Param("CASH_SLIP_NO")		int cashSlipNo
    ,	@Param("IDENTITY_NO")		String identityNo
    ,	@Param("IDENTITY_TYPE")		String identityType
    ,	@Param("TRADE_FLAG")		String tradeFlag
    ,	@Param("TRADE_FLAG_NM")		String tradeFlagNm
    ,	@Param("SP_NAME")			String spName
    ,	@Param("SALE_SP_CODE")		String saleSpCode
    ,	@Param("APPR_AMT")			int apprAmt
    ,	@Param("APPR_FLAG")			String apprFlag
    ,	@Param("APPR_NO")			String apprNo
    ,	@Param("APPR_DATETIME")		String apprDatetime
    ,	@Param("VAT_AMT")			int vatAmt
    ,	@Param("SERVICE_AMT")		int serviceAmt
    ,	@Param("SALE_FLAG")			String saleFlag
    ) throws SQLException;
    
    Boolean setSalesreceptdtlCard(
        @Param("HD_CODE")			String hdCode
	,	@Param("SP_CODE")			String spCode
	,	@Param("SALE_DATE")			String saleDate
	,	@Param("POS_NO")			String posNo
	,	@Param("BILL_NO")			String billNo
	,	@Param("CARD_SLIP_NO")		int cardSlipNo
	,	@Param("CARD_NO")			String cardNo
	,	@Param("APPR_AMT")			int apprAmt
	,	@Param("INSTALLMENT")		String installment
	,	@Param("APPR_NO")			String apprNo
	,	@Param("APPR_DATETIME")		String apprDatetime
	,	@Param("APPR_FLAG")			String apprFlag
	,	@Param("ISSUER_CODE")		String issuerCode
	,	@Param("ISSUER_NAME")		String issuerName
	,	@Param("ACQUIRER_CODE")		String acquirerCode
	,	@Param("ACQUIRER_NAME")		String acquirerName
	,	@Param("CORP_DC_AMT")		int corpDcAmt
	,	@Param("VAT_AMT")			int vatAmt
	,	@Param("SERVICE_AMT")		int serviceAmt
	,	@Param("SALE_FLAG")			String saleFlag
    ,   @Param("ERP_CODE")          String erpCode    
    ,   @Param("SP_NAME")           String spName     
    ,   @Param("SALES_SP_CODE")     String salesSpCode
    ,   @Param("BIZ_NO")            String bizNo      
    ) throws SQLException;

    Boolean setSalereceiptdtlCorp(
         @Param("SP_CODE")			String sp_code
    ,    @Param("POS_NO")			String pos_no
    ,    @Param("ERP_CODE")			String erp_code
    ,    @Param("SALE_FLAG")		String sale_flag
    ,    @Param("WCC")				String wcc
    ,    @Param("VALID_TERM")       String valid_term
    ,    @Param("PROC_FLAG")		String proc_flag
    ,    @Param("CARD_DATA")		String card_data
    ,    @Param("ORG_APPR_DATE")	String org_appr_date
    ,    @Param("ALLOT_RATE")		int allot_rate
    ,    @Param("APPR_NO")			String appr_no
    ,    @Param("PAYMENT_FLAG")		String payment_flag
    ,    @Param("REMAIN_POINT")		int remain_point
    ,    @Param("CORP_CODE")		String corp_code
    ,    @Param("SALE_DATE")		String sale_date
    ,    @Param("ALLOT_FLAG")		String allot_flag
    ,    @Param("CORP_DC_AMT")		int corp_dc_amt
    ,    @Param("CORP_NAME")		String corp_name
    ,    @Param("APPR_FLAG")		String appr_flag
    ,    @Param("MSG")				String msg
    ,    @Param("HD_CODE")			String hd_code
    ,    @Param("USABLE_POINT")		int usable_point
    ,    @Param("SP_NAME")			String sp_name
    ,    @Param("CARD_NO")			String card_no
    ,    @Param("CORP_SLIP_NO")		String corp_slip_no
    ,    @Param("APPR_DATETIME")	String appr_datetime
    ,    @Param("USE_POINT")		int use_point
    ,    @Param("SALE_SP_CODE")		String sale_sp_code
    ,    @Param("BIZ_NO")			String biz_no
    ,    @Param("SALE_POINT")		int sale_point
    ,    @Param("CARD_LEN")			String card_len
    ,    @Param("BILL_NO")			String bill_no
    ) throws SQLException;

    Boolean setSalesreceptdtlPrepaidCard(
        
             @Param("SP_CODE")			String sp_code
        ,    @Param("POS_NO")			String pos_no
        ,    @Param("ERP_CODE")			String erp_code
        ,    @Param("SALE_FLAG")		String sale_flag
        ,    @Param("APPR_AMT")		    int appr_amt
        ,    @Param("PREPAID_CODE")		String prepaid_code
        ,    @Param("WCC")				String wcc
        ,    @Param("PROC_FLAG")		String proc_flag
        ,    @Param("ORG_APPR_DATE")	String org_appr_date
        ,    @Param("CARD_DATA")		String card_data
        ,    @Param("APPR_NO")			String appr_no
        ,    @Param("PAYMENT_FLAG")		String payment_flag
        ,    @Param("SALE_DATE")		String sale_date
        ,    @Param("APPR_FLAG")		String appr_flag
        ,    @Param("MSG")				String msg
        ,    @Param("PREPAID_SLIP_NO")	String prepaid_slip_no
        ,    @Param("HD_CODE")			String hd_code
        ,    @Param("SP_NAME")			String sp_name
        ,    @Param("CARD_NO")			String card_no
        ,    @Param("PREPAID_NAME")		String prepaid_name
        ,    @Param("APPR_DATETIME")	String appr_datetime
        ,    @Param("SALE_SP_CODE")		String sale_sp_code
        ,    @Param("BIZ_NO")			String biz_no
        ,    @Param("CARD_LEN")			String card_len
        ,    @Param("BILL_NO")			String bill_no
    ) throws SQLException;
    
    Boolean setPrepaidCardSale(
        
             @Param("HD_CODE")			            String hd_code
        ,    @Param("SP_CODE")			            String sp_code
        ,    @Param("SP_NAME")			            String sp_name
        ,    @Param("BIZ_NO")		                String biz_no
        ,    @Param("ERP_CODE")		                String erp_code
        ,    @Param("SALE_DATE")		            String sale_date
        ,    @Param("PREPAID_SALE_CNT")		        int prepaid_sale_cnt
        ,    @Param("PREPAID_SALE_AMT")		        int prepaid_sale_amt
        ,    @Param("PREPAID_SALE_NORMAL_CNT")	    int prepaid_sale_normal_cnt
        ,    @Param("PREPAID_SALE_NORMAL_AMT")	    int prepaid_sale_normal_amt
        ,    @Param("PREPAID_SALE_CANCEL_CNT")	    int prepaid_sale_cancel_cnt
        ,    @Param("PREPAID_SALE_CANCEL_AMT")	    int prepaid_sale_cancel_amt
        ,    @Param("PREPAID_CHARGE_CNT")	        int prepaid_charge_cnt
        ,    @Param("PREPAID_CHARGE_AMT")	        int prepaid_charge_amt
        ,    @Param("PREPAID_CHARGE_NORMAL_CNT")    int prepaid_charge_normal_cnt
        ,    @Param("PREPAID_CHARGE_NORMAL_AMT")	int prepaid_charge_normal_amt
        ,    @Param("PREPAID_CHARGE_CANCEL_CNT")	int prepaid_charge_cancel_cnt
        ,    @Param("PREPAID_CHARGE_CANCEL_AMT")	int prepaid_charge_cancel_amt
    ) throws SQLException;
    
    Boolean setPrepaidCardSaleDtl(
        
             @Param("SALE_DATE")		String sale_date
        ,    @Param("POS_NO")			String pos_no
        ,    @Param("SEQ")			    String seq
        ,    @Param("APPR_CARD_NO")		String appr_card_no
        ,    @Param("PREPAID_NAME")		String prepaid_name
        ,    @Param("CARD_NO")		    String card_no
        ,    @Param("QTY")		        int qty
        ,    @Param("SALE_AMT")		    int sale_amt
        ,    @Param("SALE_FLAG")		String sale_flag
        ,    @Param("PAYMENT_FLAG")	    String payment_flag
        ,    @Param("APPR_NO")	        String appr_no
    ) throws SQLException;
}
