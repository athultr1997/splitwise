# Splitwise

## Setup

### Dependencies

* **java**: openjdk version "1.8.0_265"
* **maven**: 3.6.3

### Building and Running

1. Clone the repo to your local machine.
2. Go to the top level of the repository.
3. Run the following command for building the jar file:

```
 >> mvn clean install -DskipTests
```

4. Run the following command to start the application:

```
>> java -jar target/splitwise-0.0.1-SNAPSHOT.jar
```

Else you can also use the following command to start the application:

```
>> mvn spring-boot:run
```

5. The server will be listening on port number `8080` by default.

## Design
### Objects


## Features

* Separate domain and persistence models
* Payment outlives the split that created it. A payment entity has a life on its own without the
  split
* BigDecimal used
* Serializable used
* Interceptor converts all exceptions to responses
* Greedy algorithm used
* Amount credited to user is positive
* Do not have to show balances that are settled with a user. Only show the ones that are lend or
  owed.
    * Created_at should be in request since offline working.
* Even if owes in balance shows as settled

# Future Scope

* Edit Split
* Delete Split
* Send Reminders
* Pay using payment gateway
* Multicurrency support