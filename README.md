Перед первом запуске приложения необходимо в файле Application.property свойству spring.jpa.hibernate.ddl-auto присвоить значение create,
это создаст таблици в БД согласно класам Entity. Далее значение этого свойства необходимо заменить на validate, иначе приложение при каждом запуске будет создавать новые таблици
поверх старых, удаляя все данные.

1. Запроси к таблице Doctor

GET http://localhost:8080/doctor/doctors  - Отображение списка врачей

GET http://localhost:8080/doctor/{id}  - Показать врача с индексом id

POST http://localhost:8080/doctor/  - добавить врача (в теле ответа Json-объект)

PUT http://localhost:8080/doctor/{id}  - редактировать врача с индексом id (в теле ответа Json-объект)

DELETE http://localhost:8080/doctor/{id}  - Удалить врача с индексом id

пример Json-объекта
{

        "firstName": "Semen",
        "lastName": "Bobrov",
        "middleName": "Ivanivich",
        "position": "terapevt",
        "dateOfBirth": "22-05-1965",
        "phoneNamber": 563212323
    }

2. Запроси к таблице Patient

GET http://localhost:8080/patient/patients  - Отображение списка пациентов

GET http://localhost:8080/patient/{id}  - Показать пацыента с индексом id

POST http://localhost:8080/patient/  - добавить пациента (в теле ответа Json-объект)

PUT http://localhost:8080/patient/{id}  - Редактировать пациента с индексом id (в теле ответа Json-объект)

DELETE http://localhost:8080/patient/{id}  - Удалить пациента с индексом id

пример Json-объекта
{
   
        "firstName": "Oleg",
        "lastName": "Petrov",
        "middleName": "Ivanovich",
        "doctorId": 1,
        "dateOfBirth": "09-01-1989",
        "phoneNamber": 561234567
    }

