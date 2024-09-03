package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Franchises {
    /**본부코드 */
    String hd_code;
    /**매장코드 */
    String sp_code;
    /**가맹점명 */
    String sp_name;
    /**사업자번호 */
    String biz_no;
    /**개점여부 */
    String open_flag;
    /**ERP 점포코드 */
    String erp_sp_code;
    /**대표자명 */
    String master_name;
    /**전화번호 */
    String tel_no;
    /**가맹형태 */
    String sp_type;
    /**지역분류코드 */
    String area_code;
    /**지역분류코드 */
    String sale_class_code;
    /**영업분류코드 */
    String sale_class_name;
    /**주소 */
    String address1;
    /**번지 */
    String address2;
    /**브랜드코드 */
    String brand_code;
    /**브랜드명 */
    String brand_name;
}
