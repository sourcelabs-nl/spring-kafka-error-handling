# spring-kafka-error-handling

Code for article published at https://www.sourcelabs.nl/2022/03/08/error-handling-with-apache-kafka/


The code samples are divided in three retry mechanisms. These retry mechanisms use the following Spring profiles: Fixed, exponential and non-blocking. 

# How to run the code

1. go to the root directory of the code. 
2. execute the following command: ```docker-compose up -d ```
3. wait for the containers to be brought up, go to http://localhost:8080 to verify AKHQ is running. 
4. set the active profile in the run/debug configuration in intelliJ. (fixed, exponential or non-blocking). 
5. run the application
6. Post a null message body. 
7. You should see the message come in with the log message processing message: [your message] after which the message is retried. How it is retried depends on which profile you chose. See the blog for further details on the retry mechanisms. 

