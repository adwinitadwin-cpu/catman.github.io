# КоТоЛюД - Интернет-магазин товаров для кошек

Полнофункциональное веб-приложение для продажи товаров для кошек, разработанное на Spring Boot с интеграцией облачного хранилища.

## 📋 Описание проекта

**КоТоЛюД** - это современный интернет-магазин с полным функционалом управления товарами и категориями. Приложение включает административную панель для управления контентом и публичный интерфейс для покупателей.

## 🛠 Технологический стек

- **Backend:** Spring Boot 3.5.7
- **Java:** Java 21 (LTS)
- **Database:** PostgreSQL
- **ORM:** Hibernate/JPA
- **Cloud Storage:** Cloudinary (для хранения изображений)
- **Frontend:** Thymeleaf, Bootstrap 5.3.8
- **Build Tool:** Maven
- **Дополнительно:** Lombok, Validation

## ✨ Реализованный функционал

### 1. 📦 Управление Категориями
- ✅ Создание новых категорий товаров
- ✅ Редактирование существующих категорий
- ✅ Удаление категорий
- ✅ Просмотр списка всех категорий
- ✅ Валидация данных категорий
- ✅ Связь "один-ко-многим" между категориями и продуктами

### 2. 🐱 Управление Товарами (CRUD)
- ✅ **Create (Создание)** - добавление новых товаров с деталями
- ✅ **Read (Чтение)** - просмотр товаров с фильтрацией и поиском
- ✅ **Update (Обновление)** - редактирование информации о товарах
- ✅ **Delete (Удаление)** - удаление товаров из каталога
- ✅ Привязка товаров к категориям
- ✅ Сложная валидация данных (размер, длина, обязательные поля)
- ✅ Генерация уникальных ID для продуктов

### 3. 🖼️ Интеграция с Cloudinary
- ✅ Загрузка изображений товаров на удаленный сервер
- ✅ Автоматическое преобразование HEIC/HEIF в JPEG
- ✅ Оптимизация изображений (сжатие, автоматический формат)
- ✅ Удаление изображений с облачного хранилища
- ✅ Безопасное хранение учетных данных (переменные окружения)
- ✅ Обработка ошибок при загрузке/удалении

### 4. 👨‍💼 Административная панель
- ✅ **Dashboard** с статистикой товаров и категорий
- ✅ Интерфейс управления категориями
  - Таблица всех категорий
  - Форма создания/редактирования
  - Быстрые действия (редактировать, удалить)
  - Flash-сообщения об успехе/ошибке

- ✅ Интерфейс управления товарами
  - Список товаров с превью изображений
  - Подробный просмотр товара (view page)
  - Отдельная форма создания (form.html)
  - Отдельная форма редактирования (update.html)
  - Система загрузки/удаления фото
  - Карточки с информацией о товарах

### 5. 🌐 Публичный интерфейс

#### Главная страница
- ✅ Динамическая карусель последних товаров
- ✅ Автоматический переход между товарами
- ✅ Индикаторы и кнопки навигации

#### Каталог товаров
- ✅ Сетка товаров (Grid layout)
- ✅ Карточки с изображениями, названием, описанием
- ✅ Динамическая загрузка из БД
- ✅ Ссылки на подробное описание

#### Страница товара (Product Detail)
- ✅ Полная информация о товаре
- ✅ Галерея изображений
- ✅ Описание и характеристики
- ✅ Ссылка на покупку (URL продукта)
- ✅ Хлебные крошки (breadcrumb)
- ✅ Социальные ссылки
- ✅ Динамический title и meta-description

### 6. 📱 Backend API и Services

#### ProductService
- `create()` - создание товара с привязкой категории
- `getProductById()` - получение товара по ID
- `getAllProducts()` - получение всех товаров
- `getLimitProduct(int)` - получение ограниченного количества товаров (для карусели)
- `update()` - обновление товара
- `delete()` - удаление товара
- `uploadPicture()` - загрузка изображения на Cloudinary
- `deletePicture()` - удаление изображения с Cloudinary

#### CategoryService
- `create()` - создание категории
- `findById()` - получение категории по ID
- `findAll()` - получение всех категорий
- `update()` - обновление категории
- `delete()` - удаление категории

### 7. 🗄️ Database & Entities

#### Product Entity
```
- id (PK)
- name
- description
- imageUrl
- imageId
- urlProduct
- categories (OneToMany)
- createdAt
- updatedAt
```

#### Category Entity
```
- id (PK)
- name
- description
- product (ManyToOne)
- createdAt
- updatedAt
```

### 8. 🔄 DTO Структура

#### CreateProductDTO
- Используется для создания новых товаров
- Включает валидацию @NotBlank, @Size

#### UpdateProductDTO
- Используется для обновления товаров
- Содержит ID и все необходимые поля

#### ProductResponseDTO
- Возвращается API и используется в шаблонах
- Включает все поля товара и категории

### 9. 🎨 Frontend Components

#### HTML Templates
- `index.html` - главная страница с каруселью
- `product/list.html` - каталог товаров
- `product/view.html` - подробное описание товара
- `admin/dashboard.html` - админ-панель
- `admin/product/form.html` - форма создания товара
- `admin/product/update.html` - форма редактирования товара
- `admin/product/list.html` - таблица товаров
- `admin/product/view.html` - просмотр товара в админке
- `admin/category/form.html` - форма категорий
- `admin/category/list.html` - таблица категорий

#### Bootstrap Features
- Responsive Grid System
- Cards и Carousel
- Forms и Validation
- Navigation Bars
- Tables с действиями

### 10. 🔐 Безопасность и Валидация

- ✅ @Valid аннотации на уровне контроллера
- ✅ BindingResult обработка ошибок
- ✅ Custom validation messages
- ✅ SQL Injection prevention через Parameterized queries
- ✅ Безопасное хранилище учетных данных Cloudinary
- ✅ @Transactional для управления транзакциями

### 11. 📊 SEO Оптимизация

- ✅ Meta tags (title, description, keywords)
- ✅ Open Graph для социальных сетей
- ✅ Twitter Card поддержка
- ✅ Canonical URLs
- ✅ Structured Data (JSON-LD)
- ✅ Динамические title и description на каждой странице

### 12. 🚀 Дополнительные функции

- ✅ Flash сообщения об успехе/ошибке
- ✅ Логирование всех операций (@Slf4j)
- ✅ Error handling и обработка исключений
- ✅ Условное отображение элементов в Thymeleaf
- ✅ Форматирование дат и времени
- ✅ Сокращение текста в интерфейсе
- ✅ Автоматическое преобразование HEIC изображений

## 📁 Структура проекта

```
src/main/java/com/example/kotolud/
├── controller/
│   ├── admin/
│   │   ├── AdminDashboard.java
│   │   ├── AdminProductController.java
│   │   └── CategoryAdminController.java
│   └── ProductController.java
├── serivce/
│   ├── product/
│   │   ├── ProductService.java
│   │   └── ProductServiceImpl.java
│   └── category/
│       ├── CategoryService.java
│       └── CategoryServiceImpl.java
├── mapper/
│   ├── ProductMapper.java
│   └── CategoryMapper.java
├── model/
│   ├── Product.java
│   └── Category.java
├── dto/
│   ├── product/
│   │   ├── CreateProductDTO.java
│   │   ├── UpdateProductDTO.java
│   │   └── ProductResponseDTO.java
│   └── category/
│       ├── CreateCategoryDTO.java
│       └── CategoryResponseDTO.java
├── repository/
│   ├── ProductRepository.java
│   └── CategoryRepository.java
├── config/
│   └── CloudinaryConfig.java
└── util/
    └── StorageService.java

src/main/resources/templates/
├── index.html
├── admin/
│   ├── dashboard.html
│   ├── product/
│   │   ├── form.html
│   │   ├── update.html
│   │   ├── list.html
│   │   └── view.html
│   └── category/
│       ├── form.html
│       └── list.html
└── product/
    ├── list.html
    └── view.html
```

## 🚀 Установка и запуск

### Предварительные требования
- Java 21
- Maven
- PostgreSQL
- Cloudinary аккаунт

### Шаги установки

1. **Клонируйте репозиторий**
```bash
git clone https://github.com/adwinitadwin-cpu/catman.github.io.git
cd Kotolud
```

2. **Установите Java 21**
```bash
java -version  # Должна быть версия 21
```

3. **Настройте базу данных**
```bash
# Создайте базу данных PostgreSQL
CREATE DATABASE kotolud;
```

4. **Установите переменные окружения**
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/kotolud
spring.datasource.username=postgres
spring.datasource.password=ваш_пароль

cloudinary.cloud-name=ваш_cloud_name
cloudinary.api-key=ваш_api_key
cloudinary.api-secret=ваш_api_secret
```

5. **Соберите и запустите**
```bash
mvn clean compile
mvn spring-boot:run
```

6. **Откройте в браузере**
```
http://localhost:2710
```

## 📝 API Endpoints

### Products
- `GET /product` - каталог товаров
- `GET /product/{id}` - информация о товаре
- `GET /admin-product` - список товаров (админка)
- `GET /admin-product/form` - форма создания
- `GET /admin-product/edit/{id}` - форма редактирования
- `POST /admin-product/save` - сохранение товара
- `POST /admin-product/update/{id}` - обновление товара
- `POST /admin-product/delete/{id}` - удаление товара
- `POST /admin-product/upload-image/{id}` - загрузка изображения
- `POST /admin-product/delete-image/{id}` - удаление изображения

### Categories
- `GET /admin-category` - список категорий
- `GET /admin-category/form` - форма создания
- `GET /admin-category/edit/{id}` - форма редактирования
- `POST /admin-category/save` - сохранение категории
- `POST /admin-category/update/{id}` - обновление категории
- `POST /admin-category/delete/{id}` - удаление категории

## 🎯 Дальнейшие улучшения

- [ ] Система фильтрации по категориям
- [ ] Поиск товаров
- [ ] Система оценок и отзывов
- [ ] Корзина покупок
- [ ] Система заказов
- [ ] Интеграция с платежными системами
- [ ] REST API для мобильного приложения
- [ ] Системе уведомлений
- [ ] Кэширование на Redis
- [ ] Многоязычная поддержка

## 👨‍💻 Автор

**Вадим Харовюк**
- GitHub: [@adwinitadwin-cpu](https://github.com/adwinitadwin-cpu)

## 📄 Лицензия

Этот проект распространяется под лицензией MIT.

## 📞 Контакты

- Email: info@kotolud.com
- Website: [КоТоЛюД](https://kotolud.com)
- Telegram: [@kotolud_support](https://t.me/kotolud_support)

---

**Последнее обновление:** Октябрь 2025
**Версия:** 1.0.0
