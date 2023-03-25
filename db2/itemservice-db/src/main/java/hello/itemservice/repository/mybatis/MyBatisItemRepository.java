package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisItemRepository implements ItemRepository {

  // MyBatis 스프링 연동 모듈이 프록시 구현체를 만들어서 스프링 빈으로 등록함
  private final ItemMapper itemMapper;

  @Override
  public Item save(Item item) {
    itemMapper.save(item);
    return item;
  }

  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    itemMapper.update(itemId, updateParam);
  }

  @Override
  public Optional<Item> findById(Long id) {
    return itemMapper.findById(id);
  }

  @Override
  public List<Item> findAll(ItemSearchCond cond) {
    return itemMapper.findAll(cond);
  }
}
