package com.demoappfeky.repository.productRepo;

import com.demoappfeky.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
