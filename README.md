jactor-logger
=============
Logger is very important,there are two issue in logger system,first is performance,logger should not delay the main program's performance
second is data lost,because logger is in same jvm,so when jvm is crash,logger messages may lost if the writing speed is slow.  


logback appender use jactor2,another Async appender

on my pc

RollingFile with ch.qos.logback.classic.AsyncAppender test

 
FileAppenderTest total  time=105604 total lines=5000500

and this two appender is
 

Jactor2AppenderTest total  time=30628 total lines=5000500


 DisruptorTest total  time=41051 total lines=5000500

and The most import is logging is asynchronous,which means main program can run much faster

 

easy to use,such as add append in logback.xml
just down https://raw.github.com/cp149/jactor-logger/master/dist/jactor-logger-0.11.0.jar


down jactor2 from http://laforge49.github.io/JActor2/

```html
<appender name="ASYNC" class="github.com.cp149.jactor2.Jactor2Appender">
		<appender-ref ref="STDOUT" />
</appender>
```

if use disruptor, downlaod disruptor from maven
```html	
<appender name="ASYNCFile" class="github.com.cp149.disruptor.DisruptorAppender">		
		<appender-ref ref="STDOUT" />
	</appender>
```
Unlike log4j2 use Fast File Appenders,my three  Appenders use normal ch.qos.logback.core.FileAppende
The  code detail is at http://logging.apache.org/log4j/2.0/manual/async.html#Performance

if you wish test it,run org.apache.logging.log4j.core.async.perftest.PerfTestDriver at src/test/java

using maven clean test to see the unit test result
```html

Done. Total duration: 192.6 minutes

Ranking:
1. Logback: Async disruptor Appender (single thread): throughput: 9,993,043 ops/sec. latency(ns): avg=3532.4 99% < 19660.8 99.99% < 16829644.8 (285431 samples)
2. Logback: Async jactor2 Appender (single thread): throughput: 9,001,575 ops/sec. latency(ns): avg=1210.0 99% < 2048.0 99.99% < 65536.0 (21476524 samples)
3. Logback: Async jactor Appender (single thread): throughput: 8,482,989 ops/sec. latency(ns): avg=1201.4 99% < 2048.0 99.99% < 65536.0 (22639435 samples)
4. Log4j2: Loggers all async (single thread): throughput: 8,394,794 ops/sec. latency(ns): avg=8631.6 99% < 13107.2 99.99% < 26869760.0 (610686 samples)
5. Log4j2: Async Appender (single thread): throughput: 7,408,055 ops/sec. latency(ns): avg=864.6 99% < 4096.0 99.99% < 131072.0 (478795 samples)
6. Logback: Async disruptor Appender (2 threads): throughput: 4,103,790 ops/sec. latency(ns): avg=884.1 99% < 4505.6 99.99% < 65536.0 (4500675 samples)
7. Log4j2: Async Appender (2 threads): throughput: 3,829,409 ops/sec. latency(ns): avg=685.1 99% < 4096.0 99.99% < 65536.0 (2034956 samples)
8. Log4j2: Async Appender (4 threads): throughput: 2,658,120 ops/sec. latency(ns): avg=607.9 99% < 2048.0 99.99% < 239206.4 (19951913 samples)
9. Log4j2: Loggers all  Sync (4 threads): throughput: 2,480,657 ops/sec. latency(ns): avg=4324.3 99% < 7372.8 99.99% < 13841203.2 (6575838 samples)
10. Log4j2: Loggers all  Sync (2 threads): throughput: 2,430,352 ops/sec. latency(ns): avg=651.3 99% < 2457.6 99.99% < 65536.0 (8625233 samples)
11. Logback: Async disruptor Appender (4 threads): throughput: 2,301,079 ops/sec. latency(ns): avg=7799.6 99% < 4300.8 99.99% < 24169676.8 (7478483 samples)
12. Logback: Async jactor2 Appender (2 threads): throughput: 1,739,406 ops/sec. latency(ns): avg=931.2 99% < 2048.0 99.99% < 42598.4 (48717475 samples)
13. Logback: Async jactor Appender (2 threads): throughput: 1,486,585 ops/sec. latency(ns): avg=930.7 99% < 2048.0 99.99% < 36044.8 (49685633 samples)
14. Logback: Async jactor2 Appender (4 threads): throughput: 1,340,977 ops/sec. latency(ns): avg=1253.6 99% < 1587.2 99.99% < 80281.6 (87902519 samples)
15. Logback: Async jactor Appender (4 threads): throughput: 1,159,258 ops/sec. latency(ns): avg=1020.1 99% < 1587.2 99.99% < 77004.8 (99564074 samples)

Done. Total duration: 162.0 minutes
```

