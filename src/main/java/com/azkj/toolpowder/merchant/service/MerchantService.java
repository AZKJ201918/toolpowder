package com.azkj.toolpowder.merchant.service;

import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.merchant.entity.Coupon;
import com.azkj.toolpowder.merchant.entity.Details;
import com.azkj.toolpowder.merchant.entity.Merchant;

import java.util.List;

public interface MerchantService {
    List<List<Merchant>> SelectMerchant() throws ToolPowderExcption;

    List<Merchant> SelectSortMerchant(Integer id)throws ToolPowderExcption;

    Details SelectDetails(Integer id)throws ToolPowderExcption;

    List<Coupon> SelectCoupon()throws ToolPowderExcption;

    List<Coupon> SelectByCoupon(Integer id) throws  ToolPowderExcption;
}
