package com.asie_back.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStatisticsRepository extends JpaRepository<ProdStatistics, Integer> {
    //@Query(value = "FROM ProdStatistics b where b.product.productId = ?1")
    //public List<ProdStatistics> getProductStatisticsByProductId(Long productId);
}
