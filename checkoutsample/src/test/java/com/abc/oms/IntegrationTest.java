package com.abc.oms;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.Product;
import com.abc.oms.app.model.PurchaseOrder;
import com.abc.oms.repository.ProductRepository;
import com.abc.oms.service.CartServiceImpl;
import com.abc.oms.service.OrderServiceImpl;
import com.abc.oms.service.ProductServiceImpl;
import com.abc.oms.transfer.OrderCartItem;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories(basePackages = "com.abc.oms.repository")
@ComponentScan({ "com.abc.oms.service", "com.abc.oms.dao", "com.abc.oms.api" })

public class IntegrationTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private CartServiceImpl cartServiceImpl;

	@Autowired
	private OrderServiceImpl orderServiceImpl;

	@Configuration
	static class ContextConfiguration {
		@Bean
		public DataSource dataSource() {

			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.HSQL).build();
		}

		@Bean
		public EntityManagerFactory entityManagerFactory() {

			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			vendorAdapter.setGenerateDdl(true);

			LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
			factory.setJpaVendorAdapter(vendorAdapter);
			factory.setPackagesToScan("com.abc.oms.app.model");
			factory.setDataSource(dataSource());
			factory.afterPropertiesSet();

			return factory.getObject();
		}

		@Bean
		public PlatformTransactionManager transactionManager() {

			JpaTransactionManager txManager = new JpaTransactionManager();
			txManager.setEntityManagerFactory(entityManagerFactory());
			return txManager;
		}
	}

	@Test
	public void testListProducts() {
		Product prod1 = new Product();
		prod1.setProductDescription("Test");
		prod1.setProductId("1001");
		prod1.setProductName("Test");
		prod1.setProductPrice(new BigDecimal(10));
		productRepository.save(prod1);
		assertThat(productServiceImpl.listProducts()).hasSize(1);
	}

	@Test
	public void testaddToCartItem() {
		Product prod1 = new Product();
		prod1.setProductDescription("Test");
		prod1.setProductId("1001");
		prod1.setProductName("Test");
		prod1.setProductPrice(new BigDecimal(10));
		productRepository.save(prod1);

		OrderCartItem orderCartItem = new OrderCartItem();
		orderCartItem.setCartId("100001");
		orderCartItem.setProductId("1001");
		orderCartItem.setQuantity(2);
		CartItem item = cartServiceImpl.save(orderCartItem);
		assertThat(item).extracting("quantity").containsExactly(2);
		assertThat(item.getCart().getCustomerName().equals("Demo"));
		assertThat(item.getProduct().getProductName().equals("Test"));
	}

	@Test
	public void testcheckOut() {
		Product prod1 = new Product();
		prod1.setProductDescription("Test");
		prod1.setProductId("1001");
		prod1.setProductName("Test");
		prod1.setProductPrice(new BigDecimal(10));
		productRepository.save(prod1);

		OrderCartItem orderCartItem = new OrderCartItem();
		orderCartItem.setCartId("100002");
		orderCartItem.setProductId("1001");
		orderCartItem.setQuantity(2);
		cartServiceImpl.save(orderCartItem);
		List<CartItem> cartItems = cartServiceImpl.getCartItems("10002");

		PurchaseOrder order = orderServiceImpl.save(cartItems);

		assertThat(order).extracting("orderNo").doesNotContainNull();

	}
}
