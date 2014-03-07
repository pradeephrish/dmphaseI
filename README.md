Snopes.com Web Scraper

This project is intended for collecting data from snopes.com 


To get started

Clone the project using git clone <Project URL>

You can import it using eclipse

Run main program from  com.dm.driver.Driver.java

You initialiaze array of urls to be parsed as follows, 


eg.  

	String sublinks[] = {"http://www.snopes.com/music/artists/311.asp","http://www.snopes.com/disney/films/sots.asp"}
	
This main funciton will parse all the urls and it will save it to data/file.txt

Main method is self explanatory



********************************Future Goals***********************************************

-It should be able to fetch and download data from single url.
-Code doesn't handle url redirections, therefore some urls needs to be hardcoded
-Proper use of logger ( log4j or something else ) 


For Any Queries, Mail us at 

ppchaudh@asu.edu
ravindra.dingankar@asu.edu








