package com.mol.ddmanage.mapper.Office;

import java.util.Map;

public interface ElectronicContractSigninginforMapper
{
    void Upload_Contract(String id,String contract_id,String upload_time,String upload_user_id);//上传合同记录
    void Supplier_Contract(String id,String purchase_id,String supplier_id,String contract_id,String template_id,String create_time,String start_sign_time,String sign_status);//合同对应的供应商
    Map GetContractId(String  purchasId, String supplierid);
    Map GetCustomer_id(String open_id);
}
