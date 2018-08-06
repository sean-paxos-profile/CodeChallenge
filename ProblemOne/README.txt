This is a description of my solution of problem one.

I created a simple Java servlet overriding doPost() method to response with the SHA256 encrypted value and store the original value in server (external library used: Gson 2.8.5).
The POST request will make the servlet create a file with SHA256 encrypted value as filename on server.
The GET request will make the servlet response with the text stored in the filename specified (if it does not exist, will response 404). The text is formatted as JSON.

This is a small service running on one server only. The disk size, memory and CPU power of this single server is the hard limit. 
First, as number of files increases, long lookup time of those files in messages/ can be a nightmare. We can use cache on this issue. 
But things can go even worse if the number of messages continue increasing:
As the number of files grows, the size of the messages/ directory can easily go beyond the maximum disk size.
Larger number of requests will result in larger number of threads used by the servlet instance, the memory and CPU power can be a bottleneck in this case.
To resolve this problem, we can use a distributed storage system. 
For storage, NoSQL is designed for this typical key-value paire storage.
For memory and CPU power, we can have an additional metadata storage to guide the routing service.

The .war file of this servle is: 
war_file/PaxosProblemOne.war

The source code is:
source_code/PaxosProblemOne/src/com/paxos/problemone/SimpleServlet.java

I have setup Tomcat in an EC2 instance to host the webapp.

The public DNS is:
ec2-54-71-58-26.us-west-2.compute.amazonaws.com

The port is:
8089

As described in the problem description, we can use curl to send POST or GET requests to the endpoint.

Example:
Seans-MacBook-Pro:messages sean$ curl -X POST -H "Content-Type: application/json" -d '{"message": "foo"}' http://ec2-54-71-58-26.us-west-2.compute.amazonaws.com:8089/messages
{
  "digest": "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae"
}

Seans-MacBook-Pro:messages sean$ curl http://ec2-54-71-58-26.us-west-2.compute.amazonaws.com:8089/messages/2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae
{
  "message": "foo"
}




