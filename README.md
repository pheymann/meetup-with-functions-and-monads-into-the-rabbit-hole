# With Functions and Monads into the rabbit hole
 - [Follow me](https://pheymann.github.io/meetup-with-functions-and-monads-into-the-rabbit-hole/) to the Slideshow
 - And [here](https://www.meetup.com/de-DE/Scala-Hamburg/events/240727416/?comment_table_id=482166437&comment_table_name=event_comment) you get the meetup description
  
### In Short
In this talk we will start our journey into the realm of Functional Programming with Scala and Cats beginning with a short introduction into the concept of: 

 - Functions and Data 

and continue by visiting: 

 - Functors and Monads.

This journey is meant to be a gateway to become Alice in FP-Wonderland and create a knowledge foundation which can be used to discover more advanced concepts. No prior knowledge of Functional Programming is necessary to follow this talk and I will try my best to provide my fellow travellers with real-world examples which show possible use cases and why using FP in a day-to-day business makes sense.

### Run the Example Code
If you want to run the test server execute:

```
sbt "run --filename data/user_db.csv"
```

A requests looks likes this:

```
curl -v -X GET "localhost:8080/recommendations/user/0"
```
