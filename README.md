### Project Overview
Develop a console application for managing a collection of `StudyGroup` objects. This application will involve both client and server components with specific functionalities and requirements.

### General Requirements
1. **Class Implementation**: 
   - `StudyGroup` class must implement default sorting.
   - All class fields must meet specified requirements.

2. **Collection Storage**: 
   - Use `java.util.Stack` for storing objects.

3. **File Operations**: 
   - Automatically populate collection from a file at startup.
   - File name to be provided via an environment variable.
   - Data format: CSV.
   - Use `java.io.BufferedReader` for reading and `java.io.FileOutputStream` for writing data.

4. **Documentation**: 
   - All classes should be documented using Javadoc.

5. **Error Handling**: 
   - Program should handle user input errors and file access issues effectively.

### Interactive Commands
1. **Basic Commands**: `help`, `info`, `show`, `add`, `update`, `remove_by_id`, `clear`, `exit`.
2. **File Operations**: `save`, `execute_script`.
3. **Collection Manipulation**: `remove_at`, `remove_last`, `remove_greater`.
4. **Data Analysis**: `sum_of_expelled_students`, `filter_starts_with_name`, `print_field_ascending_students_count`.

### Input Format
- Standard data types: Entered on the same line as command.
- Composite data types: Entered one field per line with field name prompt.
- Enum fields: Input name of one of its constants.
- Error handling: Display error message for incorrect inputs.
- Null values: Represented by an empty line.
- Auto-generated fields: Not manually entered by the user.

### Advanced Functionalities
1. **Stream API**: Use for processing collection object operations.
2. **Serialization**: Transfer objects in serialized form between client and server.
3. **Sorting**: Sort objects in collection by name.

### Server Application Responsibilities
1. **File Management**: Handle file storing the collection.
2. **Collection Management**: Manage collection operations.
3. **Request Handling**: Process received requests and commands.
4. **Response Sending**: Send responses to client.
5. **Connection Management**: Wait for and process connections and requests.

### Server Modules
1. **Connection Receiver**
2. **Request Reader**
3. **Command Processor**
4. **Response Sender**

### Server Technical Requirements
- Operate in single-threaded mode.
- Use non-blocking network channels.
- Data exchange via UDP protocol.

### Client Application Responsibilities
1. **Command Reading**: Read commands from the console.
2. **Input Validation**
3. **Command Serialization**
4. **Command Sending**: Send commands to the server.
5. **Response Processing**

### Database Integration
- Switch from file storage to relational DBMS (PostgreSQL).
- Use database tools for generating the `id` field.
- Update collection state in memory only after successful database addition.
- Retrieve data from the in-memory collection, not the database.

### User Management
- Implement user registration and authorization.
- Hash passwords using SHA-512.
- Restrict command execution to authorized users.
- Store creator information with objects.
- Restrict object modification to the creator.

### Multi-threading and Synchronization
- Use Cached thread pool for multi-threaded request reading and processing.
- Create new threads for sending responses.
- Synchronize access to the collection using `java.util.concurrent.locks.ReadWriteLock`.

  
7th lab of Java programming. ITMO University, Software Engineering
<br />
Доработать программу из лабораторной работы №6 следующим образом:<br />

Организовать хранение коллекции в реляционной СУБД (PostgresQL). Убрать хранение коллекции в файле.<br />
Для генерации поля id использовать средства базы данных (sequence).<br />
Обновлять состояние коллекции в памяти только при успешном добавлении объекта в БД<br />
Все команды получения данных должны работать с коллекцией в памяти, а не в БД<br />
Организовать возможность регистрации и авторизации пользователей. У пользователя есть возможность указать пароль.<br />
Пароли при хранении хэшировать алгоритмом SHA-512<br />
Запретить выполнение команд не авторизованным пользователям.<br />
При хранении объектов сохранять информацию о пользователе, который создал этот объект.<br />
Пользователи должны иметь возможность просмотра всех объектов коллекции, но модифицировать могут только принадлежащие им.<br />
Для идентификации пользователя отправлять логин и пароль с каждым запросом.<br />
Необходимо реализовать многопоточную обработку запросов.<br />

Для многопоточного чтения запросов использовать Cached thread pool<br />
Для многопотчной обработки полученного запроса использовать Cached thread pool<br />
Для многопоточной отправки ответа использовать создание нового потока (java.lang.Thread)<br />
Для синхронизации доступа к коллекции использовать синхронизацию чтения и записи с помощью java.util.concurrent.locks.ReadWriteLock<br />
