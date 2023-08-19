## Это репозиторий проекта "Каршеринг для вещей, в котором можно обмениваться с друзьями вещами на время"
#### Бэкенд shareit

Это сервис, который  должен обеспечить пользователям, во-первых, возможность рассказывать, какими вещами они готовы 
поделиться, а во-вторых, находить нужную вещь и брать её в аренду на какое-то время
-------

Приложение **умеет** делать следующее:
1. Создавать, редактировать и просматривать пользователей 
2. Добавлять новые вещи, их редактировать
3. Просматривать список вещей и искать их
4. Позволяет бронировать вещь на определённые даты
5. Закрывает к вещи доступ на время бронирования от других желающих
6. Если нужной вещи на сервисе нет, у пользователей есть возможность оставлять запросы
7. Пользователи могут оставлять отзывы на вещь после того, как взяли её в аренду


Микросервисное приложение написано на **Java**, использует **Spring Boot**, **Maven**, **Hibernate**, **JUnit 5**, 
**Mockito**, **Docker**, API соответствует **REST**, данные хранятся в БД **PostgreSQL**, **H2**.
Тестовое покрытие кода - 94% строк кода. Пример кода:
```java
public class Main {
    public static void main(String[] args) {
    }
}
```
------