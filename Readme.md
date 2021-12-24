Сейчас работает взаимодействие с БД через Spring Data.
Но можно изменить его на JDBC:
- убрать в названиях файлов data-jdbc.sql и schema-jdbc.sql приставку "-jdbc"
- убрать в классах IngredientRepository, OrderRepository, TacoRepository наследование от CrudRepository
- раскоментить в классах JdbcIngredientRepository, JdbcOrderRepository и JdbcTacoRepository аннотации "@Repository"
- поправить в клнтроллерах названия интерфейсов репозиториев