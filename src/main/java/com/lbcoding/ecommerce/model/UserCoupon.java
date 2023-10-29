package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.UserCouponDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;

@Entity
public class UserCoupon {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long discountId;

    public UserCoupon(){

    }

    /**
     *
     * @param id
     * @param userId
     * @param discountId
     */
    public UserCoupon(Long id, Long userId, Long discountId) {
        this.id = id;
        this.userId = userId;
        this.discountId = discountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public UserCouponDTO toDTO(){
        UserCouponDTO userCouponDTO = new UserCouponDTO(
                this.userId,
                this.discountId
        );

        return userCouponDTO;
    }
}
