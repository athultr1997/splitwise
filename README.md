# Splitwise

* Separate domain and persistence models
* Payment outlives the split that created it. A payment entity has a life on its own without the split
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