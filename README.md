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

### Postman Collection

The postman collection for interacting with the server can be found
at: [postman collection](https://www.getpostman.com/collections/764056a2e67484d5e178)

### Database

H2 database is used currently. It is configured to act as an in-memory database by default. You can
view the tables and schema by visiting the following url after the application has started:

```
http://localhost:8080/h2-ui
```

The details to connect with the H2 database through the H2 UI are (some of them maybe filled by
default):

```
Saved Settings: Generic H2 (Embedded)
Setting Name: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:splitwisedb
User Name: athul
Password: password
```

### Scripts

A script is run during startup that populates the database with some users for easy
testing. [link](https://github.com/athultr1997/splitwise/blob/main/src/main/java/com/setu/splitwise/scripts/PopulateUserService.java)

## Design

### Data Model

![alt text](https://github.com/athultr1997/splitwise/blob/main/data_model.png)

## Features

### Functional Features

* create a split among a set of users
    * splitting the amount equally
    * splitting the total amount by amounts specified
* get details of a split
* settle outstanding amount
* see overview of the users splits and settlements
* see the total balance amounts the user has to pay to or get from other users

### Non-Functional Features

* Settling the amount between a set of people who have borrowed money is one of the central features
  of Splitwise. This is an NP-hard problem, hence the best we can come up with is a greedy solution.
  The greedy algorithm is as follows:

```
1. Get all the amount paid by each member of the group of users and the total amount owed.
2. Subtract the amount paid by the amount owed to get the amount to be settled for each user.
3. From the list of users find the maximum debiter (maxDeb) and the maximum creditor (maxCred).The maximum creditor
has the maximum amount to settle and the maximum debitor has the minimum amount to settle which
were calculated in the last step.
4. Pay as much amount as possible from maxDeb to maxCred till one of them becomes settled. This is the greedy choice.
(i.e., settled amount becomes zero) (This will result in the creation of a bill that has to be settled in the code).
5. Repeat steps 3 and 4 till all the amounts are settled.
```

### Other Features

* In splitwise the payment outlives the split that was used for created it. A payment entity has a
  life on its own without the split. For example, if we create a split and settle it and then delete
  the split, the payment won't be deleted. It will still be present.
* BigDecimal is used throughout the code to represent amounts to minimize floating point and
  rounding errors.
* Serializable is used extensively to resolve des/ser issues.
* An Interceptor is used which intercepts all exceptions, prints a log and returns an error
  response. Therefore, we do not have to print a log when an exception occurs.
* Amount credited to the user is treated positive throughout the code.
* In Splitwise, the balances that are settled with a user are not showed. Only the ones that are
  lent or owed is showed.
* Creation times and updation times should be sent by the clients to facilitate offline operations
  by the client.
* Even if owes in balance shows as settled.

# Future Scope

* Edit Split
* Delete Split
* Send Reminders
* Pay using payment gateway
* Multicurrency support
* Audit support to track changes