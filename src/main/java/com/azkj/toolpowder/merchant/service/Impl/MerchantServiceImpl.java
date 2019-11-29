package com.azkj.toolpowder.merchant.service.Impl;

import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.merchant.dao.CouponMapper;
import com.azkj.toolpowder.merchant.dao.DetailsMapper;
import com.azkj.toolpowder.merchant.dao.MerchantMapper;
import com.azkj.toolpowder.merchant.entity.Coupon;
import com.azkj.toolpowder.merchant.entity.Details;
import com.azkj.toolpowder.merchant.entity.Merchant;
import com.azkj.toolpowder.merchant.service.MerchantService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("merchantServiceImpl")
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private DetailsMapper detailsMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public List<List<Merchant>>SelectMerchant() {
        List<List<Merchant>> lists=new ArrayList<>();
        Integer count=merchantMapper.SelectCount();
        Integer i=0;
        if(count%8==0){
            i=count/8;
        }else{
            i=(count/8)+1;
        }

        for (Integer j=0;j<i;j++){
            List<Merchant> merchantList=merchantMapper.SelectPage(j*8,(j+1)*8);
            lists.add(merchantList);
        }
        return lists;
    }

    @Override
    public List<Merchant> SelectSortMerchant(Integer id) throws ToolPowderExcption {
        return merchantMapper.SelectSortMerchant(id);
    }

    @Override
    public Details SelectDetails(Integer id) {
        Details details=detailsMapper.SelectDetails(id);
        Merchant merchant=merchantMapper.selectByPrimary(details.getMid());
        details.setMerchant(merchant);
        return details;
    }

    @Override
    public List<Coupon> SelectCoupon() {
        List<Coupon> couponList=couponMapper.selectByCoupon();
        couponList.stream().forEach(
                papr->{
                    papr.setMerchant(merchantMapper.selectByPrimary(papr.getMid()));
                }
        );
        return couponList;
    }

    @Override
    public List<Coupon> SelectByCoupon(Integer id) throws ToolPowderExcption {
        List<Coupon> couponList=couponMapper.selectMerchantCoupon(id);
        if(!CollectionUtils.isNotEmpty(couponList)){
            throw  new ToolPowderExcption("此商家没有优惠活动");
        }
        return couponList ;
    }
}
