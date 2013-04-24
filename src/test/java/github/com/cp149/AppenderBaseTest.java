package github.com.cp149;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class AppenderBaseTest {
	//logback config file
	protected String LOGBACK_XML = "logback.xml";
	//log out put file
	protected String Logfile = "logback-";
	//file size of logfile before test
	private int sizeBeforTest = 0;
	
	protected org.slf4j.Logger logback = LoggerFactory.getLogger(this.getClass());	
	private LoggerContext lc;
	
	//test start time ,
	private long starttime;
	//actual full logfile name
	private String filename;
	
	//log per thread
	protected  int loglines=2000;

	public AppenderBaseTest() {
		super();
	}

	@BeforeClass(alwaysRun = true)
	public void initLogconfig() throws Exception {
		filename = "logs/" + Logfile + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
		File file = new File(filename);
		if (file.exists()) {
			sizeBeforTest = countlines(filename);
		}
		System.out.println(file.getAbsolutePath());
		lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		configureLC(lc, this.getClass().getResource("").getFile() + File.separator + LOGBACK_XML);
		//test log is ok
		logback.debug("init config" + LOGBACK_XML);
		TimeUnit.SECONDS.sleep(1);
		starttime =new Date().getTime();

	}

	private void configureLC(LoggerContext lc, String configFile) throws JoranException {
		JoranConfigurator configurator = new JoranConfigurator();
		lc.reset();
		configurator.setContext(lc);
		configurator.doConfigure(configFile);
	}

	@AfterClass()
	public void afteclass() throws IOException {
		System.out.println("run times"+(new Date().getTime() -starttime));

//		file = new File("logs/logback-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log");
		Assert.assertEquals(countlines(filename)-sizeBeforTest, 100 * loglines + 1);

	}

	public int countlines(String filename) throws IOException {
		LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(filename)));
		lnr.skip(Long.MAX_VALUE);
		lnr.close();
		return(lnr.getLineNumber());
	}
}