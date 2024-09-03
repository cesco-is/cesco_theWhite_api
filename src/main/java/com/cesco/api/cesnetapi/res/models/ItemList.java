package com.cesco.api.cesnetapi.res.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemList {
	/**본부코드 */
	String hd_code;
	/**매장코드 */
	String sp_code;
	/**상품코드 */
	String item_code;
	/**상품명 */
	String item_name;
	/**상품단축명칭 */
	String short_name;
	/**상품영문명칭 */
	String english_name;
	/**단위명칭 */
	String qty_name;
	/**대분류코드 */
	String large_scale;
	/**대분류명 */
	String large_scale_nm;
	/**중분류코드 */
	String medium_scale;
	/**중분류명 */
	String medium_scale_nm;
	/**소분류코드 */
	String small_scale;
	/**소분류명 */
	String small_scale_nm;
	/**원가 */
	String item_cost;
	/**판매가 */
	String item_price;
	/**부가세율 */
	String vat_rate;
	/**부가세구분 */
	String vat_rate_fg;
	/**취급구분 */
	String use_flag;
	/**과세유무 */
	String tax_flag;
	/**과세구분명 */
	String tax_flag_nm;
	/**가격구분 */
	String price_flag;
	/**가격구분자명 */
	String price_flag_nm;
	/**부가메뉴구분 */
	String sub_menu_type;
	/**봉사료 구분 */
	String service_flag;
	/**상품구분자 */
	String item_type;
	/** 상품구분자명 */
	String item_type_nm;
	/**상품이미지 url */
	String item_img_url;
	/**상품설명_K */
	String item_description;
	/**상품설명_E */
	String item_description_eng;
	/**판매시작시간 */
	String sale_start_time;
	/**판매제한 종료시간 */
	String sale_end_time;
	/**일판매 가능 수량 */
	String daily_sale_qty;
	/**부가메뉴구분_KIO */
	String kiosk_sub_menu_type;
	/**상품구분 */
	String kiosk_display_icon;
	/**요일 */
	String slae_days;
	/**품절유무 */
	String sold_out;
	/**오더그룹사용구분 */
	String order_group_fg;
}
