# one-play-two-springs
Prototype application for demonstrating how components can be reused in multiple Spring contexts in a web app using Play Framework

### Requirements
- two data sources (two databases) with *the same schema* - master/slave approach
- Separate `GetController` and `PostController`
- one prototype-scoped `Precondition` shared in controllers using correct database connection
- one Repository class having one singleton per each spring context
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