package com.azkj.toolpowder.user.service.Impl;

import com.azkj.toolpowder.common.cache.CommonCacheUtil;
import com.azkj.toolpowder.common.constants.Constants;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.common.utils.DateUtil;
import com.azkj.toolpowder.common.utils.MD5Util;
import com.azkj.toolpowder.merchant.dao.CouponMapper;
import com.azkj.toolpowder.merchant.dao.MerchantMapper;
import com.azkj.toolpowder.merchant.entity.Merchant;
import com.azkj.toolpowder.user.dao.*;
import com.azkj.toolpowder.user.entity.*;
import com.azkj.toolpowder.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private MessageMapper messageMapper;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LeagueMapper leagueMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private EarningsMapper earningsMapper;

    @Autowired
    private BankcardMapper bankcardMapper;

    @Autowired
    private OrderitMapper orderitMapper;

    @Autowired
    private UcouponRoleMapper ucouponRoleMapper;

    @Autowired
    private CommonCacheUtil commonCacheUtil;

    @Autowired
    private CuratorFramework curatorFramework;

    private InterProcessMutex lock=null;

    private static AtomicInteger counter = new AtomicInteger(1);

    @Override
    public void instrMessage(Message message) throws ToolPowderExcption {
        List<Message> messageList=messageMapper.selectByMessageList(message.getUuid());
        if(CollectionUtils.isNotEmpty(messageList)){
            messageMapper.updateByMessage(message.getUuid());
        }
        messageMapper.insertSelective(message);

    }


    @Override
    public void instrLeague(League league) throws ToolPowderExcption {
        League leagues=leagueMapper.selectByLeagues(league.getUuid());
        if(leagues!=null){
            throw  new ToolPowderExcption("你提出加盟的信息正在审批");
        }
        league.setCreatetime(new Date());
        leagueMapper.insertSelective(league);
    }

    @Override
    public String instrUser(String openid,String name,String img) throws Exception {
        System.out.println("openid================"+openid);
        if(openid==null){
            throw new ToolPowderExcption("获取失败");
        }
//        lock = new InterProcessMutex(curatorFramework, Constants.USER_REGISTER_DISTRIBUTE_LOCK_PATH);
//        boolean retry = true;
//        do {
//            try {
//                if(lock.acquire(3000, TimeUnit.MILLISECONDS)){
                    User user1=userMapper.SelectByOpenid(openid);
                    if(user1==null){
                        User user=new User();
                        user.setOpenid(openid);
                        String uuid= MD5Util.getMD5(DateUtil.getOrderIdByTime());
                        user.setUuid(uuid);
                        user.setUsername(name);
                        user.setUserimg(img);
                        userMapper.insertSelective(user);
//                        lock.release();
                        System.out.println("uuid==========="+uuid);
                        return uuid;
                    }else{
//                        lock.release();
                        System.out.println("user1========== "+user1.getUuid());
                        return user1.getUuid();
                    }
//
//                }
//            } catch (Exception e) {
//                lock.release();
//            }
//        }while (retry);
//
//        return null;

    }



    @Override
    public List<UserCoupon> SelectUserCoupon(String uuid) {
       List<UserCoupon> couponList=userCouponMapper.selectByCoupon(uuid);
       for(UserCoupon userCoupon:couponList){
           Merchant merchant=merchantMapper.selectByPrimary(userCoupon.getCoupon().getMid());
           userCoupon.getCoupon().setMerchant(merchant);
       }

       return  couponList;
    }

    @Override
    public Map<String, Object> SelectEarnings(String uuid) {
        Map<String,Object> map=new  HashMap<>();
        List<Earnings> earnings=earningsMapper.SelectEarnings(uuid);
        map.put("earnings",earnings);
        Double munprice=earningsMapper.selectSumEarnings(uuid);
        map.put("munprice",munprice);
        Double balance=earningsMapper.SelectBalanceEarninges(uuid);
        map.put("balance",balance);
        List<Bankcard> bankcards=bankcardMapper.SelectBankcardEarninges(uuid);
        map.put("Bankcard",bankcards);
        return map;
    }

    @Override
    public User SelectOpenid(String uuid) {
        return userMapper.selectByUser(uuid);
    }

    @Override
    public void InstrOrderit(String uuid, String out_trade_no) throws IOException {
//        List<Coupon> couponList=couponMapper.selectByCoupon();
//        couponList.forEach(
//                pram->{
//                    UserCoupon userCoupon=new UserCoupon();
//                    userCoupon.setUuid(uuid);
//                    userCoupon.setCouponid(pram.getId());
//                    userCoupon.setStatus(1l);
//                    userCouponMapper.insertSelective(userCoupon);
//
//                }
//        );
        Orderit orderit=new Orderit();
        orderit.setCreatetime(new Date());
        orderit.setOrderitid(out_trade_no);
        orderit.setUuid(uuid);
        orderitMapper.insertSelective(orderit);
    }

    @Override
    public String SelectImg(String uuid) {
        return userMapper.SelectImg(uuid);
    }

    @Override
    public void drawCoupon(String uuid, Integer cid) throws ToolPowderExcption, IOException {
        Orderit orderit=orderitMapper.SelectByOrderit(uuid);
        if(orderit==null){
            throw  new ToolPowderExcption("1");
        }
        UcouponRole ucouponRole=ucouponRoleMapper.selectByUcRole(uuid,cid);
        if(ucouponRole!=null){
            throw  new ToolPowderExcption("2");
        }
        UcouponRole role=new UcouponRole();
        role.setCid(cid);
        role.setUid(uuid);
        UserCoupon userCoupon=new UserCoupon();
        userCoupon.setUuid(uuid);
        userCoupon.setCouponid(cid);
        System.out.println("1+");
        String phone=messageMapper.selectByPhone(uuid);
        String url="https://wxpay.chazhanyuan.cn:9999/messages?phone="+phone;
        Constants.message(url);
        System.out.println("2+");
        userCouponMapper.insertSelective(userCoupon);
        System.out.println("3+");
        ucouponRoleMapper.insertSelective(role);
    }

    @Override
    public Boolean SelectInfo(String uuid) {
        List<Message> messageList=messageMapper.selectByMessageList(uuid);
        if(CollectionUtils.isNotEmpty(messageList)){
         return true;
        }else{
            return false;
        }
    }
}
