# Тестовое задание 2 Clevertec 


1. **Скачайте проект:**

    - https://github.com/user/project-name

2. **Скомпилируйте проект:**

    - Разархивируйте проект
    - В терминале перейдите в главную директорию проекта
    - Для компиляции в терминале наберите команду:
      * `javac -d out $(find src -name "*.java")`

3. **Запустите приложение:**

    - Для запуска приложения используйте команду:
      * `java -cp out ru.clevertec.check.CheckRunner id-quantity discountCard=xxxx balanceDebitCard=xxxx pathToFile=xxxx saveToFile=yyyy`

      Где *id* - id товара, *quantity* - количество товара, *discountCard* - номер дисконтной карты, 
   *balanceDebitCard* - баланс платёжной карты, *pathToFile* - путь от корневой директории проекта + название файла
   с расширением, *saveToFile* - путь от корневой директории проекта + название файла с расширением
    
4. **Пример команды:**

    - `java -cp out ru.clevertec.check.CheckRunner 2-4 1-4 3-2 discountCard=2222 balanceDebitCard=250
   pathToFile=./products.csv saveToFile=./result.csv`
