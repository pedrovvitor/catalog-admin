package com.pedrolima.catalog.admin.infrastructure.category;

import com.pedrolima.catalog.admin.infrastructure.MySQLGatewayTest;
import com.pedrolima.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjectDependencies() {
        Assertions.assertNotNull(categoryMySQLGateway);
        Assertions.assertNotNull(categoryRepository);
    }
}
