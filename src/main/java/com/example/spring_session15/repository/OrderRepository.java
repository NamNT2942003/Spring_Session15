package com.example.spring_session15.repository;

import com.example.spring_session15.entity.Order;
import com.example.spring_session15.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedDateDesc(User user);
    // 1. Kiểm tra xem User đã mua Product này và đơn hàng đã HOÀN THÀNH chưa?
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Order o JOIN o.items i " +
            "WHERE o.user.id = :userId AND i.product.id = :productId AND o.status = 'COMPLETED'")
    boolean hasUserBoughtProduct(@Param("userId") Long userId, @Param("productId") Long productId);

    // 2. Tính tổng doanh thu trong một khoảng thời gian
    @Query("SELECT COALESCE(SUM(o.totalMoney), 0) FROM Order o " +
            "WHERE o.status = 'COMPLETED' AND o.createdDate >= :startDate AND o.createdDate <= :endDate")
    BigDecimal calculateRevenue(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}