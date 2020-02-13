# httpAndNodeJSPractice

This is a practice of http request and response using Node.js and Express.

## IDEA

This is a program used during emergency situation that records and allows querying of a person's most recent status. During emergency, a person can key in his friends' or relatives' IDs to check if they are safe or missing. The data of people is stored on a server, and we are writing a program to use http request to request the information about people's status.

The offline java program on the server will query for the list fo IDs stored on the server. There is a wrapper class called "status processor" that serves as a cache: when a person's data retrieved from the server is no longer found, the cache will return the last updated status and time of that person.
