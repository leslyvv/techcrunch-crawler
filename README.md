# techcrunch-crawler
A web crawler that crawls on different web pages within the website TechCrunch. The crawler incorporates multi-threading to process multiple web pages from different categories simultaneously. 
Furthermore, these webpages’ information is scraped using Jsoup, a Java library that works with real-world HTML. The
title, author, URL, content, and data are pulled from each article. The output is formatted into a CSV file using the OpenCSV library.

<sub1>Necessary JAR/Dependencies:</sub1>
- Jsoup
- OpenCSV

Within the main method, you can change any of the four links to fit the categories on TechCrunch
you would like to scrape for the article's information.

References: https://www.youtube.com/watch?v=KZK5rnxBWcU&ab_channel=CodingWithTim

Output:
![image](https://github.com/leslyvv/techcrunch-crawler/assets/117410261/fd5d4af1-c5f6-4385-bb51-f9c556f226c3)
![image](https://github.com/leslyvv/techcrunch-crawler/assets/117410261/dda4962d-d155-487c-b3c7-9da399f46bb0)


