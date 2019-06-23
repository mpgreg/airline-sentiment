#Using static data from Kaggle
#https://www.kaggle.com/crowdflower/twitter-airline-sentiment/downloads/Tweets.csv
#
#Will create a CDSW Job to ingest nightly and call data cleaning spark job.

!hdfs dfs -mkdir /tmp/airline-sentiment
!hdfs dfs -chmod 777 /tmp/airline-sentiment
!curl https://s3.console.aws.amazon.com/s3/object/ml-field/demo/airline-sentiment/Tweets.csv | hdfs dfs -put - Tweets-`date-I`.csv

