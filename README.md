# VideosDB

## Description

![Project logo](https://blog.timescale.com/content/images/2019/02/_IoT---Relational-Database.png)
The purpose of this project was to implement a platform that simulates actions that users can perform on movies and TV shows: ratings, mark seen videos, recommendations etc.
It is one the assignments from the [PUB OOP course](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema).

The *entitites* that have been modelled are as follows: 
Video
 * Of 2 types: movies and tv shows. The difference between the two is that tv shows contain seasons.
 * Each video has in common a title, release date, one or more genres 
 * Seasons of a tv show contain a number, length of the entire season and a rating
 * Movies contain length and a rating

User
 * has 2 categories: standard and premium
 * has favorite and a history of seen videos.

Data for these entities is loaded from JSON files. Users can perform the following types of actions: *commands*, *queries* and *recommendations*

*Workflow of the application*
 * Data is parsed from the JSON files into objects
 * Actions (orders, queries or recommendations) are received sequentially and executed as they are received, their result having an effect on the Repository
 * After performing an action, its result is displayed in the output JSON file
 * At the end of all actions, the execution of the program ends

## Commands

These allow the user to perform 3 types of actions:

*Favorite* - adds a video to a user's favorite videos list, if the user has seen the video.

*View* - marks a video as seen. If the user has already seen the video, the number of views of that video by the user increases.
When viewing a series, all seasons are considered to be marked as seen.

*Rating* - gives a rating to a video that was already seen (for tv series, the rating applies for each season)
 * The rating can be done once by a user. For tv shows, it is done once per season.

## Queries

These are global searches done by users. A query contains the type of information sought: actor / video / user, sorting criteria and various parameters.
Queries also contain the sort order as a parameter, whether the sort is ascending or descending. The secondary sorting criterion is the descending / ascending alphabetical order according to the order of the first criterion.

*For actors:*

*Average* - the first N actors (N given in the query) sorted by the average ratings of the movies and series in which they played. The average is arithmetic and is calculated for all videos (with a total rating other than 0) in which they played.

*Awards* - all actors with the prizes mentioned in the query. The actors must have received all the required awards, not just some of them. The sorting will be done according to the number of prizes of each actor, according to the order criteria specified in the input.

*Filter Description* - filters the actors from their description using the keywords given in the query.
Sorting will be done in alphabetical order of the names of the actors, and the order criteria is specified in the input.

*For videos:*

*Rating* - the first N videos sorted by rating. For series, the rating is calculated as the average of all seasons, provided that at least one season has a rating, those without a rating having 0 as their score. Videos that have not received a rating will not be taken into account.

*Favorite* - the first N videos sorted by the number of appearances in users' favorite video lists

*Longest* - the first N videos sorted by duration. For series, the sum of the seasons is considered.

*Most Viewed* - the first N videos sorted by number of views. Each user also has a data structure that takes into account how many times he has seen a video. In the case of seasons, the entire series is taken as the number of views, not independently by seasons.

*For users*

*Number of ratings* - first N users sorted by the number of ratings they gave (practically the most active users)

## Recommendations 

For every user:

*Standard* - return the first unseen video by the user from the database

*Best Unseen* - returns the best video unseen by that user. All videos are sorted in descending order by rating and the first of them is chosen, the second criterion being the order of appearance in the database. 

For premium users: 

*Popular* - returns the first unseen video of the most popular genre (videos are browsed according to the order in the database). The popularity of the genre is calculated from the total number of views of videos of that genre. If all the videos of the most popular genre were seen by the user, then it proceede to the next most popular genre. The process resumes until the first video that was not viewed in the database is found.

*Favorite* - returns the video that is most common in the list of favorites (not to be seen by the user for whom the recommendation applies) of all users, the second criterion being the order of appearance in the database. The video must exist in at least one list of users' favorite videos.

*Search* - returns all videos not seen by the user of a certain genre, given as an input filter. Sorting is done in ascending order by rating, the second criterion being the name.

In recommendations (without Search Recommendation), the second sorting criterion is the order in the database (ie the order in which it appears in the "movies" / "shows" field in the database). Only in the Search Recommendation the second sorting criterion is the name.

### My output

![Output](https://i.ibb.co/S6Yj1ss/Capture.png)

    


