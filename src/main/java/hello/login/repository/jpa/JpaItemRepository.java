package hello.login.repository.jpa;

import hello.login.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaItemRepository extends JpaRepository<Item, Long> {
    Item findByItemName(String itemName);
    @Modifying
    @Query("UPDATE Item i SET i.quantity = i.quantity - 1 WHERE i.id = :itemId")
    void purchaseItem(@Param("itemId") Long itemId);
}
