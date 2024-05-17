package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
