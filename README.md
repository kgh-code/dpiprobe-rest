# dpiprobe-server

An example REST API controller written in spring-boot with minimal dependencies. The application implemments a DPI listing API for a device monitoring company. Given the API, a client can query the DPI levels for a client, and offie and individual devices - with  options.

## Prerequisites

* This application was writen ussing InteliJ IDE.
* This application was written using an open source JDK (Open JDK VM Correto 11).
* This application saves data to an instance of mmongodb (no password is set, runnning on the standard port).
* This application uses Postman to test API calls.
* This application uses Maven for life-cyle management and depenndency management.
* This appliication is compatible with Java SE 8.
* This application ingests sample data from a CSV file device_performance_index.csv in the build target.

## Requirements

1. calculate the xmin and xmax for each signal metric. At present, 7 metrics provided.
1.1. calculte the xmin and xmax using the sample data population.
1.2. calculate the xmin and xmax to 2nd percentile and 98th percentile.
1.3. store these values against the signal metrics to be used in the geometric distribution function.
1.4. create a service layer that consumes the sample data for this requirement.



## Installation for Postman, Mongo, dpiprobe-server

A linux OS was used to install the application. I assume you have an IDE and a JDK installed.For Postman and Mongo, you can follow these steps. 

1. Install a community version of mongo locally: https://www.mongodb.com/try/download/community
2. Install Postman locally: https://www.postman.com/downloads/ 
3. Clone this project in your deployment directory

```bash

~/dev git clone git@github.com:kgh-code/dpiprobe-server.git

```

After you have checked out the project, you need to build it using maven (I placed mine in ~/dev):

```bash

~/dev cd dpiprobe-server

~/dev/dpiprobe-server/mvn clean install

The 'mvn clean install' performs an initial ingest of the sample data in the file device_performance_index.csv
The 'mvn clean install' deletes collections in a mongo database dpisignals, dpibase, dpitreatedsignals


```


## Usage - Spring



* Start the application. It's a spring boot server using an embedded catalina engine running on 8080:

```bash

~/dev/dpiprobe-server/mvn spring-boot:run

```
Note the progress of the spring boot startup log and ensure the tests run correctly.

* Open a 'browser of your choice' and paste in the swagger URL: http://localhost:8080/swagger-ui/
* Follow the instructions in the swagger UI to perform the API functions:

![an image of swagger used to acccess the API for dpiprobe-serverrest by kevin hamid.](https://github.com/kgh-code/dpiprobe-server/docs/swagger-image.png)


## Usage - Mongo

* Ensure your mongodb is running - Mine runs as a service:

```bash

~/dev/dpiprobe-server/systemctl status mongodb

```

<pre> 

 mongodb.service - An object/document-oriented database
   Loaded: loaded (/lib/systemd/system/mongodb.service; enabled; vendor preset: enabled)
   Active: <font color="#859900"><b>active (running)</b></font> since Mon 2021-02-08 06:27:17 CEST; 1h 11min ago
     Docs: man:mongod(1)
 Main PID: 867 (mongod)
    Tasks: 24 (limit: 4915)
   Memory: 187.2M
   CGroup: /system.slice/mongodb.service
           └─867 /usr/bin/mongod --unixSocketPrefix=/run/mongodb --config /etc/mongodb.conf

Feb 08 10:27:17 kevin-notebook systemd[1]: Started An object/document-oriented database.

</pre>


Its always good to inspect the raw data in mongo after creating records via the API. Some commands to help are:

<pre>mongo
MongoDB shell version v3.6.8
connecting to: mongodb://127.0.0.1:27017
Implicit session: session { &quot;id&quot; : UUID(&quot;c525e9d2-3be8-4d72-a812-b26f5342594f&quot;) }
MongoDB server version: 3.6.8
Server has startup warnings: 
2020-09-14T06:27:17.510+0200 I STORAGE  [initandlisten] 
2020-09-14T06:27:17.510+0200 I STORAGE  [initandlisten] ** WARNING: Using the XFS filesystem is strongly recommended with the WiredTiger storage engine
2020-09-14T06:27:17.510+0200 I STORAGE  [initandlisten] **          See http://dochub.mongodb.org/core/prodnotes-filesystem
2020-09-14T06:27:19.154+0200 I CONTROL  [initandlisten] 
2020-09-14T06:27:19.154+0200 I CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
2020-09-14T06:27:19.154+0200 I CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
2020-09-14T06:27:19.154+0200 I CONTROL  [initandlisten] 
&gt; use dpiprobe
switched to db dpiprobe
&gt; show collections
dpisignals
dpitreatedsignals
dpibase
&gt; 
&gt; db.dpibasevalues.find().pretty()
> db.dpibase.find()
{ "_id" : ObjectId("6021adba28c3e323c57c4200"), "createdDate" : ISODate("2021-02-08T21:31:38.882Z"), "metricName" : "memoryUsage", "minValue" : 0.02, "maxValue" : 0.98, "_class" : "com.kgh.dpiprobe.models.Dpibasevalues" }
{ "_id" : ObjectId("6021adbb28c3e323c57c4201"), "createdDate" : ISODate("2021-02-08T21:31:39.005Z"), "metricName" : "cPUUsage", "minValue" : 0.008, "maxValue" : 0.97, "_class" : "com.kgh.dpiprobe.models.Dpibasevalues" }
{ "_id" : ObjectId("6021adbb28c3e323c57c4202"), "createdDate" : ISODate("2021-02-08T21:31:39.136Z"), "metricName" : "logonDuration", "minValue" : 3511, "maxValue" : 119275, "_class" : "com.kgh.dpiprobe.models.Dpibasevalues" }
{ "_id" : ObjectId("6021adbb28c3e323c57c4203"), "createdDate" : ISODate("2021-02-08T21:31:39.270Z"), "metricName" : "bootSpeed", "minValue" : 4446, "maxValue" : 121949, "_class" : "com.kgh.dpiprobe.models.Dpibasevalues" }
{ "_id" : ObjectId("6021adbb28c3e323c57c4204"), "createdDate" : ISODate("2021-02-08T21:31:39.300Z"), "metricName" : "hardResetCount", "minValue" : 1, "maxValue" : 41, "_class" : "com.kgh.dpiprobe.models.Dpibasevalues" }
{ "_id" : ObjectId("6021adbb28c3e323c57c4205"), "createdDate" : ISODate("2021-02-08T21:31:39.330Z"), "metricName" : "bSODCount", "minValue" : 0, "maxValue" : 8, "_class" : "com.kgh.dpiprobe.models.Dpibasevalues" }
{ "_id" : ObjectId("6021adbb28c3e323c57c4206"), "createdDate" : ISODate("2021-02-08T21:31:39.493Z"), "metricName" : "systemFreeSpace", "minValue" : NumberLong(1972342784), "maxValue" : NumberLong("447262482432"), "_class" : "com.kgh.dpiprobe.models.Dpibasevalues" }

&gt;
</pre>

## Unsure

* I used the systemFreeSpace to use in the DPI calculation, the formula is unclear if its to be used or not 1 to 6, but 7 metrics available

## Improvements


* using mongo for ease of use and also the raw signal data is 'throw away', the Dpitreatedsignals and the Dpibase should probably be in a transactional SQL DB if needed
* There should be a check to see how often a device sends data IE average = 6 (every 10 mins) x 24, if you dont receive on average this number of signals in a day, then alert.
* I assume the raw signal data has been validated upstream.
* The consumer should poll for updates of raw signal data every now and then.
* I assume access to the Rest API validates types before serving data, so I havent implemented type checking at the server level.
* The DPI ingestion 'simulates' a consumer that should reside on another server with access to a pub/sub service.
* I hard coded two templae methods for ingesting the data, calculating the base percentiles and calculating the DPI, limited isolation.
* No logging implemented, some console logs and error logs
* Testing is pretty basic - just testing the controller httpstatus messages, I mocked the service call, but still need to mock the DAO's
* Persistence should probably be via a que, but for this example the service is on the same JVM.
* Candidate for containerisation, but no deployment containers identified.
* Code style, lazy use of autoboxing for a fast delivery.
* Code style, comments are basic, no javadocs, some camels have one hump, some camels have two humps, some are just horses.
## License
Nada
