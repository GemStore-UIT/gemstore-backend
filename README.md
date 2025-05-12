## Mục lục

- [Tổng quan](#tổng-quan)
- [Đặc điểm](#đặc-điểm)
- [Công nghệ](#công-nghệ)
- [Cài đặt](#cài-đặt)
- [Sử dụng](#sử-dụng)
- [API Documentation](#api-documentation)

## Tổng quan

Gemstone Store là một RESTful API được xây dựng bằng Spring Boot, cung cấp nền tảng backend cho cửa hàng đá quý trực tuyến. Hệ thống cho phép quản lý sản phẩm đá quý, đơn hàng và dịch vụ một cách hiệu quả.

## Đặc điểm

-  Xác thực và phân quyền người dùng
-  Quản lý kho sản phẩm đá quý
-  Quản lý mua hàng, bán hàng
-  Quản lý dịch vụ
-  Báo cáo và thống kê
-  API Documentation với Swagger
-  Validation và xử lý lỗi

## Công nghệ

- **Backend Framework:** Spring Boot 3.4.5
- **Java Version:** Java 24
- **Database:** MySQL/H2
- **Build Tool:** Maven
- **API Documentation:** SpringDoc OpenAPI (Swagger)
- **Dependencies:**
    - Spring Data JPA
    - Spring Web
    - Spring Validation
    - Lombok
    - MySQL Connector
    - H2 Database
    - Spring DevTools

## Cài đặt

### Yêu cầu hệ thống

- JDK 24
- Maven 3.x
- MySQL (tùy chọn)
- IDE hỗ trợ Java (khuyến nghị: IntelliJ IDEA, Eclipse)

### Các bước cài đặt

1. **Clone repository**
   ```bash
   git clone https://github.com/GemStore-UIT/gemstore-backend.git
   cd gemstone-store
   ```

2. **Cấu hình database**

   Tạo file `application.properties` trong `src/main/resources`:
   ```properties
   
    spring.application.name=GemstoneStore
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driver-class-name=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    string.jpa.hibernate.ddl-auto=create
    spring.jpa.show-sql=true

   ```

3. **Build project**
   ```bash
   mvn clean install
   ```

4. **Chạy ứng dụng**
   ```bash
   mvn spring-boot:run
   ```

## Sử dụng

### Khởi động ứng dụng

```bash
java -jar target/gemstone-store-0.0.1-SNAPSHOT.jar
```

## API Documentation
Tài liệu API được tự động tạo bằng Swagger UI. Sau khi chạy ứng dụng, truy cập:
```
http://localhost:8080/swagger-ui.html
```