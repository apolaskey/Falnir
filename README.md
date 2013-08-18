Falnir
======

Classic Mud using Java and Apache MINA

To Build
======

mvn eclipse:eclipse *If your using eclipse
mvn clean install

Maven Problems? Use the below .m2/settings.xml
======
 <settings>
   <profiles>
     <profile>
       <id>falnir</id>
       <repositories>
         <repository>  
             <id>central</id>  
             <url>http://repo1.maven.org/maven2/</url>  
         </repository>  
       </repositories>
     </profile>
   </profiles>
   <activeProfiles>
     <activeProfile>falnir</activeProfile>
   </activeProfiles> 
 </settings>

Dependencies [Project is Maven Compatible]
======

Java 1.7 SDK - http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-javase6-419409.html#jdk-6u45-oth-JPR

Apache MINA - http://mina.apache.org/

Log4j 1.2 - http://logging.apache.org/log4j/1.2/

SLF4J - http://www.slf4j.org/

Google Guice - https://code.google.com/p/google-guice/

Google Guava - https://code.google.com/p/guava-libraries/

Hibernate - http://www.hibernate.org/
