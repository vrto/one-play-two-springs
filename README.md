# one-play-two-springs
Prototype application for demonstrating how components can be reused in multiple Spring contexts in a web app using Play Framework

### Usage
```
activator clean compile
activator run

curl -XGET http://localhost:9000/read <== goes to slave DB
curl -XPOST http://localhost:9000/create <== goes to master DB
curl -XGET http://localhost:9000/write-crash <== attempts to write to slave DB, crashes
```

### Components
- two data sources (two databases) with *the same schema but different data* - master/slave approach
- Separate `GetController` and `PostController`
- one prototype-scoped `Precondition` - every controller has its *own* instance pointing to the right database
- one Repository class having one singleton per each spring context (we print them out to see they have different memory addresses)
- Queries component (ReadingService)
- Commands component (WritingService)
- ability to **throw exception** if incorrect repository function is being used with wrong database (eg. can't modify data for slave DB)

Two spring contexts:
```
context-reading: {
  getController, precondition, repo, queries
}
context-writing: {
  postController, precondition, repo, commands
}
```

### Implementation notes
- `ReadConfig` - Spring config with read-only beans. Excludes any write-related beans.
- `WriteConfig` - Spring config with write-only beans. Excludes any read-related beans.
- `stereotypes` - package with stereotype annotations that can be used for identifying right components during Spring's auto-scanning process
- `SlaveDatabaseGuardian` - an aspect making sure that no modifying operation on slave instance is allowed
- `Global` - creates **two** spring contexts, serves controller from the right one